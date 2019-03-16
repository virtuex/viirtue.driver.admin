package org.virtue.basic.result;

import org.virtue.basic.exception.BasicException;

public class BizResult<T> {
    private String message;
    private long retCode;
    private T data;
    private BizResult(T data) {
        this.retCode = 0;
        this.message = "成功";
        this.data = data;
    }
    public BizResult() {

    }
    private BizResult(BasicException cm) {
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
    public static <T> BizResult<T> success(T data){
        return new BizResult<T>(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> BizResult<T> success(){
        return (BizResult<T>) success("");
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> BizResult<T> error(BasicException cm){
        return new BizResult<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> BizResult<T> error(BasicException cm, String msg){
        cm.getErrorCode().appendMsgToNew(cm.getMessage()+"--"+msg);
        return new BizResult<T>(cm);
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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRetCode(long retCode) {
        this.retCode = retCode;
    }

    public void setData(T data) {
        this.data = data;
    }
}