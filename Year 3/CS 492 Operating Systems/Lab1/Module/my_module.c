#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/sched.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Tyler Sulsenti");

static int my_module_init(void) 
{
 	printk(KERN_INFO "Hello World from Tyler Sulsenti (10413031)\n");
	return 0;
}


static void my_module_exit(void) 
{
	struct task_struct *task;

	printk(KERN_INFO "The PID of the current process is %d\n", current->pid);

	for_each_process(task)
	{
		if(task->pid == current->pid)
		{
			printk(KERN_INFO "The name of the current process is %s\n", task->comm);
		}
	}

}


module_init (my_module_init);
module_exit(my_module_exit);