package com.wooshop.utils;

import java.util.List;

public class WooshopArrayUtil {
    /**
     * list转为字符串，专用于sql中in函数
     * @return String
     * @param list
     */
    public static String sqlStrListToSqlJoin(List<String> list) {
        if (null == list || list.size() < 1) {
            return "";
        }
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                temp.append(",");
            }
            temp.append("'").append(list.get(i)).append("'");
        }
        return temp.toString();
    }
}
