#include "../../include/my.h"


char** my_str2vect (char* str)
{
	if(str == NULL)
	{
		return NULL;
	}
	int strLen = my_strlen(str);
	char** vect = (char**) malloc(sizeof(char) * 1024);
	int last = 0;

	int i = 0;
	int v = 0;
	while(i < strLen + 1)
	{
		if(str[i] == ' ' || str[i] == '\t' || str[i] == '\n' || str[i] == '\0')// End of word.
		{
			char* word = (char*) malloc(sizeof(char) * 1024);
			//if(str[i] == ' '){printf("%s%c\n","str[i] is: ", str[i]);}

 			word = my_substr(str, last, i - 1);
			vect[v] = word;
			v++;

			last = i + 1;
			i++; //Move to next spot.
			

			while(str[i] == ' ' || str[i] == '\t' || str[i] == '\n')
			{
				//Contiunue to skip the special chars
				i++;
				last = i;
			}
		}
		i++;
	}
	return vect;
}
