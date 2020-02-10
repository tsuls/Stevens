#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>
#include "dfs.h"

void DFSHelper(int v, DFSinfo d, Graph g);

int count;
int order;

#define FALSE 0
#define TRUE 1

DFSinfo DFS(Graph g)
{
    count = 0;
    order = 0;
	struct dfsinfo *dfsin = (DFSinfo) malloc(sizeof(struct dfsinfo));
	dfsin -> graph = g;
	dfsin -> discover = malloc(sizeof(int) * numVerts(g));
	dfsin -> finish = malloc(sizeof(int) * numVerts(g));
	dfsin -> parent = malloc(sizeof(int) * numVerts(g));
	dfsin -> finorder = malloc(sizeof(int) * numVerts(g));

	for(int i = 0; i < numVerts(dfsin -> graph); i++) //Initialization
	{
		dfsin -> discover[i] = 0;
		dfsin -> finish[i] = 0;
		dfsin -> parent[i] = 0;
		dfsin -> finorder[i] = 0;
	}
	
    
    for (int v = 0; v < numVerts(g); v++)
    {
        if(dfsin -> discover[v] == FALSE)
        {
            DFSHelper(v, dfsin, g);
        }
    }
	return dfsin;
}


void DFSHelper(int v, DFSinfo d, Graph g)
{
    d ->discover[v] = ++count;
    int* succ = successors(g, v);
    
    for(int s = 0; succ[s] != -1; s++)
    {
        if(d -> discover[s] == FALSE)
        {
            d -> parent[s] = v;
            DFSHelper(s, d, g);
        }
    }
    d -> finish[v] = ++count;
    d-> finorder[order++] = v;
}

