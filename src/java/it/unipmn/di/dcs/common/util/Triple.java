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

package it.unipmn.di.dcs.common.util;

/**
 * Store a triple of objects, possibly of different types.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class Triple<V1,V2,V3>
{
	private V1 v1; /** The first element of the triple */
	private V2 v2; /** The second element of the triple */
	private V3 v3; /** The third element of the triple */

	/** A constructor. */
	public Triple(V1 v1, V2 v2, V3 v3)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	/**
	 * Returns the first element of the triple.
	 */
	public V1 getFirst()
	{
		return this.v1;
	}

	/**
	 * Returns the second element of the triple.
	 */
	public V2 getSecond()
	{
		return this.v2;
	}

	/**
	 * Returns the third element of the triple.
	 */
	public V3 getThird()
	{
		return this.v3;
	}

	@Override
	public String toString()
	{
		return "<" + this.v1 + ", " + this.v2 + ", " + this.v3 + ">";
	}
}
