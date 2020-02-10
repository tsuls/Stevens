/* some tests illustrating the use of graphio.c */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#include "graph.h"
#include "graphio.h"

int main() {
    GraphInfo gi; 

    /* read and write a graph */
    gi = readGraph("series.txt", LIST);
    writeGraph(gi);
    disposeGraphInfo(gi);

    /* read another */
    gi = readGraph("statesContig.txt", LIST);
    writeGraph(gi);

    printf("Successors of NC: ");
    int NCnum = vertexNum(gi, "NC");
    int* vsucc = successors(gi->graph, NCnum);
    for (int i = 0; vsucc[i] != -1; i++)
        printf(" %s ", gi->vertnames[vsucc[i]]);
    printf("\n");

    free(vsucc);    
    disposeGraphInfo(gi);

    return 0; 
}
