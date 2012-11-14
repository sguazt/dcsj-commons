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

package it.unipmn.di.dcs.common.math;

import java.util.Random;
 
/**
 * This class provides a method to generate permutations.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class PermutationGenerator
{
	private Random generator;

	/**
	 * A constructor.
	 */
	public PermutationGenerator()
	{
		this.generator = new Random();
	}
 
	/**
	 * Gets the next permutation
	 *
	 * @param n the maximum number in the permutation
	 * @return r the array containing the permutations
	 */
	public int[] nextPermutation(int n)
	{  
		int[] p = new int[n];
		for (int i = 0; i < n; i++) 
		{
			p[i] = i + 1;
		}

		int pSize = n;
		int[] r = new int[n];

		for (int i = 0; i < n; i++)
		{
			int pos = this.generator.nextInt(pSize);
			r[i] = p[pos];
			p[pos] = p[pSize - 1];
			pSize--;
		}
		return r;
	}
}

