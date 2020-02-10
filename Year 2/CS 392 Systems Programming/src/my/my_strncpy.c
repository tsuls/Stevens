#include "../../include/my.h"

char* my_strncpy(char *dst, char *src, int n) 
{ 

	int i = 0;

	if(dst == NULL)
	{
		return NULL;
	}

	if(src == NULL) 
	{
		return dst;
	}
		
	while(src[i] != '\0' && i < n) 
	{
		dst[i] = src[i];
		i++;
	}
	
	dst[i] = '\0';
	
	return dst;
}
