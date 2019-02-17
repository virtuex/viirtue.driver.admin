package org.virtue.basic.exception;



import java.io.Serializable;

/**
 * Purpose:
 * <p>
 * 基本异常代码定义<br>
 * 实际应用请继承实本类，实现对具体产品定义错误列表
 *
 * @see BasicException
 * @since 1.0
 */
public class BasicErrorCode implements Serializable {

    private static final long serialVersionUID = 7372450782399112438L;
    /**
     * 操作成功
     */
    public static final BasicErrorCode BIZ_OK = new BasicErrorCode(0, "BIZ_OK");
    /**
     * 未知异常
     */
    public static final BasicErrorCode BIZ_UNKOWN = new BasicErrorCode(-1, "BIZ_UNKOWN");

    private long code = 0;

    /**
     * 在支持 i18n 的版本中，已经作为 i18n 的 key
     */
    private String message = null;

    private Class belong;

    protected BasicErrorCode(long code, String i18nKey) {
        this.code = code;
        this.message = i18nKey;
        this.belong = this.getClass();
    }

    public Class getBelong() {
        return belong;
    }

    public BasicErrorCode setBelong(Class belong) {
        this.belong = belong;
        return this;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public String getOriginalMessage() {
        return message;
    }

    /**
     * 由于不能改变ErrorCode中的基本注释，当增加注释时，应该自动创建一个新的对象
     *
     * @param message
     * @return
     */
    public BasicErrorCode appendMsgToNew(final String message) {
        return new BasicErrorCode(this.code, this.message) {
            @Override
            public String getMessage() {
                return super.getMessage() + ". " + message;
            }
        }.setBelong(this.getBelong());
    }


}

