package com.bronx.bronx_helper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringHelper {


    public static String getDate(String format, TimeZone timeZone){
        Date calendar = Calendar.getInstance().getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        formatDate.setTimeZone(timeZone);

        return formatDate.format(calendar.getTime());
    }

    public static String convertDateFormat(String value, String mainFormat, String outputFormat, String localize){
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(mainFormat);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat, new Locale(localize));
        try {
            Date date = inputDateFormat.parse(value);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            return "Error "+e.getMessage();
        }
    }

    public static String convertStringToCurrency(String currencyCode, double value){
        Currency currency = Currency.getInstance(currencyCode);
        String symbolCur = currency.getSymbol();

        return symbolCur+" "+String.format(Locale.getDefault(),"%,.2f", value);
    }

}
