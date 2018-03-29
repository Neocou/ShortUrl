package com.example.shorturl.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import java.util.Random;
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

    //MD5
    public static String str2MD5(String url){
        String md5 = DigestUtils.md5Hex(url);
        StringBuilder result = new StringBuilder(6);
        //这里将32位MD5分成四段，随机选取一段进行编码，当然不随机也行
        Random r = new Random();
        int i = r.nextInt(4);
        //0x3fffffff取30位，超过的忽略
        Long l = Long.valueOf(md5.substring(i*8,i*8+8),16)&0x3fffffff;
        for(int j = 0;j<6;j++){
            long index = l&0x0000003D;//0x0000003D对应十进制为61取字符组
            result.append(h62Array[(int) index]);
            l = l >> 5 ;//移动5位
        }
        return result.toString();
    }

}
