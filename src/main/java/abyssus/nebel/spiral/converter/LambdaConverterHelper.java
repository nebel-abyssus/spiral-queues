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

import java.util.Objects;
import java.util.function.IntUnaryOperator;

/**
 * Helper for creating a lambda-based converter.
 * <p>Helper class for creating an index converter based on a user-specified object that implements a functional interface.</p>
 * @since 1.0
 * @author Sergey Shpagin
 */
class LambdaConverterHelper extends AbstractConverter {

// instance fields

	/**
	 * User-defined index conversion function.
	 */
	private final IntUnaryOperator operator;

// constructors

	/**
	 * Lambda helper constructor.
	 * <p>Creates a helper instance that uses a user-specified function to convert an index.</p>
	 * @param blockSize Block size. Must be a positive number.
	 * @param conversionFunction Index conversion function. Given an index given within the block size, it should return a converted index within the same limits. Must not return the same result for different arguments.
	 * @throws IllegalArgumentException If the specified block size is not positive.
	 * @throws NullPointerException If the specified function does not exist.
	 */
	public LambdaConverterHelper (
		final int blockSize,
		final IntUnaryOperator conversionFunction
	) { // method body
		super(blockSize);
		operator = Objects.<IntUnaryOperator>requireNonNull(conversionFunction);
	} // LambdaConverterHelper()

// instance methods

	@Override
	public int convert (
		final int idx
	) { // method body
		return operator.applyAsInt(idx);
	} // convert()

} // LambdaConverterHelper
