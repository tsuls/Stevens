#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include "dijkstra.h"
#include "minprio.h"



struct node
{
	int vertex;
};
typedef struct node* Node;



float* d;

int Compare(void* l, void* r)//, int* d)
{
    Node x = l;
    Node y = r;
    
    if(d[x -> vertex] < d[y -> vertex])
    {
        return -1;
    }
    else if(d[x -> vertex] > d[y -> vertex])
    {
        return 1;
    }
    else
    {
        return 0;
    }
}



void shortestPaths(GraphInfo GI, int s)
{
	d = (float*) malloc(sizeof(float));
	MinPrio Q = makeQueue(Compare, numVerts(GI -> graph));//Change this
    int* pred = (int*) malloc(sizeof(int*));
	Handle* h = (Handle*) malloc(sizeof(struct handle));

    Node* vv = (Node*) malloc(sizeof(Node*) * numVerts(GI -> graph));
    
    //printf("Num Verts: %i\n", numVerts(GI -> graph));
    
	for(int v = 0; v < numVerts(GI -> graph); v++)
	{
       // printf("V: %i\n", v);
    
        vv[v] = (Node) malloc(sizeof(struct node));
		vv[v] -> vertex = v;
		pred[v] = -1;
		
     //   printf("Address: %i\n", &vv[v]);
        
        if(v == s)
        {
            d[v] = 0;
            h[v] = enqueue(Q, vv[v]);
        }
        
        if(v != s)
		{
			d[v] = INFINITY;
			h[v] = enqueue(Q, vv[v]);
		}
    }


    printf("%s%s\n", "The shortest Distance from vertex ", GI -> vertnames[s]);
    
	while(nonempty(Q))
	{
        Node ww  = dequeueMin(Q);
        //printf("Vertex: %i\n", ww -> vertex);
        int v = ww -> vertex;
        int* succ = successors(GI -> graph, v);
        h[v] = NULL;
    
		for(int u = 0; succ[u] != -1; u++)
		{
            printf("Edge: %f\n",(edge(GI -> graph,v,u)));
            printf("Edge + d[v]: %f\n",(d[v] + edge(GI -> graph,v,u)));
            printf("d[u]: %f\n",d[u]);
            printf("?: %i\n",(d[v] + edge(GI -> graph,v,u)) < d[u]);
			
            
            if((d[v] + edge(GI -> graph,v,u)) < d[u])
			{
				d[u] = d[v] + edge(GI -> graph, v, u);
                decreasedKey(Q,h[u]);
				pred[u] = v;
			}
		}
        
        
        printf("%s%s%s%f%s%s\n", " to ", GI -> vertnames[v]," is ", d[v], " via ",GI -> vertnames[pred[v]]);
    }
    
}

