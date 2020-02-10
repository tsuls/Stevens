#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/sched.h>
#include <linux/moduleparam.h>
#include <linux/slab.h>
#include <linux/random.h>
#include <linux/list.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Tyler Sulsenti");

struct Node
{
	int data;
	struct Node *next;
};

struct Node* head;

static int dstruct_size = 2;
module_param(dstruct_size, int,  S_IRUSR | S_IWUSR);
MODULE_PARM_DESC(dstruct_size,"this is the specified size");

static int lab2_module_init(void) 
{
	printk("LL\n----------------\n");

	printk("dstruct_size: %i\n", dstruct_size);

	int temp[dstruct_size];
 	get_random_bytes(temp,sizeof(temp));

 	//A//
 	/*Construct the linked list*/
	head = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	head-> data = temp[0];
 	head -> next = NULL;

 	struct Node *curNode = head;
 	struct Node *newNode = NULL;
 
 	int i = 1;
 	while(i < dstruct_size) 
 	{
 		newNode = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 		newNode -> data = temp[i];
 		curNode -> next = newNode;
 		curNode = newNode;
 		i++;
 	}
 	curNode -> next = NULL;
 	//EndA// 
 	
 	printk("----------------\n");

 	//B//
 	/*Prints linked list to kernel log*/
 	curNode = head;
 	while(NULL != curNode)
 	{
 		printk("The data is: %i\n", curNode->data);
 		curNode = curNode->next; 
 	}
 	//EndB//

	//C//
	/*Frees the linked list*/
	while(head != NULL)
	{
		curNode = head;
		head = head -> next;
		kfree(curNode);
	}
	printk("The linked list was freed\n");
	//End C//

 	return 0; 
}

 

static void lab2_module_exit(void) 
{
	printk("Module Unloaded\n");
}


module_init (lab2_module_init);
module_exit(lab2_module_exit);