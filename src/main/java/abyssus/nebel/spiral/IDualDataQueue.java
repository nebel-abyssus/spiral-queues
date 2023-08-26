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

/**
 * A FIFO dual-data queue interface.
 * <p>The interface is maximally simplified, and defines only two operations - placing an element in the queue, and extraction the element from the queue.</p>
 * <p>In a regular FIFO queue, the provider pushes elements into the queue so that the consumer will retrieve them later in the order they were queued. If the queue is empty, due to the fact that the provider has not yet had time to put new elements into the queue, and all previously placed elements have already been retrieved, then the consumer cannot retrieve the element, and it has to wait for the provider&apos;s actions.</p>
 * <p>A dual-data queue, if it's not empty, is no different from a regular one - the provider pushes the elements into the queue, and the consumer retrieves them out in the same order they were pushed in. Differences appear when the queue is empty, and the consumer tries to retrieve the next element. Unlike the usual queue, in case of dual-data queue, the consumer, instead of waiting for the actions of the provider, when trying to retrieve a non-existent element, queues a request. This request will be passed to the provider, and may be granted when the provider attempts to put the element on the queue.</p>
 * <p>Conceptually, a dual-data queue can be thought of as a queue of two competing providers: an element provider and a request provider. Each of these providers is also a consumer of objects provided by the other, competing provider. If the element provider is leads in the &quot;race&quot;, the queue is filled with elements, which are then consumed by the request provider. If in the &quot;race&quot; the provider of requests is in the lead, then the queue is filled with requests consumed, in the future, by the provider of elements.</p>
 * @param <T> The type of elements queued by the provider.
 * @param <R> The type of requests queued by the consumer.
 * @since 1.0
 * @author Sergey Shpagin
 */
public interface IDualDataQueue <T, R> {

// instance methods

	/**
	 * Enqueuing an element.
	 * <p>The method tries to put the specified element at the end of the queue. If the operation succeeds, returns {@code null}. However, if there is a request from the consumer at the head of the queue, then the element placement is aborted and the said request is dequeued and returned to the caller method.</p>
	 * @param elem The element placed at the end of the queue.
	 * @return <ul>
	 *     <li>{@code null} - If there were no requests from the consumer in the queue and the specified element was successfully enqueued.</li>
	 *     <li>The request from the consumer is taken from the head of the queue - Otherwise.</li>
	 * </ul>
	 * @throws NullPointerException If the specified element does not exist.
	 */
	R enqueue (T elem);

	/**
	 * Dequeuing an element.
	 * <p>The method attempts to retrieve from the head of the queue and return the element that was previously queued by the element provider. If there is no such element at the head of the queue, the method puts the specified request at the end of the queue and returns {@code null}.</p>
	 * @param request The request placed at the end of the queue.
	 * @return <ul>
	 *     <li>The dequeued element.</li>
	 *     <li>{@code null} - If at the head of the queue there were no elements previously placed by the element provider.</li>
	 * </ul>
	 * @throws NullPointerException If the specified request does not exist.
	 */
	T dequeue (R request);

} // IDualDataQueue
