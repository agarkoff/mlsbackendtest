package ru.misterparser.mlsbackendtest.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import ru.misterparser.mlsbackendtest.PartServlet;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

@Accessors(fluent = true)
@Data
@AllArgsConstructor
public class PartFilter {

    private String number;
    private String name;
    private String vendor;
    private Integer qty;
    private Date afterShipped;
    private Date beforeShipped;
    private Date afterReceived;
    private Date beforeReceived;
    private String sort;
    private String order;

    public PartFilter(HttpServletRequest req) throws ParseException {
        number = req.getParameter("number");
        name = req.getParameter("name");
        vendor = req.getParameter("vendor");
        String qtyText = req.getParameter("qty");
        qty = null;
        if (NumberUtils.isParsable(qtyText)) {
            qty = Integer.parseInt(qtyText);
        }
        String afterShippedText = req.getParameter("afterShippedText");
        String beforeShippedText = req.getParameter("beforeShippedText");
        String afterReceivedText = req.getParameter("afterReceivedText");
        String beforeReceivedText = req.getParameter("beforeReceivedText");
        afterShipped = parseDate(afterShippedText);
        beforeShipped = parseDate(beforeShippedText);
        afterReceived = parseDate(afterReceivedText);
        beforeReceived = parseDate(beforeReceivedText);
        sort = req.getParameter("sort");
        order = req.getParameter("order");
    }

    private static Date parseDate(String text) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return DateUtils.parseDate(text, PartServlet.DATE_PATTERN);
    }
}
