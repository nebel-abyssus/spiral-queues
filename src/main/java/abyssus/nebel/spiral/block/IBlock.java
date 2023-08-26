/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU LESSER GENERAL PUBLIC LICENSE Version 3 only,
 * as published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU LESSER GENERAL PUBLIC LICENSE
 * Version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU LESSER GENERAL PUBLIC LICENSE
 * Version 3 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2023 Sergey Shpagin
 */

package abyssus.nebel.spiral.block;

/**
 * Block interface.
 * <p>A block (turn of a spiral) is a set of cells that can be accessed a limited number of times. This number is determined by a specific implementation class, but by default, it should be considered that after a value has been placed and removed in a cell, it becomes unusable for further use.</p>
 * @param <T> The type of block elements.
 * @since 1.0
 * @author Sergey Shpagin
 */
public interface IBlock <T> {

	/**
	 * Removal or placement of an element.
	 * <p>The method retrieves and returns the previously placed element from the specified cell, clearing the cell, or, if the cell is still empty, places the specified element into it, returning {@code null}.</p>
	 * @param idx Cell index.
	 * @param elem The element to be placed.
	 * @return The element removed from the cell, or {@code null} if the cell was empty.
	 * @throws IndexOutOfBoundsException If the specified cell index is negative, equal to or greater than the block size.
	 * @throws NullPointerException If the element being placed does not exist.
	 */
	T getOrPut (
		int idx,
		T elem
	); // getOrPut()

	/**
	 * Block size.
	 * <p>The method returns the block size (the number of cells in the block).</p>
	 * @return Block size.
	 */
	int getBlockSize ();

} // IBlock
