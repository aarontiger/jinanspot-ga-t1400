package com.bingoyes.gat1400.util;

import java.util.Random;

public class IdUtils {
    public static String generateId(int len){
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }
}
