package cn.edu.cwnu.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class CommonUtils {


    private static DecimalFormat deFormat = new DecimalFormat("######0.00");

    private static Properties properties = new Properties();

    // 资源文件初始化加载
    static {
		/*try {
			properties.load(CommonUtils.class.getClassLoader().getResourceAsStream("serverConfig.properties"));
			logger.info("CommonUtils init success");
		} catch (IOException e) {
			logger.error("CommonUtils init exception", e);
		}*/
    }

    /**
     * 获取properties文件的内容
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * 判断两个时间间隔  大时间在后面
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Long dateInterval(Date date1, Date date2) {
        Long time1 = date1.getTime();
        Long time2 = date2.getTime();
        return time2 - time1;
    }

    /**
     * 获取时间差  单位天
     *
     * @param date
     * @param day
     * @param direction 时间差方向  true 几天前   false 几天后
     * @return
     * @author yangshaoping 2016年8月25日 上午11:41:45
     */
    public static Long getTimeDifferenceOfDay(Date date, int day, boolean direction) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (direction) {
            calendar.add(Calendar.DAY_OF_MONTH, day);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, day * -1);
        }

        return calendar.getTimeInMillis();
    }

    /**
     * 保留两位小数
     *
     * @return
     * @throws Exception
     * @author yangshaoping 2016年6月13日 下午8:11:22
     */
    public static String chargeDecimal(String str) throws Exception {
        Double mon = Double.parseDouble(str);
        return deFormat.format(mon);
    }

    /**
     * 保留两位小数
     *
     * @return
     * @throws Exception
     * @author yangshaoping 2016年6月13日 下午8:11:22
     */
    public static String chargeDecimal(Double str) throws Exception {
        return deFormat.format(str);
    }

    /**
     * MD5加密工具方法
     *
     * @param s 待加密字符串
     * @return
     * @throws NoSuchAlgorithmException
     */
    public final static String md5(String s) throws NoSuchAlgorithmException {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] btInput = s.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest digest = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        digest.update(btInput);
        // 获得密文
        byte[] md = digest.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }


    /**
     * 生成随机不重复号码
     *
     * @return
     * @author yangshaoping 2016年5月27日 上午11:14:21
     */
    public static String randomNumber() {
        int r1 = (int) (Math.random() * (9) + 1); // 产生2个1-9的随机数
        int r2 = (int) (Math.random() * (9) + 1);
        long now = System.currentTimeMillis();// 一个13位的时间戳
        String paymentID = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(now);
        return paymentID;
    }

    /**
     * 字符串转为Date
     *
     * @param string
     * @return
     * @throws ParseException
     * @author yangshaoping 2016年5月9日 下午8:26:02
     */
    public static Date changeToDate(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(string); //转换为util.date
        return date;
    }

    /**
     * 解析对象流
     *
     * @param is
     * @return
     * @author yangshaoping 2016年5月18日 下午6:37:46
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 将手机号中间4位替换为*
     *
     * @param phone
     * @return
     * @author yangshaoping 2016年5月27日 下午1:58:14
     */
    public static String substringPhone(String phone) {
        String result = String.valueOf(Long.parseLong(phone) / 100000000L) + "****" + String.valueOf(Long.parseLong(phone) % 10000);
        return result;
    }

    /**
     * 范围随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     * @author yangshaoping 2016年6月12日 下午4:25:08
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * 获取uuid
     *
     * @return
     * @author yangshaoping 2016年6月14日 下午5:18:24
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 红包
     *
     * @param moneySum
     * @param redNum
     * @return
     * @author yangshaoping 2016年7月22日 下午4:04:14
     */
    public static List<Long> wxAlgorithm(long moneySum, int redNum) {

        List<Long> arr = new ArrayList<Long>();


        if (redNum > moneySum) {
            System.out.println("参数错误");
        } else if (redNum == 1) {
            arr.add(moneySum);
        } else if (redNum == moneySum) {

            for (int i = 0; i < redNum; i++) {
                arr.add(1l);
            }

        } else {
            double a = moneySum / redNum; // 平均每个红包的金钱
            long min_a = moneySum / redNum * 4; //每个红包的最小金额
            long max_a = moneySum * 4 / redNum; //每个红包的最大金额
            Random random = new Random();
            // 每次随机的总数
            long total = 0;
            List<Long> list = new ArrayList<Long>();
            for (int i = 0; i < redNum; i++) {
                int r = random.nextInt(redNum - 1) + 1;
                r = r > redNum / 2 ? Math.round(redNum / 2) : r;
                long k = Math.round(r * a);
                list.add(k);
                total += k;
            }
            List<Long> _h = new ArrayList<Long>();
            // 红包总数
            long _y = 0;
            // 每个红包的金额
            long _u = 0;
            for (int j = 0; j < list.size(); j++) {
                _u = Math.round(list.get(j) * moneySum / total);
//				u = u == 0 ? 1 : u;
                _u = _u > max_a ? (max_a + random.nextInt((int) max_a) / 2 + redNum) : _u;
                _u = _u < min_a ? (min_a - (random.nextInt((int) min_a) / 2 + redNum)) : _u;
                _h.add(_u);
                _y += _u;
            }

            List<Long> h = new ArrayList<Long>();
            long y = 0;
            // 每个红包的金额
            long u = 0;
            for (int j = 0; j < _h.size() - 1; j++) {
                u = Math.round(_h.get(j) * moneySum / _y);
                u = u == 0 ? 1 : u;
                h.add(u);
                y += u;
            }
            System.out.println("***y***:" + y);
            h.add(moneySum - y);
            List<Long> eList = new ArrayList<Long>();
            for (int k = 0; k < h.size(); k++) {
                long g = h.get(k);
                if (g < 1) {
                    eList.add(g);
                    h.remove(k);
                    k--;
                }
            }
            for (int i = 0; i < eList.size(); i++) {
                long _ele = eList.get(i);
                for (int j = 0; j < h.size(); j++) {
                    if (_ele < 0 && h.get(j) - _ele * (-1) > 0) {
                        h.set(j, h.get(j) - _ele * (-1));
                        eList.set(i, _ele * (-1));
                    } else if (_ele < 1 && h.get(j) >= 2) {
                        h.set(j, h.get(j) - 1);
                        eList.set(i, (long) 1);
                        break;
                    }
                }
            }
            h.addAll(eList);

            int count = 0;
            for (long j : h) {
                count += j;
            }
            if (moneySum - count > 0) {
                h.set(h.size() - 1, h.get(h.size() - 1) + (moneySum - count));
            }

            return h;
        }

        return arr;
    }

    /**
     * 获取今天最小时间戳
     *
     * @return
     * @author yangshaoping 2016年9月8日 上午9:54:57
     */
    public static Long getTodayMinTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);

        return cal.getTimeInMillis();
    }

    /**
     * 格式化时间戳
     *
     * @param s    为null 默认yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     * @author yangshaoping 2016年9月28日 下午5:05:37
     */
    public static String formatTime(String s, long time) {
        String str = s == null ? "yyyy-MM-dd HH:mm:ss" : s;
        SimpleDateFormat sdf = new SimpleDateFormat(str);

        return sdf.format(new Date(time));
    }

    /**
     * 判断token失效   日期 7天
     *
     * @param time
     * @return
     * @author yangshaoping 2016年10月8日 下午6:40:38
     */
    public static boolean tokenExpire(Date time) {
        if (time == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        Long now = calendar.getTimeInMillis();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Long after = calendar.getTimeInMillis();

        if (after < now) {
            return true;    //失效
        }
        return false;
    }

    /**
     * 过滤用户
     *
     * @param userId
     * @return
     * @author yangshaoping 2016年10月29日 上午10:14:14
     */
    public static boolean filterUserId(String userId) {
		/*String userIds[] = {"600092","600093","600098","600103","600110","600119","600278","600527","600585","600653"
				,"601712","606819","609329","609330","609344","609346","609352","609361","609364","609377","609376","609378"
				,"609379","609380","600743","600031","600050","600090","600092","600192"};*/
        String userIds[] = {};
        for (String str : userIds) {
            if (str.equals(userId)) {
                return true;
            }
        }
        return false;
    }
}