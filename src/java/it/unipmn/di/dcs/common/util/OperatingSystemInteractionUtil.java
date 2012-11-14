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

package it.unipmn.di.dcs.common.util;

import it.unipmn.di.dcs.common.io.IOUtil;
import it.unipmn.di.dcs.common.CommonException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Producer/Consumer thread worker.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
final class StreamProducerConsumer implements Runnable
{
	private Reader producer;
	private Writer consumer;
	private boolean closeProducer = false;
	private boolean closeConsumer = false;

	public StreamProducerConsumer(InputStream producer, OutputStream consumer)
	{
		this.producer = new InputStreamReader( producer );
		this.consumer = new OutputStreamWriter( consumer );
	}

	public StreamProducerConsumer(Reader producer, Writer consumer)
	{
		this.producer = producer;
		this.consumer =  consumer;
	}

	public void producerIsCloseable(boolean value)
	{
		this.closeProducer = value;
	}

	public boolean isProducerCloseable()
	{
		return this.closeProducer;
	}

	public void consumerIsCloseable(boolean value)
	{
		this.closeConsumer = value;
	}

	public boolean isConsumerCloseable() 
	{
		return this.closeConsumer;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			// Read to EOF: Variant #1

			char[] buf = new char[2048];
			//char[] buf = new char[4096];
			int nread = 0; // num of characters read

			// reads characters to the end
			while ( ( nread = this.producer.read( buf ) ) != -1 )
			{
				// writes read characters
				this.consumer.write( buf, 0, nread );
				//wr.flush();

				// blanks the buffer
				Arrays.fill( buf, 0, nread, (char) 0 );
			}
			this.consumer.flush();

/*
			// Read to EOF: Variant #2

			BufferedReader brd = new BufferedReader( this.producer );
			java.io.PrintWriter pwr = new java.io.PrintWriter( this.consumer );
			String line = null;
			while ( ( line = brd.readLine() ) != null )
			{
				pwr.println( line );
				//pwr.flush();
			}
			pwr.flush();
*/
			if ( this.isProducerCloseable() )
			{
				this.producer.close(); // for Variant #1
				//brd.close(); // for Variant #2
				this.producer = null;
			}

			if ( this.isConsumerCloseable() )
			{
				this.consumer.close(); // for Variant #1
				//pwr.close(); // for Variant #2
				this.consumer = null;
			}
		}
		catch ( Throwable t )
		{
			t.printStackTrace();
		}
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable
	{
		if ( this.producer != null )
		{
			if ( isProducerCloseable() )
			{
				this.producer.close();
				this.producer = null;
			}
		}
		if ( this.consumer != null )
		{
			if ( isConsumerCloseable() )
			{
				this.consumer.close();
				this.consumer = null;
			}
		}

		super.finalize();
	}
}

/**
 * Utility class for the interaction with the underlying operating system.
 *
 * @author <a href="mailto:marco.guazzone@mfn.unipmn.it">Marco Guazzone</a>
 */
