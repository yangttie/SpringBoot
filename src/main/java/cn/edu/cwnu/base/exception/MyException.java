package cn.edu.cwnu.base.exception;

import java.io.Serializable;

/**
 * 公共异常处理类
 *
 * @author wuhong  2017-7-21 下午5:52:19
 * @version share 1.0
 */
public class MyException extends Exception implements Serializable {


    private static final long serialVersionUID = 1L;


    /********异常信息***********/
    private String message;
    /********异常代码***********/
    private Integer code;

    public MyException(String message) {
        super(message);
        this.message = message;
    }

    public MyException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public MyException(String message, Exception e) {
        super(message, e);
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }
}
