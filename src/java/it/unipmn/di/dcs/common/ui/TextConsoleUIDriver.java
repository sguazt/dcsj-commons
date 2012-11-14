/*
 * Copyright (C) 2007-2012  Marco Guazzone
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

package it.unipmn.di.dcs.common.ui;

import it.unipmn.di.dcs.common.util.Strings;

import java.io.Console;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.IOException;
//import java.io.Reader;
//import java.io.PrintWriter;

/**
 * Class for a black-and-white textual console.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class TextConsoleUIDriver implements IUIDriver
{
	private ConsoleWrapper console;

	public TextConsoleUIDriver()
	{
		this.console = new ConsoleWrapper();
	}

	protected ConsoleWrapper getConsole()
	{
		return this.console;
	}

	//@{ IUIDriver implementation

	public void showMsg(String msg)
	{
		this.console.writer().println( msg );
	}

	public void showOk(String msg)
	{
		this.console.writer().println( "[OK] " + msg );
	}

	public void showError(String msg)
	{
		this.console.writer().println( "[ERROR] " + msg );
	}

	public void showWarning(String msg)
	{
		this.console.writer().println( "[WARNING] " + msg );
	}

	public void showInfo(String msg)
	{
		this.console.writer().println( "[INFO] " + msg );
	}

	public String ask(String question) throws UIException
	{
		return this.ask(question, null);
	}

	public int askInt(String question) throws UIException
	{
		return this.askInt(question, null);
	}

	public String ask(String question, String defValue) throws UIException
	{
		String s;
		StringBuilder fmt = new StringBuilder();

		// Build the format string
		fmt.append( "%1$s" );
		if ( defValue != null )
		{
			if ( defValue.length() != 0 )
			{
				fmt.append( " [" + defValue.toString() + "]" );
			}
			else
			{
				fmt.append( " [<Press ENTER if none>]" );
			}
		}
		fmt.append( "? " );

		int streamErr = 0;
		do
		{
			try
			{
				s = this.console.readLine( fmt.toString(), question );
			}
			catch (IOException ioe)
			{
				if ( streamErr < 3 )
				{
					// retry
					s = null;
					streamErr++;
				}
				else
				{
					throw new UIException( ioe );
				}
			}
		} while ( defValue == null && Strings.IsNullOrEmpty(s) );

		return 	Strings.IsNullOrEmpty(s)
			? ( ( defValue != null )
				? defValue
				: "" )
			: s;
	}

	public int askInt(String question, Integer defValue) throws UIException
	{
		int value = 0;
		boolean ok;

		do
		{
			ok = true;
			try
			{
				value = Integer.parseInt(
					this.ask( question, ( defValue != null ) ? defValue.toString() : null )
				);
			}
			catch (NumberFormatException nfe)
			{
				this.showError( "You must insert a number!" );
				ok = false;
			}
		} while ( !ok );

		return value;
	}

	public boolean askYesNo(boolean pedantic) throws UIException
	{
		String s;

		return this.askYesNo( null, pedantic );
	}

	public boolean askYesNo(String msg, boolean pedantic) throws UIException
	{
		String s;

		if ( Strings.IsNullOrEmpty( msg ) )
		{
			msg = "Continue";
		}

		do
		{
			s = this.ask( msg + " [y/N]" );
		} while (
			Strings.IsNullOrEmpty(s)
			|| ( !s.equals("y") && !s.equals("N") && pedantic )
			|| ( !s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n") )
		);

		return s.equalsIgnoreCase("y");
	}

/*TODO
	public THreeStateLogic askYesNoCancel(boolean pedantic) throws UIException
	{
		String s;

		return this.askYesNoCancel( null, pedantic );
	}

	public THreeStateLogic askYesNoCancel(String msg, boolean pedantic) throws UIException
	{
		String s;

		if ( Strings.IsNullOrEmpty( msg ) )
		{
			msg = "Continue";
		}

		do
		{
			s = this.ask( msg + " [y/N/C]" );
		} while (
			Strings.IsNullOrEmpty(s)
			|| ( !s.equals("y") && !s.equals("N") && !s.equals("C") && pedantic )
			|| ( !s.equalsIgnoreCase("y") && !s.equalsIgnoreCase("n") && !s.equalsIgnoreCase("c") )
		);

		return s.equalsIgnoreCase("y");
	}
*/

	//@} IUIDriver implementation
}
