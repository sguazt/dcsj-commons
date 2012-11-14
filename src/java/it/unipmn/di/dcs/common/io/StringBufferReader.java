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

import java.io.IOException;
import java.io.Reader;

/**
 * A reader for <code>StringBuffer</code> objects.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 *
 * @see java.io.Reader
 */
public class StringBufferReader extends Reader
{
	private StringBuffer buf; /** The internal buffer. */
	private int bufLen; /** The length of internal buffer (cached for speed). */
	private int nextPos = 0; /** Position where starting next read. */
	private int markPos = 0; /** Position of last mark. */

	/**
	 * A constructor.
	 *
	 * @param s A <code>StringBuffer</code> providing the underlying character stream.
	 */
	public StringBufferReader(StringBuffer s)
	{
		this.buf = s;
		this.bufLen = s.length();
	}

	/**
	 * Make sure this stream has not been closed.
	 *
	 * @throws IOException if the stream has been closed.
	 */
	private void ensureOpen() throws IOException
	{
		if ( this.buf == null )
		{
			throw new IOException( "The Stream is closed" );
		}
	}

	@Override
	public int read() throws IOException
	{
		synchronized ( this.lock )
		{
			this.ensureOpen();

			if ( this.nextPos >= this.bufLen)
			{
				return -1;
			}

			return this.buf.charAt( this.nextPos++ );
		}
	}

	@Override
	public int read(char cbuf[], int off, int len) throws IOException
	{
		synchronized ( this.lock )
		{
			this.ensureOpen();

			// Sanity checks on the given array
			if (
				off < 0
				|| off > cbuf.length
				|| len < 0
				|| ( off + len ) > cbuf.length
				|| ( off + len ) < 0
			)
			{
				throw new IndexOutOfBoundsException();
			}
			else if ( len == 0 )
			{
				return 0;
			}

			// Checks the state of current stream
			if ( this.nextPos >=  this.bufLen )
			{
				// No more char to read.
				return -1;
			}

			// Reads and returns.

			int nread = Math.min( this.bufLen - this.nextPos, len );
			this.buf.getChars(
				this.nextPos,
				this.nextPos + nread,
				cbuf,
				off
			);
			this.nextPos += nread;

			return nread;
		}
	}

	@Override
	public long skip(long ns) throws IOException
	{
		synchronized ( this.lock )
		{
			this.ensureOpen();

			// Checks the state of current stream.
			if ( this.nextPos >=  this.bufLen )
			{
				// No more char to read.
				return 0;
			}

			// Skips and returns.

			long nskip = Math.min( this.bufLen -  this.nextPos, ns );
			nextPos += nskip;

			return nskip;
		}
	}

	@Override
	public boolean ready() throws IOException
	{
		synchronized ( this.lock )
		{
			this. ensureOpen();

			return true;
		}
	}

	@Override
	public boolean markSupported()
	{
		return true;
	}

	@Override
	public void mark(int readAheadLimit) throws IOException
	{
		if ( readAheadLimit < 0 )
		{
			throw new IllegalArgumentException("Read-ahead limit < 0");
		}

		synchronized ( this.lock )
		{
			this.ensureOpen();

			this.markPos = this.nextPos;
		}
	}

	@Override
	public void reset() throws IOException
	{
		synchronized ( this.lock )
		{
			this.ensureOpen();

			this.nextPos = this.markPos;
		}
	}

	@Override
	public void close()
	{
		this.buf = null;
	}
}
