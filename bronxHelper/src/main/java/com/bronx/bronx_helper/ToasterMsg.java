package com.bronx.bronx_helper;

import android.content.Context;
import android.widget.Toast;

public class ToasterMsg {

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
