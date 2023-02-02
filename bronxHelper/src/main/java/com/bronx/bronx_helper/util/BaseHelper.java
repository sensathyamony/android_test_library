package com.bronx.bronx_helper.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Arrays;

public class BaseHelper {
    private static final String STRING_DOT = ".";
    private static final String REGEX_DOT = "\\.";

    private static String toRegex(String text){
        return text.replace(STRING_DOT, REGEX_DOT);
    }

    public static String getPath(Context context, Uri uri){
        String[] filePathColumn = {MediaStore.MediaColumns.DISPLAY_NAME};
        boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)){
            if (isExternalStorageDocument(uri)){
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(toRegex(":"));
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory().toString()+"/"+split[1];
                }
            }
            else if(isDownloadsDocument(uri)){
                String id = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Cursor cursor = null;
                    try {
                        cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                        if (cursor != null && cursor.moveToFirst()){
                            String fileName = cursor.getString(0);
                            String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            if (!TextUtils.isEmpty(path)){
                                return path;
                            }
                        }
                    }finally {
                        cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)){
                        if(id.startsWith("raw:")){
                            return id.replaceFirst(toRegex("raw:"), "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{"content://downloads/public_downloads", "content://downloads/my_downloads"};
                        for (String contentUriPrefix: contentUriPrefixesToTry) {
                            try {
                                Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), java.lang.Long.parseLong(id));
                                return getDataColumn(context, contentUri, null, null);
                            }catch (NumberFormatException err){
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }
                    }
                } else {
                    id = DocumentsContract.getDocumentId(uri);
                    if (id.startsWith("raw:")){
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.parseLong(id));
                        return getDataColumn(context, contentUri, null, null);
                    }catch (NumberFormatException err){
                        err.printStackTrace();
                    }
                }
            }
            else if (isMediaDocument(uri)){
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(toRegex(":"));
                String type = split[0];
                Uri contentUri = null;
                switch (type){
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }
                String selection = "_id=?";
                String[] selectionArgs = {};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            return getDataColumn(context, uri, null, null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs){
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(column);
                return cursor.getString(columnIndex);
            }
        }finally {
            cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri){
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri){
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri){
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getMimeType(Context context, Uri uri) {

        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;

    }

    public static String getMimeTypeFromFile(File file) {

        String mimeType = "*/*";

        if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
            // Word document
            mimeType = "application/msword";
        } else if (file.toString().contains(".pdf")) {
            // PDF file
            mimeType = "application/pdf";

        } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
            // Powerpoint file
            mimeType = "application/vnd.ms-powerpoint";

        } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
            // Excel file
            mimeType = "application/vnd.ms-excel";
        } else if (file.toString().contains(".zip")) {
            // ZIP file
            mimeType = "application/zip";
        } else if (file.toString().contains(".rar")) {
            // RAR file
            mimeType = "application/x-rar-compressed";
        } else if (file.toString().contains(".rtf")) {
            // RTF file
            mimeType = "application/rtf";
        } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
            // WAV audio file
            mimeType = "audio/x-wav";
        } else if (file.toString().contains(".gif")) {
            // GIF file
            mimeType = "image/gif";
        } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg")
                || file.toString().contains(".png")) {
            // JPG file
            mimeType = "image/jpeg";

        } else if (file.toString().contains(".txt")) {
            // Text file
            mimeType = "text/plain";
        } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg") ||
                file.toString().contains(".mpeg") || file.toString().contains(".mpe") ||
                file.toString().contains(".mp4") || file.toString().contains(".avi")) {
            // Video files
            mimeType = "video/*";
        }

        return mimeType;
    }
}
