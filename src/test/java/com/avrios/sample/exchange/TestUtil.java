package com.avrios.sample.exchange;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;

public class TestUtil {

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String readResourceFile(final String fileName) throws Exception {
        try (final InputStream stream = TestUtil.class.getResourceAsStream(fileName)) {
            return IOUtils.toString(stream, "UTF-8");
        }
    }

    public static Date parseDate(String date){
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
