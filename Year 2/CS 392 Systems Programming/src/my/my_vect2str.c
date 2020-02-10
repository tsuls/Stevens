#include "../../include/my.h"

char* my_vect2str(char** x)
{
	char* newString = (char*) malloc(sizeof(char*));

	if(x == NULL || *x == NULL)
	{
		return my_strdup("");
	}

	for(int i = 0; x[i] != NULL; i++)
	{
		newString = my_strconcat(newString, x[i]);
		if(x[i + 1] != NULL)
		{
			newString = my_strconcat(newString, " ");	
		}
	}
	return newString;
}

