#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "SIdict.h"


struct si_dict
{
    int val;
    char* key;
    SIdict nxt;
    SIdict right;
    SIdict left;
};

SIdict makeSIdict()
{
    SIdict s = (SIdict) malloc(sizeof(struct si_dict));
    s-> val = 0;
    s-> key = NULL;
    s-> nxt = NULL;
    s-> right = NULL;
    s-> left = NULL;
    return s;
}

int hasKey(SIdict d, char* key)
{
    if(d == NULL)
    {
        return 0;
    }
    else //d is not null
    {
        if(strcmp(key, d->key) == 0) // d is the key
        {
            return 1;
        }
        else if(strcmp(key, d->key) == 0)// d is not the key
        {
            if(strcmp(key, d->key) > 0)
            {
                hasKey(d->right, key);
            }
            else
            {
                hasKey(d->left, key);
            }
        }
        return 0;
    }
}

int addOrUpdate(SIdict d, char* key, int val)
{
    if(d == NULL)//NOT THERE/EMpty
    {
        d = makeSIdict();
        d-> key = key;
        d-> val = val;
        SIdict left = makeSIdict();
        SIdict right = makeSIdict();
        d-> left = left;
        d->right = right;
        return 1;
    }
    else
    {
        
        if(d -> nxt != NULL && strcmp(key, d->key) == 0)//Update
        {
            d->val = val;
            return 1;
        }
        else
        {
            d->val = val;
            d->key = key;
          //  SIdict left = makeSIdict();
            //SIdict right = makeSIdict();
          //  d-> left = left;
           // d->right = right;
            
            if(strcmp(key, d->key) > 0)
            {
                addOrUpdate(d-> right, key, val);
            }
            else
            {
                addOrUpdate(d-> left, key, val);
            }
        }
    }
    return 0;
}

int lookup(SIdict d, char* key)
{
    if(strcmp(key, d-> key) == 0)
    {
        return d-> val;
    }
    else
    {
        if(strcmp(key, d->key) > 0)
        {
            lookup(d-> right, key);
        }
        else
        {
            lookup(d-> left, key);
        }
    }
    return 0;
}

int remKey(SIdict d, char* key)
{
    if(strcmp(key, d-> key) == 0)
    {
        if(d-> left == NULL)
        {
            d = d-> right;
        }
        else
        {
            if(d-> right == NULL)
            {
                d = d-> left;
            }
            else
            {
                SIdict t = d-> right;
                while(t-> left != NULL)
                {
                    t = t-> left;
                }
                d = t;
            }
        }
    }
    else
    {
        if(strcmp(key, d->key) > 0)
        {
            remKey(d-> right, key);
        }
        else
        {
            remKey(d-> left, key);
        }
    }
    return 0;
}