package com.android.game2048;

/**
 * Created by js_66RPG on 2017/11/17.
 */

public class Single2 {

    private static Single2 single;

    private Single2(){}

    private static Single2 getSingleInstance(){
        synchronized (Single2.class){
            if (single==null){
                single = new Single2();
            }
        }
        return single;
    }

    public static synchronized Single2 getInstance() {
        if (single == null) {
            single = new Single2();
        }
        return single;
    }

}
