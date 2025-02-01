package com.customerloan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	private static final String FORMAT_DD_MM_YYYY = "dd.MM.yyyy";

	public static Date getFirstDayOfNextMonth(Date givenCurrentDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenCurrentDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfNextMonth = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        String formattedDate = sdf.format(firstDayOfNextMonth);
        return sdf.parse(formattedDate); 
	}
	
	public static Date getDateFormatted(Calendar givenCalender) throws ParseException {
		Date givenDate = givenCalender.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        String formattedDate = sdf.format(givenDate);
        return sdf.parse(formattedDate); 
	}
	
	public static Calendar getFirstDayOfNextMonthAsCalendar() {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar; 
	}
	
	public static Date getDateFromCalendar(Calendar givenCalender) {
		return givenCalender.getTime();
	}
}