/*
 * Copyright (C) 2008  Distributed Computing System (DCS) Group, Computer
 * Science Department - University of Piemonte Orientale, Alessandria (Italy).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unipmn.di.dcs.common;

/**
 * Base class for exception thrown from the {@code it.unipmn.di.dcs.common}
 * classes.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class CommonException extends Exception
{
	/** A constructor. */
	public CommonException()
	{
		super();
	}

	/** A constructor. */
	public CommonException(String message)
	{
		super( message );
	}

	/** A constructor. */
	public CommonException(Throwable cause)
	{
		super( cause );
	}

	/** A constructor. */
	public CommonException(String message, Throwable cause)
	{
		super( message, cause );
	}
}
