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
 * Enumeration for bit-per-second measure unit.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public enum BpsMeasureUnit
{
	BPS	("bps"),
	KILOBPS	("Kbps"),
	MEGABPS	("Mbps"),
	GIGABPS	("Gbps"),
	TERABPS	("Tbps"),
	PETABPS	("Pbps");

	private String unit;

	/** A constructor. */
	BpsMeasureUnit(String unit)
	{
		this.unit = unit;
	}

	/**
	 * Parse the given string looking for a string representation of a
	 * bit-per-second measure unit.
	 *
	 * @return The found bit-per-second measure unit or [@code null} if
	 * not found.
	 */
	public static BpsMeasureUnit FromString(String s)
	{
		if ( BPS.unit.equals(s) )
		{
			return BPS;
		}
		else if ( KILOBPS.unit.equals(s) )
		{
			return KILOBPS;
		}
		else if ( MEGABPS.unit.equals(s) )
		{
			return MEGABPS;
		}
		else if ( GIGABPS.unit.equals(s) )
		{
			return GIGABPS;
		}
		else if ( TERABPS.unit.equals(s) )
		{
			return TERABPS;
		}
		else if ( PETABPS.unit.equals(s) )
		{
			return PETABPS;
		}

		return null;
	}
}
