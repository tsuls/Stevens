#include <stdio.h>
#include "SIdict.h"
#include "SIdictLL.c"


int main()
{
	SIdict test = makeSIdict();
	printf("%i\n", addOrUpdate(test, "hello", 123));
	printf("%i\n", addOrUpdate(test, "hello", 123));
	printf("%i\n", hasKey(test , "hello"));
	printf("%i\n", lookup(test, "hello"));
	printf("%i\n", remKey(test, "Hello"));
	return 0;
}