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

package it.unipmn.di.dcs.common.net;

import it.unipmn.di.dcs.common.CommonException;
import it.unipmn.di.dcs.common.util.OperatingSystemInteractionUtil;
import it.unipmn.di.dcs.common.util.Pair;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Probe a host for checking its aliveness and the reachability of some
 * services.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class HostProber implements IHostProber
{
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static final int PING_COUNT = 5;

	private String host;
	private boolean pingEnabled = false;
	//private int pingCount = PING_COUNT;
	private IHostPinger pinger;
	private Set<Pair<InetProtocol,Integer>> services;

	/** A constructor. */
	public HostProber()
	{
		this( DEFAULT_HOST );
	}

	/** A constructor. */
	public HostProber(String host)
	{
		this.host = host;
		this.services = new HashSet<Pair<InetProtocol,Integer>>();
	}

	public void setHost(String value)
	{
		this.host = value;
	}

	public String getHost()
	{
		return this.host;
	}

	//@{ IHostProber implementation ///////////////////////////////////////

	public void disablePing()
	{
		this.pingEnabled = false;
		this.pinger = null;
	}

	public void enablePing()
	{
		this.enablePing( PING_COUNT );
	}

	public void enablePing(int count)
	{
		this.pingEnabled = true;
		this.pinger = new HostPinger(this.getHost(), count);
	}

	public void enablePing(IHostPinger pinger)
	{
		this.pinger = pinger;
	}

	public void addInetService(InetProtocol proto, int port)
		{
		this.services.add( new Pair<InetProtocol, Integer>(proto, port) );
	}

	public IHostProberStats probe() throws CommonException
	{
		HostProberStats stats = null;

		stats = new HostProberStats();

		try
		{
			if ( this.isPingEnabled() )
			{
				IHostPingerStats pingStats = null;

				pingStats = pinger.ping();

				stats.setPingStats(pingStats);
			}
			else
			{
				stats.setPingStats(null);
			}

			for (Pair<InetProtocol,Integer> svc : this.services)
			{
				IInetServiceStats svcStats = null;

				svcStats = this.probeService(
						svc.getFirst(),
						svc.getSecond()
				);
				stats.addInetServiceStats(
					svcStats
				);
			}
		}
		catch (Exception e)
		{
			throw new CommonException(e);
		}

		return stats;
	}

	//@} IHostProber implementation ///////////////////////////////////////

	protected boolean isPingEnabled()
	{
		return this.pingEnabled;
	}

	protected IInetServiceStats probeService(InetProtocol proto, int port) throws Exception
	{
		boolean reachable = false;
		InetServiceStats stats = null;

		stats = new InetServiceStats( proto, port );

		switch (proto)
		{
			case TCP:
			{
				Socket sock = null;

				// Try to connect to the host:port
				try
				{
					sock = new Socket( this.host, port );
					stats.setIsReachable( sock.isConnected() );
				}
				catch (Exception ee)
				{
					// ignore
				}

				if ( sock != null && sock.isBound() )
				{
					sock.close();
					sock = null;
				}
				break;
			}
			case UDP:
			{
				DatagramSocket sock = null;

				// Try to connect to the host:port
				try
				{
					InetAddress addr = null;

					addr = InetAddress.getByName( this.host );
					sock = new DatagramSocket();
					sock.connect( addr, port );
					// Note: the method 'sock.isConnected'
					// is not reliable since in most cases
					// returns true even if the service is
					// really donw.
					//stats.setIsReachable( sock.isConnected() );
					if ( sock.isConnected() )
					{
						// Try to send a packet and wait for something
						try
						{
							byte[] data = "".getBytes();
							sock.send(
								new DatagramPacket(
									data,
									data.length,
									addr,
									port
								)
							);
							sock.setSoTimeout( 5000 );
							sock.receive(
								new DatagramPacket(
									data,
									data.length
								)
							);
							stats.setIsReachable( true );
						}
						//catch (PortUnreachableException e)
						catch (Exception e)
						{
							stats.setIsReachable( false );
						}
					}
					else
					{
						stats.setIsReachable( false );
					}
				}
				catch (Exception ee)
				{
					// ignore
				}

				if ( sock != null && sock.isBound() )
				{
					sock.close();
					sock = null;
				}
				break;
			}
		}

		return stats;
	}
}
