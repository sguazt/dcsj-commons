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

import it.unipmn.di.dcs.common.CommonException;
import it.unipmn.di.dcs.common.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Utility class for files.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class FileUtil
{
	/**
	 * Copy the given {@code InputStream} into the given
	 * {@code OutputStream}.
	 */
	public static void CopyStream(InputStream is, OutputStream os) throws CommonException
	{
		byte[] buf = new byte[8192];
		int nread;

		try
		{
			while ( (nread = is.read(buf, 0, buf.length)) > 0 )
			{
				os.write(buf, 0, nread);
			}
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
	}

	/**
	 * Copy the given {@code InputStream} into the given
	 * {@code OutputStream}.
	 */
	public static void Copy(String source, String dest) throws CommonException
	{
		// preconditions
		if ( Strings.IsNullOrEmpty( source ) || Strings.IsNullOrEmpty( dest ) )
		{
			throw new CommonException("Source or dest file name not specified.");
		}

		if ( source.equals( dest ) )
		{
			return;
		}

		if (
			CheckRead(source)
			&& (
				CheckWrite(dest)
				|| (
					!CheckExist(dest)
					&& CheckWrite( new File(dest).getParent() )
				)
			)
		) {
			FileChannel srcChannel = null;
			FileChannel dstChannel = null;

			try
			{
				// Create channel on the source
				srcChannel = new FileInputStream( source ).getChannel();
				// Create channel on the destination
				dstChannel = new FileOutputStream( dest ).getChannel();
				// Copy file contents from source to destination
				dstChannel.transferFrom( srcChannel, 0, srcChannel.size() );
			}
			catch (IOException ioe)
			{
				throw new CommonException(ioe);
			}
			finally
			{
				// Close the channels
				if ( srcChannel != null )
				{
					try { srcChannel.close(); } catch (Exception e) { ; }
					srcChannel = null;
				}
				if ( dstChannel != null )
				{
					try { dstChannel.close(); } catch (Exception e) { ; }
					dstChannel = null;
				}
			}
		}
	}

	/** Try to copy {@code source} file into {@code dest} file. */
	public static boolean TryCopy(String source, String dest)
	{
//		boolean copied = false;
//
//		// preconditions
//		if ( Strings.IsNullOrEmpty( source ) || Strings.IsNullOrEmpty( dest ) )
//		{
//			return false;
//		}
//		if ( source.equals( dest ) )
//		{
//			return true;
//		}
//
//		if (
//			CheckRead(source)
//			&& (
//				CheckWrite(dest)
//				|| (
//					!CheckExist(dest)
//					&& CheckWrite( new File(dest).getParent() )
//				)
//			)
//		) {
//			// Old style copy
//			//InputStream in = new FileInputStream(source);
//			//OutputStream out = new FileOutputStream(dest);
//			//
//			//// Transfer bytes from in to out
//			//byte[] buf = new byte[1024];
//			//int len;
//			//while ((len = in.read(buf)) > 0)
//			//{
//			//	out.write(buf, 0, len);
//			//}
//			//in.close();
//			//out.close();
//
//			FileChannel srcChannel = null;
//			FileChannel dstChannel = null;
//
//			try
//			{
//				// Create channel on the source
//				srcChannel = new FileInputStream( source ).getChannel();
//				// Create channel on the destination
//				dstChannel = new FileOutputStream( dest ).getChannel();
//				// Copy file contents from source to destination
//				dstChannel.transferFrom( srcChannel, 0, srcChannel.size() );
//
//				copied = true;
//			}
//			catch (IOException e)
//			{
//				copied = false;
//			}
//			finally
//			{
//				// Close the channels
//				if ( srcChannel != null )
//				{
//					try { srcChannel.close(); } catch (Exception e) { ; }
//					srcChannel = null;
//				}
//				if ( dstChannel != null )
//				{
//					try { dstChannel.close(); } catch (Exception e) { ; }
//					dstChannel = null;
//				}
//			}
//		}
//
//		return copied;

		try
		{
			Copy( source, dest );
		}
		catch (CommonException ce)
		{
			return false;
		}

		return true;
	}

	/** Try to move (rename) {@code source} file into {@code dest} file . */
	public static boolean TryMove(String source, String dest)
	{
		boolean moved = false;

		// preconditions
		if ( Strings.IsNullOrEmpty( source ) || Strings.IsNullOrEmpty( dest ) )
		{
			return false;
		}
		if ( source.equals( dest ) )
		{
			return true;
		}

		if (
			CheckWrite(source)
			&& (
				CheckWrite(dest)
				|| (
					!CheckExist(dest)
					&& CheckWrite( new File(dest).getParent() )
				)
			)
		) {
			try
			{
				moved = new File(source).renameTo( new File(dest) );
			}
			catch (Exception e)
			{
				moved = false;
			}

			if ( !moved )
			{
				// File.renameTo might fails on certain operating systems
				// so try a more portable way
				if ( TryCopy( source, dest ) )
				{
					if ( TryDelete( source ) )
					{
						moved = true;
					}
					else
					{
						TryDelete( dest );
					}
				}
			}
		}

		return moved;
	}

	/** Try to delete {@code source} file. */
	public static boolean TryDelete(String source)
	{
		boolean deleted = false;

		// preconditions
		if ( Strings.IsNullOrEmpty( source ) )
		{
			return false;
		}

		if ( CheckWrite(source) )
		{
			try
			{
				deleted = new File(source).delete();
			}
			catch (Exception e)
			{
				// Ignore
			}
		}

		return deleted;
	}

	/**
	 * Try to delete {@code source} file (or file tree).
	 *
	 * If {@code recursive} is true and {@code source} is a directory, the
	 * deletion is recursively propagated down in the hierarchy.
	 */
	public static boolean TryDelete(String source, boolean recursive)
	{
		boolean deleted = false;

		// preconditions
		if ( Strings.IsNullOrEmpty( source ) )
		{
			return false;
		}

		if ( CheckWrite(source) )
		{
			try
			{
				File f = new File(source);

				if ( f.isDirectory() && recursive )
				{
					// Delete child files

					String pathPrefix = f.getAbsolutePath() + File.separator;

					boolean childDeleted = true;

					for (String child : f.list())
					{
						childDeleted &= FileUtil.TryDelete( pathPrefix + child, true );
					}

					// Delete itself
					deleted = f.delete() & childDeleted;
				}
				else
				{
					deleted = f.delete();
				}
			}
			catch (Exception e)
			{
				// Ignore
			}
		}

		return deleted;
	}

	/** Returns {@code true} if the given file exists. */
	protected static boolean CheckExist(String fileName)
	{
		if ( Strings.IsNullOrEmpty( fileName ) )
		{
			return false;
		}

		return new File( fileName ).exists();
	}

	/**
	 * Returns {@code true} if the given file (or file tree) is writable.
	 *
	 * If {@code recursive} is true and {@code source} is a directory, the check
	 * is recursively propagated down in the file hierarchy.
	 */
	protected static boolean CheckWrite(String fileName)
	{
		if ( Strings.IsNullOrEmpty( fileName ) )
		{
			return false;
		}

		return new File( fileName ).canWrite();
	}

	/**
	 * Returns {@code true} if the given file (or file tree) is readable.
	 *
	 * If {@code recursive} is true and {@code source} is a directory, the check
	 * is recursively propagated down in the file hierarchy.
	 */
	public static boolean CheckRead(String fileName)
	{
		if ( Strings.IsNullOrEmpty( fileName ) )
		{
			return false;
		}

		return new File( fileName ).canRead();
	}
}
