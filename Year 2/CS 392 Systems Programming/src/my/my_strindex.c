#include "../../include/my.h"


int my_strindex(char* str, char target)
{
    if(str == NULL)
    {
        return -1;
    }
	for(int i = 0; str[i] != '\0'; i++)
	{
		if(str[i] == target)
		{
			return i;
		}
	}
	return -1;
}
