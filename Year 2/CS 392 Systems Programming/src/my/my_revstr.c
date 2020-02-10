#include "../../include/my.h"
//#include <stdio.h>


    int my_revstr(char* str)
    {
        
        if(str == NULL)
        {
            return -1;
        }
        int strlen = my_strlen(str);

		int end = strlen - 1;
		for(int i = 0; i <= (strlen - 1)/2; i++)
		{
			char temp = str[i];
			str[i] = str[end];
			str[end] = temp;
			end--;
		}

		/*printf("%s%s\n","The string is: ", str );

		for(int i = 0; str[i] != '\0'; i++)
		{
			printf("%c\n", str[i]);	
		}*/
		return strlen;
    }
