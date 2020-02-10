#ifndef minprio_H
#define minprio_H

/* min-priority queues 
 * Items in a given queue are struct pointers that should be 
 * comparable by the comparator provided to makeQueue.  
 *
 * Implement using an array heap as in Levitin 6.4 (but MIN).
 * 
 * This API provides "addressable" queue, meaning that the client
 * is given a handle for each enqueued item.  This enables an 
 * efficient interface for the decrease-key operation.  (The handle
 * enables the decreaseKey to find, in constant time, an item's 
 * index in the array.)
 * 
 * rev 4/18/16 by DN to clarify memory management
 * rev 4/3/17 by DN to polish comments
 */


/* the queue type */
struct minprio; /* to be defined by the implementation */
typedef struct minprio* MinPrio;


/* handles for efficient access to enqueued items 
 * ALERT: Clients must not read or write the pos field; it's for use only
 * by code in minprio.c. 
 */
struct handle {
    int pos;       /* not for client use (current position in queue array) */
    void* content; /* the client's data */
};
typedef struct handle* Handle;


/* type and contract for comparison function
 * Assumes lhs and rhs non-null.
 * Returns <0, =0, >0 according to whether lhs<rhs, lhs==rhs, lhs>rhs */
typedef int (*Comparator)(void* lhs, void* rhs);


/* make an empty queue
 * Items will be compared using comp.
 *
 * It' the client's responsibility to ensure that
 * there are never more than maxsize elements in queue.
 */
MinPrio makeQueue(Comparator comp, int maxsize);


/* dispose of memory owned by the queue
 * Namely: the queue object, the array, and the Handles.
 * The Handle contents are the responsibility of the client.
 */
void disposeQueue(MinPrio qp);


/* enqueue 
 * Assumes queue currently contains less than maxsize elements.
 * Assumes qp and item non-null. 
 * Returns a Handle containing the item, for use with decreaseKey.
 */
Handle enqueue(MinPrio qp, void* item);  


/* 1 if queue has elements, else 0 (assuming qp non-null) */
int nonempty(MinPrio qp);


/* dequeue and return a minimum element according to the comparator.
 * Assumes qp non-null and queue nonempty.
 * Frees the handle, so client should no longer use handle.
 */
void* dequeueMin(MinPrio qp); 


/* decrease the item's key
 * Must be called whenever comparison value may have changed.
 * Must only decrease comparison value (i.e. raise priority).
 *
 * Assumes qp non-null and hand is in the queue. 
 */
void decreasedKey(MinPrio qp, Handle hand); 


#endif
