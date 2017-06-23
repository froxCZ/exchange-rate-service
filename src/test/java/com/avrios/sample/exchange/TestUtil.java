package com.avrios.sample.exchange;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class TestUtil {

    public static String readResourceFile(final String fileName) throws Exception {
        try (final InputStream stream = TestUtil.class.getResourceAsStream(fileName)) {
            return IOUtils.toString(stream, "UTF-8");
        }
    }
}
