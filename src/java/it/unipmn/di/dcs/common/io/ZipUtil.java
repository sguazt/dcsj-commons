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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Utility class for zip files.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class ZipUtil
{
	private static final int RD_BUFFER_SIZE = 2048;

	private ZipUtil()
	{
		// empty
	}

	/**
	 * Create an empty zip file.
	 *
	 * @param zipName The name of the resulting ZIP file.
	 * @param level An integer number between 0 (minimum/faster compression)
	 *        and 9 (maximum/slower compression).
	 */
	public static void CreateEmptyArchive(String zipName, int level) throws CommonException
	{
		CreateArchive( zipName, null, level, null );
	}

	/**
	 * Create a zip file including the given files.
	 *
	 * @param zipName The name of the resulting ZIP file.
	 * @param fileNames The name of the files to include in the ZIP file.
	 * @param level An integer number between 0 (minimum/faster compression)
	 *        and 9 (maximum/slower compression).
	 * @param stripPrefixes List of prefixes to be stripped from each file name.
	 */
	public static void CreateArchive(String zipName, String[] fileNames, int level, String[] stripPrefixes) throws CommonException
	{
		ZipOutputStream zos = null;

		// Reverse-sort the prefixes so that longest prefixes are
		// stripped first
		if ( !ArrayIsNullOrEmpty(stripPrefixes) )
		{
			Arrays.sort( stripPrefixes );
			for (int i = 0; i < stripPrefixes.length; i++)
			{
				// swap
				int j = stripPrefixes.length - i - 1;
				String tmp = stripPrefixes[i];
				stripPrefixes[i] = stripPrefixes[j];
				stripPrefixes[j] = tmp;
			}
		}

		try
		{
			// Create the ZIP file
			zos = new ZipOutputStream(
				new FileOutputStream(zipName)
			);

			zos.setLevel( level );

			// Compress the files
			if ( !ArrayIsNullOrEmpty(fileNames) )
			{
				AddFilesToArchive( zos, fileNames, stripPrefixes );
			}

			zos.finish();
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
		finally
		{
			// Complete the ZIP file
			if (zos != null)
			{
				try { zos.close(); } catch (Exception e) { /* ignore */ }
				zos = null;
			}
		}
	}

	public static void PrintArchive(String zipFileName, Writer wr) throws CommonException
	{
		ZipFile zf = null;
		PrintWriter pwr = null;

		try
		{
			// Open the ZIP file
			zf = new ZipFile( zipFileName );
			pwr = new PrintWriter( wr );

			pwr.println( "Archive: " + zipFileName );
			pwr.println( "Length\tMethod\tSize\tRatio\tDate\tTime\tCRC-32\tName\tComment\tExtra" );
			pwr.println( "------\t------\t----\t-----\t----\t----\t------\t----\t-------\t-----" );

			SimpleDateFormat dateFmt = null;
			dateFmt = new SimpleDateFormat( "yyyy-MM-dd" );

			SimpleDateFormat timeFmt = null;
			timeFmt = new SimpleDateFormat( "HH:mm" );

			// Enumerate each entry
			for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements(); )
			{
				ZipEntry ze = e.nextElement();
				Date date = new Date( ze.getTime() );

				pwr.println(
					ze.getCompressedSize()
					+ "\t"
					+ CompressionMethodToString( ze.getMethod() )
					+ "\t"
					+ ze.getSize()
					+ "\t"
					+ ( ze.getSize() > 0 ? (100 * (ze.getSize() - ze.getCompressedSize()) / ze.getSize()) : 0 ) + "%"
					+ "\t"
					+ dateFmt.format( date )
					+ "\t"
					+ timeFmt.format( date )
					+ "\t"
					+ ze.getCrc()
					+ "\t"
					+ ze.getName()
					+ "\t"
					+ ( ze.getComment() != null ? ze.getComment() : "" )
					+ "\t"
					+ ( ze.getExtra() != null ? ze.getExtra() : "" )
				);
			}
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
		finally
		{
			// Complete the ZIP file
			if (zf != null)
			{
				try { zf.close(); } catch (Exception e) { /* ignore */ }
				zf = null;
			}
			// Close the print writer
			if (pwr != null)
			{
				try { pwr.close(); } catch (Exception e) { /* ignore */ }
				pwr = null;
			}
		}
	}

	public static List<ZipEntry> ReadArchive(String zipName) throws CommonException
	{
		ZipFile zf = null;
		List<ZipEntry> entries = new ArrayList<ZipEntry>();

		try
		{
			// Open the ZIP file
			zf = new ZipFile( zipName );

			// Enumerate each entry
			for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements(); )
			{
				ZipEntry ze = e.nextElement();

				entries.add( ze );
			}
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
		finally
		{
			// Complete the ZIP file
			if (zf != null)
			{
				try { zf.close(); } catch (Exception e) { /* ignore */ }
				zf = null;
			}
		}

		return entries;
	}

	public static void ExtractArchive(String zipFileName, String destPath) throws CommonException
	{
		ZipFile zf = null;

		// Cache for directory created
		//SortedSet dirsMade = null;
		//dirsMade = new TreeSet();

		try
		{
			zf = new ZipFile(zipFileName);

			// Enumerate each entry
			for (Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements(); )
			{
				ZipEntry ze = e.nextElement();

				ExtractFile( zf, ze );
			}
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
		finally
		{
			if (zf != null)
			{
				try { zf.close(); } catch (Exception e) { /* ignore */ }
				zf = null;
			}
		}
	}

	public static void AddFilesToArchive(String zipFileName, String[] fileNames, String[] stripPrefixes) throws CommonException
	{
		ZipOutputStream zos = null;

		try
		{
			// Create a temp file where storing the new zip
			File tmp = null;
			tmp = File.createTempFile("zip", ".zip");
			tmp.deleteOnExit();

			// Open the stream for the new zip
			zos = new ZipOutputStream(
				new FileOutputStream(tmp)
			);

			// Open the old zip
			ZipFile oldZip = null;
			oldZip = new ZipFile( zipFileName );

			// Copy old entries
			for (Enumeration<? extends ZipEntry> e = oldZip.entries(); e.hasMoreElements(); )
			{
				ZipEntry ze = e.nextElement();

				InputStream is = null;
				is = oldZip.getInputStream(ze);

				zos.putNextEntry(ze);

				FileUtil.CopyStream( is, zos );

				zos.closeEntry();

				is.close();
				is = null;
			}

			// add new entries
			AddFilesToArchive( zos, fileNames, stripPrefixes );

			zos.finish();

			FileUtil.Copy( tmp.getAbsolutePath(), zipFileName );
		}
		catch (CommonException ce)
		{
			throw ce;
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
		finally
		{
			if ( zos != null )
			{
				try { zos.close(); } catch (Exception e) { /* ignore */ }
				zos = null;
			}
		}
	}

	protected static void AddFilesToArchive(ZipOutputStream zos, String[] fileNames, String[] stripPrefixes) throws IOException
	{
		Arrays.sort( fileNames );

		for (String fileName : fileNames)
		{
			if ( ".".equals(fileName) || "..".equals(fileName) )
			{
				continue;
			}

			File f = new File(fileName);
			String entryName = null;

			if ( !ArrayIsNullOrEmpty(stripPrefixes) )
			{
				boolean prefixFound = false;

				for (String prefix : stripPrefixes)
				{
					if (fileName.startsWith(prefix))
					{
						entryName = fileName.substring( prefix.length() );
						prefixFound = true;
						break;
					}
				}

				if ( !prefixFound )
				{
					entryName = fileName;
				}
			}
			else
			{
				entryName = fileName;
			}

			// Check for absolute/relative entry name
			if ( entryName.startsWith( File.separator ) )
			{
				// Absolute entry (/) ==> Relative entry (./)
				entryName = "." + entryName;
			}

			if ( f.isDirectory() )
			{
				File[] childFiles = f.listFiles();
				//String[] childFiles = f.list();

				if ( childFiles.length > 0 )
				{
					String[] children = new String[childFiles.length];

					for (int i = 0; i < childFiles.length; i++)
					{
						String childName = childFiles[i].getName();
						//String childName = childFiles[i];

						if ( ".".equals(childName) || "..".equals(childName) )
						{
							continue;
						}

						children[i] = childFiles[i].getAbsolutePath();
						//File childFile = null;
						///childFile = new File(childFiles[i]);
						//children[i] = childFile.getAbsolutePath();
						//childFile = null;
					}

					childFiles = null;

					AddFilesToArchive(zos, children, stripPrefixes);
				}

//				if ( !entryName.endsWith( File.separator ) )
//				{
//					entryName = entryName + File.separator;
//				}

				// Add ZIP entry to output stream.
//				zos.putNextEntry(
//					new ZipEntry( entryName )
//				);
			}
			else
			{
//TODO: should I use buffered input for faster read but with possible
//      more mem consumption
//				BufferedInputStream bis = null;
				FileInputStream fis = null;

				try
				{
					fis = new FileInputStream( fileName );
//					bis = new BufferedInputStream(bis, RD_BUFFER_SIZE);

					// Add ZIP entry to output stream.
					zos.putNextEntry(
						new ZipEntry( entryName )
					);

					// Transfer bytes from the file to the ZIP file
					int nread = 0;
					byte[] buf = new byte[RD_BUFFER_SIZE];

					while ( (nread = fis.read(buf)) > 0 )
					{
						zos.write(buf, 0, nread);
					}

//					zos.finish();
				}
				finally
				{
//					if (bis != null)
//					{
//						try { bis.close(); } catch (Exception e) { /* ignore */ }
//						bis = null;
//					}
					if (fis != null)
					{
						try { fis.close(); } catch (Exception e) { /* ignore */ e.printStackTrace(); }
						fis = null;
					}
				}
			}
		}
	}

	/**
	 * Process one file from the zip, given its name. Either print the name, or
	 * create the file on disk.
	 */
	protected static void ExtractFile(ZipFile zf, ZipEntry ze) throws CommonException
	{
		String fileName = ze.getName();
		if (fileName.startsWith("/"))
		{
//			System.out.println("Ignoring absolute paths");
			fileName = fileName.substring(1);
		}
		// if a directory, just return. We mkdir for every file,
		// since some widely-used Zip creators don't put out
		// any directory entries, or put them in the wrong place.
		if (fileName.endsWith("/"))
		{
			return;
		}
		// Else must be a file; open the file for output
		// Get the directory part.
		int ix = fileName.lastIndexOf('/');
		if (ix > 0)
		{
			String dirName = fileName.substring(0, ix);
//			if (!dirsMade.contains(dirName))
//			{
				File d = new File(dirName);
				// If it already exists as a dir, don't do anything
				if (!(d.exists() && d.isDirectory()))
				{
					// Try to create the directory, warn if it fails
//					System.out.println("Creating Directory: " + dirName);
					if (!d.mkdirs())
					{
//						System.err.println("Warning: unable to mkdir " + dirName);
					}
					//dirsMade.add(dirName);
				}
//			}
		}
//		System.err.println("Creating " + zipName);
		FileOutputStream os = null;
		InputStream is = null;
		try
		{
			is = zf.getInputStream(ze);
			os = new FileOutputStream(fileName);
			int n = 0;
			byte[] b = new byte[RD_BUFFER_SIZE];
			while ((n = is.read(b)) > 0)
			{
				os.write(b, 0, n);
			}
		}
		catch (IOException ioe)
		{
			throw new CommonException(ioe);
		}
		finally
		{
			if (is != null)
			{
				try { is.close(); } catch (Exception e) { /* ignore */ }
				is = null;
			}
			if (os != null)
			{
				try { os.close(); } catch (Exception e) { /* ignore */ }
				os = null;
			}
		}
	}

	protected static String CompressionMethodToString(int method)
	{
		switch (method)
		{
			case ZipEntry.STORED:
				return "STORED";
			case ZipEntry.DEFLATED:
				return "DEFLATED";
			default:
				return "UNKNOWN";
		}
	}

	// Wrapped for avoiding conflicts with java.util.Arrays.
	private static boolean ArrayIsNullOrEmpty(String[] array)
	{
		return it.unipmn.di.dcs.common.util.Arrays.IsNullOrEmpty(array);
	}
}
