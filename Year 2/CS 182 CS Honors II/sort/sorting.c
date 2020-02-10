#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <time.h>

int binarySearch(char* arr[], int l, int r, char* k);
int partition(char* arr[], int l, int r, int x);
void swap(char* arr[], int i, int j);
int findMedian(int x, int y, int z);

#define BOOL char
#define FALSE 0
#define TRUE 1
//Tyler Sulsenti and James Spaniak
//We Pledge our honor that we have abided by the stevens honor system


void insertionsort(char* arr[], int l, int r)
{
    int loc, i ,j;
    char* temp;
    
    for (i = 1; i < r; i++)
    {
        loc = binarySearch (arr, 0, i, arr[i]);
    
        temp = arr[i];
        for ( j = i - 1; j >= loc; j--)
        {
            arr[j + 1] = arr[j];
        }
        arr[loc] = temp;
    }
}

int binarySearch(char* arr[], int l, int r, char* k)
{
    int m;
    
    if (l == r)
    {
        return l;
    }
    
    m = l + ((r - l) / 2);

    if (strcmp(k, arr[m]) > 0)
    {
        return binarySearch (arr, m + 1, r, k);
    }
    else if (strcmp(k, arr[m]) < 0)
    {
        return binarySearch (arr, l, m, k);
    }
    
    return m;
}

void quicksort(char* arr[], int l, int r)
{
    if( l < r)
    {
        int pivot = partition(arr, l, r, 0);
        quicksort(arr,l,pivot);
        quicksort(arr,pivot + 1,r);
    }
}

void quicksortPlus(char* arr[], int l, int r)
{
    if(r - 1 <= 10)
    {
        insertionsort(arr, l, r);
    }
    else
    {
        int pivot = partition(arr, l, r , 1);
        quicksortPlus(arr,l,pivot);
        quicksortPlus(arr,pivot + 1,r);
    }
}

int findMedian(int x, int y, int z)
{
    if((x < y && x > z) || (x > y && x < z))
    {
        return x;
    }
    else if((y < x && y > z) || (y > x && y < z))
    {
        return y;
    }
    else
    {
        return z;
    }
}

void swap(char* arr[], int i, int j)
{
    char* temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

int partition(char* arr[], int l, int r, int x)
{
    int pivot;
   
    if( x == 0)
    {
        pivot = *arr[l];
    }
    else
    {
        pivot = findMedian(*arr[l], *arr[r-l], *arr[(l+r)/2]);
    }
    int cL = l + 1;
    int cR = r - 1;
    BOOL stop  = FALSE;
    
    while(stop == FALSE)
    {
        while(cL <= cR && *arr[cL] <= pivot)
        {
            cL++;
        }
        while(*arr[cR] >= pivot && cR >= cL)
        {
            cR --;
        }
        if(cR < cL)
        {
            stop = TRUE;
        }
        else
        {
            swap(arr, cL, cR);
        }
    }
    swap(arr, l, cR);
    return cR;
}


void compareSorts(char* arr[], int len)
{
    double insTime = 0.0, qsTime = 0.0, qspTime = 0.0;
    
    for (int i = 0; i < 20; i++)
    {
        clock_t begin, end;
        begin = clock();
        insertionsort(arr, 0, len);
        end = clock();
        insTime = ((double)(end - begin)) / CLOCKS_PER_SEC;
    }
    
    for (int i = 0; i < 20; i++)
    {
        clock_t begin, end;
        begin = clock();
        quicksort(arr, 0, len);
        end = clock();
        qsTime = ((double)(end - begin)) / CLOCKS_PER_SEC;
    }
    
    for (int i = 0; i < 20; i++)
    {
        clock_t begin, end;
        begin = clock();
        quicksortPlus(arr, 0, len);
        end = clock();
        qspTime = ((double)(end - begin)) / CLOCKS_PER_SEC;
    }
    
    printf("%s%f\n", "Insertion Sort time", insTime);
    printf("%s%f\n", "QuickSort time", qsTime);
    printf("%s%f\n", "QuickSort Plus time", qspTime);
    
}