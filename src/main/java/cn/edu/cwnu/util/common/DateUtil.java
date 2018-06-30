package cn.edu.cwnu.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String dateToString(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        return "";
    }

    public static Date longToDate(Long time) {
        if (time == null) {
            time = System.currentTimeMillis();
        }
        Date date = new Date(time);
        return date;
    }

    /**
     * 计算时长(分)
     *
     * @param begin 起止时间
     * @param end   结束时间
     * @return
     */
    public static int DateLength(Date begin, Date end) {
        Long t1 = begin.getTime();
        Long t2 = end.getTime();
        Long t = t2 - t1;
        return (int) (t / 60000);
    }

    /**
     * @param time    时间字符串 "2017-05-23"
     * @param pattern 时间字符串格式 默认"yyyy-MM-dd"
     * @return
     */
    public static Date StringToDate(String time, String pattern) {
        try {
            if (pattern == null || pattern == "") {
                pattern = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 比较d1,d2时间大小
     *
     * @param d1
     * @param d2
     * @return d1=>d2 -->1  d1<d2 -->-1
     */
    public static int DateDate(Date d1, Date d2) {
        Long t1 = d1.getTime();
        Long t2 = d2.getTime();

        return t1 >= t2 ? 1 : -1;
    }

    /**
     * 给制定日期加上天数
     *
     * @param num
     * @return
     * @throws ParseException
     */
    public static Date plusDay(int num, Date currdate) throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        return currdate;
    }

    /**
     * 给当前日期加上天数
     *
     * @param num
     * @return
     */
    public static Date plusDay2(int num) {
        Date d = new Date();
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        return d;
    }

    public static String dateToString(Date date) {
        return dateToString(date, "yyyy-MM-dd hh:mm:ss");
    }
}