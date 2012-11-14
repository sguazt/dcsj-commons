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

//import it.unipmn.di.dcs.common.annotation.ExperimentalVisitor;
//import it.unipmn.di.dcs.common.annotation.FIXMEVisitor;
//import it.unipmn.di.dcs.common.annotation.TODOVisitor;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.util.DeclarationVisitors;

/**
 * A very simple annotation processor.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class SimpleAnnotationProcessor implements AnnotationProcessor
{
	/** The annotation processor environment. */
	protected final AnnotationProcessorEnvironment env;

	/**
	 * A Constructor.
	 *
	 * @param env The annotation processor environment.
	 */
	public SimpleAnnotationProcessor(AnnotationProcessorEnvironment env)
	{
		this.env = env;
	}

	//@{ AnnotationProcessor implementation ////////////////////////////////

	public void process()
	{
		ExperimentalVisitor expVisitor = new ExperimentalVisitor( env );
		FIXMEVisitor fixmeVisitor = new FIXMEVisitor( env );
		TODOVisitor todoVisitor = new TODOVisitor( env );

		for ( Declaration d : env.getTypeDeclarations() )
		{
			// Experimental visitor
			d.accept(
				DeclarationVisitors.getSourceOrderDeclarationScanner(
					expVisitor,
					DeclarationVisitors.NO_OP
				)
			);
			// FIXME visitor
			d.accept(
				DeclarationVisitors.getSourceOrderDeclarationScanner(
					fixmeVisitor,
					DeclarationVisitors.NO_OP
				)
			);
			// TODO visitor
			d.accept(
				DeclarationVisitors.getSourceOrderDeclarationScanner(
					todoVisitor,
					DeclarationVisitors.NO_OP
				)
			);
		}
	}

	//@} AnnotationProcessor implementation ////////////////////////////////
}
