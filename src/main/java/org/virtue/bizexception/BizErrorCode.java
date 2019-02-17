package org.virtue.bizexception;

/**
 * 服务端错误代码
 *
 */
public class BizErrorCode extends BaseBizErrorCode {

    public static final int MODULE_FLAG = 0;

    private static final int MOBULE_BASE_ERROR_CODE = SYS_BASE_ERROR_CODE - MODULE_FLAG;

    private BizErrorCode(int code, String msg) {
        super(code, msg);
    }

    // @formatter:off
    // 定义异常信息
    public static final BizErrorCode UNKNOW_ERROR_CODE = new BizErrorCode(MOBULE_BASE_ERROR_CODE - 1, "an.unknown.error");
}
