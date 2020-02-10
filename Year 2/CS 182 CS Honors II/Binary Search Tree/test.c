#include <stdio.h>
#include "SIdict.h"



int main()
{
	SIdict test = makeSIdict();
	printf("%i\n", addOrUpdate(test, "hello", 123));
	printf("%i\n", hasKey(test , "hello"));
	printf("%i\n", lookup(test, "hello"));
	printf("%i\n", remKey(test, "hello"));
	printf("%i\n", remKey(test, "hello"));
	printf("%s\n", "Testing HasKey Last");
	printf("%i\n", hasKey(test , "hello"));
	return 0;
}