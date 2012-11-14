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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import it.unipmn.di.dcs.common.annotation.Experimental;
import it.unipmn.di.dcs.common.annotation.TODO;
//import it.unipmn.di.dcs.common.annotation.TODO.SeverityType;
import it.unipmn.di.dcs.common.util.Arrays;

/**
 * This class implement a specialized version of <code>BufferedWriter</code>
 * where only the last line of data is written.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
//public class LastNonEmptyLineWriter extends Writer
public class LastNonEmptyLineWriter extends BufferedWriter
{
	private StringBuffer lastLine = new StringBuffer(); /** The line to be written */
	private boolean bufIsDirty = false; /** Monitors changes to internal buffer */
	private static String newLine = null; /** The underlying newline string */
	//private boolean newLinePresent = false; /** "true" if "lastLine" contains a newline */
	private int newLineSentinelIdx = 0; /** Keep track of fragmented newlines */

	/** A constructor */
	public LastNonEmptyLineWriter(Writer wr)
	{
		super( wr );

		this.newLine = System.getProperty( "line.separator" );
	}

	/** A constructor */
	public LastNonEmptyLineWriter(Writer wr, int sz) 
	{
		super( wr, sz );

		this.newLine = System.getProperty( "line.separator" );
	}

/*
	private static char[] subArray(char[] array, int beginPos, int len)
	{
		char[] result = new char[ len ];
		int endPos = beginPos + len - 1;

		for ( int i = beginPos; i <= endPos; i++ )
		{
			result[i] = array[i];
		}

		return result;
	}
*/

	/** @see java.io.BufferedWriter#flush() */
	@Override
	public void flush() throws IOException
	{
		synchronized ( this.lock )
		{
			if ( this.bufIsDirty == true && this.lastLine.length() > 0 )
			{
				super.write(
					this.lastLine.toString(),
					0,
					this.lastLine.length()
				);

				super.flush();

				this.bufIsDirty = false;
			}
		}
	}

	/** @see java.io.BufferedWriter#write(char[],int,int) */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException
	{
		synchronized ( this.lock )
		{
			// Looks for a beginning fragmented newline
			if ( this.newLineSentinelIdx > 0 )
			{
				// a possible pending fragmented newline

				int newLineLen = this.newLine.length();
				int i = this.newLineSentinelIdx;
				int j = off;
				for ( ; i < newLineLen && j < len; i++, j++ )
				{
					if ( this.newLine.charAt(i) != cbuf[j] )
					{
						break;
					}
				}

				if ( i == newLineLen || j == len )
				{
					// Found the other part of the fragment
					// or
					// Found a second fragment of a newline
					// (cbuf is shorter than newline).

					int delta = i - this.newLineSentinelIdx;
					off += delta;
					len -= delta;

					if ( i == newLineLen )
					{
						// A complete newline has been found
						// => reset the sentinel

						this.newLineSentinelIdx = 0;
					}
				}

				if ( off >= cbuf.length || len <= 0 )
				{
					// No more chars to write.
					return;
				}
			}

			// Looks for an ending fragmented newline
			{
				int newLineLen = this.newLine.length();
				int lastPos = off + len - 1;

				for ( int l = newLineLen; l > 0; l-- )
				{
					// looks for a suffix of length l

					int firstPos = lastPos - l + 1;
					int j = firstPos;
					for ( ; j <= lastPos; j++ )
					{
						if ( this.newLine.charAt(j - firstPos) != cbuf[j] )
						{
							// this suffix don't match
							break;
						}
					}

					if ( j > lastPos )
					{
						// found a common suffix

						this.newLineSentinelIdx = l;
						len -= l;
						break;
					}
				}

				if ( len <= 0 )
				{
					// No more chars to write.
					return;
				}
			}

			BufferedReader br = new BufferedReader(
				new StringReader(
					new String( Arrays.SubArray( cbuf, off, len ) )
				)
			);

			String line = null;
			while ( ( line = br.readLine() ) != null )
			{
				if ( line.length() > 0 )
				{
/*
					this.lastLine.setLength(0);
					this.lastLine.append( line );
*/
					this.lastLine.setLength( line.length() );
					this.lastLine.replace( 0, line.length(), line );
				}
			}
			br.close();
			br = null;

			this.bufIsDirty = true;
		}
	}

//	/** @see java.io.BufferedWriter#write(int) */
/* Implicitly implemented via write(char[], int, int)
	@TODO( value="Optimize and add code to handle fragmented newlines", severity=TODO.SeverityType.LOW )
	public void write(int c) throws IOException
	{
		this.write(
			new String(
				new int[] { c },
				0,
				1
			),
			0,
			1
		);
	}
*/

//	/** @see java.io.BufferedWriter#write(String,int,int) */
/* Implicitly implemented via write(char[], int, int)
	public void write(String s, int off, int len) throws IOException
	{
		if ( s.length() > 0 )
		{
			this.write( s.toCharArray(), off, len );
		}
	}
*/

	/**
	 * Returns the internal buffer.
	 *
	 * @return The internal buffer.
	 */
	public StringBuffer getBuffer()
	{
		return this.lastLine;
	}

	/**
	 * Returns the last written line.
	 *
	 * @return The last written line.
	 *
	 * @see java.lang.Object#toString
	 */
	@Override
	public String toString()
	{
		return this.lastLine.toString();
	}
}
