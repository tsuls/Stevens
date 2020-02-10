#ifndef TCLOSE_H
#define TCLOSE_H
#include "graph.h"

/* Construct a new graph that is the transitive closure of g.
 * Do not modify g.
 * Assume g is non-null pointer to non-empty graph.
 * Ignore the weights of g.  The weights of the new graph
 * should all be 0. There is an edge u->v in the new graph
 * if and only if there is a path from u to v in g.
 * 
 * Implement this using Warshall's algorithm and matrix 
 * representation for the result graph.
 * Note that Levitin's presentation is described in terms of a matrix of
 * 1s and 0s where 0 means there is no edge.  In our graph.h API,
 * the edge() function returns INFINITY to indicate no edge, and otherwise
 * the weight of an existing edge, which can be 0.0. 
 */
Graph transClose(Graph g);


#endif
