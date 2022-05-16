package com.test.meta.util;

import java.time.format.DateTimeFormatter;

public class MetaDateUtil {

    public static final String pattern = "MM/dd/yyyy";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);

}
