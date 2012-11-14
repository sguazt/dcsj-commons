/*
 * Copyright (C) 2008  Distributed Computing System (DCS) Group, Computer
 * Science Department - University of Piemonte Orientale, Alessandria (Italy).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.unit;

import it.unipmn.di.dcs.common.io.ZipUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * Test class for ZipUtil.
 *
 * @author <a href="mailto:marco.guazzone@mfn.unipmn.it">Marco Guazzone</a>
 */
public final class ZipUtilTester
{
	private static final transient Logger Log = Logger.getLogger(ZipUtilTester.class.getName());

	@Before
	public void setUp()
	{
	}

	@Test
	public void testCreateZip()
	{
		try
		{
			File target = File.createTempFile("zip", ".zip");
			target.deleteOnExit();

			Log.info( "[zip] Target: " + target.getName() );
			File[] infiles = {
				File.createTempFile("tmp", ".tmp"),
				File.createTempFile("tmp", ".tmp"),
				File.createTempFile("tmp", ".tmp")
			};
			String[] infileNames = new String[ infiles.length ];
			for (int i = 0; i < infiles.length; i++)
			{
				Log.info( "[zip] Input File: " + infiles[i].getName() );
				infiles[i].createNewFile();
				infiles[i].deleteOnExit();
				infileNames[i] = infiles[i].getAbsolutePath();
			}
			ZipUtil.CreateArchive( target.getAbsolutePath(), infileNames, 9, null );

			StringWriter swr = null;
			swr = new StringWriter();
			ZipUtil.PrintArchive( target.getAbsolutePath(), swr );
			Log.info( "[zip] Zip Content: " + swr.toString() );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			assertTrue( false );
		}

		assertTrue( true );
	}

	@Test
	public void testUpdateZip()
	{
		try
		{
			File target = File.createTempFile("zip", ".zip");
			//target.deleteOnExit();

			Log.info( "[zip] Target: " + target.getName() );
			File[] infiles = {
				File.createTempFile("tmp", ".tmp"),
				File.createTempFile("tmp", ".tmp"),
				File.createTempFile("tmp", ".tmp")
			};
			String[] infileNames = new String[ infiles.length ];
			for (int i = 0; i < infiles.length; i++)
			{
				Log.info( "[zip] Input File: " + infiles[i].getName() );
				infiles[i].createNewFile();
				infiles[i].deleteOnExit();
				infileNames[i] = infiles[i].getAbsolutePath();
			}
			ZipUtil.CreateArchive( target.getAbsolutePath(), infileNames, 9, null );

			StringWriter swr = null;
			swr = new StringWriter();
			ZipUtil.PrintArchive( target.getAbsolutePath(), swr );
			Log.info( "[zip] Zip Content: " + swr.toString() );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			assertTrue( false );
		}

		assertTrue( true );
	}

	@After
	public void tearDown()
	{
		// empty
	}

	public static void main(String[] args)
	{
		JUnitCore.main( ZipUtilTester.class.getName() );
	}
}
