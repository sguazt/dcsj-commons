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

package it.unipmn.di.dcs.common.examples;

import it.unipmn.di.dcs.common.io.ZipUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple zip application.
 *
 * @author <a href="mailto:marco.guazzone@mfn.unipmn.it">Marco Guazzone</a>
 */
public class ZipApp
{
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			usage();
			System.exit(1);
		}

		boolean help = false;
		boolean decompress = false;
		String errmsg = null;
		String zipName = null;
		int level = 6;
		List<String> files = new ArrayList<String>();

		for (int i = 0; i < args.length && errmsg == null; i++)
		{
			if (args[i].equals("-c"))
			{
				decompress = false;
			}
			else if (args[i].equals("-d"))
			{
				decompress = true;
			}
			else if (args[i].equals("-h"))
			{
				help = true;
			}
			else if (args[i].equals("-l"))
			{
				i++;
				if (i < args.length)
				{
					try
					{
						level = Integer.parseInt(args[i]);
					}
					catch (NumberFormatException nfe)
					{
						errmsg = "Bad compression level number";
					}
				}
				else
				{
					errmsg = "Missing compression level number";
				}
			}
			else if (args[i].startsWith("-"))
			{
					errmsg = "Unrecognzied option '" + args[i] + "'";
			}
			else
			{
				if (zipName == null)
				{
					zipName = args[i];
				}
				else
				{
					files.add(args[i]);
				}
			}
		}

		if (errmsg == null && zipName == null)
		{
			errmsg = "Zip file not specified.";
		}

		if (help)
		{
			usage();
			System.exit(0);
		}
		else if (errmsg != null)
		{
			System.err.println(errmsg);
			usage();
			System.exit(1);
		}

		if (decompress)
		{
		}
		else
		{
			try
			{
				ZipUtil.CreateArchive(zipName, files.toArray(new String[0]), level, null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private static void usage()
	{
			System.out.println("Usage: " + ZipApp.class.getName() + " [options] <zipfile> [files]");
	}
}
