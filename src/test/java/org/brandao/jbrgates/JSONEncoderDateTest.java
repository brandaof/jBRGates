/*
 * JBRGates http://jgates.sourceforge.net/
 * Copyright (C) 2010 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */


package org.brandao.jbrgates;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Afonso Brandao
 */
public class JSONEncoderDateTest extends TestCase implements Test{

    public JSONEncoderDateTest(){
        super();
    }

    public void testDate() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( DateTestHelper.testDateValue );
        assertEquals( DateTestHelper.dateFormat.format(DateTestHelper.testDateValue), jse.toString() );
    }

    public void testTime() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( DateTestHelper.testTimeValue );
        assertEquals( DateTestHelper.dateFormat.format(DateTestHelper.testTimeValue), jse.toString() );
    }

    public void testTimestamp() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( DateTestHelper.testTimestampValue );
        assertEquals( DateTestHelper.dateFormat.format(DateTestHelper.testTimestampValue), jse.toString() );
    }

    public void testCalendar() throws IOException{
        JSONEncoder jse = new JSONEncoder();
        jse.encode( DateTestHelper.testCalendarValue );
        assertEquals( DateTestHelper.dateFormat.format(DateTestHelper.testCalendarValue.getTime()), jse.toString() );
    }

}
