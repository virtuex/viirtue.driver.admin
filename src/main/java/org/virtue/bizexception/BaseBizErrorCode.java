package org.virtue.bizexception;

import org.virtue.basic.exception.BasicErrorCode;

/**
 * 错误代码定义，一共12位数<br>
 * <blockquote>
 * <p>
 * <pre>
 *       0000      000         00000
 *         |        |            |
 *       代表系统定义 |            |
 *                系统内部模块     |
 *                             每个子系统错误定义
 *       </pre>
 * <p>
 * </blockquote>
 *
 */
public abstract class BaseBizErrorCode extends BasicErrorCode {


    public static final int SYS_BASE_ERROR_CODE = -1500000000;

    protected BaseBizErrorCode(int code, String msg) {
        super(code, msg);
    }

    protected BaseBizErrorCode(long code, String msg) {
        super(code, msg);
    }

    public BizException toException() {
        return new BizException(this);
    }

    /**
     * @param moreMessage
     * @return
     */
    public BizException toException(String moreMessage) {
        return new BizException(this, moreMessage);
    }


    public BizException toException(Throwable cause) {
        return new BizException(this, cause);
    }

    /**
     * @param moreMessage
     * @param cause
     * @return
     */
    public BizException toException(String moreMessage, Throwable cause) {
        return new BizException(this, moreMessage, cause);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicErrorCode) {
            return this.getCode() == ((BasicErrorCode) obj).getCode();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
