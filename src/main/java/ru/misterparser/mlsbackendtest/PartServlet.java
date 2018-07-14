package ru.misterparser.mlsbackendtest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import ru.misterparser.mlsbackendtest.db.PartDao;
import ru.misterparser.mlsbackendtest.db.PartFilter;
import ru.misterparser.mlsbackendtest.domain.MlsPart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
public class PartServlet extends HttpServlet {

    public static final String DATE_PATTERN = "MMM dd, yyyy";

    private PartDao partDao;

    /**
     * Создает ссылку для заголовка колонки из всех параметров текущего запроса, за исключением флага form.
     */
    public static String buildHeaderUrl(PageContext pageContext, String column) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String r = request.getContextPath();
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            if (!StringUtils.equalsIgnoreCase(parameterName, "form")) {
                map.put(parameterName, parameterValue);
            }
        }
        map.put("sort", column);
        map.put("order", (String) pageContext.getRequest().getAttribute("order"));
        String q = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
        return r + "?" + q;
    }

    public void init() {
        try {
            log.debug("Servlet init...");
            Properties properties = new Properties();
            try (FileInputStream in = new FileInputStream(getServletContext().getInitParameter("database.properties"))) {
                properties.load(in);
            }
            String jdbcDriver = properties.getProperty("jdbc.driver");
            String jdbcUrl = properties.getProperty("jdbc.url");
            String jdbcUser = properties.getProperty("jdbc.user");
            String jdbcPassword = properties.getProperty("jdbc.password");
            partDao = new PartDao(jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword);
            log.debug("Set english locale...");
            Locale.setDefault(Locale.ENGLISH);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log.debug("doGet method started...");
            PartFilter partFilter = new PartFilter(req);
            List<MlsPart> parts = partDao.findByCriteria(partFilter);
            req.setAttribute("parts", parts);
            req.setAttribute("number", partFilter.number());
            req.setAttribute("name", partFilter.name());
            req.setAttribute("vendor", partFilter.vendor());
            req.setAttribute("qty", partFilter.qty());
            req.setAttribute("afterShippedText", formatDate(partFilter.afterShipped()));
            req.setAttribute("beforeShippedText", formatDate(partFilter.beforeShipped()));
            req.setAttribute("afterReceivedText", formatDate(partFilter.afterReceived()));
            req.setAttribute("beforeReceivedText", formatDate(partFilter.beforeReceived()));
            req.setAttribute("sort", partFilter.sort());
            boolean form = StringUtils.isNotBlank(req.getParameter("form"));
            // переключаем сортировку только если запрос пришёл не из формы
            if (!form) {
                if (StringUtils.equalsIgnoreCase(partFilter.order(), "ASC")) {
                    req.setAttribute("order", "DESC");
                } else {
                    req.setAttribute("order", "ASC");
                }
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/part_list.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException | ParseException e) {
            throw new ServletException(e);
        }
    }

    private static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, DATE_PATTERN);
    }
}
