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
 * Converter that performs division into subranges.
 * <p>The idea used by this converter is to logically split the entire index range into many independent subranges. The converter maps the sequence of indices of the entire range first to the initial indices of the individual subranges, then to the next ones, and so on.</p>
 * <p>The entire index range is divided into an integer number <em>n</em> of subranges of equal size <em>m</em>. Each of the indices of the entire range <em>i = j * n + k</em>, where <em>0 &lt;= k &lt; n</em>, maps to the <em>j</em>-th index of the <em>k</em>-th subrange. Since the statement <em>0 &lt;= i &lt; n * m</em> is true, then <em>0 &lt;= j &lt; m</em>.</p>
 * <p>The point is that if the subrange size is a multiple of the target machine's cache line size, then neighboring indices will be mapped to different cache lines, which will reduce the contention between cores for exclusive access to cache lines when they are modified.</p>
 * @since 1.0
 * @author Sergey Shpagin
 */
public class SubrangesConverter extends AbstractConverter {

// instance fields

	/**
	 * The number of subranges in the block.
	 */
	private final int n;

	/**
	 * Subrange size.
	 */
	private final int m;

// constructors

	/**
	 * Constructor of the converter based on subranges.
	 * @param blockSize Block size. Must be positive and a multiple of the subrange size.
	 * @param m Subrange size. Must be positive.
	 * @throws IllegalArgumentException If any of the following conditions are not satisfied:<ul>
	 *     <li>The specified block size is positive.</li>
	 *     <li>The specified size of the subrange is positive.</li>
	 *     <li>The block size is a multiple of the subrange size.</li>
	 * </ul>
	 */
	public SubrangesConverter (
		final int blockSize,
		final int m
	) { // constructor body
		super(blockSize);
		if (!((m > 0) && (blockSize % m == 0))) throw new IllegalArgumentException();
		this.n = blockSize / m;
		this.m = m;
	} // SubrangesConverter()

// instance methods

	/**
	 * Index conversion.
	 * <p>If the specified index is outside the block index range, then the result of the method is undefined.</p>
	 */
	@Override
	public int convert (final int idx) {
		final int j = idx / n;
		final int k = idx % n;
		return m * k + j;
	} // convert()

} // SubrangesConverter
