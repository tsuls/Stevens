#include "../../include/my.h"

char* my_strcat(char* dst, char* src) 
{
	if(src == NULL && dst == NULL)
	{
		return NULL;
	}
	if(dst == NULL)
	{
		return NULL;
	}
	if(src == NULL)
	{
		return dst;
	}
	
	int i = 0;
	int endofD = my_strlen(dst);
	int endofS = my_strlen(src);
	
	if(dst == src) 
	{
		while(i < endofS)
		 {
			dst[i + endofD] = src[i];
			i++;
		}
		return dst;
	}
	

	
	
	while(i < endofD + endofS) 
	{
		dst[endofD + i] = src[i];
		i++;
	}
	

	dst[endofD + i] = '\0';
	return dst;
}
