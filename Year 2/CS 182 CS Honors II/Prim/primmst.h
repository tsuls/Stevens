#ifndef PRIMMST_H
#define PRIMMST_H
#include "graph.h"

/* Assume g is non-null pointer to non-empty graph.
 * Assume g is a symmetric graph, so it can be 
 * considered as an undirected graph.  
 * (In particular, the weight u->v must equal weight v->u
 * for every u,v.)
 * Assume the weights are not INFINITY and g is connected.
 * 
 * Return a minimum spanning tree, as a new graph.
 * (So it will have the same number of vertices, 
 * but only some of the edges.  Weights same as in g.)
 * 
 * Implement this using Prim's algorithm.  The implementation only
 * needs to access the input graph via the graph.h API, so 
 * it shouldn't matter which representation the input uses.
 * Since the tree will be a sparse graph, use LIST representation 
 * for the output graph.
 * 
 */
Graph minSpanTree(Graph g);

#endif
