#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "graph.h"
#include "graphio.h"
#include "topocycle.h"

/* The main function tests topocycle.h and also an alternate algorithm for topological sorting. 
 * Does not free memory.
 */


/* Assuming the file exists and is an acyclic graph, print the vertices in a topological order 
 * Use the algorithm that finds no-predecessor vertices and deletes their successor edges. 
 * ALERT: modifies the graph by deleting edges.
*/
void toposort2(GraphInfo gi) {
    Graph g = gi->graph;
    int numV = numVerts(g);
    short done[numV]; // 1 if vertex already printed, otherwise 0
    for (int i = 0; i < numV; i++) 
        done[i] = 0;

    for (int numPrinted = 0; numPrinted < numV; numPrinted++) {

        /* set noPred to a vertex with no predecessors and not already done */
        int noPred;
        int* ps; // predecessor list
        for (int v = 0; v < numV; v++) {
            if (!done[v]) {
                ps = predecessors(g, v);
                if (ps[0] == -1 ) { // list is empty?
                    noPred = v;
                    break;
                }
            }
        }

        /* print noPred and mark as done */ 
        printf("%s ", gi->vertnames[noPred]);
        done[noPred] = 1;

        /* get successors of noPred and delete them */
        int* snoPred = successors(gi->graph, noPred);
        for (int i = 0; snoPred[i] != -1; i++) 
            delEdge(gi->graph, noPred, snoPred[i]);
    }
    
    /* cleanup */
    printf("\n");
}


/* test both toposort2 and topocycle 
 * (of course don't expect them to produce exactly the same results) */
void test(char* filepath) {
    GraphInfo gi;
    gi = readGraph(filepath, MATRIX);
    printf("Topological sort by predecessor technique %s\n", filepath);
    toposort2(gi); 
    printf("\nTopological sort by DFS technique %s\n", filepath);
    gi = readGraph(filepath, MATRIX); // MUST read again or clone original since toposort2 deletes
    printf("fdasfd\n");
    topocycle(gi);
} 


/* test both toposort2 and topocycle */
//int main() {
    /* acyclic graphs */

   /* char* paths[6] = {"series.txt",
                      "LevitinTopo.txt", 
                      "LevitinTopo2.txt", 
                      "LevitinTopo3.txt",
                      "prereqs.txt",
                      "statesContig.txt" };
    for (int i = 0; i < 6; i++)
        test(paths[i]);*/

    /* cyclic graphs */

   /* char* filepath;
    filepath = "cycle3.txt";
    printf("\nCyclic graph %s\n", filepath);
    topocycle(readGraph(filepath, LIST));

    filepath = "statesContig.txt";
    printf("\nCyclic graph %s  made symmetric\n", filepath);
    topocycle(readGraphMakeSymm(filepath, LIST));

    filepath = "prereqCatch22.txt";
    printf("\nCyclic graph %s\n", filepath);
    topocycle(readGraph(filepath, LIST));

    return 0;
}*/


