#include <linux/kernel.h>

asmlinkage int my_syscall2(char string[])
{
	printk("The argument given to my_syscall2 is %s\n", string);

	int i = 0;
	while (string[i] != 0)
	{
		i++;
	}

	int size = i + 1; // +1 for null terminator
	printk("%i\n", size);

	//printk("Here1\n");

	if( size > 128)
	{
		//printk("Here7\n");
		printk("The return value of my_syscall2 is -1\n");
		return -1;
	}

	//printk("Here2\n");

	int count = 0;
	i = 0;
	//char replace = '0';

	//printk("Here3\n");

	while(string[i] != 0)
	{
		//printk("Here4\n");
		//printk("%c\n", string[i]);
		if(string[i] == 'o' || string[i] == 'O')
		{
			//printk("Here5\n");
			string[i] = '0';
			//printk("Here8\n");
			count++;
		}
		i++;
	}

	//printk("Here6\n");
    printk("The return value of my_syscall2 is %i\n", count);

    return count;
}