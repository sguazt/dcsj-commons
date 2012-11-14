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

import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
//import javax.lang.model.type.*;
//import javax.lang.model.util.*;
import javax.tools.Diagnostic;
//import static javax.lang.model.SourceVersion.*;
//import static javax.lang.model.element.Modifier.*;
//import static javax.lang.model.element.ElementKind.*;
//import static javax.lang.model.type.TypeKind.*;
//import static javax.lang.model.util.ElementFilter.*;
import static javax.tools.Diagnostic.Kind.*;

/**
 * A processor for the <em>FIXME</em> annotation.
 *
 * This annotation processor follows the new JDK 6 annotation (JSR269) API.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
@SupportedAnnotationTypes( "it.unipmn.di.dcs.common.annotation.FIXME" )
public class FIXMEAnnotationProcessor extends AbstractProcessor
{
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		if (!roundEnv.processingOver())
		{
			for ( Element el : roundEnv.getRootElements() )
			{
				FIXME fixme = el.getAnnotation( FIXME.class );
				if ( fixme != null )
				{
					this.printMessage( fixme, el );
				}
			}
		}

		return false; // Allow other processors to examine files too.
	}

	@Override
	public SourceVersion getSupportedSourceVersion()
	{
		/*
		 * Return latest source version instead of a fixed version
		 * like RELEASE_6.  To return a fixed version, this class
		 * could be annotated with a SupportedSourceVersion
		 * annotation.
		 *
		 * Warnings will be issued if any unknown language constructs
		 * are encountered.
		 */
		return SourceVersion.latest();
	}

	private void printMessage(FIXME fixme, Element el)
	{
		String fixmeStr = "FIXME: [!" + fixme.severity() + "] " + fixme.value();

		Messager m = this.processingEnv.getMessager();

		Diagnostic.Kind kind = null;
		switch ( fixme.severity() )
		{
			case CRITICAL:
				kind = ERROR;
				break;
			case IMPORTANT:
			case MODERATE:
			case LOW:
				kind = WARNING;
				break;
			case DOCUMENTATION:
				kind = NOTE;
				break;
		}

		m.printMessage(
			kind,
			el.getSimpleName() + ">> " + fixmeStr,
			el
		);
	}
}
