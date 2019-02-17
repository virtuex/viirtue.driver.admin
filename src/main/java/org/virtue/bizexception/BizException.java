package org.virtue.bizexception;

import org.virtue.basic.exception.BasicErrorCode;
import org.virtue.basic.exception.BasicException;

/**
 * 顶级异常
 *
 */
public class BizException extends BasicException {

    private static final long serialVersionUID = 1L;

    public BizException(BasicErrorCode errCode) {
        super(errCode);
    }

    /**
     * @param errCode
     * @param moreMessage
     */
    @Deprecated
    public BizException(BasicErrorCode errCode, String moreMessage) {
        super(errCode, moreMessage);
    }

    public BizException(BasicErrorCode errCode, Throwable cause) {
        super(errCode, cause);
    }

    /**
     * @param errCode
     * @param moreMessage
     * @param cause
     */
    @Deprecated
    public BizException(BasicErrorCode errCode, String moreMessage, Throwable cause) {
        super(errCode, moreMessage, cause);
    }

}
