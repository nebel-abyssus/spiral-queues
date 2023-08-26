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

package abyssus.nebel.spiral;

import abyssus.nebel.spiral.block.DisposableBlockSupplier;
import abyssus.nebel.spiral.block.IBlock;
import abyssus.nebel.spiral.converter.FractalConverter;
import abyssus.nebel.spiral.converter.IIndexConverter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Spiral queue.
 * <p>MPMC FIFO lock-free dual-data queue Spiral.</p>
 * <p>The queue consists of separate blocks (spiral turns). Operations with the blocks themselves are hidden from the user, allowing only the size of the block and the index transformation method used when accessing the block cells to be adjusted. Both are specified by specifying a transform object that encapsulates the block size and the transform method that depends on it. A larger block size gives more performance, at the cost of increased memory usage. Index conversion methods are aimed at reducing contention for individual processor cache lines when accessing cells with adjacent indexes in multi-threaded access, but can increase the amount of cache memory used, thereby increasing the number of cache misses. Thus, when choosing a block size and the conversion technique used, one should balance memory consumption and proceed from a specific queue operation scenario. The advantage is that both the sizes of the blocks used and the conversion technique can be freely changed right during the operation of the queue, but it should be borne in mind that changes will be taken into account only after some time has passed - when new blocks are placed by the queue.</p>
 * <p>Spiral is a FIFO queue, meaning that for any pair of elements (or requests) sequentially placed in the queue, the relative order is guaranteed when sequentially retrieved.</p>
 * <p>When executing most operations, Spiral behaves like a wait-free queue, only when changing blocks it goes into lock-free mode. The queue does not use double-word synchronization primitives and therefore does not require their software or hardware support.</p>
 * <p>Queue instances are completely thread-safe and do not require the use of external synchronization, which, however, is not guaranteed for a given index converter, nor for enqueued element and request objects.</p>
 * <p>The queue defines the <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-17.html#jls-17.4.5">happens-before</a> relation between the operations of putting an object (which can be either an element or a request) into the queue and retrieving this object from the queue: Let <em>x</em> be the operation of putting some object into the queue, and <em>y</em> the operation of retrieving this object from the queue, then <em>hb(x, y)</em>, which means <em>x happens-before y</em>.</p>
 * @param <T> The type of elements queued by the provider.
 * @param <R> The type of requests queued by the consumer.
 * @since 1.0
 * @author Sergey Shpagin
 */
public class Spiral <T, R> implements IDualDataQueue<T, R> {

// static fields

	/**
	 * The block size used by default.
	 */
	public static final int DEFAULT_BLOCK_SIZE = 512;

	/**
	 * The default index converter.
	 */
	public static final IIndexConverter DEFAULT_CONVERTER = new FractalConverter(DEFAULT_BLOCK_SIZE);

// instance fields

	/**
	 * Producer head.
	 */
	private final AtomicReference<UniversalHead> producerHead;

	/**
	 * Consumer head.
	 */
	private final AtomicReference<UniversalHead> consumerHead;

	/**
	 * Block supplier.
	 */
	private final DisposableBlockSupplier<Object> blockSupplier;

// constructors

	/**
	 * Customizable Spiral queue constructor.
	 * <p>Allows to specify an initial index converter that defines the size of blocks and index conversion technique. The index converter used can be freely changed while the queue is running.</p>
	 * @param converter Initial index converter.
	 * @throws NullPointerException If the specified index converter does not exist.
	 */
	public Spiral (
		final IIndexConverter converter
	) { // method body
		blockSupplier = new DisposableBlockSupplier<Object>(converter);
		final IBlock<Object> initialBlock = blockSupplier.get();
		final LinkedNode<IBlock<Object>> initialNode = new LinkedNode<IBlock<Object>>(initialBlock);
		producerHead = new AtomicReference<UniversalHead>(new UniversalHead(new AtomicInteger(), initialNode));
		consumerHead = new AtomicReference<UniversalHead>(new UniversalHead(new AtomicInteger(), initialNode));
	} // Spiral()

	/**
	 * Spiral queue constructor.
	 * <p>This constructor specifies to use the default index converter as the initial one. The index converter used can be freely changed while the queue is running.</p>
	 */
	public Spiral (
	) { // method body
		this(DEFAULT_CONVERTER);
	} // Spiral()

// instance methods

	/**
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public R enqueue (
		final T elem
	) { // method body
		return (R) universalEnqueue(elem, producerHead);
	} // enqueue()

	/**
	 * @throws NullPointerException {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T dequeue (
		final R request
	) { // method body
		return (T) universalEnqueue(request, consumerHead);
	} // dequeue()

	/**
	 * Specifying the index converter to use.
	 * <p>The method allows to set the index converter used when placing new blocks.</p>
	 * <p>It should be noted that the specified converter will be used only after some time has passed, when new blocks are placed by the queue. In addition, the specified converter must be safe for multi-thread access, since no synchronization is used when calling converters by the queue.</p>
	 * @param converter New index converter.
	 * @throws NullPointerException If the specified converter does not exist.
	 */
	public void setConverter (
		final IIndexConverter converter
	) { // method body
		blockSupplier.setConverter(converter);
	} // setConverter()

	/**
	 * Generic method for placing an object in a queue.
	 * <p>The method tries to put the specified object into the specified queue. When there is not enough space in the head of the queue, the head is shifted to a free block, which can be created if necessary. After finding an unused slot, an attempt is made to place the specified object in this slot. If the slot is still free, then the object is placed in it, after which the method returns null. If the slot is already occupied, then its contents returned to the calling method, while the slot itself is cleared.</p>
	 * @param obj The object to be queued.
	 * @param headRef An atomically mutable reference to the head of the target queue.
	 * @return <ul>
	 *     <li>{@code null} if the specified object was successfully placed into the target queue.</li>
	 *     <li>Otherwise, the value removed from the queue.</li>
	 * </ul>
	 * @throws NullPointerException If either the specified object or the target queue does not exist.
	 */
	private Object universalEnqueue (
		final Object obj,
		final AtomicReference<UniversalHead> headRef
	) { // method body
		IBlock<Object> block;
		int idx;
		do {
			final UniversalHead head = headRef.get();
			block = head.node().getContent();
			final int blockSize = block.getBlockSize();
			idx = head.index().getAndIncrement();
			if ((idx >= 0) && (idx < blockSize)) break;
			head.index().setPlain(blockSize);
			moveHeadForward(headRef, head);
		} while (true);
		return block.getOrPut(idx, obj);
	} // universalEnqueue()

	/**
	 * Moving the head of a queue.
	 * <p>The method moves the head of the queue to the next node, creating it if necessary.</p>
	 * @param headRef An atomically mutable reference to the head of the queue.
	 * @param currentHead The current head of the queue.
	 * @throws NullPointerException If the reference to the head of the queue, or the specified current head of the queue does not exist.
	 */
	private void moveHeadForward (
		final AtomicReference<UniversalHead> headRef,
		final UniversalHead currentHead
	) { // method body
		LinkedNode<IBlock<Object>> nextNode = currentHead.node().getNextNode();
		if (nextNode == null) {
			final IBlock<Object> newBlock = blockSupplier.get();
			final LinkedNode<IBlock<Object>> newNode = new LinkedNode<IBlock<Object>>(newBlock);
			nextNode = currentHead.node().trySetNext(newNode);
		} // if
		final UniversalHead newHead = new UniversalHead(new AtomicInteger(), nextNode);
		headRef.compareAndSet(currentHead, newHead);
	} // moveHeadForward()

} // Spiral
