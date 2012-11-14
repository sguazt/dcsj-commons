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

/**
 * A "do-nothing" writer.
 *
 * Mimic the behaviour of /dev/null device under Unix.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
class NullWriter extends Writer
{
	/** The default constructor */
        public NullWriter() { }

	/** A constructor */
        public NullWriter(Object lock) { }

	/** @see java.io.Writer#append(char) */
	@Override
        public Writer append(char c) { return this; }

	/** @see java.io.Writer#append(CharSequence) */
	@Override
        public Writer append(CharSequence csq) { return this; }

	/** @see java.io.Writer#append(CharSequence, int, int) */
	@Override
        public Writer append(CharSequence csq, int start, int end) { return this; }

	/** @see java.io.Writer#close() */
	@Override
        public void close() { }

	/** @see java.io.Writer#flush() */
	@Override
        public void flush() { }

	/** @see java.io.Writer#write(char[]) */
	@Override
        public void write(char[] cbuf) { }

	/** @see java.io.Writer#write(char[], int, int) */
	@Override
        public void write(char[] cbuf, int off, int len) { }

	/** @see java.io.Writer#write(char) */
	@Override
        public void write(int c) { }

	/** @see java.io.Writer#write(String) */
	@Override
        public void write(String s) { }

	/** @see java.io.Writer#write(String, int, int) */
	@Override
        public void write(String str, int off, int len) { }
}
