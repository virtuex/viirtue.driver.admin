package org.virtue.basic.result;

import org.virtue.basic.exception.BasicException;

public class BasicResult<T> {
    private String message;
    private long retCode;
    private T data;
    private BasicResult(T data) {
        this.retCode = 0;
        this.message = "成功";
        this.data = data;
    }
    private BasicResult(BasicException cm) {
        if(cm == null){
            return;
        }
        this.retCode = cm.getErrorCode().getCode();
        this.message = cm.getMessage();
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> BasicResult<T> success(T data){
        return new BasicResult<T>(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> BasicResult<T> success(){
        return (BasicResult<T>) success("");
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> BasicResult<T> error(BasicException cm){
        return new BasicResult<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> BasicResult<T> error(BasicException cm, String msg){
        cm.getErrorCode().appendMsgToNew(cm.getMessage()+"--"+msg);
        return new BasicResult<T>(cm);
    }
    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
    public long getRetCode() {
        return retCode;
    }
}