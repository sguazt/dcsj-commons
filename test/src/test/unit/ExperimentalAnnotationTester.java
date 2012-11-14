/*
 * Copyright (C) 2007  Distributed Computing System (DCS) Group, Computer
 * Science Department - University of Piemonte Orientale, Alessandria (Italy).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.unit;

import it.unipmn.di.dcs.common.annotation.Experimental;

import java.lang.annotation.Annotation;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * Experimental annotation tester.
 *
 * Note: this test succeeds only if the retention policy of Experimental
 * annotation is RUNTIME, because that policy allow to read annotations
 * reflectively.
 *
 * @author Marco Guazzone, &lt;marco.guazzone@mfn.unipmn.it&lt;
 */
@Experimental
public class ExperimentalAnnotationTester
{
	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void test()
	{
		boolean result = false;

		System.out.println( "Entering ExperimentalAnnotationTest.test" );
		for ( Annotation a : ExperimentalAnnotationTester.class.getAnnotations() )
		{
			System.out.println( "Found code annotation: " + a );

			if ( a instanceof Experimental )
			{
				Experimental ea = (Experimental) a;

				System.out.println( "Found experimental code annotation" );
				if ( ea.value() != null && ea.value().length() > 0 )
				{
					System.out.println( "Comment: " + ea.value() );
				}

				result = true;
			}
		}

		System.out.println( "Leaving ExperimentalAnnotationTest.test" );

		assertTrue( result );
	}

	@After
	public void tearDown()
	{
		// empty
	}

	public static void main(String args[])
	{
		System.out.println( "Entering ExperimentalAnnotationTest.main" );

		JUnitCore.main( ExperimentalAnnotationTester.class.getName() );

		// Uncomment this below for running outside of JUnit
		//new ExperimentalAnnotationTester().test();

		System.out.println( "Leaving ExperimentalAnnotationTest.main" );
	}
}
