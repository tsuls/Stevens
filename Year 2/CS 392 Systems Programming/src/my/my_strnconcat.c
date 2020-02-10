#include "../../include/my.h"

char* my_strnconcat(char* a, char* b, int n) 
{
	char* newStr = (char*)malloc(sizeof(*a)+sizeof(*b));

	if(a == NULL && b == NULL)
	{
		return NULL;
	}
	
	int i = 0;

	if(a == NULL) 
	{
		while(i < n) 
		{
			newStr[i] = b[i];
			i++;
		}
		
		newStr[i] = '\0';
		return newStr;
	}

	if(b == NULL) 
	{
		newStr = a;
		return newStr;
	}	

	
	while(a[i] != '\0')
	 {
		newStr[i] = a[i];
		i++;
	}

	int j = 0;

	while(b[j] != '\0' && j < n) 
	{
		newStr[j+i] = b[j];
		j++;
	}

	newStr[i+j] = '\0';
	return newStr;
}
