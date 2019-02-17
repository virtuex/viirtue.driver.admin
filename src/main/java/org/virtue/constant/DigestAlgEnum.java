package org.virtue.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuda
 * @email xuda@koal.com
 * @Description: 摘要算法枚举类
 * @date 2019/1/22 13:47
 */
public enum DigestAlgEnum {
    MD5("MD5"),
    MD4("MD4"),
    SHA1("SHA1"),
    SHA224("SHA224"),
    SHA256("SHA256"),
    SHA384("SHA384"),
    SHA512("SHA512")
    ;
    private String digAlgName;
    DigestAlgEnum(String digAlgName){
        this.digAlgName = digAlgName;
    }

    public String getDigAlgName(){
        return this.digAlgName;
    }

    public static List<String> getValuesMap() {
        List<String> list = new ArrayList<>();
        for (DigestAlgEnum p : DigestAlgEnum.values()) {
            list.add(p.getDigAlgName());
        }
        return list;
    }

}
