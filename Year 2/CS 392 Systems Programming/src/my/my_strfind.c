#include "../../include/my.h"

char* my_strfind(char* str, char chr) 
{
	if(str == NULL || chr=='\0')
	{
		return NULL;
	}

	if(my_strlen(str)==-1)
	{
		return NULL;
	} 

	if(my_strindex(str, chr)==-1)
	{
		return NULL;
	}
	
	return (str+my_strindex(str, chr));
}
