#include <stdlib.h>
#include <stdio.h>
#include <assert.h>

#define BOOL char
#define FALSE 0
#define TRUE 1

/* Quicksort exercise. */

/* assuming i,j in range for arr, swaps the values, in place */
void swap(int arr[], int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}


/* Precondition: 0 <= l and l + 1 < r <= length of arr
 * Postcondition: arr has the same elements, but permuted,
 * and the return value s satisfies
 *    l <= s < r  and  arr[l:s] <= arr[s] <= arr[s:r]
 * (i.e., all elements arr[l]...arr[s-1] are at most arr[s],
 * and all elements arr[s]...arr[r-1] are at least arr[s].
 *
 * HINT: let arr[l] be the 'pivot value', that will end up at position s.
 * HINT: use local variables s and t, and maintain some kind of invariant
 * like this: arr[l:s] <= piv <= arr[t:r]
 *
 */
int partition(int arr[], int l, int r)
{
    int pivot = arr[l];
    int curLeft = l + 1;
    int curRight = r - 1;

    
    BOOL fin = FALSE;
    
    while (fin == FALSE)
    {
        while(curLeft <= curRight && arr[curLeft] <= pivot)
        {
            curLeft++;
        }
        while(arr[curRight] >= pivot && curRight >= curLeft)
        {
            curRight--;
        }
        if(curRight < curLeft)
        {
            fin  = TRUE;
        }
        else
        {
            swap(arr, curLeft, curRight);
        }
    }
    
    swap(arr, l, curRight);
    return curRight;
}


/* Sort arr[l:r] in place, using the quicksort algorithm.
 * Precondition: 0 <= l <= r <= len(arr).
 */
void quicksort(int arr[], int l, int r)
{
    if (l + 1 < r) {
        int s = partition(arr,l,r);
        quicksort(arr,l,s);
        quicksort(arr,s+1,r);
    }
}


/* Print elements of arr, assuming its length in len */
void printArr(int arr[], int len) {
    for (int i = 0; i < len; i++)
        printf("%i ", arr[i]);
    printf("\n");
}


int main() {
    int t0[] = {26, 24, 13, 18, 2, 29, 24, 12, 0, 5};
    int t1[] = {2, 1, 1, 1, 2, 0, 0, 0, 0, 2};
    int t2[] = {11, 7, 6, 28, 1, 20, 15, 9, 29, 3, 5};
    int t3[] = {2, 12, 11, 22, 11, 15, 27, 7, 19, 26, 27, 20};
    int t4[] = {12, 25, 23, 11, 11, 11, 21, 26, 25, 22, 16, 13, 7, 19, 6, 6, 12, 21, 16, 10};
    int t5[] = {2, 0, 4, 3, 2, 3, 1, 3, 1, 1, 3, 2, 3, 2, 2, 0, 0, 4, 1, 0};
    //
    quicksort(t0, 0, 10);
    printArr(t0, 10);
    //
    quicksort(t1, 0, 10);
    printArr(t1, 10);
    
    quicksort(t2, 0, 11);
    printArr(t2, 11);
    
    quicksort(t3, 0, 12);
    printArr(t3, 12);
    
    quicksort(t4, 0, 20);
    printArr(t4, 20);
    
    quicksort(t5, 0, 20);
    printArr(t5, 20);
}




