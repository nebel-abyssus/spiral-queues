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
 * Index converters.
 * <p>The package contains block cell index converters. Index converters map a block cell index to another index in the same range to reduce contention for individual cache lanes when accessing adjacent indices. However, such a conversion has a negative side - it leads to an increase in the load on the cache and, in some cases, may, on the contrary, reduce performance.</p>
 * <p>Because index converters must map block indices to the same range, their operation is highly dependent on block size. Converter instances store and use the block size. Since the converter itself is an integral functional part of the block, the responsibility of determining the block size is delegated to the converter. That is, it is the index converter used when creating the block that determines the size of the block.</p>
 * @since 1.0
 */
package abyssus.nebel.spiral.converter;
