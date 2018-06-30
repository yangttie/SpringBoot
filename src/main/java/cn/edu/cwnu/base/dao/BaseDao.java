package cn.edu.cwnu.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.edu.cwnu.util.common.StringUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@SuppressWarnings("rawtypes")
@Repository("baseDao")
public class BaseDao extends SqlSessionDaoSupport implements Serializable {

    private static final long serialVersionUID = -8801380828163126776L;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }


    /**
     * 单一条件查询对象
     */
    public Object selectObjectByOne(String sqlId, Object obj) {
        if (!StringUtil.isEmpty(obj)) {
            return getSqlSession().selectOne(sqlId, obj);
        } else {
            return getSqlSession().selectOne(sqlId);
        }
    }

    /**
     * 单一条件查询对象集合
     */
    public List selectListByOne(String sqlId, Object obj) {
        if (!StringUtil.isEmpty(obj)) {
            return getSqlSession().selectList(sqlId, obj);
        } else {
            return getSqlSession().selectList(sqlId);
        }
    }

    /**
     * 多条件查询对象集合
     */
    public List selectObjectByMap(String sqlId, Map<Object, Object> map) {
        if (map != null) {
            return getSqlSession().selectList(sqlId, map);
        } else {
            return getSqlSession().selectList(sqlId);
        }
    }

    /**
     * 多条件查询对象
     */
    public List selectOneByMap(String sqlId, Map<Object, Object> map) {
        if (map != null) {
            return getSqlSession().selectOne(sqlId, map);
        } else {
            return getSqlSession().selectOne(sqlId);
        }
    }

    /**
     * 插入对象
     */
    public Serializable addNewObject(String sqlId, Object obj) {
        if (!StringUtil.isEmpty(obj)) {
            return getSqlSession().insert(sqlId, obj);
        } else {
            return null;
        }

    }

    /**
     * 批量插入对象
     */
    public Serializable batchListObject(String sqlId, List<Object> obj) {
        if (!obj.isEmpty()) {
            return getSqlSession().insert(sqlId, obj);
        } else {
            return null;
        }

    }

    /**
     * 更新对象
     */
    public boolean updateObject(String sqlId, Object obj) {
        if (!StringUtil.isEmpty(obj)) {
            getSqlSession().update(sqlId, obj);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 批量更新对象
     */
    public int updateListObject(String sqlId, List<Object> obj) {
        if (!obj.isEmpty()) {
            return getSqlSession().update(sqlId, obj);
        } else {
            return 0;
        }
    }

    /**
     * 多条件查询更新对象
     */
    public boolean updateObject(String sqlId, Map<Object, Object> map) {
        if (!StringUtil.isEmpty(map)) {
            getSqlSession().update(sqlId, map);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除对象
     */
    public int deleteObject(String sqlId, Map<Object, Object> map) {
        if (!StringUtil.isEmpty(map)) {
            int i = getSqlSession().delete(sqlId, map);
            return i;
        } else {
            int i = getSqlSession().delete(sqlId);
            return i;
        }
    }


}
