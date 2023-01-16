package com.bronx.bronx_helper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FunctionHelper {

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

   public static void snackBarPopUpError(@NonNull View view, String textMsg, int colorRes, int textColor){
      Snackbar.make(view, textMsg, Snackbar.LENGTH_LONG)
              .setBackgroundTint(ContextCompat.getColor(view.getContext(), colorRes))
              .setTextColor(ContextCompat.getColor(view.getContext(), textColor))
              .show();
   }
}
