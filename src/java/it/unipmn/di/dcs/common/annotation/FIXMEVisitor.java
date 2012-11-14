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

//import it.unipmn.di.dcs.common.annotation.FIXME;
//import it.unipmn.di.dcs.common.annotation.TODO;
//import it.unipmn.di.dcs.common.annotation.TODOs;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Messager;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.util.SimpleDeclarationVisitor;

/**
 * A declaration visitor for FIXME annotations.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class FIXMEVisitor extends SimpleDeclarationVisitor
{
	protected final AnnotationProcessorEnvironment env;

	/**
	 * A Constructor
	 *
	 * @param env The annotation processor environment.
	 */
	public FIXMEVisitor(AnnotationProcessorEnvironment env)
	{
		this.env = env;
	}

	@Override
	public void visitDeclaration(Declaration decl)
	{
		// Gets FIXME annotations
		FIXME fixme = decl.getAnnotation( FIXME.class );
		if ( fixme != null )
		{
			printMessage( decl, fixme );
		}
	}

	/**
	 * Prints FIXME annotation to the console.
	 * @param decl The declaration where the annotation has been found.
	 * @param fixme The FIXME annotation.
	 */
	public void printMessage(Declaration decl, FIXME fixme)
	{
		String fixmeStr = "FIXME: [!" + fixme.severity() + "] " + fixme.value();
		Messager m = env.getMessager();

		switch ( fixme.severity() )
		{
			case CRITICAL:
				m.printError(
					decl.getPosition(),
					decl.getSimpleName() + ">> " + fixmeStr
				);
				break;
			case IMPORTANT:
			case MODERATE:
			case LOW:
				m.printWarning(
					decl.getPosition(),
					decl.getSimpleName() + ">> " + fixmeStr
				);
				break;
			case DOCUMENTATION:
				m.printNotice(
					decl.getPosition(),
					decl.getSimpleName() + ">> " + fixmeStr
				);
				break;
		}
	}
}
