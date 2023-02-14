package com.hp.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtils {
    public static String getAccountName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name;
    }
}
