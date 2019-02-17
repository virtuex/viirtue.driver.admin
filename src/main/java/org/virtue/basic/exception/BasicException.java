package org.virtue.basic.exception;


/**
 * Purpose: 异常类，使用错误代码包装出现的异常
 *
 * @see BasicErrorCode
 */
public class BasicException extends Exception {

    private static final long serialVersionUID = 1L;

    protected BasicErrorCode errorCode = BasicErrorCode.BIZ_OK;

    public BasicException(BasicErrorCode errCode) {
        this.errorCode = errCode;
    }

    /**
     * @param errCode
     * @param appendMessage
     */
    public BasicException(BasicErrorCode errCode, String appendMessage) {
        this.errorCode = errCode.appendMsgToNew(appendMessage);
    }

    public BasicException(BasicErrorCode errCode, Throwable cause) {
        super(cause);
        this.errorCode = errCode;
    }

    /**
     * @param errCode
     * @param appendMessage
     * @param cause
     */
    public BasicException(BasicErrorCode errCode, String appendMessage, Throwable cause) {
        super(cause);
        this.errorCode = errCode.appendMsgToNew(appendMessage);
    }



    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }


    public BasicErrorCode getErrorCode() {
        return errorCode;
    }

}