package com.ad.myhome.utils.common;

public class CommonFunctions {

    private CommonFunctions() {
    }

    public static Boolean isMissing(Integer i){
        return (i==null || i==0);
    }

    public static Boolean isMissing(Long i){
        return (i==null || i==0);
    }


}
