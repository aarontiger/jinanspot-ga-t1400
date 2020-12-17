package com.bingoyes.gat1400.common.enums;


/**
 *
 * @date 2020-07-03
 **/
public enum GenderEnum {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    GenderEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(Integer code){
        GenderEnum[] values = GenderEnum.values();
        for(GenderEnum genderEnum:values){
            if(code.equals(genderEnum.getCode())){
                return genderEnum.getName();
            }
        }
        return null;
    }
}
