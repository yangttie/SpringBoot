package cn.edu.cwnu.base.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import cn.edu.cwnu.base.exception.MyException;
import cn.edu.cwnu.base.exception.SystemError;
import cn.edu.cwnu.base.service.BaseService;
import cn.edu.cwnu.util.resultutil.ResultUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ApiLogAspect extends BaseService {

    /******log record******/
    protected Logger log = Logger.getLogger(ApiLogAspect.class);
    @Autowired
    private HttpServletRequest req;

    @Around("execution(* cn.edu.cwnu.controller.*.*(..))")
    public Object getCache(ProceedingJoinPoint jp) throws Throwable {

        //获取类名
        String className = jp.getThis().toString();
        //获取方法名
        String methodName = jp.getSignature().getName();

        //方法开始时间
        long start = System.currentTimeMillis();

        String token = req.getHeader("token");
        String ad = req.getHeader("uid");
        //验证码不进行日记记录
        if ("getVerification".equals(methodName)) {
            jp.proceed();
            return null;
        }
        String paramStr = "";
        Enumeration<String> enu = req.getParameterNames();
        Object result = null;
        try {

            while (enu.hasMoreElements()) {
                String paraName = (String) enu.nextElement();
                paramStr += paraName + "=" + req.getParameter(paraName) + "&";
            }

            result = jp.proceed();
            log.info("\n" + "----------------接口日志开始---------------" + "\n" +
                    "类名:" + className + "\n" +
                    "方法名:" + methodName + "\n" +
                    "请求ip地址:" + req.getRemoteAddr() + "\n" +
                    "请求uid:" + ad + "\n" +
                    "请求token:" + token + "\n" +
                    "请求参数列表:" + paramStr + "\n" +
                    "返回参数列表:" + result.toString() + "\n" +
                    "共计消耗:" + (System.currentTimeMillis() - start) + " ms" + "\n" +
                    "----------------接口日志结束---------------" + "\n");
            return result;
        } catch (Exception e) {

            //自定义异常暂时不管
            if (e instanceof MyException) {
                return ResultUtil.returnError(((MyException) e).getCode(), ((MyException) e).getMessage());
            } else {
                //系统异常打印到日志
                log.error("\n类名:" + className + " ,\n" +
                        "方法名:" + methodName + " ,\n" + "异常连接--》", e);
                return ResultUtil.returnError(SystemError.OTHER_EXCEPTION.getCode(),
                        SystemError.OTHER_EXCEPTION.getMessage());
            }
        }
    }


}
