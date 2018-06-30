package cn.edu.cwnu.util.redisutil;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@SuppressWarnings(value = {"rawtypes", "unused", "unchecked"})

@Component
public class RedisUtils {
    //日志记录
    protected Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private Long defaultCacheExpireTime = (long) (60 * 60 * 4); // 缓存默认的过期时间,这里设置了3h

    /**
     * 创建缓存key
     *
     * @param targetName
     * @param methodName
     * @param arguments
     */
    private String getCacheKey(String targetName, String methodName,
                               Object[] arguments) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(targetName).append("_").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sbu.append("_").append(arguments[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * ****************************************
     * function:判断redis缓存中是否有对应的value
     *
     * @param key
     * @return ****************************************
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * ****************************************
     * function:根据key读取缓存中的value
     *
     * @param key
     * @return value
     * ****************************************
     */
    public Object getCache(final String key) {
        Object result = null;
        try {

            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            result = operations.get(key);
            return result;
        } catch (Exception e) {
            log.error("根据key读取缓存中的value失败！", e);
        }
        return null;
    }

    /**
     * ****************************************
     * function:  添加缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 有效时间 （S）
     * @return ****************************************
     */
    public boolean setCache(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error(" 添加缓存失败", e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ****************************************
     * function:移除键值对
     *
     * @param key ****************************************
     */
    public void remove(final String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
