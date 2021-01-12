package com.vtu.web.util;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AliPayUtils {

    public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            System.out.println(name + " = " +valueStr);
            params.put(name, valueStr);
        }
        return params;
    }

}
