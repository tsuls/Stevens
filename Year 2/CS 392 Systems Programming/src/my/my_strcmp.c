#include "../../include/my.h"

int my_strcmp(char* a, char* b) 
{
	int i = 0; 

	if(a == NULL && b == NULL)
	{
		return 0;
	}
	if(a == NULL)
	{
		return -1;
	}
	if(b == NULL)
	{
		return 1;
	}

	while(a[i] != '\0' && b[i] != '\0')
	 {
		if((a[i]-b[i]) < 0) 
		{
			return -1;
		}
		else if((a[i]-b[i]) > 0) 
		{
			return 1;
		}
		i++;
	}
	
	if(a[i] != '\0')
	{
		return 1;
	}
	if(b[i] != '\0')
	{
		return -1;
	}
	return 0;
}
