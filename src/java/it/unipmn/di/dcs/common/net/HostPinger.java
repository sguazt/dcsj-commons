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

import java.io.BufferedWriter;
//import java.io.IOException;
import java.io.Writer;
import java.io.StringWriter;
import java.net.InetAddress;

/**
 * Ping a given target.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class HostPinger implements IHostPinger
{
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static final int DEFAULT_COUNT = 3;
	private static final int DEFAULT_TIMEOUT = 3000;

	/** The host to be pinged. */
	private String host;
	/** The number of maximum ping. */
	private int count;
	/** The max time to wait before claiming a timeout. */
	private int timeout;

	/** A constructor. */
	public HostPinger()
	{
		this(DEFAULT_HOST, DEFAULT_COUNT, DEFAULT_TIMEOUT);
	}

	/** A constructor. */
	public HostPinger(String host)
	{
		this(host, DEFAULT_COUNT, DEFAULT_TIMEOUT);
	}

	/** A constructor. */
	public HostPinger(String host, int count)
	{
		this(host, count, DEFAULT_TIMEOUT);
	}

	/** A constructor. */
	public HostPinger(String host, int count, int timeout)
	{
		this.host = host;
		this.count = count;
		this.timeout = timeout;
	}

	public void setHost(String value)
	{
		this.host = value;
	}

	public String getHost()
	{
		return this.host;
	}

	public void setCount(int value)
	{
		this.count = value;
	}

	public int getCount()
	{
		return this.count;
	}

	public void setTimeout(int value)
	{
		this.timeout = value;
	}

	public int getTimeout()
	{
		return this.timeout;
	}

	/** Ping the host. */
	public IHostPingerStats ping() throws CommonException
	{
		HostPingerStats stats = null;

		stats = new HostPingerStats();

		try
		{
			boolean reachable = false;
			reachable = InetAddress.getByName(
				this.host
			).isReachable(
				this.timeout
			);

			stats.setIsReachable(reachable);

			if ( reachable )
			{
				StringWriter swr = null;
				swr = new StringWriter();

				OperatingSystemInteractionUtil.executeCmd(
					"ping",
					new String[] { "-c " + Integer.toString(this.count), this.host },
					null,
					new BufferedWriter( swr ),
					null
				);

				try { swr.close(); } catch (Exception e) { /* empty */ }

				stats.setInfo( swr.toString() );
			}
		}
		catch (CommonException ce)
		{
			throw ce;
		}
		catch (Exception e)
		{
			throw new CommonException(e);
		}

		return stats;
	}
}
