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

import it.unipmn.di.dcs.common.annotation.Experimental;

/**
 * Utility class for arrays.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class Arrays
{
	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(byte[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(boolean[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(char[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(double[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(float[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(int[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(long[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static boolean IsNullOrEmpty(short[] array)
	{
		return (array == null || array.length == 0);
	}

	/** Returns {@code true} if the given array is null or empty. */
	public static <T> boolean IsNullOrEmpty(T[] array)
	{
		return (array == null || array.length == 0);
	}

	/**
	 * Returns a portion of an array.
	 *
	 * @param array The array.
	 * @param beginPos The position where the sub-array must begin.
	 * @param len The length of the sub-array.
	 * @return A portion of the array <code>array</code> beginning at
	 * position <code>beginPos</code> and with length <code>len</code>.
	 */
	public static char[] SubArray(char[] array, int beginPos, int len)
	{
		char[] result = new char[ len ];
		int endPos = beginPos + len - 1;

		for ( int i = beginPos; i <= endPos; i++ )
		{
			result[i] = array[i];
		}

		return result;
	}

	/**
	 * This version of <code>deepToString</code> is very generic;
	 * it works both with primitive types and object.
	 * However the main disadvantages are:
	 * <ul>
	 * <li>Is slower than specialized/Generics version (since use
	 * reflection</li>
	 * <li>The parameter say nothing about it is an array; i.e. we
	 * may pass a Dictionary as the first parameter and this works
	 * at runtime; maybe we want a compile time error.</li>
	 * <li>We must force the array homogeneity (i.e. the type of
	 * array's items must be the same or at least compatible).</li>
	 * </ul>
	 * So the status of this method for now remains <em>Experimental</em>.
	 */
	@Experimental
	private static String _DeepToString(Object o, String sep)
	{
		StringBuffer buf = new StringBuffer();

		if ( o == null || !o.getClass().isArray() )
		{
			// o not an array

			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( o == null ? "[null]" : o );
		}
		else
		{
			int len = java.lang.reflect.Array.getLength( o );
			for ( int i = 0; i < len; i++ )
			{
				//_deepToString( Array.get( o, i ), sep, buf ); // recursive version
				if ( buf.length() > 0 )
				{
					buf.append( sep );
				}
				buf.append( java.lang.reflect.Array.get( o, i ) );
			}
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 *
	 * Note: Java don't allow to use primitive type as generic type. So
	 * for primitive type use the specialized method.
	 */
	public static <T> String DeepToString(T[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( T t : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( t.toString() );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(char[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( char c : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( c );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(byte[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( byte b : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( b );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(boolean[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( boolean b : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( b );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(int[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( int i : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( i );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(short[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( short s : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( s );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(long[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( long l : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( l );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(float[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( float f : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( f );
		}

		return buf.toString();
	}

	/**
	 * Returns a string representation of the "deep contents" of the
	 * specified array.
	 *
	 * @param array The array.
	 * @param sep The array items separator.
	 * @return The string representation of the contents of the array.
	 */
	public static String DeepToString(double[] array, String sep)
	{
		StringBuffer buf = new StringBuffer();

		for ( double d : array )
		{
			if ( buf.length() > 0 )
			{
				buf.append( sep );
			}
			buf.append( d );
		}

		return buf.toString();
	}
}
