package com.bronx.bronx_helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bronx.bronx_helper.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CallApi {
    private static final String TAG = "BROX-CALLAPI-LOG";
    private static OnApiTaskCallBackResponseArray onApiArrayCallback;
    private static OnApiTaskCallBackResponseObject onApiObjectCallback;

    public static void makeApiRequestObject(Context context, int requestMethod, final int callBackCode, String baseUrl, String url, Map<String, String> param, String token) {
        Log.e(TAG, "baseUrl " + baseUrl + url);
        Log.e(TAG, "    Param "+ param + "");

        if (param == null) {
            param = new HashMap<>();
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(
                requestMethod,
                baseUrl+url,
                new JSONObject(param),
                response -> {
                    Log.e(TAG, "        Success response " + response.toString());
                    onApiObjectCallback.onApiSuccessCallBack(callBackCode, "success", response);
                },
                error -> {
                    Log.e(TAG, "        Error response " + error.getMessage());
                    onApiObjectCallback.onApiErrorCallBack(callBackCode, error.getMessage(), error.networkResponse.statusCode);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json; charset=utf-8");
                headers.put("Content-Language", "en");

                if (!token.isEmpty()) {
                    headers.put("Authorization-Token", token);
                }

                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 1, 1.f));
        queue.add(request);
    }

    public void setCallBackListerObject(OnApiTaskCallBackResponseObject onApiTaskCallBack) {
        onApiObjectCallback = onApiTaskCallBack;
    }

    public interface OnApiTaskCallBackResponseObject{
        void onApiSuccessCallBack(int callBackCode, String successMsg, JSONObject respondObject);

        void onApiErrorCallBack(int callBackCode, String errorMsg, @Constant.API_STATUS_CODE int status);
    }

    public static void makeApiRequestArray(Context context, int requestMethod, final int callBackCode, String baseUrl, String url, Map<String, String> param, String token) throws JSONException {
        Log.e(TAG, "baseUrl " + baseUrl + url);
        Log.e(TAG, "    Param "+ param + "");

        if (param == null) {
            param = new HashMap<>();
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                requestMethod,
                baseUrl+url,
                null,
                response -> {
                    Log.e(TAG, "        Success response " + response.toString());
                    onApiArrayCallback.onApiSuccessCallBack(callBackCode, "success", response);
                },
                error -> {
                    Log.e(TAG, "        Error response " + error.networkResponse.statusCode);
                    onApiArrayCallback.onApiErrorCallBack(callBackCode, error.getMessage(), error.networkResponse.statusCode);
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json; charset=utf-8");
                headers.put("Content-Language", "en");

                if (!token.isEmpty()) {
                    headers.put("Authorization-Token", token);
                }

                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 1, 1.f));
        queue.add(request);
    }

    public void setCallBackListerArray(OnApiTaskCallBackResponseArray onApiTaskCallBack) {
        onApiArrayCallback = onApiTaskCallBack;
    }

    public interface OnApiTaskCallBackResponseArray{
        void onApiSuccessCallBack(int callBackCode, String successMsg, JSONArray respondArray);

        void onApiErrorCallBack(int callBackCode, String errorMsg, @Constant.API_STATUS_CODE int status);
    }
}
