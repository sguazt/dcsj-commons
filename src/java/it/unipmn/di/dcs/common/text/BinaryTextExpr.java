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

package it.unipmn.di.dcs.common.text;

/**
 * Class for binary textual operators.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class BinaryTextExpr implements ITextExpr
{
	private ITextExpr left; /** The left operand. */
	private BinaryTextOp op; /** The binary operator. */
	private ITextExpr right; /** The right operand. */

	/** A constructor. */
	protected BinaryTextExpr()
	{
		// empty
	}

	/** A constructor. */
	public BinaryTextExpr(ITextExpr left, BinaryTextOp op, ITextExpr right)
	{
		this.left = left;
		this.op = op;
		this.right = right;
	}

	/**
	 * Set the left operand.
	 */
	protected void setLeft(ITextExpr value)
	{
		this.left = value;
	}

	/**
	 * Get the left operand.
	 */
	public ITextExpr getLeft()
	{
		return this.left;
	}

	/**
	 * Set the binary operator.
	 */
	protected void setOp(BinaryTextOp value)
	{
		this.op = value;
	}

	/**
	 * Get the binary operator.
	 */
	public BinaryTextOp getOp()
	{
		return this.op;
	}

	/**
	 * Set the right operand.
	 */
	protected void setRight(ITextExpr value)
	{
		this.right = value;
	}

	/**
	 * Get the right operand.
	 */
	public ITextExpr getRight()
	{
		return this.right;
	}

	@Override
	public String toString()
	{
		return	this.left.toString()
			+ " " + this.op.getSymbol()
			+ " " + this.right.toString();
	}
}
