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

package it.unipmn.di.dcs.common.format;

import it.unipmn.di.dcs.common.CommonException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing measure unit (and associated amounts) from string.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class SizeParser
{
	private static final Pattern BYTE_PATTERN = Pattern.compile("(\\d+(?:\\.\\d+)?)([BKMGPT]?)");
	private static final Pattern FREQUENCY_PATTERN = Pattern.compile("(\\d+(?:\\.\\d+)?)([KMGPT]?Hz)?");
	private static final Pattern BPS_PATTERN = Pattern.compile("(\\d+(?:\\.\\d+)?)([KMGPT]?bps)?");

	/** Parse a string looking for byte size specification. */
	public static float ParseByteSize(String s, ByteMeasureUnit defaultUnit, ByteMeasureUnit returnUnit) throws CommonException
	{
		float amount = 0;
		Matcher matcher = null;

		matcher = BYTE_PATTERN.matcher(s);
		if ( !matcher.matches() )
		{
			throw new CommonException("Illegal Argument: '" + s + "' is an unknown byte specification.");
		}

		ByteMeasureUnit unit = null;

		amount = Float.parseFloat( matcher.group(1) );
		unit = ByteMeasureUnit.FromString( matcher.group(2) );

		if ( unit == null )
		{
			unit = defaultUnit;
		}

		switch (unit)
		{
			case BYTE:
				amount *= 1;
				break;
			case KILOBYTE:
				amount *= 1024;
				break;
			case MEGABYTE:
				amount *= 1024 * 1024;
				break;
			case GIGABYTE:
				amount *= 1024 * 1024 * 1024;
				break;
			case TERABYTE:
				amount *= 1024 * 1024 * 1024 * 1024;
				break;
			case PETABYTE:
				amount *= 1024 * 1024 * 1024 * 1024 * 1024;
				break;
		}

		switch (returnUnit)
		{
			case BYTE:
				amount /= 1;
				break;
			case KILOBYTE:
				amount /= 1024;
				break;
			case MEGABYTE:
				amount /= 1024 * 1024;
				break;
			case GIGABYTE:
				amount /= 1024 * 1024 * 1024;
				break;
			case TERABYTE:
				amount /= 1024 * 1024 * 1024 * 1024;
				break;
			case PETABYTE:
				amount /= 1024 * 1024 * 1024 * 1024 * 1024;
				break;
		}

		return amount;
	}

	/** Parse a string looking for frequency size specification. */
	public static float ParseFrequencySize(String s, FrequencyMeasureUnit defaultUnit, FrequencyMeasureUnit returnUnit) throws CommonException
	{
		float amount = 0;
		Matcher matcher = null;

		matcher = FREQUENCY_PATTERN.matcher(s);
		if ( !matcher.matches() )
		{
			throw new CommonException("Illegal Argument: '" + s + "' is an unknown frequency specification.");
		}

		FrequencyMeasureUnit unit = null;

		amount = Float.parseFloat( matcher.group(1) );
		unit = FrequencyMeasureUnit.FromString( matcher.group(2) );

		if ( unit == null )
		{
			unit = defaultUnit;
		}

		switch (unit)
		{
			case HERTZ:
				amount *= 1;
				break;
			case KILOHERTZ:
				amount *= 1000;
				break;
			case MEGAHERTZ:
				amount *= 1000 * 1000;
				break;
			case GIGAHERTZ:
				amount *= 1000 * 1000 * 1000;
				break;
			case TERAHERTZ:
				amount *= 1000 * 1000 * 1000 * 1000;
				break;
			case PETAHERTZ:
				amount *= 1000 * 1000 * 1000 * 1000 * 1000;
				break;
		}

		switch (returnUnit)
		{
			case HERTZ:
				amount /= 1;
				break;
			case KILOHERTZ:
				amount /= 1000;
				break;
			case MEGAHERTZ:
				amount /= 1000 * 1000;
				break;
			case GIGAHERTZ:
				amount /= 1000 * 1000 * 1000;
				break;
			case TERAHERTZ:
				amount /= 1000 * 1000 * 1000 * 1000;
				break;
			case PETAHERTZ:
				amount /= 1000 * 1000 * 1000 * 1000 * 1000;
				break;
		}

		return amount;
	}

	/** Parse a string looking for bps size specification. */
	public static float ParseBpsSize(String s, BpsMeasureUnit defaultUnit, BpsMeasureUnit returnUnit) throws CommonException
	{
		float amount = 0;
		Matcher matcher = null;

		matcher = BPS_PATTERN.matcher(s);
		if ( !matcher.matches() )
		{
			throw new CommonException("Illegal Argument: '" + s + "' is an unknown bps specification.");
		}

		BpsMeasureUnit unit = null;

		amount = Float.parseFloat( matcher.group(1) );
		unit = BpsMeasureUnit.FromString( matcher.group(2) );

		if ( unit == null )
		{
			unit = defaultUnit;
		}

		switch (unit)
		{
			case BPS:
				amount *= 1;
				break;
			case KILOBPS:
				amount *= 1000;
				break;
			case MEGABPS:
				amount *= 1000 * 1000;
				break;
			case GIGABPS:
				amount *= 1000 * 1000 * 1000;
				break;
			case TERABPS:
				amount *= 1000 * 1000 * 1000 * 1000;
				break;
			case PETABPS:
				amount *= 1000 * 1000 * 1000 * 1000 * 1000;
				break;
		}

		switch (returnUnit)
		{
			case BPS:
				amount /= 1;
				break;
			case KILOBPS:
				amount /= 1000;
				break;
			case MEGABPS:
				amount /= 1000 * 1000;
				break;
			case GIGABPS:
				amount /= 1000 * 1000 * 1000;
				break;
			case TERABPS:
				amount /= 1000 * 1000 * 1000 * 1000;
				break;
			case PETABPS:
				amount /= 1000 * 1000 * 1000 * 1000 * 1000;
				break;
		}

		return amount;
	}
}
