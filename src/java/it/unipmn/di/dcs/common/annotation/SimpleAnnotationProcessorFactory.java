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

package it.unipmn.di.dcs.common.annotation;

//import it.unipmn.di.dcs.common.annotation.Experimental;
//import it.unipmn.di.dcs.common.annotation.FIXME;
//import it.unipmn.di.dcs.common.annotation.TODO;
//import it.unipmn.di.dcs.common.annotation.TODOs;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Annotation processory factory.
 *
 * <a href="http://adiguba.developpez.com/tutoriels/java/tiger/annotations/">http://adiguba.developpez.com/tutoriels/java/tiger/annotations/</a>
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class SimpleAnnotationProcessorFactory implements AnnotationProcessorFactory
{
	/** Collection containing names of supported Annotations */
	private static Collection<String> supportedAnnotationTypes = Arrays.asList(
		Experimental.class.getName(),
		FIXME.class.getName(),
		TODO.class.getName(),
		TODOs.class.getName()
	);

	/** Collection of supported options */
	//private static Collection<String> supportedOptions = Collections.emptyList();

	//@{ AnnotationProcessorFactory implementation /////////////////////////

	public Collection<String> supportedAnnotationTypes()
	{
		return SimpleAnnotationProcessorFactory.supportedAnnotationTypes;
	}

	public Collection<String> supportedOptions()
	{
		//return SimpleAnnotationProcessorFactory.supportedOptions;
		return Collections.emptyList();
	}

	public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> atds, AnnotationProcessorEnvironment env)
	{
		if ( atds.isEmpty() )
		{
			// no annotation is present => returns an empty processor
			return AnnotationProcessors.NO_OP;
		}

		return new SimpleAnnotationProcessor( env );
	}

	//@} AnnotationProcessorFactory implementation /////////////////////////
}
