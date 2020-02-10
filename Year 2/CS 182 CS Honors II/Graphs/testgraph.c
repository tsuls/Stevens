#include <stdlib.h>
#include <stdio.h>
#include <math.h> 
#include <string.h>
#include <assert.h>
#include "graph.h"

/* A simple, klunky test routine for graph.h.
 * You need to manually inspect the printed output. */

void test1(int rep) {
    printf("\nRunning test1 for representation %i \n", rep);
    Graph g = makeGraph(10, rep);

    // make some edges
    addEdge(g, 0, 1, 0.0); addEdge(g, 0, 2, 0.0);
    addEdge(g, 1, 0, 0.0); addEdge(g, 1, 1, 1.0); addEdge(g, 1, 2, 2.0);

    // print some successors
    int* s0 = successors(g, 0);
    int i = 0;
    while (s0[i] != -1) {
        printf("vert 0 has successor %i, ", s0[i]);
        i++;  
    }
    printf("\n");
    s0 = successors(g, 1);
    i = 0;
    while (s0[i] != -1) {
        printf("vert 1 has successor %i, ", s0[i]);
        i++;
    }
    free(s0);
    printf("\n%s\n", "now delete some edges and look again");

    // delete some edges and print again
    delEdge(g, 0, 1);
    delEdge(g, 1, 2);
    s0 = successors(g, 0);
    i = 0;
    while (s0[i] != -1) {
        printf("vert 0 has successor %i, ", s0[i]);
        i++;
    }
    free(s0);
    printf("\n");
    s0 = successors(g, 1);
    i = 0;
    while (s0[i] != -1) {
        printf("vert 1 has successor %i, ", s0[i]);
        i++;
    }
    free(s0);
    printf("\n");

    // clean up
    disposeGraph(g);
    g = NULL;
}


int main() {
    test1(MATRIX);
    test1(LIST);
}
