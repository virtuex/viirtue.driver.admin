package org.virtue.basic.exception;

/**
 * 错误编码生成工厂，每个项目应该设置一个基础数据 错误代码定义，一共12位数<br>
 * 其中前面7位由此工厂类生成，后续的位数由用户自行填写<br>
 * <blockquote>
 *
 * <pre>
 * 000      000         00000
 *   |        |            |
 * 代表系统定义 |            |
 *          系统内部模块     |
 *                       每个子系统错误定义
 * </pre>
 *
 * </blockquote>
 *
 * @author huangff
 *
 */
public class ErrorCodeFactory {

    /**
     * 系统名称，如果未设置，则使用默认名称
     */
    private String sysName = "fixSysName";

    private static ErrorCodeFactory instance = new ErrorCodeFactory();

    public static ErrorCodeFactory getInstance() {
        return instance;
    }

    private ErrorCodeFactory() {

    }

    /**
     * 生成Int类型的编码，长度是9位
     *
     * @param modName
     * @return
     */
    public int genModBaseCode(String modName) {
        return genModBaseCode(this.sysName, modName);
    }

    /**
     * 生成Int类型的编码，长度是9位
     *
     * @param sysName
     * @param modName
     * @return
     */
    public int genModBaseCode(String sysName, String modName) {
        String code = genCode(sysName, modName, 3);
        return -Integer.valueOf(code + "000");
    }

    /**
     * 生成Long类型的编码，长度是13位
     *
     * @param modName
     * @return
     */
    public long genModBaseCodeLong(String modName) {
        return genModBaseCodeLong(this.sysName, modName);
    }

    /**
     * 生成Long类型的编码，长度是13位
     *
     * @param sysName
     * @param modName
     * @return
     */
    public long genModBaseCodeLong(String sysName, String modName) {
        String code = genCode(sysName, modName, 4);
        return -Long.valueOf(code + "00000");
    }

    private String genCode(String sysName, String modName, int fixCode) {
        int sysCode = ErrorCodeUtil.fixCode(sysName, fixCode);
        int modCode = ErrorCodeUtil.fixCode(modName, fixCode);
        String modCodeStr = String.valueOf(modCode);
        int modeCodeLen = modCodeStr.length();

        if (modeCodeLen < fixCode) {
            int paddingLen = fixCode - modeCodeLen;
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < paddingLen; i++) {
                value.append("0");
            }
            modCodeStr = value + modCodeStr;
        }
        return sysCode + modCodeStr;
    }

    public String getSysName() {
        return sysName;
    }

    /**
     * 每个项目都应该设置此值
     *
     * @param sysName
     */
    public ErrorCodeFactory setSysName(String sysName) {
        this.sysName = sysName;
        return this;
    }

}

