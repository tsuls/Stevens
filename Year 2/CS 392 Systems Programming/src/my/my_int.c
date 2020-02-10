#include "../../include/my.h"
//#include <stdio.h>

void my_int(int n)
{
	long num = n;
	if(num < 0)
	{
		my_char('-');
		num = num * -1;
	}
	else if(num == 0)
	{
		my_char('0');
	}
	if(num > 0)
	{
		int temp = num;
		int len = 0;
		
		while(temp != 0) //Get the Lengh of the number
		{
			temp = temp / 10;
			len++;
		}	
		
		int div = 1;
		for(int i = 1; i < len; i++) // Rasie the div to the power of Ten necessary 
		{	
			div = div * 10;
		}

		int i = 0; 
		while(i < len) //Break the number down
		{
			my_char('0' + num/div);
			num = num % div;
			div = div/10;
			i++;
		}
	}
}
