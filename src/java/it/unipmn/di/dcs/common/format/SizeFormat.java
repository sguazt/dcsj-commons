/*
 * Copyright (C) 2007-2012  Marco Guazzone
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

package it.unipmn.di.dcs.common.format;

/**
 * Formatter for various kind of size.
 *
 * <a href="http://physics.nist.gov/cuu/pdf/sp811.pdf">http://physics.nist.gov/cuu/pdf/sp811.pdf</a>
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class SizeFormat
{
	/**
	 * Returns a string representing a formatted byte size.
	 */
	public static String FormatByte(double value)
	{
		String unit = null;

		if ( value < 1024D )
		{
			// bytes
			unit = "B";
		}
		else if ( value < 1048576D )
		{
			// kilobytes
			value /= 1024;
			unit = "KB";
		}
		else if ( value < 1073741824D )
		{
			// megabytes
			value /= 1048576D;
			unit = "MB";
		}
		else if ( value < 1099511627776D )
		{
			// gigabytes
			value /= 1073741824D;
			unit = "GB";
		}
		else if ( value < 1125899906842624D )
		{
			// terabytes
			value /= 1099511627776D;
			unit = "TB";
		}
		else if ( value < 1152921504606846976D )
		{
			// petabytes
			value /= 1125899906842624D;
			unit = "PB";
		}
		else if ( value < 1180591620717411303424D )
		{
			// exabytes
			value /= 1152921504606846976D;
			unit = "EB";
		}
		else if ( value < 1208925819614629174706176D )
		{
			// zettabytes
			value /= 1180591620717411303424D;
			unit = "ZB";
		}
		else
		{
			// yottabytes
			value /= 1208925819614629174706176D;
			unit = "YB";
		}

		//return Double.toString( value ) + unit;
		return String.format( "%.2f%s", value, unit );
	}

	/**
	 * Returns a string representing a formatted time difference size.
	 *
	 * Time measure is intended as the duration in seconds of an event (for
	 * the clock time see method {@code FormatClockTimeDiff}).
	 */
	public static String FormatTimeDiff(double value, boolean shortFmt)
	{
		String unit = null;

		if ( value < 1.0e-21 )
		{
			// yoctosecond
			value *= 1.0e+24;
			unit = ( shortFmt ? "ys" : "ysec(s)" );
		}
		if ( value < 1.0e-18 )
		{
			// zeptosecond
			value *= 1.0e+21;
			unit = ( shortFmt ? "zs" : "zeptosec(s)" );
		}
		if ( value < 1.0e-15 )
		{
			// attosecond
			value *= 1.0e+18;
			unit = ( shortFmt ? "as" : "attosec(s)" );
		}
		if ( value < 1.0e-12 )
		{
			// femtosecond
			value *= 1.0e+15;
			unit = ( shortFmt ? "fs" : "femtosec(s)" );
		}
		if ( value < 1.0e-9 )
		{
			// picosecond
			value *= 1.0e+12;
			unit = ( shortFmt ? "ps" : "picosec(s)" );
		}
		else if ( value < 1.0e-6 )
		{
			// picosecond
			value *= 1.0e+9;
			unit = ( shortFmt ? "ns" : "nanosec(s)" );
		}
		else if ( value < 1.0e-3 )
		{
			// microsecond
			value *= 1.0e+6;
			unit = ( shortFmt ? "mus" : "microsec(s)" );
		}
		else if ( value < 0 )
		{
			// millisecond
			value *= 1.0e+3;
			unit = ( shortFmt ? "ms" : "millisec(s)" );
		}
		else if ( value < 1.0e+3 )
		{
			// second
			unit = ( shortFmt ? "s" : "sec(s)" );
		}
		else if ( value < 1.0e+6 )
		{
			// kilosecond
			value /= 1.0e+3;
			unit = ( shortFmt ? "ks" : "kilosec(s)" );
		}
		else if ( value < 1.0e+9 )
		{
			// Megasecond
			value /= 1.0e+6;
			unit = ( shortFmt ? "Ms" : "Megasec(s)" );
		}
		else if ( value < 1.0e+12 )
		{
			// Gigasecond
			value /= 1.0e+9;
			unit = ( shortFmt ? "Gs" : "Gigasec(s)" );
		}
		else if ( value < 1.0e+15 )
		{
			// Terasecond
			value /= 1.0e+12;
			unit = ( shortFmt ? "Ts" : "Terasec(s)" );
		}
		else if ( value < 1.0e+18 )
		{
			// Petasecond
			value /= 1.0e+15;
			unit = ( shortFmt ? "Ps" : "Petasec(s)" );
		}
		else if ( value < 1.0e+21 )
		{
			// Exasecond
			value /= 1.0e+18;
			unit = ( shortFmt ? "Es" : "Exasec(s)" );
		}
		else if ( value < 1.0e+24 )
		{
			// Zettasecond
			value /= 1.0e+21;
			unit = ( shortFmt ? "Zs" : "Zettasec(s)" );
		}
		else
		{
			// Yottasecond
			value /= 1.0e+24;
			unit = ( shortFmt ? "Ys" : "Yottasec(s)" );
		}

		//return Double.toString( value ) + unit;
		return String.format( "%.2f%s", value, unit );
	}

	/**
	 * Returns a string representing a formatted time difference size.
	 *
	 * Time measure is intended as a difference of clock times (in seconds).
	 */
	public static String FormatClockTimeDiff(double value, boolean shortFmt)
	{
		String unit = null;

		if ( value < 1 )
		{
			// millisecond
			value *= 1.0e+3;
			unit = ( shortFmt ? "ms" : "millisec(s)" );

			return String.format( "%.2f%s", value, unit );
		}

		long q = 1; // the quotient
		long r = 0; // the remainder;
		long divisor = 1;

		if ( value < 60 )
		{
			// second
			divisor = 1;
			unit = ( shortFmt ? "s" : "sec(s)" );
		}
		else if ( value < 3600 )
		{
			// minute
			divisor = 60;
			unit = ( shortFmt ? "m" : "min(s)" );
		}
		else if ( value < 86400 )
		{
			// hour
			divisor = 3600;
			unit = ( shortFmt ? "h" : "hour(s)" );
		}
		else if ( value < 604800 )
		{
			// day
			divisor = 86400;
			unit = ( shortFmt ? "d" : "day(s)" );
		}
		else if ( value < 2592000 )
		{
			// week
			divisor = 604800;
			unit = ( shortFmt ? "w" : "week(s)" );
		}
		else if ( value < 31536000 )
		{
			// month
			divisor = 2592000;
			unit = ( shortFmt ? "M" : "month(s)" );
		}
		else
		{
			// year
			divisor = 31536000;
			unit = ( shortFmt ? "y" : "year(s)" );
		}

		long ivalue = (long) Math.floor(value);
		double dec = value - ivalue;
		q = ivalue / divisor;
		r = ivalue % divisor;

		if ( r != 0 )
		{
			//unit = unit + ", " + FormatClockTimeDiff( (q - r * divisor), shortFmt );
			unit = unit + ", " + FormatClockTimeDiff( r, shortFmt );
		}
		if ( dec != 0 )
		{
			unit = unit + ", "  + FormatClockTimeDiff( dec, shortFmt );
		}

		//return String.format( "%.2f%s", value, unit );
		return String.format( "%d%s", q, unit );
	}
}
