package com.example.miaoz.xgpush.network2;

/**
 * @author apple
 * @Description :
 * @date 17/3/6  上午11:29
 */

public class ProguardUtil {

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
