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
 * Identity conversion.
 * <p>The index converter that always returns the specified index. Converter without conversion. The fastest. Can be used with any positive block size. However, it does not reduce contention for access to cache lines in any way, when accessing adjacent indices.</p>
 * @since 1.0
 * @author Sergey Shpagin
 */
public class IdentityConverter extends AbstractConverter {

// constructors

	/**
	 * Converter constructor.
	 * @param blockSize Block size. Must be strictly positive.
	 * @throws IllegalArgumentException If the specified block size is less than or equal to zero.
	 */
	public IdentityConverter (final int blockSize) {
		super(blockSize);
	} // IdentityConverter()

// instance methods

	/**
	 * Index conversion.
	 * <p>No conversion takes place. The method always returns the specified index unchanged.</p>
	 */
	@Override
	public int convert (final int idx) {
		return idx;
	} // convert()

} // IdentityConverter
