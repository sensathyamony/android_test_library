package com.bronx.bronx_helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.bronx.bronx_helper.util.BaseHelper;

import java.util.ArrayList;

public class PickHelper {

    /* public static void multiImagePicker(Activity activity, int requestCode){
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(pickIntent, "Select Images"), requestCode);
    }

    public static void shareMultipleImage(Context context, ArrayList<Uri> imageList){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("image/*");

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageList);
        context.startActivity(shareIntent);
    }

    public static void multiVideoPicker(Activity activity, int requestCode){
        Intent pickIntent = new Intent();
        pickIntent.setType("video/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(pickIntent, "Select Videos"), requestCode);
    }

    public static void shareMultipleVideos(Context context, ArrayList<Uri> videoList){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("video/*");

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, videoList);
        context.startActivity(shareIntent);
    } */

    public static void filePicker(Activity activity, int requestCode){
        Intent pickIntent = new Intent();
        pickIntent.setType("*/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(pickIntent, "Select Images"), requestCode);
    }

    public static void shareFile(Context context, ArrayList<Uri> fileList){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        String mime = BaseHelper.getMimeType(context, fileList.get(0));
        shareIntent.setType(mime);

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileList);
        context.startActivity(shareIntent);
    }
}
