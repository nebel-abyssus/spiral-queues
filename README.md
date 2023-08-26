# Spiral queue.

MPMC FIFO lock-free dual-data queue Spiral.

The queue consists of separate blocks (spiral turns). Operations with the blocks themselves are hidden from the user, allowing only the size of the block and the index transformation method used when accessing the block cells to be adjusted. Both are specified by specifying a transform object that encapsulates the block size and the transform method that depends on it. A larger block size gives more performance, at the cost of increased memory usage. Index conversion methods are aimed at reducing contention for individual processor cache lines when accessing cells with adjacent indexes in multi-threaded access, but can increase the amount of cache memory used, thereby increasing the number of cache misses. Thus, when choosing a block size and the conversion technique used, one should balance memory consumption and proceed from a specific queue operation scenario. The advantage is that both the sizes of the blocks used and the conversion technique can be freely changed right during the operation of the queue, but it should be borne in mind that changes will be taken into account only after some time has passed - when new blocks are placed by the queue.

Spiral is a FIFO queue, meaning that for any pair of elements (or requests) sequentially placed in the queue, the relative order is guaranteed when sequentially retrieved.

When executing most operations, Spiral behaves like a wait-free queue, only when changing blocks it goes into lock-free mode. The queue does not use double-word synchronization primitives and therefore does not require their software or hardware support.

Queue instances are completely thread-safe and do not require the use of external synchronization, which, however, is not guaranteed for a given index converter, nor for enqueued element and request objects.

The queue defines the [happens-before](https://docs.oracle.com/javase/specs/jls/se17/html/jls-17.html#jls-17.4.5) relation between the operations of putting an object (which can be either an element or a request) into the queue and retrieving this object from the queue: Let *x* be the operation of putting some object into the queue, and *y* the operation of retrieving this object from the queue, then *hb(x, y)*, which means *x happens-before y*.
