#include "../../include/my.h"
//#include <stdio.h>

void my_num_base(int n, char* str) 
{
	  int base = my_strlen(str);
	  long num = n;
	  
	  if(str == NULL)
	  {
	  	my_str("The given string cannot be NULL");
	  }
	  if(my_strlen(str) == 0)
	  {
	  	my_str("The given string cannot be Empty");
	  }
	  if(base == 0)
	  {
	  	return;
	  }
	  if (n < 0)
	  {
	  	my_char('-');
	  	num = num * -1;
	  }
	  else if(num == 0 && base != 1)
	  {
	  	my_char('0');
	  }

	  if(base == 1)
	  {
	  	if(num == 0)
	  	{
	  		return;
	  	}

	  	for(int i = 0; i < num; i++)
	  	{
	  		my_char(str[0]);
	  	}
	  }

	  if(num > 0 && base > 1 && str != NULL)
	  {
       
		//Prepare to convert base without recursion
		long convert = 0;
		long i = 1;
	  	while (num > 0) //Convert Base
	  	{	
	  	  	int result = num / base;
	  		int remainder = num % base;
	  	//	printf("%s%i\n","Base is: ", base);
	  	//	printf("%s%i\n", "remainder is: ", remainder);
	  	//	printf("%s%ld\n","i is: " ,i);
	  	//	printf("%s%ld\n","Convert is before: " ,convert);
	  		convert = convert + (remainder * i);
	  	//	printf("%s%ld\n","Convert is after: " ,convert);
	  		
	  		if(remainder >= 10)
	  		{
	  			i = i * 100;
	  		}
	  		else
	  		{
	  			i = i * 10;
	  		}
	  		num = result;
	  	}
	  	//printf("%s%ld\n","Convert is: " ,convert);
	 	//With our base converted, we can now print our chars.
	 	//We will need to follow the process of raising a number to the appropriate power
	  	long temp = convert;
		long len = 0;
		
		while(temp != 0) //Get the Lengh of the number
		{
			temp = temp / 10;
			len++;
		}	
		
		long div = 1;
		for(int i = 1; i < len; i++) // Rasie the div to the power of Ten necessary 
		{	
			div = div * 10;
		}
		
		if(base == 16)
		{
			int in = 1;
			//printf("%s%ld\n","len is: ", len );
			if(len > 1 && len != 15)
			{
				div = div/10;
				len  = len - 2;
			}
			int i = 0;
			//div = div/10;
			//printf("%s%ld\n","Div is: " ,div);
			while(convert != 0)
			{
			//	printf("%s%ld\n","Convert is: " ,convert);
				/*if(len > 1)
				{
					div = div/10;
					len = len - 2;
				}*/
				in = convert / div;
				//printf("%s%i\n","in is: ", in);
				my_char(str[in]);
				convert = convert % div;
				div = div/10;
				i++;
				if(len > 1)
				{
					div = div/10;
					len = len - 2;
				}
			}
		}
		else
		{
			int i = 0;
			while(i < len) //Break the number down
			{
				//printf("%s%ld\n","Convert is: " ,convert);
				my_char(str[convert/div]);
				convert = convert % div;
				div = div/10;
				i++;
			}
		}
	}
}

