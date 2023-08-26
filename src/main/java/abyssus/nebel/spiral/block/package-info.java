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

/**
 * Blocks (turns of the spiral).
 * <p>The package contains classes for providing queue blocks.</p>
 * <p>Each of the blocks is used by the queue once, after which it is passed to the mercy of the garbage collector. The block encapsulates an index converter used to reduce contention for cache lines when accessing neighboring cells from several threads, and which determines the block size, as well as the set of cells itself, in which the elements of the queue are stored.</p>
 * <p>Each block can be thought of as a separate turn of a spiral - the entire infinite spiral consists of individual turns connected together.</p>
 * @since 1.0
 */
package abyssus.nebel.spiral.block;
