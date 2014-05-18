package by.bsu.goals.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	private static DateFormat simpleDateFormat;
	public static final String DATE_TEMPLATE_dd_MMM_yyyy="dd MMM yyyy";
	public static final String DATE_TEMPLATE_dd_MMM_yyyy_kk_mm="dd MMM yyyy kk:mm";
	
	public static Date parseStringToDate(String template, String target) throws ParseException{
		simpleDateFormat = new SimpleDateFormat(template);
		Date result = simpleDateFormat.parse(target);
		return result;
	}
	
	public static String formatDateToString(String template, Date target){
		simpleDateFormat = new SimpleDateFormat(template);
		String result = simpleDateFormat.format(target);
		return result;
	}

	public static boolean isValidDates(String startDate, String finichDate) throws ParseException  {
		Date stDate = parseStringToDate(DATE_TEMPLATE_dd_MMM_yyyy_kk_mm, startDate);
		Date fnDate = parseStringToDate(DATE_TEMPLATE_dd_MMM_yyyy_kk_mm, finichDate);
		if(fnDate.compareTo(stDate) == -1){
			return false;
		} else {
			return true;
		}
	}
	
}
