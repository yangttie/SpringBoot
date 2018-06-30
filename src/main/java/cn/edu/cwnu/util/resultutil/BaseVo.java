package cn.edu.cwnu.util.resultutil;

public class BaseVo {

    private Integer status;
    private String resmsg;
    private Integer totalrow;
    private Object data;


    public BaseVo() {
        super();
    }


    public BaseVo(String resmsg, Integer totalrow) {
        super();
        this.resmsg = resmsg;
        this.totalrow = totalrow;
    }

    public String getResmsg() {
        return resmsg;
    }

    public void setResmsg(String resmsg) {
        this.resmsg = resmsg;
    }

    public Integer getTotalrow() {
        return totalrow;
    }

    public void setTotalrow(Integer totalrow) {
        this.totalrow = totalrow;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

}
