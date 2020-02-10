#include "../../include/my.h"


int my_strlen(char* str)
{
	if( str == NULL)
	{
		return -1;
	}
	else
	{
		int strlen = 0;
		for(int i = 0; str[i] != '\0'; i++)
		{
			strlen++;
		}
		return strlen;
	}	
}
