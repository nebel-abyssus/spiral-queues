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

package abyssus.nebel.spiral;

import abyssus.nebel.spiral.block.IBlock;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Universal queue head.
 * <p>The head structure is the same for both the element provider and the request provider. The head of the queue stores an atomic index into the block, and a linked node containing the block itself.</p>
 * @param index The atomic index of the next block cell.
 * @param node The linked node containing the block itself.
 * @since 1.0
 * @author Sergey Shpagin
 */
record UniversalHead (
	AtomicInteger index,
	LinkedNode<IBlock<Object>> node
) { // record body

// constructors

	/**
	 * The constructor of the universal head of the queue.
	 * <p>The only purpose of the constructor is to make sure that the specified record components exist.</p>
	 * @param index The atomic index of the next block cell.
	 * @param node The linked node containing the block itself.
	 * @throws NullPointerException If any of the arguments does not exist.
	 */
	UniversalHead {
		Objects.<AtomicInteger>requireNonNull(index);
		Objects.<LinkedNode<IBlock<Object>>>requireNonNull(node);
	} // UniversalHead()

} // UniversalHead
