package com.luisz.hideandseekpowers.lib576;

public class LConvert {

    public static int convertToInteger(String numberS) {
        try {
            return Integer.parseInt(numberS);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int module(int a){
        return a >= 0 ? a : a*-1;
    }

}