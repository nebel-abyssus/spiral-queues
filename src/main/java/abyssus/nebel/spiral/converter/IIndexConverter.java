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

import java.util.function.IntUnaryOperator;

/**
 * Index converter interface.
 * <p>An index converter maps a block cell index to another index in the same range to reduce contention for individual cache lanes when accessing adjacent indexes.</p>
 * <p>The converter stores and can use the block size. Since the converter itself is an integral functional part of the block, the responsibility of determining the block size is delegated to the converter. That is, it is the index converter used when creating the block that determines the size of the block.</p>
 * @since 1.0
 * @author Sergey Shpagin
 */
public interface IIndexConverter {

// static methods

	/**
	 * Index converter based on the given function.
	 * <p>The method returns a new index converter based on the user-specified conversion function.</p>
	 * @param blockSize Block size. Must be a positive number.
	 * @param conversionFunction Index conversion function. Taking an index given within the block size must return a converted index within the same limits. Must not return the same result for different arguments.
	 * @return Index converter that uses the specified function to convert.
	 * @throws IllegalArgumentException If the specified block size is not a positive number.
	 * @throws NullPointerException If the specified function does not exist.
	 */
	static IIndexConverter of (
		final int blockSize,
		final IntUnaryOperator conversionFunction
	) { // method body
		return new LambdaConverterHelper(blockSize, conversionFunction);
	} // of()

// instance methods

	/**
	 * Block size.
	 * <p>The method returns the block size that defines the range in which the converter performs index transformations. The block size returned by a particular converter instance is always the same. The block size is always strictly greater than zero.</p>
	 * @return Block size.
	 */
	int getBlockSize ();

	/**
	 * Index conversion.
	 * <p>The method maps the specified index greater than or equal to zero and less than the block size to another index in the same range. If the specified index is outside the specified range, the behavior of the method is undefined.</p>
	 * @param idx The index to be converted.
	 * @return The converted index.
	 */
	int convert (int idx);

} // IIndexConverter
