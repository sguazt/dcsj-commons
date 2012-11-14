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

//import it.unipmn.di.dcs.common.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds statistics collected during host probing.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class HostProberStats implements IHostProberStats
{
	private IHostPingerStats pingStats;
	private Map<String,IInetServiceStats> svcStats;

	public HostProberStats()
	{
		this.svcStats = new HashMap<String,IInetServiceStats>();
	}

	public void setPingStats(IHostPingerStats stats)
	{
		this.pingStats = stats;
	}

	public void addInetServiceStats(IInetServiceStats stats)
	{
		String key = null;

		key = Integer.toString(stats.getPort()) + "/" + stats.getProtocol();

		this.svcStats.put(
			key,
			stats
		);
	}

	//@{ IHostProberStats /////////////////////////////////////////////////

	public IHostPingerStats getPingStats()
	{
		return this.pingStats;
	}

	public Collection<IInetServiceStats> getInetServicesStats()
	{
		return this.svcStats.values();
	}

	public IInetServiceStats getInetServiceStats(InetProtocol proto, int port)
	{
		String key = null;

		key = Integer.toString(port) + "/" + proto;

		return this.svcStats.get( key );
	}

	//@} IHostProberStats /////////////////////////////////////////////////
}
