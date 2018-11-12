package com.work.borrow.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.*;

public class Params {

    public Params() {

    }
    public static Params getParams(Object... objs) {
        List<Object> objects = Arrays.asList(objs);
        ListIterator<Object> objectListIterator = objects.listIterator();
        Map<String,Object> param = new HashMap<>();
        while (objectListIterator.hasNext()) {
            param.put("",objectListIterator.next());
        }
        return new Params();
    }
}
