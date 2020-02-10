#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "graphio.h"

/* PARTIAL IMPLEMENTATION OF graphio.h
 * DN 2/16/2017 extracted from my graphio.c */


/* max data to read from a line of input, allowing for two vertex names and weight */
#define LINE_LEN (2 * (MAX_NAMELEN+1) + 20)


/* see graphio.h */
/* TODO: could sort the vertices once read, and use bin search. */
int vertexNum(GraphInfo gi, char* name) {
    int i = 0;
    int n = numVerts(gi->graph);
    while ( i < n && strcmp(gi->vertnames[i], name) )
        i++;
    if (i < n)
        return i;
    else
        return -1;
}



/* Read a graph from a text file, using the requested representation.
 Assumes filepath is a null-terminated string that is valid file path.
 Assumes the file exists and has the format specified in graphio.h.
 If makeSymmetric is MAKE_SYMM, add both (S,T) and (T,S) for each edge S T in the file
 (except when (T,S) present from an earlier line of the file).
 */
GraphInfo readGraphX(char* filepath, int repType, int makeSymmetric) {
    
    /* open file, initialize input buffer, get number of vertices */
    FILE* file = fopen(filepath, "r");
    if (file == NULL) {
        fprintf(stderr, "graphio:readGraph - fatal error: could not open file %s\n", filepath);
        exit(1);
    }
    size_t lineSize = LINE_LEN;
    char* line = (char *) malloc(lineSize);
    getline(&line, &lineSize, file); /* get one line of input */
    int numVerts;
    sscanf(line, "%i", &numVerts); /* parse the line */
    
    /* allocate and initialize GraphInfo */
    GraphInfo gi = (GraphInfo) malloc(sizeof(struct graphinfo));
    gi->graph = makeGraph(numVerts, repType);
    char **vertnames = (char**) malloc(numVerts * sizeof(char *));
    gi->vertnames = vertnames;
    
    /* get vertex names */
    char source[MAX_NAMELEN + 1];
    int i = 0;
    while( i < numVerts && getline(&line, &lineSize, file) > 0 ) {
        sscanf(line, "%s", source);
        vertnames[i] = (char *) malloc(strlen(source) + 1);
        strcpy(vertnames[i], source);
        i++;
    }
    if (i != numVerts) {
        fprintf(stderr, "graphio:readGraph - file format error getting vertex names in %s\n", filepath);
        exit(1);
    }
    
    /* get the edges */
    char target[MAX_NAMELEN + 1];
    float weight;
    
    while ( getline(&line, &lineSize, file) > 0 ) {
        int result = sscanf(line, "%s %s %f\n", source, target, &weight);
        
        if (result >= 2 ) { // read at least two items
            int src = vertexNum(gi, source);
            int trg = vertexNum(gi, target);
            if (result == 2) // weight not included
                weight = DEFAULT_WEIGHT;
            addEdge(gi->graph, src, trg, weight);
            if (makeSymmetric)
                addEdge(gi->graph, trg, src, weight);
        } else
            if (result == 1)
                fprintf(stderr, "graphio:readGraph - file format error (ignored) getting edges in %s\n", filepath);
    }
    
    /* clean up and return */
    free(line);
    fclose(file);
    
    return gi;
}


/* see graphio.h */
GraphInfo readGraph(char* filepath, int repType) {
    return readGraphX(filepath, repType, 0);
}


/* see graphio.h */
GraphInfo readGraphMakeSymm(char* filepath, int repType) {
    return readGraphX(filepath, repType, 1);
}


/* see graphio.h */
void writeGraph(GraphInfo gi) {
    
    printf("writeGraph not yet implemented.\n");
    
    /* HINT: use printf to print everything */
    
}


/* see graphio.h */
void disposeGraphInfo(GraphInfo gi) {
    for (int i = 0; i < numVerts(gi->graph); i++)
        free(gi->vertnames[i]);
    free(gi->vertnames);    
    disposeGraph(gi->graph);   
    free(gi);
}



