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

package abyssus.nebel.spiral.converter;

/**
 * Abstract index converter.
 * <p>The class implements the functionality common to most converters for storing and returning the block size, leaving the conversion itself to descendants.</p>
 * @since 1.0
 * @author Sergey Shpagin
 */
public abstract class AbstractConverter implements IIndexConverter {

// instance fields

	/**
	 * Block size.
	 */
	private final int blockSize;

// constructors

	/**
	 * Abstract converter constructor.
	 * <p>Sets the block size to be used for the class instance being created.</p>
	 * @param blockSize Block size. Must be strictly greater than zero.
	 * @throws IllegalArgumentException If the specified block size is not greater than zero.
	 */
	public AbstractConverter (final int blockSize) {
		if (blockSize <=0) throw new IllegalArgumentException();
		this.blockSize = blockSize;
	} // AbstractConverter()

// instance methods

	@Override
	public int getBlockSize () {
		return blockSize;
	} // getBlockSize()

} // AbstractConverter
