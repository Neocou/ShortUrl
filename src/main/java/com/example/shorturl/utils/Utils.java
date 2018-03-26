package com.example.shorturl.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class Utils {
    //62个对应编码
    public static final char[] h62Array = {
        'Z','X','C','V','B','N','M','A','S','D','F','G','H',
        'J','K','L','Q','W','E','R','T','Y','U','I','O','P',
        'z', 'x','c','v','b','n','m','a','s','d','f','g','h',
        'j','k', 'l','q','w','e','r','t','y','u','i','o','p',
        '0','1','2', '3','4','5','6','7','8','9'
    };
    //转换为json
    public static String getRespons(Object data){
        JSONObject oper = new JSONObject();
        oper.put("status",200);
        oper.put("code",0);
        oper.put("data",data);
        return oper.toJSONString();
    }
    public static Boolean checkHttpUrl(String url){
//        Pattern patternHttp = Pattern.compile("(?<!\\d)(?:(?:[\\w[.-://]]*\\.[com|cn|net|tv|gov|org|biz|cc|uk|jp|edu]+[^\\short|^\\u4e00-\\u9fa5]*))");
//        Matcher matcher = patternHttp.matcher(url);
//        if(matcher.find()){
//            return true;
//        }

        //先不验证
        return true;
    }
    //62求余选取
    public static String long2H62(Long id){
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        do{
            stack.add(h62Array[new Long((id - (id / 62) * 62)).intValue()]);
            id = id / 62;
        } while (id != 0);
        for (;!stack.isEmpty();) {
            result.append(stack.pop());
        }
        return result.toString();

    }



}
