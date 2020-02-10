#include "../../include/my.h"


char* my_strconcat(char* dst, char* src)
 {

	char* new = (char*)malloc(sizeof(*dst)+sizeof(*src));
	
	int i = 0;
	int d;
	int s;

	if(src == NULL && dst == NULL)
	{
		return NULL;
	}
	
	if(dst == NULL) 
	{
		s = my_strlen(src);
		
		while(i < s)
		{
			new[i]=src[i];
			i++;
		}

		new[i] = '\0';
		return new;
	}
	if(src == NULL) 
	{
		d = my_strlen(dst);
		while(i < d) 
		{
			new[i]=dst[i];
			i++;
		}
		
		new[i] = '\0';
		return new;
	}

	d = my_strlen(dst);
	s = my_strlen(src);

	if(dst == src) 
	{
		while(i < s) 
		{
			new[i] = src[i];
			new[i+d] = src[i];
			i++;
		}
		return new;
	}
	
	while(i < d) 
	{
		new[i] = dst[i];
		i++;
	}

	i = 0;

	while(i < s) 
	 {
		new[i+d] = src[i];
		i++;
	}
	
	new[d+i] = '\0';

	return new;
}
