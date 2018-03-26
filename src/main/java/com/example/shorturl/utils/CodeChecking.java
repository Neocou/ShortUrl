package com.example.shorturl.utils;

import org.springframework.stereotype.Repository;

@Repository
public class CodeChecking {
    public static Boolean checking(String code,int length){
        if( null == code || code.isEmpty()==true || code.length() > length){
            return false;
        }
        return true;
    }
}
