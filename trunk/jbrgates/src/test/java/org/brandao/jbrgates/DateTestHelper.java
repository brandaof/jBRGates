package org.brandao.jbrgates;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateTestHelper {

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("'\"'yyyy-MM-dd'T'hh:mm:ss.sssXXX'\"'");
	
	public static final Date testDateValue;

	public static final Date[] testDateArrayValue;

	public static final List<Date> testDateCollectionValue;

	static{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		
		testDateValue = cal.getTime();
		
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 3);
		
		testDateArrayValue = new Date[]{testDateValue, cal.getTime()};
		
		testDateCollectionValue = Arrays.asList(testDateArrayValue);
	}

	public static final Timestamp testTimestampValue;

	public static final Timestamp[] testTimestampArrayValue;

	public static final List<Timestamp> testTimestampCollectionValue;

	static{
		testTimestampValue = new Timestamp(testDateValue.getTime());
		
		testTimestampArrayValue = 
			new Timestamp[]{
				testTimestampValue, 
				new Timestamp(testDateArrayValue[1].getTime())};
		
		testTimestampCollectionValue = Arrays.asList(testTimestampArrayValue);
	}

	public static final Time testTimeValue;

	public static final Time[] testTimeArrayValue;

	public static final List<Time> testTimeCollectionValue;

	static{
		testTimeValue = new Time(testDateValue.getTime());
		
		testTimeArrayValue = 
			new Time[]{
				testTimeValue, 
				new Time(testDateArrayValue[1].getTime())};
		
		testTimeCollectionValue = Arrays.asList(testTimeArrayValue);
	}

	public static final Calendar testCalendarValue;

	public static final Calendar[] testCalendarArrayValue;

	public static final List<Calendar> testCalendarCollectionValue;

	static{
		testCalendarValue = GregorianCalendar.getInstance();
		testCalendarValue.setTime(testDateValue);

		Calendar testCalendar2Value = GregorianCalendar.getInstance();
		testCalendarValue.setTime(testDateArrayValue[1]);
		
		testCalendarArrayValue = 
			new Calendar[]{
				testCalendarValue, 
				testCalendar2Value};
		
		testCalendarCollectionValue = Arrays.asList(testCalendarArrayValue);
	}
	
}
