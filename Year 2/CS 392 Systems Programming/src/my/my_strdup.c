#include "../../include/my.h"

char* my_strdup(char* str) 
{
	if(str==NULL)
	{
		return NULL;
	}
	
	char* new = (char*)malloc(sizeof(*str));
	my_strcpy(new, str);
	
	return new;

}
