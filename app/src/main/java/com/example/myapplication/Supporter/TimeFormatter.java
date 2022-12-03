package com.example.myapplication.Supporter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

    public static String FormatToHourTime(long seconds)
    {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60));
        return String.format("%d:%02d:%02d", h,m,s);
    }

    public static String FormatDateTime(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static Date convertToDate(String strDate)
    {
        Date date = null;
        try {
            date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
