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

/**
 * Interface for host probers.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IHostProber
{
//	String getHost();

	void enablePing();

	void enablePing(int count);

	void enablePing(IHostPinger pinger);

	void disablePing();

	void addInetService(InetProtocol proto, int port);

	IHostProberStats probe() throws CommonException;
}
