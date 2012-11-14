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
import java.io.IOException;
import java.io.Reader;

/**
 * This class implement a specialized version of <code>BufferedReader</code>
 * where data are read only from last line.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class LastNonEmptyLineReader extends BufferedReader
{
	/** The last line of current stream */
	private String lastLine = null;
	private long pos = 0;

	/** A constructor */
	public LastNonEmptyLineReader(Reader rd) throws IOException
	{
		super( rd );

		this.skipToLastLine();
	}

	/** A constructor */
	public LastNonEmptyLineReader(Reader rd, int sz) throws IllegalArgumentException, IOException
	{
		super( rd, sz );

		this.skipToLastLine();
	}

	/**
	 * Skips all characters until the last line, or end of stream, is
	 * encountered.
	 */
	private void skipToLastLine() throws IOException
	{
		String line = null;
		while ( ( line = super.readLine() ) != null )
		{
			if ( line.trim().length() > 0 )
			{
				// store last non-empty line
				this.lastLine = line;
			}
		}
	}

	/**
	 * Checks if it is possible to read from this stream.
	 *
	 * @return <code>true</code> if the stream can be read;
	 * <code>false</code> otherwise.
	 */
	private boolean canRead()
	{
		return ( this.lastLine != null && this.pos < this.lastLine.length() )
			? true
			: false;
	}

	@Override
	public int read() throws IOException
	{
		synchronized ( this.lock )
		{
			return this.canRead()
				? this.lastLine.codePointAt( (int) this.pos++ )
				: -1;
		}
	}

	@Override
	public int read(char[] buf, int off, int len) throws IOException
	{
		synchronized ( this.lock )
		{
			int nread = -1;

			if ( this.canRead() )
			{
				nread = (int) Math.min(
					len,
					this.lastLine.length() - this.pos
				);

				this.lastLine.getChars(
					(int) this.pos,
					nread,
					buf,
					off
				);
			}

			return nread;
		}
	}

	@Override
	public String readLine() throws IOException
	{
		synchronized ( this.lock )
		{
			return this.canRead()
				? this.lastLine
				: null;
		}
	}

	@Override
	public long skip(long n) throws IOException
	{
		synchronized ( this.lock )
		{
			long nskipped = 0;

			if ( this.canRead() )
			{
				if ( (n + this.pos) <= this.lastLine.length() )
				{
					this.pos += n;
					nskipped = n;
				}
				else
				{
					nskipped = this.lastLine.length() - this.pos;
					this.pos = this.lastLine.length();
				}
			}

			return nskipped;
		}
	}

	@Override
	public boolean markSupported()
	{
		return false;
	}
}
