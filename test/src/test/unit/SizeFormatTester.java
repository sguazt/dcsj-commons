/*
 * Copyright (C) 2008-2012  Marco Guazzone
 *                          [Distributed Computing System (DCS) Group,
 *                           Computer Science Institute,
 *                           Department of Science and Technological Innovation,
 *                           University of Piemonte Orientale,
 *                           Alessandria (Italy)]
 *
 * This file is part of dcj-commons.
 *
 * dcsj-commons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dcsj-commons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with dcsj-commons.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.unit;

import it.unipmn.di.dcs.common.format.SizeFormat;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * Test class for SizeFormat.
 *
 * @author <a href="mailto:marco.guazzone@mfn.unipmn.it">Marco Guazzone</a>
 */
public class SizeFormatTester
{
	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testByte()
	{
		assertEquals( "1.50B", SizeFormat.FormatByte( 1.5 ) );
	}

	@Test
	public void testKiloByte()
	{
		assertEquals( "1.50KB", SizeFormat.FormatByte( 1.5 * 1024 ) );
	}

	@Test
	public void testMegaByte()
	{
		assertEquals( "1.50MB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,2) ) );
	}

	@Test
	public void testGigaByte()
	{
		assertEquals( "1.50GB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,3) ) );
	}

	@Test
	public void testTeraByte()
	{
		assertEquals( "1.50TB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,4) ) );
	}

	@Test
	public void testPetaByte()
	{
		assertEquals( "1.50PB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,5) ) );
	}

	@Test
	public void testExaByte()
	{
		assertEquals( "1.50EB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,6) ) );
	}

	@Test
	public void testZettaByte()
	{
		assertEquals( "1.50ZB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,7) ) );
	}

	@Test
	public void testYottaByte()
	{
		assertEquals( "1.50YB", SizeFormat.FormatByte( 1.5 * Math.pow(1024,8) ) );
	}

	@Test
	public void testSecondClockTimeShort()
	{
		assertEquals( "1s", SizeFormat.FormatClockTimeDiff( 1, true ) );
	}

	@Test
	public void testSecondClockTimeLong()
	{
		assertEquals( "1sec(s)", SizeFormat.FormatClockTimeDiff( 1, false ) );
	}

	@Test
	public void testMinuteClockTimeShort()
	{
		assertEquals( "1m", SizeFormat.FormatClockTimeDiff( 60, true ) );
	}

	@Test
	public void testMinuteClockTimeLong()
	{
		assertEquals( "1min(s)", SizeFormat.FormatClockTimeDiff( 60, false ) );
	}

	@Test
	public void testHourClockTimeShort()
	{
		assertEquals( "1h", SizeFormat.FormatClockTimeDiff( 3600, true ) );
	}

	@Test
	public void testHourClockTimeLong()
	{
		assertEquals( "1hour(s)", SizeFormat.FormatClockTimeDiff( 3600, false ) );
	}

	@Test
	public void testDayClockTimeShort()
	{
		assertEquals( "1d", SizeFormat.FormatClockTimeDiff( 86400, true ) );
	}

	@Test
	public void testDayClockTimeLong()
	{
		assertEquals( "1day(s)", SizeFormat.FormatClockTimeDiff( 86400, false ) );
	}

	@Test
	public void testWeekClockTimeShort()
	{
		assertEquals( "1w", SizeFormat.FormatClockTimeDiff( 7*86400, true ) );
	}

	@Test
	public void testWeekClockTimeLong()
	{
		assertEquals( "1week(s)", SizeFormat.FormatClockTimeDiff( 7*86400, false ) );
	}

	@Test
	public void testMonthClockTimeShort()
	{
		assertEquals( "1M", SizeFormat.FormatClockTimeDiff( 30*86400, true ) );
	}

	@Test
	public void testMonthClockTimeLong()
	{
		assertEquals( "1month(s)", SizeFormat.FormatClockTimeDiff( 30*86400, false ) );
	}

	@Test
	public void testYearClockTimeShort()
	{
		assertEquals( "1y", SizeFormat.FormatClockTimeDiff( 365*86400, true ) );
	}

	@Test
	public void testYearClockTimeLong()
	{
		assertEquals( "1year(s)", SizeFormat.FormatClockTimeDiff( 365*86400, false ) );
	}

	@Test
	public void testDecimalSecondClockTimeShort()
	{
		assertEquals( "1s, 500.00ms", SizeFormat.FormatClockTimeDiff( 1 + 0.5, true ) );
	}

	@Test
	public void testDecimalSecondClockTimeLong()
	{
		assertEquals( "1sec(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 1 + 0.5, false ) );
	}

	@Test
	public void testDecimanlMinuteClockTimeShort()
	{
		assertEquals( "1m, 500.00ms", SizeFormat.FormatClockTimeDiff( 60 + 0.5, true ) );
	}

	@Test
	public void testDecimanlMinuteClockTimeLong()
	{
		assertEquals( "1min(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 60 + 0.5, false ) );
	}

	@Test
	public void testDecimanlHourClockTimeShort()
	{
		assertEquals( "1h, 500.00ms", SizeFormat.FormatClockTimeDiff( 3600 + 0.5, true ) );
	}

	@Test
	public void testDecimanlHourClockTimeLong()
	{
		assertEquals( "1hour(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 3600 + 0.5, false ) );
	}

	@Test
	public void testDecimanlDayClockTimeShort()
	{
		assertEquals( "1d, 500.00ms", SizeFormat.FormatClockTimeDiff( 86400 + 0.5, true ) );
	}

	@Test
	public void testDecimanlDayClockTimeLong()
	{
		assertEquals( "1day(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 86400 + 0.5, false ) );
	}

	@Test
	public void testDecimanlWeekClockTimeShort()
	{
		assertEquals( "1w, 500.00ms", SizeFormat.FormatClockTimeDiff( 7*86400 + 0.5, true ) );
	}

	@Test
	public void testDecimanlWeekClockTimeLong()
	{
		assertEquals( "1week(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 7*86400 + 0.5, false ) );
	}

	@Test
	public void testDecimanlMonthClockTimeShort()
	{
		assertEquals( "1M, 500.00ms", SizeFormat.FormatClockTimeDiff( 30*86400 + 0.5, true ) );
	}

	@Test
	public void testDecimanlMonthClockTimeLong()
	{
		assertEquals( "1month(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 30*86400 + 0.5, false ) );
	}

	@Test
	public void testDecimanlYearClockTimeShort()
	{
		assertEquals( "1y, 500.00ms", SizeFormat.FormatClockTimeDiff( 365*86400 + 0.5, true ) );
	}

	@Test
	public void testDecimanlYearClockTimeLong()
	{
		assertEquals( "1year(s), 500.00millisec(s)", SizeFormat.FormatClockTimeDiff( 365*86400 + 0.5, false ) );
	}

	@After
	public void tearDown()
	{
		// empty
	}

	public static void main(String[] args)
	{
		JUnitCore.main( ExperimentalAnnotationTester.class.getName() );
	}
}
