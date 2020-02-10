#include "../../include/my.h"


int my_strrindex(char* str, char target)
{
    
    if(str == NULL)
    {
        return -1;
    }
    
	char lastDex = -1;
	for(int i = 0; str[i] != '\0'; i++)
	{
		if(str[i] == target)
		{
			lastDex = i;
		}
	}
	return lastDex;	
}

