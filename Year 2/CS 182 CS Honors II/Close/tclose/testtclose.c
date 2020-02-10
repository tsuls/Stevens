#include <stdlib.h>
#include <stdio.h>
//#include <string.h>
//#include <assert.h>
#include "graph.h"
#include "graphio.h"
#include "tclose.h"

/* Tests for transitive closure 
 * This file also provides sample code for double-index notation with arrays.
*/

/* Sample code showing one way to work with 2D arrays, namely,
 * as an array of arrays.  An advantage is that we can use double
 * index notation.  A disadvantage is that space is used for pointers.
 * For sample code using a true 2D array (not array of arrays), see
 * DN's graph.c. 
 */  
void sample(char* path) {
    // Load the graph
    GraphInfo gi = readGraph(path, LIST); // it doesn't matter, LIST or MATRIX
    Graph g = gi->graph;
    int numV = numVerts(g);

    // array of array pointers
    float **M = (float **) malloc(sizeof(float *) * numV);

    // create the rows
    for (int i = 0; i < numV; i++)
        M[i] = (float *) malloc(sizeof(float) * numV);

    // copy the graph into M
    for (int i = 0; i < numV; i++)
        for (int j = 0; j < numV; j++)
            M[i][j] = edge(g, i, j);  // this is where we use double indexes

    printf("This is just sample code, it doesn't do anything useful.\n");
}


/* Test on one graph.
 * Assume path is nonnull name of a nonempty graph.
 */
void test(char* path) {

    /* load and print the graph */
    GraphInfo gi; 
    printf("Testing %s \n", path); 
    gi = readGraph(path, MATRIX);
    writeGraph(gi);

    /* compute the transitive closure and print that */
    Graph origG = gi->graph; 
    Graph tcG = transClose(origG);
    printf("\nTransitive closure of %s:\n", path);
    gi->graph = tcG; /* ALERT replace the graph in gi */
    writeGraph(gi);

    disposeGraph(origG);
    disposeGraphInfo(gi);
}


int main() {

    test("triangleMST.txt"); // already transitively closed

    test("LevitinPrim.txt"); // a graph example in Levitin book 

    test("sixMST.txt"); // a slightly larger graph

    test("prereqs.txt"); // which courses depend on which

}

