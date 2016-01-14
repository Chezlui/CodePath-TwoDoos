package es.quizit.twodoos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chezlui on 14/01/2016.
 */
public class Utils {

	public static String convertDate2FriendlyString(long milliseconds) {
		String format = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

		return sdf.format(new Date(milliseconds));
	}

	public static long convertFriendlyDate2Milliseconds(String date) {
		String format = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

		try {
			return (sdf.parse(date)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
