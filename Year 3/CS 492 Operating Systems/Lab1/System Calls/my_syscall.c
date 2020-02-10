#include <linux/kernel.h>

asmlinkage int my_syscall(int x, int y)
{
	int sum = x + y;
	
	printk("The arguments given to my_syscall are %i and %i\n", x, y);

	printk("The return value of my_syscall is %i\n", sum); 

	return sum;
}