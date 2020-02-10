#include "../../include/my.h"
#include <stdio.h>

int main()
{
	char testStr[] = "Hello World";

	char** vect = my_str2vect(testStr);


	printf("%s\n","Ouput: " );
	for(int i = 0; i < 2; i++)
	{
		printf("%s\n",vect[i]);
	}
}
