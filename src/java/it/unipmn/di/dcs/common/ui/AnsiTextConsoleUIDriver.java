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

import java.io.IOException;

/**
 * Class for ANSI text consoles.
 *
 * An ANSI terminal can be personalized though a set of attributes defined by
 * the so called <em>escape sequences</em>: * <code>&lt;ESC&gt;[{attr1};...;{attrn}m</code>.
 * Here below are common attributes:
 *
 * <p>
 * 0      Reset all attributes
 * 1      Bright
 * 2      Dim
 * 4      Underscore      
 * 5      Blink
 * 7      Reverse
 * 8      Hidden
 * </p>
 * <p>
 * Foreground Colours
 * 30     Black
 * 31     Red
 * 32     Green
 * 33     Yellow
 * 34     Blue
 * 35     Magenta
 * 36     Cyan
 * 37     White
 * </p>
 * <p>
 * Background Colours
 * 40     Black
 * 41     Red
 * 42     Green
 * 43     Yellow
 * 44     Blue
 * 45     Magenta
 * 46     Cyan
 * 47     White
 * </p>
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */ 
public class AnsiTextConsoleUIDriver implements IUIDriver
{
	public static enum Color
	{
		// Dark colors
		Black		( 0, false ),
		DarkRed		( 1, false ),
		DarkGreen	( 2, false ),
		DarkYellow	( 3, false ),
		DarkBlue	( 4, false ),
		DarkMagenta	( 5, false ),
		DarkCyan	( 6, false ),
		Gray		( 7, false ),
		// Light colors
		DarkGray	( 0, true ),
		Red		( 1, true ),
		Green		( 2, true ),
		Yellow		( 3, true ),
		Blue		( 4, true ),
		Magenta		( 5, true ),
		Cyan		( 6, true ),
		White		( 7, true );
		

		private int code;
		private boolean light;

		Color(int code, boolean light)
		{
			this.code = code;
			this.light = light;
		}

		public int code()
		{
			return this.code;
		}

		public boolean light()
		{
			return this.light;
		}
	}

	private ConsoleWrapper console;

	private Color fgColor = null;

	private Color bgColor = null;

	private Color fgColorMsg = null;

	private Color bgColorMsg = null;

	private Color fgColorOk = Color.Green;

	private Color bgColorOk = null;

	private Color fgColorWarn = Color.Yellow;

	private Color bgColorWarn = null;

	private Color fgColorErr = Color.Red;

	private Color bgColorErr = null;

	private Color fgColorInfo = Color.Cyan;

	private Color bgColorInfo = null;

	protected final static char ESC = 27;

	public AnsiTextConsoleUIDriver()
	{
		this.console = new ConsoleWrapper();

		this.resetAllAttributes();
	}

	public AnsiTextConsoleUIDriver(Color fg, Color bg)
	{
		this.console = new ConsoleWrapper();
		this.fgColor = fg;
		this.bgColor = bg;
		this.bgColorOk = this.bgColorWarn = this.bgColorErr = this.bgColor;

		this.resetAllAttributes();
	}

	protected void resetAllAttributes()
	{
		this.console.writer().print( AnsiTextConsoleUIDriver.ESC + "[0m" );
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();

		this.resetAllAttributes();
	}

	protected ConsoleWrapper getConsole()
	{
		return this.console;
	}

	protected void printColors(Color fg, Color bg)
	{
		String seq = null;

		if ( fg != null )
		{
			// Sets foreground color
			if ( fg.light() )
			{
				seq = "[9";
			}
			else
			{
				seq = "[3";
			}
			this.console.writer().print( AnsiTextConsoleUIDriver.ESC + seq + fg.code() + "m" );
		}

		if ( bg != null )
		{
			// Sets background color
			if ( bg.light() )
			{
				seq = "[10";
			}
			else
			{
				seq = "[4";
			}
			this.console.writer().print( AnsiTextConsoleUIDriver.ESC + seq + bg.code() + "m" );
		}
	}

	//@{ IUIDriver implementation

	public void showMsg(String msg)
	{
		this.printColors( this.fgColorMsg, this.bgColorMsg );

		this.console.writer().print( msg );

		//this.printColors( this.fgColor, this.bgColor );
		this.resetAllAttributes();

		this.console.writer().println();
	}

	public void showOk(String msg)
	{
		this.printColors( this.fgColorOk, this.bgColorOk );

		this.console.writer().print( "[OK] " + msg );

		//this.printColors( this.fgColor, this.bgColor );
		this.resetAllAttributes();

		this.console.writer().println();
	}

	public void showError(String msg)
	{
		this.printColors( this.fgColorErr, this.bgColorErr );

		this.console.writer().print( "[ERROR] " + msg );

		//this.printColors( this.fgColor, this.bgColor );
		this.resetAllAttributes();

		this.console.writer().println();
	}

	public void showWarning(String msg)
	{
		this.printColors( this.fgColorWarn, this.bgColorWarn );

		this.console.writer().print( "[WARNING] " + msg );

		//this.printColors( this.fgColor, this.bgColor );
		this.resetAllAttributes();

		this.console.writer().println();
	}

	public void showInfo(String msg)
	{
		this.printColors( this.fgColorInfo, this.bgColorInfo );

		this.console.writer().print( "[INFO] " + msg );

		//this.printColors( this.fgColor, this.bgColor );
		this.resetAllAttributes();

		this.console.writer().println();
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