public final class OperatingSystemInteractionUtil
{
	/**
	 * Execute a command.
	 *
	 * @param cmd Command to be executed.
	 * @param args Parameters to pass to the command.
	 * @param stdIn Reader for the redirection of the command standard
	 * input. Can be <code>null</code>.
	 * @param stdOut Writer for the redirection of the command standard
	 * output. Can be <code>null</code>.
	 * @param stdErr Writer for the redirection of the command standard
	 * error. Can be <code>null</code>.
	 * @return The command exit code.
	 */
	static public int executeCmd(String cmd, String[] args, Reader stdIn, Writer stdOut, Writer stdErr) throws CommonException
	{
		int exitCode; // command exit code to be returned

		// preconditions
		if ( cmd == null || cmd.length() == 0 )
		{
			throw new CommonException( "Command not specified" );
		}

		//System.err.println("[executeCmd>>> BEGIN exec command");//XXX

		// prepares startup informations
		ProcessBuilder pb = null;
		if ( args != null && args.length > 0 )
		{
			// with command arguments

			List<String> cmdList = null;
			cmdList = new ArrayList<String>();
			cmdList.add( cmd );
			cmdList.addAll( Arrays.asList( args ) );

			pb = new ProcessBuilder( cmdList );
		}
		else
		{
			// no command arguments
			pb = new ProcessBuilder( cmd );
		}

		// starts the process
		Process p = null;

		try
		{
			p = pb.start();
/*
		// redirects standard input
		if ( stdIn != null )
		{
			OutputStreamWriter owr = new OutputStreamWriter( p.getOutputStream() );
			IOUtility.readAll(
				stdIn,
				owr
			);
			owr.close();
			owr = null;
		}
		// redirects standard output
		if ( stdOut != null )
		{
			InputStreamReader ird = new InputStreamReader( p.getInputStream() );
			IOUtility.readAll(
				ird,
				stdOut
			);
			ird.close();
			ird = null;
		}
		// redirects standard error
		if ( stdErr != null )
		{
			InputStreamReader ird = new InputStreamReader( p.getErrorStream() );
			IOUtility.readAll(
				ird,
				stdErr
			);
			ird.close();
			ird = null;
		}

		// waits for process termination (FIXME: possible deadlock?!?)
		exitCode = p.waitFor();
*/

//		try
//		{
			OutputStream stdinStream = p.getOutputStream();
			InputStream stdoutStream = p.getInputStream();
			InputStream stderrStream = p.getErrorStream();

			StreamProducerConsumer stdinGlobber = null;
			StreamProducerConsumer stdoutGlobber = null;
			StreamProducerConsumer stderrGlobber = null;

			if ( stdIn != null && stdinStream != null )
			{
				stdinGlobber = new StreamProducerConsumer(
					stdIn,
					new OutputStreamWriter( stdinStream )
				);
				stdinGlobber.producerIsCloseable(false);
				stdinGlobber.consumerIsCloseable(true);
			}
			else
			{
/*
				if ( stdinStream != null )
				{
					stdinStream.close();
					stdinStream = null;
				}
*/
			}

			if ( stdOut != null && stdoutStream != null )
			{
				stdoutGlobber = new StreamProducerConsumer(
					new InputStreamReader( stdoutStream ),
					stdOut
				);
				stdoutGlobber.producerIsCloseable(true);
				stdoutGlobber.consumerIsCloseable(false);
			}
			else
			{
/*
				if ( stdoutStream != null )
				{
					stdoutStream.close();
					stdoutStream = null;
				}
*/
			}

			if ( stdErr != null && stderrStream != null )
			{
				stderrGlobber = new StreamProducerConsumer(
					new InputStreamReader( stderrStream ),
					stdErr
				);
				stderrGlobber.producerIsCloseable(true);
				stderrGlobber.consumerIsCloseable(false);
			}
			else
			{
/*
				if ( stderrStream != null )
				{
					stderrStream.close();
					stderrStream = null;
				}
*/
			}

			Thread stdinGlobberTh = null;
			Thread stdoutGlobberTh = null;
			Thread stderrGlobberTh = null;

			if ( stdinGlobber != null )
			{
				stdinGlobberTh = new Thread( stdinGlobber );
				stdinGlobberTh.start();
				//stdinGlobberTh.join();
			}
			if ( stdoutGlobber != null )
			{
				stdoutGlobberTh = new Thread( stdoutGlobber );
				stdoutGlobberTh.start();
				//stdoutGlobberTh.join();
			}
			if ( stderrGlobber != null )
			{
				stderrGlobberTh = new Thread( stderrGlobber );
				stderrGlobberTh.start();
				//stderrGlobberTh.join();
			}

			//System.err.println("[executeCmd>>> WAITING for process");//XXX

			// waits for process termination (FIXME: possible deadlock?!?)
			exitCode = p.waitFor();

/*
			System.err.println("[executeCmd>>> CLOSING streams");

			if ( stdinStream != null )
			{
				stdinStream.close();
				stdinStream = null;
			}
			if ( stdoutStream != null )
			{
				stdoutStream.close();
				stdoutStream = null;
			}
			if ( stderrStream != null )
			{
				stderrStream.close();
				stderrStream = null;
			}
*/

			//System.err.println("[executeCmd>>> WAITING for STDIN thread");//XXX

			if ( stdinGlobberTh != null )
			{
				stdinGlobberTh.join();
				stdinGlobber = null;
				stdinGlobberTh = null;;
			}

			//System.err.println("[executeCmd>>> WAITING for STDOUT thread");//XXX

			if ( stdoutGlobberTh != null )
			{
				stdoutGlobberTh.join();
				stdoutGlobber = null;
				stdoutGlobberTh = null;
			}

			//System.err.println("[executeCmd>>> WAITING for STDERR thread");//XXX

			if ( stderrGlobberTh != null )
			{
				stderrGlobberTh.join();
				stderrGlobber = null;
				stderrGlobberTh = null;
			}
		}
		catch ( Throwable t )
		{
			t.printStackTrace(); //XXX
			throw new CommonException(t);
		}

		//System.err.println("[executeCmd>>> END exec command");//XXX

		return exitCode;
	}
}
