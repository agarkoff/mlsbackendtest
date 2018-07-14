package ru.misterparser.mlsbackendtest.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "number")
@AllArgsConstructor
public class MlsPart {

    private String number;
    private String name;
    private String vendor;
    private int qty;
    private Date shipped;
    private Date received;

    public static boolean checkColumn(String sort) {
        return StringUtils.equals(sort, "number") ||
                StringUtils.equals(sort, "name") ||
                StringUtils.equals(sort, "vendor") ||
                StringUtils.equals(sort, "qty") ||
                StringUtils.equals(sort, "shipped") ||
                StringUtils.equals(sort, "received");
    }
}
