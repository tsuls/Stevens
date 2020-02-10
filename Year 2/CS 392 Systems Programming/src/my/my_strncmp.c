#include "../../include/my.h"

int my_strncmp(char* a, char* b, int n)
 {
	int i = 0;
	int R = 0;


	if(a == NULL && b == NULL)
	{
		return 0;
	}

	if(a == NULL)
	{
		return -1;
	}

	if(b == NULL)
	{
		return 1;
	}

	if(n == 0 || n < 0)
	{
		return 0;
	}

	while(a[i] != '\0' && b[i] != '\0' && i < n)
	 {
		if((a[i] - b[i]) < 0) 
		{
			return -1;
		}
		else if((a[i] - b[i]) > 0) 
		{
			return 1;
		}

		i++;
		if(i == n - 1)
		{
			R = 1;
		}
	}

	if(R == 1)
	{
		return 0;
	}
	if(a[i] != '\0')
	{
		return 1;
	}
	if(b[i] != '\0')
	{
		return -1;
	}
	return 0;
}

