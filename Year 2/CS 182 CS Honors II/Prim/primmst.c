#include "primmst.h"
#include "minprio.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
int Compare(void* l, void* r);

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

struct node{
	int vertex;
	float distance;
};typedef struct node* Node;


Graph minSpanTree(Graph g)
{
	Graph newGraph = makeGraph(g -> numVerts, LIST);

	int visited[g -> numVerts];
	ENode fin;
	//int* E = malloc(sizeof(int*));

	memset(visited, 0, g -> numVerts * sizeof(int));
	
	MinPrio qp = makeQueue(Compare, g -> numVerts);
	enqueue(qp, g-> aList[0]);
	visited[0] = 1;

	for(int i = 1; i < g -> numVerts - 1; i++)
	{
		fin = dequeueMin(qp);
		addEdge(newGraph, i - 1, i, fin -> wt);
		enqueue(qp, g-> aList[i]);
	}
	return newGraph;
}

int Compare(void* l, void* r)
{
	if(l < r)
	{
		return -1;
	}
	else if(l > r)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}