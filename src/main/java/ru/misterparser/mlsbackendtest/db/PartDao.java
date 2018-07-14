package ru.misterparser.mlsbackendtest.db;

import lombok.extern.slf4j.Slf4j;
import ru.misterparser.mlsbackendtest.domain.MlsPart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class PartDao {

    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public PartDao(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPassword = jdbcPassword;
    }

    private void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            log.debug("Connect to database...");
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        }
    }

    private void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            log.debug("Disconnect from database...");
            jdbcConnection.close();
        }
    }

    public List<MlsPart> findByCriteria(PartFilter partFilter) throws SQLException {
        try {
            log.debug("findByCriteria with filter {}", partFilter);
            connect();
            PreparedStatement preparedStatement = new PartPreparedStatementBuilder(partFilter, jdbcConnection).build();
            List<MlsPart> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MlsPart part = build(resultSet);
                result.add(part);
            }
            resultSet.close();
            preparedStatement.close();
            log.debug("Fetched {} rows", result.size());
            return result;
        } finally {
            disconnect();
        }
    }

    private static MlsPart build(ResultSet resultSet) throws SQLException {
        String number = resultSet.getString("number");
        String name = resultSet.getString("name");
        String vendor = resultSet.getString("vendor");
        int qty = resultSet.getInt("qty");
        Date shipped = resultSet.getDate("shipped");
        Date received = resultSet.getDate("received");
        return new MlsPart(number, name, vendor, qty, shipped, received);
    }
}
