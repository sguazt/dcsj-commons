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

package it.unipmn.di.dcs.common.util;

import java.util.Random;

/**
 * Utility class for string manipulation.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class Strings
{
	public static String Join(String[] seq, String sep)
	{
		StringBuilder sb = new StringBuilder();

		for ( String s : seq )
		{
			if ( sb.length() > 0 && sep.length() > 0 )
			{
				sb.append( sep );
			}
			sb.append( s );
		}

		return sb.toString();
	}

	public static String[] Split(String t, String sep)
	{
		return t.split( sep );
	}

        /**
	 * Tells if the given string is {@code null} or empty
	 *
	 * @return {@code true} if the given string is {@code null} or
	 * empty; {@code false} otherwise.
	 */
	public static boolean IsNullOrEmpty(String s)
	{
		return ( s == null || s.length() == 0 );
	}

	/**
	 * Returns a boolean representation of the given string.
	 *
	 * @return <code>true</code> if <code>s</code> is either
	 * <code>"TRUE"</code> or <code>"YES"</code> or <code>"1"</code>
	 * (case insensitive); <code>false</code> otherwise.
	 */
	public static boolean GetBoolean(String s)
	{
		return (
			s != null
			&& (
				"TRUE".equalsIgnoreCase(s)
				|| "YES".equalsIgnoreCase(s)
				|| "1".equalsIgnoreCase(s)
			)
		);
	}

	/**
 	 * Returns the longest substring common to strings <code>str1</code> and
 	 * <code>str2</code>.
 	 */
	public String LongestCommonSubstring(String str1, String str2)
	{
		if ( Strings.IsNullOrEmpty(str1) || Strings.IsNullOrEmpty(str2) )
		{
			return "";
		}

		int[][] num = new int[ str1.length() ][ str2.length() ];
		int maxlen = 0;
		int lastSubsBegin = 0;
		StringBuilder sequenceBuilder = new StringBuilder();

		for (int i = 0; i < str1.length(); i++)
		{
			for (int j = 0; j < str2.length(); j++)
			{
				if (str1.charAt(i) != str2.charAt(j))
				{
					num[i][j] = 0;
				}
				else
				{
					if ((i == 0) || (j == 0))
					{
						num[i][j] = 1;
					}
					else
						num[i][j] = 1 + num[i - 1][j - 1];

					if (num[i][j] > maxlen)
					{
						maxlen = num[i][j];
						int thisSubsBegin = i - num[i][j] + 1;
						if (lastSubsBegin == thisSubsBegin)
						{
							// if the current LCS is the same as the last time this block ran
							sequenceBuilder.append(str1.charAt(i));
						}
						else
						{
							//this block resets the string builder if a different LCS is found
							lastSubsBegin = thisSubsBegin;
							sequenceBuilder.delete(0, sequenceBuilder.length());//clear it
							sequenceBuilder.append(str1.substring(lastSubsBegin, (i + 1) - lastSubsBegin));
						}
					}
				}
			}
		}

		return sequenceBuilder.toString();
	}

	/**
 	 * Returns the length of the longest substring common to strings
 	 * <code>str1</code> and <code>str2</code>.
 	 *
 	 * The implementation is similar to the
 	 * <code>LongestCommonSubstring</code>'s one but more efficient.
 	 */
	public int LongestCommonSubstringLength(String str1, String str2)
	{
		if ( Strings.IsNullOrEmpty(str1) || Strings.IsNullOrEmpty(str2) )
		{
			return 0;
		}

		int[][] num = new int[ str1.length() ][ str2.length() ];
		int maxlen = 0;

		for (int i = 0; i < str1.length(); i++)
		{
			for (int j = 0; j < str2.length(); j++)
			{
				if (str1.charAt(i) != str2.charAt(j))
					num[i][j] = 0;
				else
				{
					if ((i == 0) || (j == 0))
						num[i][j] = 1;
					else
						num[i][j] = 1 + num[i - 1][j - 1];

					if (num[i][j] > maxlen)
					{
						maxlen = num[i][j];
					}
				}
			}
		}
		return maxlen;
	}

	/**
	 * Returns a string created from the concatenation of <code>n</code>
	 * for <code>n</code> times.
	 */
	public static String Repeat(String s, int n)
	{
		if ( Strings.IsNullOrEmpty(s) )
		{
			return s;
		}
		if ( n <= 0 )
		{
			return "";
		}

		StringBuilder sb = new StringBuilder(s);

		while (n > 1)
		{
			sb.append(s);
			n--;
		}

		return sb.toString();
	}

	public static String RandomString(int size)
	{
		Random letterRnd = new Random();
		Random caseRnd = new Random();

		byte b[] = new byte[size];
		for (int i = 0; i < size; i++)
		{
			int base;

			if ( caseRnd.nextFloat() > 0.5 )
			{ 
				base = (int) 'A';
			}
			else
			{
				base = (int) 'a';
			}
			b[i] = (byte) (base + letterRnd.nextInt( 27 ));
		}

		return new String(b);
	}
}
