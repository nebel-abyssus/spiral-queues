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

import java.util.concurrent.atomic.AtomicReference;
import java.util.Objects;

/**
 * Linked node.
 * <p>Singly linked list node that allows you to atomically set a link to the next node once.</p>
 * @param <T> The node's content type.
 * @since 1.0
 * @author Sergey Shpagin
 */
class LinkedNode <T> {

// instance fields

	/**
	 * Node content.
	 */
	private final T content;

	/**
	 * Atomically mutable link to the next node.
	 */
	private final AtomicReference<LinkedNode<T>> nextNode = new AtomicReference<LinkedNode<T>>();

// constructors

	/**
	 * Linked node constructor.
	 * @param content The content of the node to be installed.
	 * @throws NullPointerException If the specified content does not exist.
	 */
	LinkedNode (final T content) {
		this.content = Objects.<T>requireNonNull(content);
	} // LinkedNode()

// instance methods

	/**
	 * Getting the contents of the node.
	 * @return Node content.
	 */
	T getContent () {
		return content;
	} // getContent()

	/**
	 * Getting a link to the next node.
	 * @return Link to the next node.
	 */
	LinkedNode<T> getNextNode () {
		return nextNode.getPlain();
	} // getNextNode()

	/**
	 * Attempt to set a link to the next node.
	 * <p>The method tries to atomically set a link to the next node if it was not successfully set earlier. Returns the value of the link to the next node after the attempt, which, if the attempt succeeds, is the same as the specified value.</p>
	 * @param newNextNode The new value of the link to the next node. Must not be {@code null}.
	 * @return The value of the link to the next node.
	 * @throws NullPointerException If the specified new value of the next node reference is {@code null}.
	 */
	LinkedNode<T> trySetNext (final LinkedNode<T> newNextNode) {
		Objects.<LinkedNode<T>>requireNonNull(newNextNode);
		LinkedNode<T> next = nextNode.getPlain();
		if (next == null) {
			next = nextNode.compareAndExchange(null, newNextNode);
			if (next == null) {
				next = newNextNode;
			} // if
		} // if
		return next;
	} // trySetNext()

} // LinkedNode
