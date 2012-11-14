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
 * Enumeration for frequency measure unit.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public enum FrequencyMeasureUnit
{
	HERTZ		("Hz"),
	KILOHERTZ	("KHz"),
	MEGAHERTZ	("MHz"),
	GIGAHERTZ	("GHz"),
	TERAHERTZ	("THz"),
	PETAHERTZ	("PHz");

	private String unit;

	/** A constructor. */
	FrequencyMeasureUnit(String unit)
	{
		this.unit = unit;
	}

	/**
	 * Parse the given string looking for a string representation of a
	 * frequency measure unit.
	 *
	 * @return The found frequency measure unit or [@code null} if not
	 * found.
	 */
	public static FrequencyMeasureUnit FromString(String s)
	{
		if ( HERTZ.unit.equals(s) )
		{
			return HERTZ;
		}
		else if ( KILOHERTZ.unit.equals(s) )
		{
			return KILOHERTZ;
		}
		else if ( MEGAHERTZ.unit.equals(s) )
		{
			return MEGAHERTZ;
		}
		else if ( GIGAHERTZ.unit.equals(s) )
		{
			return GIGAHERTZ;
		}
		else if ( TERAHERTZ.unit.equals(s) )
		{
			return TERAHERTZ;
		}
		else if ( PETAHERTZ.unit.equals(s) )
		{
			return PETAHERTZ;
		}

		return null;
	}
}
