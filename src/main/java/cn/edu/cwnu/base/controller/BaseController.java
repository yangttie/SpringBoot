package cn.edu.cwnu.base.controller;

import cn.edu.cwnu.util.redisutil.RedisUtils;
import cn.edu.cwnu.util.weixin.WxAuth;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 控制层基类
 *
 * @TODO
 * @date 2018年3月24日下午6:17:18
 * @description
 */
public class BaseController {

    @Autowired
    protected RedisUtils redisUtils;

    @Autowired
    protected WxAuth wxAuth;

}
