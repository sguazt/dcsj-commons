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

/**
 * Enum representing a byte measure unit.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public enum ByteMeasureUnit
{
	BYTE		("B"),
	KILOBYTE	("K"),
	MEGABYTE	("M"),
	GIGABYTE	("G"),
	TERABYTE	("T"),
	PETABYTE	("P");
	//EXABYTE

	private String unit;

	/** A constructor. */
	ByteMeasureUnit(String unit)
	{
		this.unit = unit;
	}

	/**
	 * Parse the given string looking for a string representation of a
	 * byte measure unit.
	 *
	 * @return The found byte measure unit or [@code null} if not found.
 	 */
	public static ByteMeasureUnit FromString(String s)
	{
		if ( BYTE.unit.equals(s) )
		{
			return BYTE;
		}
		else if ( KILOBYTE.unit.equals(s) )
		{
			return KILOBYTE;
		}
		else if ( MEGABYTE.unit.equals(s) )
		{
			return MEGABYTE;
		}
		else if ( GIGABYTE.unit.equals(s) )
		{
			return GIGABYTE;
		}
		else if ( PETABYTE.unit.equals(s) )
		{
			return PETABYTE;
		}
		else if ( TERABYTE.unit.equals(s) )
		{
			return TERABYTE;
		}

		return null;
	}
}
