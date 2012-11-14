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

import java.io.Writer;
import java.io.IOException;

/**
 * A character stream that collects its output in a string buffer, which can
 * then be used to construct a string.
 * <p>
 * Closing a {@code FastStringWriter} has no effect. The methods in this class
 * can be called after the stream has been closed without generating an
 * {@code IOException}.
 * </p>
 * <p>
 * This is based on the {@code java.io.StringWriter} but backed by a
 * {@code java.lang.StringBuilder} instead than a
 * {@code java.lang.StringBuffer}.
 * </p>
 * <p>
 * Since {@code StringBuilder} doesn't guarantee synchronization, this class
 * should be used only by single thread at time.
 * </p>
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class FastStringWriter extends Writer
{
	private StringBuilder buf;

	/**
	 * Create a new string writer using the default initial string-buffer
	 * size.
	 */
	public FastStringWriter()
	{
		super();

		this.buf = new StringBuilder();
		this.lock = this.buf;
	}

	/**
	 * Create a new string writer using the specified initial string-buffer
	 * size.
	 *
	 * @param initialSize
	 *        The number of <tt>char</tt> values that will fit into this buffer
	 *        before it is automatically expanded
	 *
	 * @throws IllegalArgumentException
	 *         If <tt>initialSize</tt> is negative
	 */
	public FastStringWriter(int initialSize)
	{
		super();

		if (initialSize < 0)
		{
			throw new IllegalArgumentException("Negative buffer size");
		}
		this.buf = new StringBuilder(initialSize);
		this.lock = this.buf;
	}

	/**
	 * Write a single character.
	 */
	@Override
	public void write(int c)
	{
		this.buf.append((char) c);
	}

	/**
	 * Write a portion of an array of characters.
	 *
	 * @param  cbuf  Array of characters
	 * @param  off   Offset from which to start writing characters
	 * @param  len   Number of characters to write
	 */
	@Override
	public void write(char cbuf[], int off, int len)
	{
		if ((off < 0) || (off > cbuf.length) || (len < 0) ||
		((off + len) > cbuf.length) || ((off + len) < 0))
		{
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0)
		{
			return;
		}
		this.buf.append(cbuf, off, len);
	}

	/**
	 * Write a string.
	 */
	@Override
	public void write(String str)
	{
		this.buf.append(str);
	}

	/**
	 * Write a portion of a string.
	 *
	 * @param  str  String to be written
	 * @param  off  Offset from which to start writing characters
	 * @param  len  Number of characters to write
	 */
	@Override
	public void write(String str, int off, int len)
	{
		this.buf.append(str.substring(off, off + len));
	}

	/**
	 * Appends the specified character sequence to this writer.
	 *
	 * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 *     out.write(csq.toString()) </pre>
	 *
	 * <p> Depending on the specification of <tt>toString</tt> for the
	 * character sequence <tt>csq</tt>, the entire sequence may not be
	 * appended. For instance, invoking the <tt>toString</tt> method of a
	 * character buffer will return a subsequence whose content depends upon
	 * the buffer's position and limit.
	 *
	 * @param  csq
	 *         The character sequence to append.  If <tt>csq</tt> is
	 *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
	 *         appended to this writer.
	 *
	 * @return  This writer
	 */
	public FastStringWriter append(CharSequence csq)
	{
		if (csq == null)
		{
			this.write("null");
		}
		else
		{
			this.write(csq.toString());
		}
		return this;
	}

	/**
	 * Appends a subsequence of the specified character sequence to this writer.
	 *
	 * <p> An invocation of this method of the form <tt>out.append(csq, start,
	 * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
	 * exactly the same way as the invocation
	 *
	 * <pre>
	 *     out.write(csq.subSequence(start, end).toString()) </pre>
	 *
	 * @param  csq
	 *         The character sequence from which a subsequence will be
	 *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
	 *         will be appended as if <tt>csq</tt> contained the four
	 *         characters <tt>"null"</tt>.
	 *
	 * @param  start
	 *         The index of the first character in the subsequence
	 *
	 * @param  end
	 *         The index of the character following the last character in the
	 *         subsequence
	 *
	 * @return  This writer
	 *
	 * @throws  IndexOutOfBoundsException
	 *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
	 *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
	 *          <tt>csq.length()</tt>
	 */
	public FastStringWriter append(CharSequence csq, int start, int end)
	{
		CharSequence cs = (csq == null ? "null" : csq);

		this.write(cs.subSequence(start, end).toString());

		return this;
	}

	/**
	 * Appends the specified character to this writer. 
	 *
	 * <p> An invocation of this method of the form <tt>out.append(c)</tt>
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 *     out.write(c) </pre>
	 *
	 * @param  c
	 *         The 16-bit character to append
	 *
	 * @return  This writer
	 */
	public FastStringWriter append(char c)
	{
		this.write(c);

		return this;
	}

	/**
	 * Return the buffer's current value as a string.
	 */
	@Override
	public String toString()
	{
		return this.buf.toString();
	}

	/**
	 * Return the string buffer itself.
	 *
	 * @return StringBuffer holding the current buffer value.
	 */
	public StringBuilder getBuffer()
	{
		return this.buf;
	}

	/**
	 * Flush the stream.
	 */
	@Override
	public void flush()
	{ 
		// empty
	}

	/**
	 * Closing a {@code FastStringWriter} has no effect. The methods in this
	 * class can be called after the stream has been closed without generating
	 * an <tt>IOException</tt>.
	 */
	@Override
	public void close() throws IOException
	{
		// empty
	}
}
