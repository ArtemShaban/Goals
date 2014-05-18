package by.bsu.goals.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.bsu.goals.log.Logger;

public class Util {

	private static DateFormat simpleDateFormat;
	public static final String DATE_TEMPLATE_dd_MMM_yyyy = "dd MMM yyyy";
	public static final String DATE_TEMPLATE_dd_MMM_yyyy_kk_mm = "dd MMM yyyy kk:mm";
	private static Logger logger = new Logger(Util.class);

	public static Date parseStringToDate(String template, String target) {
		simpleDateFormat = new SimpleDateFormat(template);
		Date result;
		try {
			result = simpleDateFormat.parse(target);
		} catch (ParseException e) {
			logger.e("Exception rised when string parsed to date", e);
			throw new RuntimeException();
		}
		return result;
	}

	public static String formatDateToString(String template, Date target) {
		simpleDateFormat = new SimpleDateFormat(template);
		String result = simpleDateFormat.format(target);
		return result;
	}

	public static boolean isValidDates(String startDate, String finichDate) {
		Date stDate = parseStringToDate(DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
				startDate);
		Date fnDate = parseStringToDate(DATE_TEMPLATE_dd_MMM_yyyy_kk_mm,
				finichDate);
		if (fnDate.compareTo(stDate) == -1) {
			return false;
		} else {
			return true;
		}
	}

}
