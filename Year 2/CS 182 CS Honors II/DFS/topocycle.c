#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>
#include <assert.h>

#include "topocycle.h"
#include "graphio.h"
#include "graph.h"
#include "dfs.h"
#include "Queue.h"

typedef int bool;
#define true 1
#define false 0
#define WHITE 3
#define GRAY 4
#define BLACK 5
Graph BreadthFirstSearch(GraphInfo gi, int start);


/* Determine whether the graph has a cycle.
 If so, print "Has a cycle:" followed by the vertices of a cycle (using the
 vertex names, not their numbers).
 Just print one cycle, even if there are several.
 
 If not cyclic, print all the vertex names, in a topologically sorted order.
 
 For this purpose, we use Levitin's definition of a cycle, which does not include
 self-loops, and which includes the starting vertex also at the end of the path.
 So the shortest cycle has three vertices: A B A where there are edges A->B and B->A.
 */


struct bfsinfo {
    Graph graph; // non-null graph
    int* color;
    int* depth;
    int* parent;  //
};
typedef struct bfsinfo* BFSinfo;

Graph BreadthFirstSearch(GraphInfo gi, int start)
{
    Graph g = gi -> graph;
    struct bfsinfo *bfsin = (BFSinfo) malloc(sizeof(struct bfsinfo));
    bfsin -> graph = g;
    bfsin -> parent = malloc(sizeof(int) * numVerts(g));
    bfsin -> color = malloc(sizeof(int) * numVerts(g));
    bfsin -> depth = malloc(sizeof(int) * numVerts(g));
    
    for(int i = 0; i < numVerts(bfsin -> graph); i++) //Initialization
    {
        bfsin -> parent[i] = 0;
        bfsin -> color[i] = 0;
        bfsin -> depth[i] = 0;
    }
    
    for(int v = 0; v < numVerts(g); v++)
    {
        bfsin -> color[v] = WHITE;
        bfsin -> parent[v] = -1;
        bfsin -> depth[v] = INFINITY;
    }
    bfsin -> color[start] = GRAY;
    bfsin -> color[start] = 0;
    bfsin -> parent[start] = -1;
    
    Queue q = (Queue) malloc(sizeof(struct queue));
    q-> arr = (int*) malloc(sizeof(int));
    q-> size = 0;
    
    q = enqueue(q, start);
    
    while(empty(q) == 0) //NOT empty
    {
        int u = dequeue(q);
        printf("%i\n", u);
        int* succ = successors(g, u);
        for(int w = 0; succ[w] != -1; w++)
        {
            if(bfsin -> color[w] == WHITE)
            {
                bfsin -> color[w] = GRAY;
                bfsin -> depth[w] =  bfsin -> depth[u] + 1;
                bfsin -> parent[w] = u;
                enqueue(q,w);
            }
        }
        bfsin -> color[u] = BLACK;
    }
    return g;
}




void topocycle(GraphInfo gi)
{
    DFSinfo info = DFS(gi->graph);
    int n = numVerts(gi->graph);
    
    int found = -1;
    int start = -1;
    int end = -1;
    
    
    
    for(int i = 0; i < n; i++)
    {
        int* succ = successors(gi->graph, i);
        int index = 0;
        
        while(succ[index] != -1)
        {
            if(info->discover[succ[index]] < info->discover[i] && info->finish[i] < info->finish[succ[index]])
            {
                start = succ[index];
                end = i;
                i=n;
                found = 1;
                break;
            }
            index++;
        }
    }
    
    if(found == -1)
    {
        for(int i = n-1; i>=0; i--)
        {
            printf("%s ", gi->vertnames[i]);
        }
        printf("\n");
    }
    
    int check = 0;
    
    if(found == 1)
    {
        int* l = (int*) malloc(n * sizeof(int));
        
        while( end != start){
            l[check] = end;
            end = info->parent[end];
            check++;
        }
        
        check--;
        printf("cyclic: %s ", gi->vertnames[start]);
        
        while(check != -1)
        {
            printf("%s ", gi->vertnames[l[check]]);
            check--;
        }
        printf("%s\n", gi->vertnames[start]);
        
    }
}
