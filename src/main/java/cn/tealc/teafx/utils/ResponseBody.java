package cn.tealc.teafx.utils;


/**
 * 可用于处理多线程以及网络请求的包装类
 */
public class ResponseBody<T> {
    public static final int ERROR = -1;
    public static final int SUCCESS = 200;
    private Integer code; //code可以随意设置，但成功必须是200
    private String msg;
    private T data;

    public ResponseBody() {
    }

    public ResponseBody(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseBody<T> create(Integer code, String msg, T value) {
        ResponseBody<T> responseBody = new ResponseBody<>();
        responseBody.setCode(code);
        responseBody.setMsg(msg);
        responseBody.setData(value);
        return responseBody;
    }

    /**
     * 成功必须有返回data
     * @param msg
     * @param value
     */
    public static <T> ResponseBody<T> success(String msg, T value) {
        ResponseBody<T> responseBody = new ResponseBody<>();
        responseBody.setCode(SUCCESS);
        responseBody.setMsg(msg);
        responseBody.setData(value);
        return responseBody;
    }

    public static <T> ResponseBody<T> error(String msg, T value) {
        ResponseBody<T> responseBody = new ResponseBody<>();
        responseBody.setCode(ERROR);
        responseBody.setMsg(msg);
        responseBody.setData(value);
        return responseBody;
    }

    public static <T> ResponseBody<T> error(String msg) {
        ResponseBody<T> responseBody = new ResponseBody<>();
        responseBody.setCode(ERROR);
        responseBody.setMsg(msg);
        return responseBody;
    }

    public boolean isSuccess() {
        return code == 200;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



}