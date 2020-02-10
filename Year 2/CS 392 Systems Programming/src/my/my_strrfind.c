#include "../../include/my.h"

char* my_strrfind(char* str, char chr) 
{

	if(str == NULL || chr == '\0')
	{
		return NULL;
	}

	if(my_strlen(str) == -1)
	{
		return NULL;
	}

	if(my_strrindex(str, chr) == -1)
	{
		return NULL;
	}
	
	char* newStr = (str + my_strrindex(str, chr));
	return newStr;
}
