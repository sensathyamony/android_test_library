package com.bronx.bronx_helper;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bronx.bronx_helper.util.BaseHelper;
import com.bronx.bronx_helper.util.GlobalConstant;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FunctionHelper {

   private static final String TAG = "FUNCTION_HELPER";
   public static void writeFileLog(final Context context, String filename, String ext, String value) {

      try {
         File file = getFile(context, filename+"."+ext);
         Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

         out.write(value);
         out.close();

      } catch (Exception e) {
         Log.e("Error save text", e.getMessage() + "-");
      }
   }

   public static File getFile(final Context context, String filename) {
      return new File(context.getFilesDir(), filename);
   }

   public static void snackBarPopUp(@NonNull View view, String textMsg, int colorRes, int textColor){
      Snackbar.make(view, textMsg, Snackbar.LENGTH_LONG)
              .setBackgroundTint(ContextCompat.getColor(view.getContext(), colorRes))
              .setTextColor(ContextCompat.getColor(view.getContext(), textColor))
              .show();
   }

   public static boolean isPermissionAllowed(Activity context, String[] permissionName) {
      //Getting the permission status

      for (String s : permissionName) {
         int result = ContextCompat.checkSelfPermission(context, s);

         //If permission is granted returning true
         if (result != PackageManager.PERMISSION_GRANTED) {
            return false;
         }

      }
      //If permission is not granted returning false
      return true;
   }

   //Requesting permission
   public static void requestPermission(Context context, String[] permissionName, final int STORAGE_PERMISSION_CODE) {

      //And finally ask for the permission
      ActivityCompat.requestPermissions((Activity) context, permissionName, STORAGE_PERMISSION_CODE);

   }

   public static void htmlToPrint(Context context, String fileName, String value, PrintAttributes.MediaSize pageSize){
      WebView webView = new WebView(context);
      webView.setWebViewClient(new WebViewClient(){
         @Override
         public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
         }
         @Override
         public void onPageFinished(WebView view, String url) {
            printWebPage(context, fileName, view, pageSize);
         }
      });
      webView.loadDataWithBaseURL(null, value, "text/HTML", "UTF-8", null);
   }

   private static void printWebPage(Context context, String fileName, WebView webView, PrintAttributes.MediaSize pageSize) {
      PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
      // Get a print adapter instance
      PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(fileName);
      PrintAttributes.Builder builder = new PrintAttributes.Builder();
      builder.setMediaSize(pageSize);
      // Create a print job with name and adapter instance
       printManager.print(fileName, printAdapter, builder.build());
   }

   public static File convertStringToFile(Context context, String urlImage, String fileNameWithExt) throws IOException {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);

      InputStream stream = new URL(urlImage).openStream();
      Bitmap bitmap = BitmapFactory.decodeStream(stream);
      File cachePath = new File(context.getCacheDir(), "images");
      cachePath.mkdirs();

      FileOutputStream outputStream = new FileOutputStream(cachePath + "/"+fileNameWithExt);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
      outputStream.close();

      File imagePath = new File(context.getCacheDir(), "images");
      return new File(imagePath, fileNameWithExt);
   }

   public static void shareImageToMedia(Context context, Uri uri, String shareText, String packageName){
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);
      try {
//         Uri uri = FileProvider.getUriForFile(context, packageName, file);
         String mimeType = BaseHelper.getMimeType(context, uri);

         Intent shareIntent = new Intent(Intent.ACTION_SEND);
         shareIntent.setType(mimeType);

         shareIntent.setClipData(new ClipData("test", new String[]{mimeType}, new ClipData.Item(uri)));

         shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
         shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareText);
         shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
         shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
         shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

         context.startActivity(shareIntent);
      } catch (Exception e) {
         e.printStackTrace();

         Log.e("ERROR", "shareImageToMedia: "+e.getMessage() );
         Toast.makeText(context, "Error please check the LOG", Toast.LENGTH_SHORT).show();
      }
   }

   public static File covertUriToFile(Context context, Uri uri, int fileSizeLimitInMB) throws IOException {
      File file;
      try {
         file = new File(BaseHelper.getPath(context, uri));
      } catch (Exception e) {
         file = new File(uri.getPath());

      }

      long size = file.length();
      long sizeMegabyte = size / (1024 * 1024);

      String extension = ""; // file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));

      Log.e("sizeMegabyte", sizeMegabyte + "-" + extension);
      String strFileName = file.getName();

      //limit 100MB
      if (sizeMegabyte < fileSizeLimitInMB) {

         int indexExtension = strFileName.lastIndexOf(".");
         if (indexExtension != -1)
            extension = strFileName.substring(strFileName.lastIndexOf("."));

         String mimeType = BaseHelper.getMimeType(context, uri);
         if (extension.isEmpty()) {
            if (mimeType.equalsIgnoreCase("image/jpeg")) {
               extension = GlobalConstant.IMAGE_EXTENSION;
            } else if (mimeType.equalsIgnoreCase("video/mp4")) {
               extension = GlobalConstant.VIDEO_EXTENSION;
            } else if (mimeType.equalsIgnoreCase("audio/mpeg")) {
               extension = GlobalConstant.MUSIC_EXTENSION;
            } else if (mimeType.equalsIgnoreCase("application/pdf")) {
               extension = GlobalConstant.PDF_EXTENSION;
            }
            strFileName += extension;
         }

         if (!extension.isEmpty() && !extension.equalsIgnoreCase(".exe") &&
                 !extension.equalsIgnoreCase(".php") &&
                 !extension.equalsIgnoreCase(".js")) {

            InputStream is = context.getContentResolver().openInputStream(uri);
            byte[] bytesArray = new byte[is.available()];
            is.read(bytesArray);

            Log.e("fileUpload", bytesArray.length + " - " + strFileName + "");

            return file;

         } else {
            Toast.makeText(context, "FILE NOT SUPPORT", Toast.LENGTH_SHORT).show();
            return null;
         }
      } else {
         Log.e("fileUpload", sizeMegabyte + " MB - error");
         return null;
      }
   }

}
