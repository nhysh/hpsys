package com.hp.utils;

import com.hp.exception.ParamException;

public class AssertUtil {
    public static void isTrue(Boolean flag,String msg){
        if(flag){
            throw new ParamException(msg);
        }
    }
}
