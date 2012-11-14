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

package it.unipmn.di.dcs.common.io;

import it.unipmn.di.dcs.common.util.Strings;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

/**
 * Utility class for input/output operations.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class IOUtil
{
	/**
	 * Read characters from a Reader to the end and write to a Writer.
	 *
	 * @param rd Reader from which reading characters.
	 * @param wr Writer to which writing characters.
	 */
	public static void ReadAll(Reader rd, Writer wr) throws Throwable
	{
		IOUtil.ReadAll_Variant1( rd, wr );
	}

	/**
	 * Reads all the given data and write them to a string.
	 *
	 * @return The given data as a string.
	 * @throws Throwable if the reader is not specified or something goes
	 * wrong.
	 */
	public static String ReadAllToString(Reader rd) throws Throwable
	{
		String result = null;

		// preconditions
		if ( rd == null	)
		{
			throw new Exception( "Reader not specified." );
		}

		StringWriter swr = null;

		try
		{
			swr = new StringWriter();
			IOUtil.ReadAll( rd, swr );
			result = swr.toString();
		}
		finally
		{
			if ( swr != null )
			{
				try { swr.close(); } finally { }
				swr = null;
			}
		}

		return result;
	}

	/**
	 * Reads all the given data and write them to a string buffer.
	 *
	 * @return The given data as a string buffer.
	 * @throws Throwable if the reader is not specified or something goes
	 * wrong.
	 */
	public static StringBuffer ReadAllToStringBuffer(Reader rd) throws Throwable
	{
		StringBuffer result = null;

		// preconditions
		if ( rd == null	)
		{
			throw new Exception( "Reader not specified." );
		}

		java.io.StringWriter swr = null;

		try
		{
			swr = new StringWriter();
			IOUtil.ReadAll( rd, swr );
			result = swr.getBuffer();
		}
		finally
		{
			if ( swr != null )
			{
				try { swr.close(); } finally { }
				swr = null;
			}
		}

		return result;
	}

	/**
	 * Reads all the given data and write them to a string builder.
	 *
	 * @return The given data as a string builder.
	 * @throws Throwable if the reader is not specified or something goes
	 * wrong.
	 */
	public static StringBuilder ReadAllToStringBuilder(Reader rd) throws Throwable
	{
		StringBuilder result = null;

		// preconditions
		if ( rd == null	)
		{
			throw new Exception( "Reader not specified." );
		}

		StringWriter swr = null;

		try
		{
			swr = new StringWriter();
			IOUtil.ReadAll( rd, swr );
			result = new StringBuilder( swr.toString() );
		}
		finally
		{
			if ( swr != null )
			{
				try { swr.close(); } finally { }
				swr = null;
			}
		}

		return result;
	}

	/**
	 * Reads all the given data and write them to a byte array.
	 *
	 * @return The given data as a byte array.
	 * @throws Throwable if the reader is not specified or something goes
	 * wrong.
	 */
	public static byte[] ReadAllToBytes(Reader rd) throws Throwable
	{
		byte[] result = null;

		// preconditions
		if ( rd == null	)
		{
			throw new Exception( "Reader not specified." );
		}

		ByteArrayOutputStream baos = null;
		OutputStreamWriter oswr = null;

		try
		{
			baos = new ByteArrayOutputStream();
			oswr = new OutputStreamWriter( baos );
			IOUtil.ReadAll( rd, oswr );
			result = baos.toByteArray();
		}
		finally
		{
			if ( baos != null )
			{
				try { baos.close(); } finally { }
				baos = null;
				//try { oswr.close(); } finally { }
				oswr = null;
			}
		}

		return result;
	}

	/**
	 * Reads all the given data and write them to a file.
	 *
	 * @throws Throwable if the reader is not specified or something goes
	 * wrong.
	 */
	public static void ReadAllToFile(Reader rd, File f) throws Throwable
	{
		// preconditions
		if ( rd == null	)
		{
			throw new Exception( "Reader not specified." );
		}

		java.io.FileWriter fwr = null;

		try
		{
			fwr = new FileWriter( f );
			IOUtil.ReadAll( rd, fwr );
		}
		finally
		{
			if ( fwr != null )
			{
				try { fwr.close(); } finally { }
				fwr = null;
			}
		}
	}

	/**
	 * Returns the relative path respect to the given {@code baseFile}.
	 */
	public static String GetRelativePath(File absoluteFile, File baseFile)
	{
		return IOUtil.GetRelativePath(
			absoluteFile.getAbsolutePath(),
			baseFile.getAbsolutePath()
		);
	}

	/**
	 * Returns the relative path respect to the given {@code basePath}.
	 */
	public static String GetRelativePath(String absolutePath, String basePath)
	{
		// preconditions
		if (
			Strings.IsNullOrEmpty(absolutePath)
			|| Strings.IsNullOrEmpty(basePath)
		) {
			return null;
		}

		// Makes sure the basePath is really a prefix of the absolute
		// path.
		if ( !absolutePath.startsWith( basePath ) )
		{
			return null;
		}

		StringBuilder sb = null;

		sb = new StringBuilder(
			absolutePath.substring( basePath.length() )
		);

		// Check for heading "/"
		if (
			sb.length() > 0
			&& sb.charAt(0) == File.separatorChar
		) {
			sb.deleteCharAt(0);
		}

		return sb.toString();
	}

	private static void ReadAll_Variant1(Reader rd, Writer wr) throws Throwable
	{
		// preconditions
		if ( rd == null )
		{
			throw new Exception( "Reader not specified" );
		}
		if ( wr == null )
		{
			throw new Exception( "Writer not specified" );
		}

		//FIXME: a better way to know an optimal buf length?!
		char[] buf = new char[2048];
		//char[] buf = new char[4096];
		int nread = 0; // num of characters read

		// reads characters to the end
		while ( ( nread = rd.read( buf ) ) != -1 )
		{
			// writes read characters
			wr.write( buf, 0, nread );
			//wr.flush();

			// blanks the buffer
			Arrays.fill( buf, 0, nread, (char) 0 );
		}
		wr.flush();
	}

	private static void ReadAll_Variant2(Reader rd, Writer wr) throws Exception
	{
                // preconditions
                if ( rd == null )
                {
                        throw new Exception( "Reader not specified" );
                }
                if ( wr == null )
                {
                        throw new Exception( "Writer not specified" );
                }

                BufferedReader brd = new BufferedReader( rd );
                PrintWriter pwr = new PrintWriter( wr );
                //java.io.PrintWriter pwr = new java.io.PrintWriter( new java.io.BufferedWriter( wr ) );

                String line = null;
                while ( ( line = brd.readLine() ) != null )
                {
                        pwr.println( line );
			//pwr.flush();
		}
		pwr.flush();
	}
}
