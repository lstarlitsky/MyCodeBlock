package org.mit.utils;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

class MyTimeUtilsTest {

    @Test
    void isInTimeRange() throws ParseException {
        assert MyTimeUtils.isInTimeRange("13:12:00", "11:45:56", "24:00:00");
    }
}