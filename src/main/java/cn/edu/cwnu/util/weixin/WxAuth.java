package cn.edu.cwnu.util.weixin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

import cn.edu.cwnu.ApiApplication;
import cn.edu.cwnu.base.exception.MyException;
import cn.edu.cwnu.base.service.BaseService;
import cn.edu.cwnu.base.task.Task;
import cn.edu.cwnu.util.redisutil.RedisUtils;
import cn.edu.cwnu.util.weixin.config.BasicInfo;
import cn.edu.cwnu.util.weixin.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 微信相关授权
 *
 * @author ASLONGYOULOVEME
 */
@Component
public class WxAuth extends BaseService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Task task;

    private Logger log = LoggerFactory.getLogger(ApiApplication.class);

    /**
     * 微信js授权
     *
     * @param url
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public Object WxJsAuth(String url) throws Exception {

        HashMap<String, String> map = new HashMap<String, String>();
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        log.info("timestamp:" + timestamp);
        if (redisUtils.exists("jsapi_ticket_agent")) {
            jsapi_ticket = (String) redisUtils.getCache("jsapi_ticket_agent");
            if (jsapi_ticket == null || jsapi_ticket == "") {
                task.getWxAccessToken();
                jsapi_ticket = (String) redisUtils.getCache("jsapi_ticket_agent");
            }
        } else {
            task.getWxAccessToken();
            jsapi_ticket = (String) redisUtils.getCache("jsapi_ticket_agent");
        }

        //生成签名
        String nonce_str = UUID.randomUUID().toString();
        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(str.getBytes("UTF-8"));
        String signature = SignUtil.byteToHex(crypt.digest());
        map.put("appId", BasicInfo.appID);
        map.put("timestamp", timestamp);
        map.put("nonceStr", nonce_str);
        map.put("signature", signature);
        return map;
    }

    /**
     * 微信用户授权
     * @param code
     * @return
     * @throws MyException
     * @throws Exception
     */
//	public String WxUserAuth(String code) throws Exception{
//
//	}
}
