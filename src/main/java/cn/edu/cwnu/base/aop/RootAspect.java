package cn.edu.cwnu.base.aop;

import javax.servlet.http.HttpServletRequest;

import cn.edu.cwnu.base.exception.MyException;
import cn.edu.cwnu.base.exception.SystemError;
import cn.edu.cwnu.util.redisutil.RedisUtils;
import cn.edu.cwnu.util.resultutil.ResultUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class RootAspect {

    @Autowired
    private HttpServletRequest req;
    @Autowired
    private RedisUtils redisUtils;

    /******log record******/
    protected Logger log = Logger.getLogger(RootAspect.class);

    /**
     * 权限验证
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("execution(* cn.edu.cwnu.controller.*.*Root(..))")
    public Object VaApp(ProceedingJoinPoint jp) throws Throwable {

        try {
            String ad = req.getHeader("uid");
            Object result = null;
            if (ad == "" || ad == null) {
                return ResultUtil.returnFail(SystemError.LOGIN_FAIL.getMessage(),
                        SystemError.LOGIN_FAIL.getCode());
            } else {
                String u = (String) redisUtils.getCache(ad);
                if (u == null) {
                    return ResultUtil.returnFail(SystemError.LOGIN_FAIL.getMessage(),
                            SystemError.LOGIN_FAIL.getCode());
                }
                //用户传输的token
                String token = req.getHeader("token");
                if (u.equals(token)) {
                    result = jp.proceed();
                    return result;
                } else {
                    return ResultUtil.returnFail(SystemError.TOKEN_OVER.getMessage(),
                            SystemError.TOKEN_OVER.getCode());
                }
            }
        } catch (Exception e) {

            //获取类名
            String className = jp.getThis().toString();
            //获取方法名
            String methodName = jp.getSignature().getName();
            log.error("\r\n类名:" + className + " ,\r\n" +
                    "方法名:" + methodName + " ,\r\n" + "异常连接--》", e);
            if (e instanceof NullPointerException) {
                //空指针异常处理
                return ResultUtil.returnError(SystemError.NULL_EXCEPTION.getCode(),
                        SystemError.NULL_EXCEPTION.getMessage());
            } else if (e instanceof MyException) {
                //自定义异常
                return ResultUtil.returnError(((MyException) e).getCode(), ((MyException) e).getMessage());
            } else {
                //其他系统异常
                return ResultUtil.returnError(SystemError.OTHER_EXCEPTION.getCode(),
                        SystemError.OTHER_EXCEPTION.getMessage());
            }
        }
    }

}
