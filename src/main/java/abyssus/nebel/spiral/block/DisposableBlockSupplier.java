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
import java.util.function.Supplier;

/**
 * Supplier of disposable blocks.
 * <p>Class instances have been delegated the role of containers that store the index converter selected by the user of the queue, and suppliers of instances of the disposable blocks that use the selected converter. The converter can be changed during the lifetime of the supplier, in which case new disposable blocks will be created based on the new converter.</p>
 * @param <T> The type of elements of the blocks being created.
 * @since 1.0
 * @author Sergey Shpagin
 */
public class DisposableBlockSupplier <T> implements Supplier<DisposableBlock<T>> {

// instance fields

	/**
	 * Index converter.
	 * <p>The index converter used to create new disposable blocks.</p>
	 */
	private IIndexConverter converter;

// constructors

	/**
	 * Disposable block supplier constructor.
	 * <p>Accepts the index converter used to create new blocks. The selected converter can be changed during the life of the supplier.</p>
	 * @param converter Index converter.
	 * @throws NullPointerException If the specified converter does not exist.
	 */
	public DisposableBlockSupplier (
		final IIndexConverter converter
	) { // method body
		setConverter(converter);
	} // DisposableBlockSupplier()

// instance methods

	/**
	 * Change the index converter used.
	 * <p>The method sets up a new index converter used to create new blocks. The selected converter can be changed during the life of the supplier.</p>
	 * @param converter Index converter.
	 * @throws NullPointerException If the specified converter does not exist.
	 */
	public void setConverter (
		final IIndexConverter converter
	) { // method body
		this.converter = Objects.<IIndexConverter>requireNonNull(converter);
	} // setConverter()

	/**
	 * Create a new disposable block.
	 * <p>Returns a new disposable block based on the selected index converter.</p>
	 * @return New disposable block.
	 */
	@Override
	public DisposableBlock<T> get (
	) { // method body
		return new DisposableBlock<T>(converter);
	} // get()

} // DisposableBlockSupplier
