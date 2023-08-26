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

import abyssus.nebel.spiral.converter.IIndexConverter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Disposable block.
 * <p>Each of the cells of the block becomes unusable after the first withdrawal of the value previously placed in this cell.</p>
 * @param <T> The type of block elements.
 * @since 1.0
 * @author Sergey Shpagin
 */
public class DisposableBlock <T> implements IBlock<T> {

// instance fields

	/**
	 * Block size.
	 * <p>It is stored in the field of the object, as it is required to be checked every time the block is accessed.</p>
	 */
	private final int size;

	/**
	 * Index converter.
	 * <p>Used to transform the index of block elements, in order to reduce contention for individual cache lines when accessing adjacent indices.</p>
	 */
	private final IIndexConverter converter;

	/**
	 * Array of atomically mutable references to block elements.
	 */
	private final AtomicReferenceArray<T> elems;

// constructors

	/**
	 * Disposable block constructor.
	 * <p>It takes an index converter as its only argument. The index converter sets the block size, and is used further for its intended purpose - to transform the index.</p>
	 * @param converter Index converter.
	 * @throws NullPointerException If the specified converter does not exist.
	 */
	public DisposableBlock (
		final IIndexConverter converter
	) { // method body
		this.size = converter.getBlockSize();
		this.converter = converter;
		this.elems = new AtomicReferenceArray<T>(size);
	} // DisposableBlock()

// instance methods

	/**
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	public T getOrPut (
		int idx,
		final T elem
	) { // method body
		if ((idx < 0) || (idx >= size)) throw new IndexOutOfBoundsException();
		Objects.<T>requireNonNull(elem);
		idx = converter.convert(idx);
		final T oldValue = elems.getAndSet(idx, elem);
		if (oldValue != null) {
			elems.setPlain(idx, null);
		} // if
		return oldValue;
	} // getOrPut()

	@Override
	public int getBlockSize (
	) { // method body
		return size;
	} // getBlockSize()

} // DisposableBlock
