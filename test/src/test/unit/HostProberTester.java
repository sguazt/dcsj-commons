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

package test.unit;

import it.unipmn.di.dcs.common.net.IHostProber;
import it.unipmn.di.dcs.common.net.IHostProberStats;
import it.unipmn.di.dcs.common.net.InetProtocol;
import it.unipmn.di.dcs.common.net.HostProber;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * Test class for HostProber.
 *
 * @author <a href="mailto:marco.guazzone@mfn.unipmn.it">Marco Guazzone</a>
 */
public class HostProberTester
{
	private HostProber prober = null;

	@Before
	public void setUp()
	{
		this.prober = new HostProber("127.0.0.1");
	}

	@Test
	public void testPinger()
	{
		try
		{
			this.prober.enablePing();

			IHostProberStats stats = null;

			stats = this.prober.probe();

			System.out.println( "[ping] Reachable? " + stats.getPingStats().isReachable() );
			System.out.println( "[ping] Info: " + stats.getPingStats().getInfo() );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			assertTrue( false );
		}

		assertTrue( true );
	}

	@Test
	public void testInetService()
	{
		try
		{
			IHostProberStats stats = null;

			this.prober.addInetService( InetProtocol.TCP, 22 ); //SSH
			this.prober.addInetService( InetProtocol.UDP, 631 ); //IPP

			stats = this.prober.probe();

			System.out.println( "[inet-service] Reachable? " + stats.getInetServiceStats(InetProtocol.TCP, 22).isReachable() );
			System.out.println( "[inet-service] Info: " + stats.getInetServiceStats(InetProtocol.TCP, 22).getInfo() );
			System.out.println( "[inet-service] Reachable? " + stats.getInetServiceStats(InetProtocol.UDP, 631).isReachable() );
			System.out.println( "[inet-service] Info: " + stats.getInetServiceStats(InetProtocol.UDP, 631).getInfo() );
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
		JUnitCore.main( HostProberTester.class.getName() );
	}
}
