package cn.edu.cwnu.base.service;

import cn.edu.cwnu.base.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Service基类
 *
 * @TODO
 * @date 2018年3月23日下午1:26:57
 * @description
 */
public class BaseService {

    @Autowired
    protected BaseDao baseDao;


}
