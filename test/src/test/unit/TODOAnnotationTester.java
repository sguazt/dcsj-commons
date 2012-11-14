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

import it.unipmn.di.dcs.common.annotation.TODO;

import java.lang.annotation.Annotation;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * TODO annotation tester.
 *
 * Note: this test succeeds only if the retention policy of TODO
 * annotation is RUNTIME, because that policy allow to read annotations
 * reflectively.
 *
 * @author Marco Guazzone, &lt;marco.guazzone@mfn.unipmn.it&gt;
 */
@TODO("Something ToDo on class TODOAnnotationTester ...")
public class TODOAnnotationTester
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

		System.out.println( "Entering TODOAnnotationTest.test" );

		for ( Annotation a : TODOAnnotationTester.class.getAnnotations() )
		{
			System.out.println( "Found code annotation: " + a );

			if ( a instanceof TODO )
			{
				TODO ta = (TODO) a;

				System.out.println( "Found experimental code annotation" );
				if ( ta.value() != null && ta.value().length() > 0 )
				{
					System.out.println( "Comment: " + ta.value() );
					System.out.println( "Severity: " + ta.severity() );
				}

				result = true;
			}
		}

		System.out.println( "Leaving TODOAnnotationTest.test" );

		assertTrue( result );
	}

	@After
	public void tearDown()
	{
		// empty
	}

	public static void main(String args[])
	{
		System.out.println( "Entering TODOAnnotationTest.main" );

		JUnitCore.main( TODOAnnotationTester.class.getName() );

		// Uncomment this below for running outside of JUnit
		//new ExperimentalAnnotationTester().test();

		System.out.println( "Leaving TODOAnnotationTest.main" );
	}
}
