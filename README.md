## Getting Start
First you need to include this into your project
### Gradle
```groovy
dependencies {
    implementation 'com.github.sensathyamony:android_test_library:{latest_version}'
}
```

## Here are some usable Class

#### 1. PickHelper Usage
``` java
// This using for pick media. It can be pick one or many
PickHelper.filePicker(MainActivity.this, CALLBACK);

// return value
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CALLBACK){
        //do something
    }
}
/* 
    This using for share media. You share the multiple file by using this
    fileList = ArrayList<Uri>();     
 */
PickHelper.shareFile(MainActivity.this, ArrayList<Uri>);
```

#### 2. FunctionHelper Usage
``` java
// This using for pick media. It can be pick one or many
1. FunctionHelper.requestPermission(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CALLBACK);

//Return Value
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == CALLBACK){}
}
/*
    This function use to check if the permission is allow or not. 
    This function return Boolean.
*/
2. FunctionHelper.isPermissionAllowed(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})) 

/*
    This function is customer color snackbarPopup. This function use 4 method
    snackBarPopUp(@NonNull View view, String textMsg, int colorRes, int textColor)
*/
3. FunctionHelper.snackBarPopUp(view, "Permission Granted", R.color.purple_700, R.color.red);

/*
    writeFileLog(Context context, String filename, String ext, String value)
    This function is use to create local file log inside your application directory.
*/
4. FunctionHelper.writeFileLog(MainActivity.this, "testFile", "log", "Hello world");

/*
    getFile(Context context, String filename)
    This function is use to get your application file in return.
*/
5. FunctionHelper.getFile(MainActivity.this, "fileNameWithExtension");

/*
    htmlToPrint(Context context, String fileName, String value, PrintAttributes.MediaSize pageSize)
    This function is use to print html string.
*/
6. FunctionHelper.htmlToPrint(MainActivity.this, "mainIndex.html",  "<h1>Hello World</h1>", PrintAttributes.MediaSize.ISO_A5);
```

#### 3. StringHelper Usage
``` java
/*
    convertStringToCurrency(String currencyCode, double value)
    This function is use to return value with currency as String
*/
1. StringHelper.convertStringToCurrency("KHR", 10000.1);

/*
    convertDateFormat(String value, String mainFormat, String outputFormat, String localize)
    This function is use convert your date format with localization and return as String
*/
2. StringHelper.convertDateFormat("01 Jan 2023 09:09:00", "dd MMM yyyy hh:mm:ss", "MMM dd yyy", en );

/*
    getDate(String format, TimeZone timeZone)
    This function is use to get current date follow the format and timeZone and return as String
*/
3. StringHelper.getDate("01 Jan 2023 09:09:00", TimeZone.getTimeZone("Asia/Phnom_Penh"));
```

#### 4. CallApi Usage
1. implement this interface into your Activity
``` java
public class MainActivity ... implements CallApi.OnApiTaskCallBackResponseArray, CallApi.OnApiTaskCallBackResponseObject {
}
```

2. Declare it into onCreate()
``` java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CallApi callApi = new CallApi();
        callApi.setCallBackListerArray(this); //this is used for responseArray
        callApi.setCallBackListerObject(this); //this is used for responseObject
    }
```
3. Use the function
``` java
    /*
    makeApiRequestObject(Context context, int requestMethod, final int callBackCode, String baseUrl, String url, Map<String, String> param, String token)
    */
    //for response as Object
    CallApi.makeApiRequestObject(MainActivity.this, 1, 99, baseUrl, endPoint, parameter, "" );
    
    //for response as Array
    CallApi.makeArrayRequest(MainActivity.this, 0, 99,baseUrl, endPoint, parameter, "" );
    
    // RETURN VALUE
    @Override
    public void onApiSuccessCallBack(int callBackCode, String successMsg, JSONArray respondArray) {
        if (callBackCode == 99) {
            //Do Something
        }
    }

    @Override
    public void onApiSuccessCallBack(int callBackCode, String successMsg, JSONObject respondObject) {
        if (callBackCode == 99) {
            //Do Something
        }
    }

    @Override
    public void onApiErrorCallBack(int callBackCode, String errorMsg, int status) {
        if (callBackCode == 99) {
            //Do Something
        }
    }
```