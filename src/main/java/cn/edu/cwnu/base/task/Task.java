package cn.edu.cwnu.base.task;

import cn.edu.cwnu.base.controller.BaseController;
import cn.edu.cwnu.util.common.HttpUtil;
import cn.edu.cwnu.util.weixin.config.BasicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;


/**
 * 定时器
 *
 * @author ASLONGYOULOVEME
 */
@Component
public class Task extends BaseController {

    /******log record******/
    private static Logger log = LoggerFactory.getLogger(Task.class);


    private static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token";
    private static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /**
     * 获取微信access_token 2小时
     */
//	@Scheduled(cron="0 0 */2 * * *")
    public void getWxAccessToken() {

        try {
            log.info("--------获取微信access_token开始----------");
            String result = HttpUtil.get(access_token_url +
                    "?grant_type=client_credential" +
                    "&appid=" + BasicInfo.appID +
                    "&secret=" + BasicInfo.AppSecret);

            JSONObject object = JSONObject.parseObject(result);
            String access_token = (String) object.get("access_token");

            String result1 = HttpUtil.get(jsapi_ticket_url +
                    "?access_token=" + access_token +
                    "&type=jsapi");

            JSONObject object1 = JSONObject.parseObject(result1);
            String jsapi_ticket = (String) object1.get("ticket");
            //将数据添加到redis
            redisUtils.setCache("jsapi_ticket_agent", jsapi_ticket, (long) 60 * 60 * 2);

            log.info("--------获取微信access_token成功----------" + jsapi_ticket);
        } catch (Exception e) {
            log.error("获取微信access_token异常", e);
        }
    }
}
