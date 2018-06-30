package cn.edu.cwnu.util.common;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IDUtil {


    /**
     * 订单id生成   时间戳+用户id+随机数
     *
     * @param uid
     * @return
     */
    public static String getOrderNo(Integer uid) {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String now = format.format(date);

        //当前时间（ms）
        String millis = System.currentTimeMillis() + "";
        String time = millis.substring(10, 13);

        //拼接id
        String str = now + (int) (10000 + Math.random() * 90000) + time;
        return str;
    }

    /**
     * 获取随机6位数
     *
     * @return
     */
    public static String getSixRandNum() {
        //随机数
        int num = (int) (Math.random() * 900000) + 100000;
        return num + "";
    }

    /**
     * uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
