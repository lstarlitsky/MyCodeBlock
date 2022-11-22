package org.mit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间工具类
 *
 * @author mit
 * @date 2022-11-17 23:43:83
 */
public class MyTimeUtils {
    /**
     * 判断给定的时间是否在时间范围内
     *
     * @param time      时间
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return boolean
     * @throws ParseException 解析异常
     */
    public static boolean isInTimeRange(String time, String beginTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = sdf.parse(time);
        Date beginDate = sdf.parse(beginTime);
        Date endDate = sdf.parse(endTime);
        return date.after(beginDate) && date.before(endDate);
    }
}




