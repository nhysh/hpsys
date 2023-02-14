package com.hp.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageResultUtil {
    public static Map<String,Object> getResult(long total, List<?> list){
        Map<String,Object> map = new HashMap<>();
        map.put("code",0); //对于layui，要求返回的code必须是0
        map.put("msg",""); //message为空
        map.put("count",total); //总的信息条数
        map.put("data",list); //一页数据
        return map;
    }
}
