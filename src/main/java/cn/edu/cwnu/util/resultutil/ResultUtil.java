package cn.edu.cwnu.util.resultutil;

import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.pagehelper.dialect.helper.HsqldbDialect;

public class ResultUtil {


    /**
     * ****************************************
     * function: 请求返回成功
     *
     * @param obj   数据
     * @param total 条数]
     * @param code  0:正常
     * @return ****************************************
     */
    public static String returnSuccess(Object obj, int total) {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(0);
        baseVo.setResmsg("SUCCESS");
        baseVo.setTotalrow(total);
        baseVo.setData(obj);
        return JsonUtil.ObjectToJson(baseVo);
    }

    /**
     * ****************************************
     * function:请求数据为空
     *
     * @return ****************************************
     */
    public static String returnNull() {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(0);
        baseVo.setResmsg("SUCCESS");
        baseVo.setTotalrow(0);
        return JsonUtil.ObjectToJson(baseVo);

    }

    /**
     * ****************************************
     * function:请求失败(非系统异常)
     *
     * @param resMsg 失败信息
     * @return ****************************************
     */
    public static String returnFail(String resMsg, Integer code) {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(code);
        baseVo.setResmsg(resMsg);
        baseVo.setTotalrow(1);
        return JsonUtil.ObjectToJson(baseVo);
    }

    /**
     * ****************************************
     * function:请求异常(系统异常)
     *
     * @return ****************************************
     */
    public static String returnError(Integer code, String message) {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(code);
        baseVo.setResmsg(message);
        baseVo.setTotalrow(1);
        return JsonUtil.ObjectToJson(baseVo);
    }

    public static Object returnError(BindingResult result) {
        HashMap<String, String> resMap = new HashMap<>();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError e : errors) {
            resMap.put(e.getField(), e.getDefaultMessage());
        }
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(400);
        baseVo.setData(resMap);
        baseVo.setResmsg("参数不合法");
        baseVo.setTotalrow(1);
        return JsonUtil.ObjectToJson(baseVo);
    }
}
