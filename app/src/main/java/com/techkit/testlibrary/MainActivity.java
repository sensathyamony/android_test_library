package com.techkit.testlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.bronx.bronx_helper.CallApi;
import com.bronx.bronx_helper.FunctionHelper;
import com.bronx.bronx_helper.PickHelper;
import com.bronx.bronx_helper.StringHelper;
import com.bronx.bronx_helper.util.BaseHelper;
import com.bronx.bronx_helper.util.GlobalConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements CallApi.OnApiTaskCallBackResponseObject, CallApi.OnApiTaskCallBackResponseArray {

    Button btn;
    private final int CALLBACK = 99;
    private static final String TAG = "MONY_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        CallApi apiService = new CallApi();
        apiService.setCallBackListerObject(this);
        apiService.setCallBackListerArray(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn.setOnClickListener(v -> {

//            PickHelper.filePicker(MainActivity.this, CALLBACK);

//            FunctionHelper.requestPermission(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CALLBACK);
//            FunctionHelper.filePicker(MainActivity.this, CALLBACK);
            callShare();

//            FunctionHelper.htmlToPrint(MainActivity.this, "mainIndex.html",  "<h1>Hello World</h1>", PrintAttributes.MediaSize.ISO_A5);
//            FunctionHelper.writeFileLog(MainActivity.this, "hello", "txt", "Hello world");

//            checkCurrencyConvert();
//            functionCheckString();

//            ToasterMsg.showToast(this, "Hello world");
//            FunctionHelper.writeFile(this, "TestFile", "ok");
//            FunctionHelper.writeFileLog(MainActivity.this, "testFile", "log", "Hello world");
//            Map<String, String> param = new HashMap<>();
//            param.put("cust_name", "bo");
//            CallApi.makeArrayRequest(MainActivity.this, 0, 99, "http://test_everest.merkun.com/", "mapi/customer/list?cust_name=bo", param, "" );

//            FunctionHelper.requestPermission(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CALLBACK);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALLBACK){
            try {
                Log.e(TAG, "onActivityResult: data = " + data);
                ArrayList<Uri> imageList = new ArrayList<>();
                if (data.getData() != null){
                    Uri uri = data.getData();
                    imageList.add(uri);
                }else {
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        imageList.add(data.getClipData().getItemAt(i).getUri());
                    }
                }
                PickHelper.shareFile(MainActivity.this, imageList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void callShare(){

        String urlImage = "https://i.pinimg.com/474x/26/85/f3/2685f3f51ea768380a343e33bc498b29.jpg";
        try {
            File file = FunctionHelper.convertStringToFile(MainActivity.this, urlImage, "running.jpg");

            Log.e("MONY_LOG", "onCreate: checking image path " + file);
            ArrayList<Uri> imageList = new ArrayList<>();
            imageList.add(Uri.parse(urlImage));
            Log.e("MONY_LOG", "onCreate: checking image path " + imageList);
            PickHelper.shareFile(MainActivity.this, imageList);
//            FunctionHelper.shareImageToMedia(MainActivity.this, Uri.fromFile(file), file.getName(), BuildConfig.APPLICATION_ID+".provider");
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private void checkCurrencyConvert(){

        String cur = StringHelper.convertStringToCurrency(GlobalConstant.CurrencyString.DONG.getCurrencyCode(), 10000.1);
        Log.e("MONY_LOG", "onCreate: check String currency format = "+cur);

    }
    private void functionCheckString(){
        String date;
        date = StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.CAMBODIA.getTimezone()));
        Log.e("MONY_LOG", "onCreate: date = "+ date);
        Log.e("MONY_LOG", "onCreate: TIMEZONE = "+GlobalConstant.TimeZoneString.CAMBODIA.getTimezone()+ " before format = "+ date);
        Log.e("MONY_LOG", "     Time = "+ StringHelper.convertDateFormat(
                StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.CAMBODIA.getTimezone())),
                GlobalConstant.CALENDAR_DATE,
                GlobalConstant.SYSTEM_DISPLAY_DATE_FORMAT+ " " +GlobalConstant.OUTPUT_TIME_FORMAT,
                GlobalConstant.LocalizationString.KHMER.getLocalize()
                )
        );

        Log.e("MONY_LOG", "onCreate: TIMEZONE = "+GlobalConstant.TimeZoneString.CHINESE.getTimezone()+ " before format = "+ StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.FRENCH.getTimezone())));
        Log.e("MONY_LOG", "     Time = "+ StringHelper.convertDateFormat(
                StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.CHINESE.getTimezone())),
                GlobalConstant.CALENDAR_DATE,
                GlobalConstant.SYSTEM_DISPLAY_DATE_FORMAT+ " " +GlobalConstant.OUTPUT_TIME_FORMAT,
                GlobalConstant.LocalizationString.CHINESE.getLocalize()
                )
        );

        Log.e("MONY_LOG", "onCreate: TIMEZONE = "+GlobalConstant.TimeZoneString.JAPAN.getTimezone()+ " before format = "+ StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.JAPAN.getTimezone())));
        Log.e("MONY_LOG", "     Time = "+ StringHelper.convertDateFormat(
                StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.JAPAN.getTimezone())),
                GlobalConstant.CALENDAR_DATE,
                GlobalConstant.SYSTEM_DISPLAY_DATE_FORMAT+ " " +GlobalConstant.OUTPUT_TIME_FORMAT,
                GlobalConstant.LocalizationString.JAPAN.getLocalize()
                )
        );

        Log.e("MONY_LOG", "onCreate: TIMEZONE = "+GlobalConstant.TimeZoneString.ENGLISH.getTimezone()+ " before format = "+ StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.ENGLISH.getTimezone())));
        Log.e("MONY_LOG", "     Format Time = "+ StringHelper.convertDateFormat(
                StringHelper.getDate(GlobalConstant.CALENDAR_DATE, TimeZone.getTimeZone(GlobalConstant.TimeZoneString.ENGLISH.getTimezone())),
                GlobalConstant.CALENDAR_DATE,
                GlobalConstant.SYSTEM_DISPLAY_DATE_FORMAT+ " " +GlobalConstant.OUTPUT_TIME_FORMAT,
                GlobalConstant.LocalizationString.ENGLISH.getLocalize()
                )
        );
        Log.e("MONY_LOG", "============================");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALLBACK){
            if (FunctionHelper.isPermissionAllowed(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})){
                FunctionHelper.snackBarPopUp(btn, "Permission Granted", R.color.purple_700, R.color.red);
            }
        }
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