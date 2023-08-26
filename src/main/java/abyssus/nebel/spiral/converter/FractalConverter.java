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
 * Fractal converter.
 * <p>The idea of the converter is based on the fractal division of the index range into two parts. First, a whole range of indices is taken. Then it is split into two equal parts. Then each of the resulting parts is divided into two more parts, and so on.</p>
 * <p>This imposes a limit on the block size. Only those blocks whose size is one of the powers of two can be processed in this way.</p>
 * <p>The split starts with a whole range of indices, and continues until a specific index is selected. At each partition iteration, a fragment is considered, limited by the start and end indices. If no index has yet been selected within this fragment, then the selection stops at the first index of the fragment. Otherwise, at the next iteration of the partition, the half of this fragment is considered, the opposite of the one in which the index was chosen last time. Thus, for any particular fragment, each time an index is selected from the range of the fragment, the selected half is "switched".</p>
 * <p>However, it is not necessary to perform a cyclic division of the index range to calculate the converted index. Such a fractal division has already been implemented for us, and it is contained in the binary representation of the iteration number, since the very structure of the binary number system within the given bit grid implies a fractal partition of the available range of values into two parts. Therefore, all we need to find the value of the converted index is to reverse the digit order of the binary representation of the iteration number represented by the index being converted, while remaining within the bit grid given by the block size.</p>
 * @since 1.0
 * @author Sergey Shpagin
 */
public class FractalConverter extends AbstractConverter {

// instance fields

	/**
	 * The shift of the reversed value.
	 * <p>The shift required for the reversed binary representation of the converted index to be back on the bit grid of the block size.</p>
	 */
	private final int shift;

// constructors

	/**
	 * Fractal converter constructor.
	 * @param blockSize Block size. Must be strictly greater than zero, and equal to one of the powers of two.
	 * @throws IllegalArgumentException If the specified block size is less than or equal to zero, or is not a power of 2.
	 */
	public FractalConverter (final int blockSize) {
		super(blockSize);
		if (((blockSize - 1) & blockSize) != 0) throw new IllegalArgumentException();
		shift = Integer.numberOfLeadingZeros(blockSize) + 1;
	} // FractalConverter()

// instance methods

	/**
	 * Index conversion.
	 * <p>The method converts the specified index by reversing the order of digits in its binary representation within the bit grid defined by the selected block size.</p>
	 * <p>The specified index must be non-negative and strictly less than the selected block size. Otherwise, the behavior of the method is undefined.</p>
	 */
	@Override
	public int convert (final int idx) {
		return Integer.reverse(idx) >>> shift;
	} // convert()

} // UniversalConverter
