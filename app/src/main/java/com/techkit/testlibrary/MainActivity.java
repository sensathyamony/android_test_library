package com.techkit.testlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Button;

import com.bronx.bronx_helper.CallApi;
import com.bronx.bronx_helper.FunctionHelper;
import com.bronx.bronx_helper.ToasterMsg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CallApi.OnApiTaskCallBackResponseObject, CallApi.OnApiTaskCallBackResponseArray {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        CallApi apiService = new CallApi();
        apiService.setCallBackListerObject(this);
        apiService.setCallBackListerArray(this);

        btn.setOnClickListener(v -> {
//            ToasterMsg.showToast(this, "Hello world");
//            FunctionHelper.writeFile(this, "TestFile", "ok");
//            FunctionHelper.writeFileLog(MainActivity.this, "testFile", "log", "Hello world");\
            Map<String, String> param = new HashMap<>();

            try {
                CallApi.makeApiRequestArray(MainActivity.this, 0, 99, "http://test_everest.merkun.com/", "mapi/category/list", param, "" );
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("MONY_LOG", "onCreate: error = "+e.getMessage() );
            }
        });


    }

    @Override
    public void onApiSuccessCallBack(int callBackCode, String successMsg, JSONObject respondObject) {
        FunctionHelper.snackBarPopUp(btn, "Success text here.", R.color.purple_700, R.color.white);
    }

    @Override
    public void onApiSuccessCallBack(int callBackCode, String successMsg, JSONArray respondArray) {
        FunctionHelper.snackBarPopUp(btn, "Success text here.", R.color.purple_500, R.color.white);
    }

    @Override
    public void onApiErrorCallBack(int callBackCode, String errorMsg, int status) {
        FunctionHelper.snackBarPopUp(btn, "Error text here.", R.color.red, R.color.white);
    }
}