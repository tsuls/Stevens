#include <stdio.h>
#include "tclose.h"

struct eNode {
    float wt;
    int target;
    struct eNode* next;
};
typedef struct eNode* ENode;
struct graph {
    short repType; /* MATRIX or LIST representation */
    int numVerts;
    float* adjM; /* the adjacency matrix, size numVerts*numVerts */
    ENode* aList; /* array of adjacency lists; length numVerts */
};


Graph transClose(Graph g)
{
	int n = numVerts(g);
	Graph x = cloneGraph(g, MATRIX);
	for(int k = 1; k < n; k++)
	{
		for(int i = 1; i < n; i++)
		{
			for(int j = 1; j < n; j++)
			{
				if(edge(x,i,j) != INFINITY || (edge(x, i ,k) != INFINITY && edge(x,k,j) != INFINITY))
				{
					delEdge(x,i,j);
					addEdge(x, i, j, 0.0);
				}
			} 
		}
	}
	return x;
}
