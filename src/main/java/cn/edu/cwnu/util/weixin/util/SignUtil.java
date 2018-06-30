package cn.edu.cwnu.util.weixin.util;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.edu.cwnu.util.weixin.config.BasicInfo;
import cn.edu.cwnu.util.weixin.model.RefundModel;
import cn.edu.cwnu.util.weixin.model.TransfersModel;
import cn.edu.cwnu.util.weixin.model.UnifiedorderModel;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * 微信签名工具类
 */
public class SignUtil {

    /**
     * 微信统一签名
     *
     * @param params
     * @return
     */
    public static String sign(Map<String, String> params, String mchKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (value != null && !"".equals(value) && !"sign".equals(key)
                    && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        toSign.append("key=").append(mchKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 微信签名使用
     *
     * @param hash
     * @return
     */
    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}
