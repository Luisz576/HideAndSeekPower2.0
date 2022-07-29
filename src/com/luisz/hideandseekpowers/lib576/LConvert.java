package com.luisz.hideandseekpowers.lib576;

public class LConvert {

    public static int convertToInteger(String numberS) {
        try {
            return Integer.parseInt(numberS);
        } catch (Exception e) {
            return -1;
        }
    }

}