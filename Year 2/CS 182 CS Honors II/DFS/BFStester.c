#include <stdlib.h>
#include <stdio.h>
#include "graph.h"
#include "graphio.h"
#include "topocycle.h"

struct bfsinfo {
    Graph graph; // non-null graph
    int* color;
    int* depth;
    //int* discover; // these are arrays of length g->numVerts()//
    //int* finish; // finish[j] is the finish time for vertex j//
    int* parent;  //
    //int* finorder; // vertex numbers in ascending order of finish time//
};
typedef struct bfsinfo* BFSinfo;
int main()
{
	char* paths[7] = {"/Users/Tyler/Google Drive/Stevens/CS 182/DFS/series.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/LevitinTopo.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/LevitinTopo2.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/LevitinTopo3.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/prereqs.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/statesContig.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/emptyGraph.txt"};
    
    GraphInfo g1 = readGraph(paths[0], 1);
    GraphInfo g2 = readGraph(paths[1], 1);
    GraphInfo g3 = readGraph(paths[2], 1);
    GraphInfo g4 = readGraph(paths[3], 1);
    GraphInfo g5 = readGraph(paths[4], 1);
    GraphInfo g6 = readGraph(paths[5], 1);
    GraphInfo g7 = readGraph(paths[6], 1);
    
   /* BFSinfo d1 = BFS(g1 -> graph);
    BFSinfo d2 = BFS(g2 -> graph);
    BFSinfo d3 = BFS(g3 -> graph);
    BFSinfo d4 = BFS(g4 -> graph);
    BFSinfo d5 = BFS(g5 -> graph);
    BFSinfo d6 = BFS(g6 -> graph);
    BFSinfo d7 = BFS(g7 -> graph);*/

    
    BreadthFirstSearch(g1, 0);

}
