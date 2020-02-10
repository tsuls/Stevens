#include "../../include/my.h"

void my_str(char* str)
{
	if(str != NULL)
	{
		for(int i = 0; str[i] != '\0'; i++)
		{
			my_char(str[i]);
		}	
	}
}
