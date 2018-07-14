package ru.misterparser.mlsbackendtest.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import ru.misterparser.mlsbackendtest.domain.MlsPart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

@Slf4j
public class PartPreparedStatementBuilder {

    private final PartFilter partFilter;
    private final Connection connection;

    private final static String BASE_SQL = "SELECT number, name, vendor, qty, shipped, received FROM parts";

    private String sql;
    private ArrayListValuedHashMap<String, Object> wheres = new ArrayListValuedHashMap<>();

    public PartPreparedStatementBuilder(PartFilter partFilter, Connection connection) {
        this.partFilter = partFilter;
        this.connection = connection;
    }

    public PreparedStatement build() throws SQLException {
        sql = BASE_SQL;
        wheres.clear();
        parseFilter();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        fill(preparedStatement);
        return preparedStatement;
    }

    private void parseFilter() {
        if (StringUtils.isNotBlank(partFilter.number())) {
            wheres.put("number LIKE ?", partFilter.number());
        }
        if (StringUtils.isNotBlank(partFilter.name())) {
            wheres.put("name LIKE ?", partFilter.name());
        }
        if (StringUtils.isNotBlank(partFilter.vendor())) {
            wheres.put("vendor LIKE ?", partFilter.vendor());
        }
        if (partFilter.qty() != null) {
            wheres.put("qty >= ?", partFilter.qty());
        }
        if (partFilter.afterShipped() != null || partFilter.beforeShipped() != null) {
            wheres.put("daterange(?, ?, '[]') @> shipped", partFilter.afterShipped());
            wheres.put("daterange(?, ?, '[]') @> shipped", partFilter.beforeShipped());
        }
        if (partFilter.afterReceived() != null || partFilter.beforeReceived() != null) {
            wheres.put("daterange(?, ?, '[]') @> received", partFilter.afterReceived());
            wheres.put("daterange(?, ?, '[]') @> received", partFilter.beforeReceived());
        }
        if (wheres.size() > 0) {
            sql += " WHERE ";
            sql += StringUtils.join(wheres.keySet(), " AND ");
        }
        if (partFilter.sort() != null && MlsPart.checkColumn(partFilter.sort())) {
            sql += " ORDER BY " + partFilter.sort();
            if (partFilter.order() != null &&
                    (StringUtils.equalsIgnoreCase(partFilter.order(), "ASC") || StringUtils.equalsIgnoreCase(partFilter.order(), "DESC"))) {
                sql += " " + partFilter.order();
            }
        }
        log.debug("SQL statement: {}", sql);
    }

    private void fill(PreparedStatement preparedStatement) throws SQLException {
        int parameterIndex = 1;
        for (String where : wheres.keySet()) {
            for (Object o : wheres.get(where)) {
                if (o instanceof String) {
                    preparedStatement.setString(parameterIndex++, "%" + o + "%");
                } else if (o instanceof Integer) {
                    preparedStatement.setInt(parameterIndex++, (Integer) o);
                } else if (o instanceof Date) {
                    preparedStatement.setDate(parameterIndex++, new java.sql.Date(((Date) o).getTime()));
                } else if (o == null &&
                        (StringUtils.endsWithIgnoreCase(where, "shipped") || StringUtils.endsWithIgnoreCase(where, "received"))) {
                    preparedStatement.setNull(parameterIndex++, Types.DATE);
                }
            }
        }
    }
}
