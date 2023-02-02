package com.bronx.bronx_helper.util;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class GlobalConstant {
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 400;
    public static final int INVALID_TOKEN_CODE = 401;
    public static final int DEACTIVATE_USER = 406;
    public static final int SERVER_MAINTAINANCE = 503;
    public static final String VOICE_EXTENSION = ".ogg";
    public static final String IMAGE_EXTENSION = ".jpg";
    public static final String VIDEO_EXTENSION = ".mp4";
    public static final String MUSIC_EXTENSION = ".mp3";
    public static final String PDF_EXTENSION = ".pdf";
    @IntDef({SUCCESS_CODE, FAIL_CODE, INVALID_TOKEN_CODE, DEACTIVATE_USER, SERVER_MAINTAINANCE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface API_STATUS_CODE {
    }

    public static final String SYSTEM_DISPLAY_DATE_FORMAT = "EEE, dd MMM yyyy";
    public static final String SYSTEM_DATE_TIME_FORMAT = "dd-MMM-yyyy hh:mm a";
    public static final String SYSTEM_DATE_FORMAT = "dd MMM, yyyy";
    public static final String GENERAL_DATE = "dd MM yyyy";
    public static final String SYSTEM_TIME_FORMAT = "hh:mm a";
    public static final String FORMAT_FULL_MONTH = "dd MMMM yyyy";
    public static final String FORMAT_FULL_MONTH_AM_PM = "dd MMMM yyyy | a";
    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String SERVER_DATE_TIME_MINUTE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String OUTPUT_TIME_FORMAT = "hh:mm a";
    public static final String MMMM_DD_FORMAT = "MMMM dd";
    public static final String FORMAT_DMY = "dd-MMM-yyyy";
    public static final String DATE_TIME_ZONE = "UTC";
    public static final String CALENDAR_DATE = "dd MMM yyyy HH:mm:ss";

    public enum TimeZoneString {

        CAMBODIA("Asia/Phnom_Penh"),
        AUSTRALIA("Australia/Sydney"),
        THAILAND("Asia/Bangkok"),
        CHINESE("Asia/Hong_Kong"),
        ENGLISH("Europe/London"),
        FRENCH("Europe/Paris"),
        KOREAN("Asia/Seoul"),
        JAKATA("Asia/Jakarta"),
        JAPAN("Asia/Tokyo");

        final String type;

        TimeZoneString(String type) {
            this.type = type;
        }

        public String getTimezone() {
            return type;
        }

    }

    public enum LocalizationString {

        KHMER("km"),
        CHINESE("zh"),
        TAIWAN("zh_TW"),
        ENGLISH("en"),
        SPANISH("es"),
        FRENCH("fr"),
        KOREAN("ko"),
        THAI("th"),
        VIETNAMESE("vi"),
        JAPAN("ja");

        final String type;

        LocalizationString(String type) {
            this.type = type;
        }

        public String getLocalize() {
            return type;
        }

    }

    public enum CurrencyString {

        RIEL("KHR"),
        DOLLAR("USD"),
        BAHT("THB"),
        AUS_DOLLAR("AUD"),
        KOREAN_WON("KRW"),
        CHINA("CNY"),
        SINGAPORE("SGD"),
        DONG("VND"),
        YEN("JPY"),
        EURO("EUR");

        final String type;

        CurrencyString(String type) {
            this.type = type;
        }

        public String getCurrencyCode() {
            return type;
        }

    }
}
