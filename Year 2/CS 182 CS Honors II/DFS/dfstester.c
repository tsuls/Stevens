#include <stdlib.h>
#include <stdio.h>
#include "graph.h"
#include "graphio.h"
#include "dfs.h"

/*int main()
{
	//GraphInfo gi = readGraph("/Users/Tyler/Google Drive/Stevens/CS 182/DFS/series.txt", 1);
//	DFSinfo d = DFS(gi->graph);

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
    
    DFSinfo d1 = DFS(g1 -> graph);
    DFSinfo d2 = DFS(g2 -> graph);
    DFSinfo d3 = DFS(g3 -> graph);
    DFSinfo d4 = DFS(g4 -> graph);
    DFSinfo d5 = DFS(g5 -> graph);
    DFSinfo d6 = DFS(g6 -> graph);
    DFSinfo d7 = DFS(g7 -> graph);

   /// printf("%s%i\n","NumVerts", numVerts(d2 -> graph));

    
	/*for(int i = 0; i < numVerts(d2 -> graph); i++)
	{
		printf("%i\n", d2->finorder[i]);
	}

}*/
