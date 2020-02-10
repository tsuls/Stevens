#include "../../include/my.h"

char* my_substr(char* str, int beg, int end)
{
	if(str == NULL || end > my_strlen(str) || beg > my_strlen(str))
	{
		return  str;
	}

	char* newStr = (char*) malloc(sizeof(char*));
	int j = 0;

	for(int i = beg; i <= end; i++)
	{
		newStr[j] = str[i];
		j++;
	}

	newStr[end + 1] = '\0';

	return newStr;
}
