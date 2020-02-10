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
        printf("%s\n","null");
        return 1;
    }
    else //d is not null
    {
        if(strcmp(key, d->key) == 0) // d is the key
        {
            printf("%s\n","The key was found");
            return 0;
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
        printf("%s\n","The key was not found");
        return 1;
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
        printf("%s\n","Added");
        return 0;
    }
    else
    {
        
        if(d -> nxt != NULL && strcmp(key, d->key) == 0)//Update
        {
            d->val = val;
            printf("%s\n","Updated");
            return 0;
        }
        else
        {
            d->val = val;
            d->key = key;
            
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
    printf("%s\n","Added, Done");
    return 0;
}

int lookup(SIdict d, char* key)
{
   /* if(d-> nxt == NULL)
    {
        printf("%s\n","Null");
    }*/
    if(d->key != NULL && strcmp(key, d-> key) == 0)
    {
        printf("%s\n","Looked Up");
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
    printf("%s\n","Not Found");
    return 1;
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
    printf("%s\n","Removed");
    return 0;
}
