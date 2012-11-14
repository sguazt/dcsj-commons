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

package it.unipmn.di.dcs.common.ui;

import java.io.IOException;

/**
 * Interface for User Interface drivers.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IUIDriver
{
	/**
	 * Show a message.
	 */
	void showMsg(String msg);

	/**
	 * Show a confirmation message.
	 */
	void showOk(String msg);

	/**
	 * Show an error message.
	 */
	void showError(String msg);

	/**
	 * Show a warning message.
	 */
	void showWarning(String msg);

	/**
	 * Show an informational message.
	 */
	void showInfo(String msg);

	/**
	 * Ask a question; the answer is mandatory.
	 */
	String ask(String question) throws UIException;

	/**
	 * Ask a question; the answer is mandatory and must be integral.
	 */
	int askInt(String question) throws UIException;

	/**
	 * Ask a question; the answer is optional (may be empty).
	 */
	String ask(String question, String defValue) throws UIException;

	/**
	 * Ask a question; the answer is optional (may be empty).
	 */
	int askInt(String question, Integer defValue) throws UIException;

	/**
	 * Ask a "yes/no" question.
	 */
	boolean askYesNo(boolean pedantic) throws UIException;

	/**
	 * Ask a "yes/no" question with a user supplied message.
	 */
	boolean askYesNo(String msg, boolean pedantic) throws UIException;

//TODO
//	/**
//	 * Ask a "yes/no" question.
//	 */
//	ThreeStateLogic askYesNoCancel(boolean pedantic) throws UIException;
//
//	/**
//	 * Ask a "yes/no" question with a user supplied message.
//	 */
//	ThreeStateLogic askYesNoCancel(String msg, boolean pedantic) throws UIException;
}
