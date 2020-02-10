#include <stdlib.h>
#include <stdio.h>
#include "graph.h"
#include "graphio.h"
#include "dijkstra.h"


int main ()
{
    
    char* paths[7] = {"/Users/Tyler/Google Drive/Stevens/CS 182/DFS/series.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/LevitinTopo.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/LevitinTopo2.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/LevitinTopo3.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/prereqs.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/statesContig.txt",
        "/Users/Tyler/Google Drive/Stevens/CS 182/DFS/emptyGraph.txt"};
    
	GraphInfo gi;
    gi = readGraph(paths[5], MATRIX);

 	shortestPaths(gi,0);
}
