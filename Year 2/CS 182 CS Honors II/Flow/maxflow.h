#ifndef MAXFLOW_H
#define MAXFLOW_H
#include "graph.h"

/* Interface for max-flow */

/* Result from max-flow:
 * - flow is a graph in MATRIX representation with weights 
 *   that represent the maximum flow (or INFINITY for non-existent edges?).
 * - cut is an array of length cutSize; its elements are the reached
 *   vertices in a minumum cut (named X* in Levitin and other references).
 * 
 * TODO wouldn't it be nicer to know the crossing edges that have full 
 * capacity?
 */ 
struct flowinfo {
    Graph flow;
    int cutSize;
    int *cut;
};
typedef struct flowinfo* FlowInfo;


/* Requires: 
 * - g is non-null and its number of vertices, NV, is at least 2.
 * - Edge weights are integral (though our API uses type float).
 * - Vertex 0 is a source, i.e., has no incoming edges.
 * - Vertex NV-1 is a sink, i.e., has no outgoing edges.
 * - Aside from the source and sink, every vertex can be reached
 *    from the source and can reach the sink (via directed paths).
 * 
 * Ensures:
 * - result is non-null and satisfies the description of FlowInfo
 *   for the given graph g.
 * - the caller owns the FlowInfo
 * 
 * TODO add a dispose function.
 */
FlowInfo maxflow(Graph G);

#endif
