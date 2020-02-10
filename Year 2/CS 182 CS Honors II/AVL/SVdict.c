#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include "SVdict.h"

int height(SVdict d);
SVdict rotLeft(SVdict d, int a);
SVdict rotRight(SVdict d, int a);

struct sv_dict
{
  //  SVdict root;
    SVdict left;
    SVdict right;
    char* value;
    char* key;
};

SVdict makeSVdict()
{
    SVdict s = (SVdict) malloc(sizeof(struct sv_dict));
    //s -> root = NULL;
    return s;
}

SVdict newNode()
{
    SVdict n = (SVdict) malloc(sizeof(struct sv_dict));
    n -> left = NULL;
    n -> right = NULL;
    n -> value = 0;
    n -> key = 0;
    
    return n;
}

void disposeSVdict(SVdict d)
{
    // Fuck what else
    free(d);
}

int getBal(SVdict d)
{
    int bal = 0;
    
    if(d -> left != NULL)
    {
        bal += height(d -> left);
    }
    if(d -> right != NULL)
    {
        bal -= height(d -> right);
    }
    
    return bal;
}

SVdict rotLeft(SVdict d, int a)
{
    if(a == 0)
    {
        SVdict current = d;
        SVdict temp = d -> left;
        
        current -> left = temp -> right;
        temp -> right = current;
        return temp;
    }
    else
    {
        SVdict current = d;
        SVdict temp = d -> left;
        SVdict temp2 = d -> right;
        
        current -> left = temp2 -> right;
        temp -> right = temp2 -> left;
        temp2 -> left = temp;
        temp2 -> right = current;
        
        return temp2;
        
    }
}

SVdict rotRight(SVdict d, int a)
{
    if (a == 0)
    {
        SVdict current = d;
        SVdict temp = current -> right;
        
        current -> right = temp -> left;
        temp -> left = current;
        return temp;
    }
    else
    {
        SVdict current = d;
        SVdict temp = current -> right;
        SVdict temp2  = temp -> left;
        
        current -> right = temp2 -> left;
        temp -> left =  temp2 -> right;
        temp2 -> right = temp;
        temp2 -> left = current;
        return temp2;
    }
}

SVdict balance(SVdict d)
{
    SVdict x = newNode();
    if(d -> left != NULL)
    {
        d -> left = balance(d -> left);
    }
    if(d -> right != NULL)
    {
        d -> right = balance(d -> right);
    }
    
    int theBalance = getBal(d);
    
    if(theBalance >= 2)
    {
        if(getBal(d -> left) <= -1)
        {
            x = rotLeft(d, 1);
        }
        else
        {
            x = rotLeft(d, 0);
        }
    }
    else if(theBalance <= -2)
    {
        if(getBal(d -> right) >= 1)
        {
            x = rotRight(d, 1);
        }
        else
        {
            x = rotLeft(d, 0);
        }
    }
    else
    {
        x = d;
    }
    return x;
}


int hasKey(SVdict d, char* key)
{
    SVdict current = d; //Grab Current
   
    while(current != NULL && current -> key != key)
    {
        if(key > current -> key)
        {
            current = current -> right;
        }
        else
        {
            current = current -> left;
        }
    }
    
    if(current == NULL)
    {
        return 0;
    }
    else
    {
        return 1;
    }
}

int addOrUpdate(SVdict d, char* key, void* val)
{
    SVdict current = d;
    
    if(current == NULL)
    {
        d = makeSVdict();
        d -> value = val;
        d -> key = key;
        d -> left = makeSVdict();
        d -> right = makeSVdict();
        return 0;
    }
    
    while (current != NULL)
    {
        if( key < current -> key)
        {
            current = current -> left;
        }
        else if(key > current -> key)
        {
            current = current -> right;
        }
        else if(key == current -> key)
        {
            current -> value = val;
        }
    }
    
    balance(d);
    return 0;
}

int height(SVdict d)
{
    int hLeft = 0;
    int hRight = 0;
    
    if(d -> left)
    {
        hLeft = height(d -> left);
    }
    if(d -> right)
    {
        hRight = height(d -> right);
    }
    
    return hRight > hLeft ? ++hRight : ++hLeft;
}




int remKey(SVdict d, char* key)
{
    return 0;
}


void* lookup(SVdict d, char* key)
{
    SVdict current = d;
    
    while(current != NULL && current -> key != key)
    {
        if(key > current -> key)
        {
            current = current -> right;
        }
        else
        {
            current = current -> left;
        }
    }
    return current -> value;
}

void preorderKeys(SVdict d)
{
    if (d != NULL)
    {
        printf("%s\n", d -> key);
        preorderKeys(d -> left);
        preorderKeys(d -> right);
    }
}
