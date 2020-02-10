#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/sched.h>
#include <linux/moduleparam.h>
#include <linux/slab.h>
#include <linux/random.h>
#include <linux/list.h>
void constructLinkedList(void);
void printLinkedList(void);
void freeLinkedList(void);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Tyler Sulsenti");

struct Node
{
	int data;
	struct Node *next;
};

struct Node* head;
struct Node *curNode;
struct Node *newNode;
int *listData;

static int dstruct_size = 2;
module_param(dstruct_size, int,  S_IRUSR | S_IWUSR);
MODULE_PARM_DESC(dstruct_size,"this is the specified size");

//A//
void constructLinkedList(void)
{
	head = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	head-> data = listData[0];
 	head -> next = NULL;
 	curNode = head;
 	newNode = NULL;

 	int i = 1;
 	while(i < dstruct_size) 
 	{
 		newNode = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 		newNode -> data = listData[i];
 		curNode -> next = newNode;
 		curNode = newNode;
 		i++;
 	}
 	curNode -> next = NULL;
}

//B//
void printLinkedList(void)
{
	curNode = head;
 	while(NULL != curNode)
 	{
 		printk("The data is: %i\n", curNode->data);
 		curNode = curNode->next; 
 	}
}

//C//
void freeLinkedList(void)
{
	while(head != NULL)
	{
		curNode = head;
		head = head -> next;
		kfree(curNode);
	}
	printk("The linked list was freed\n");
}

static int lab2_module_init(void) 
{
	printk("LL2\n----------------\n");

	printk("dstruct_size: %i\n", dstruct_size);

	int temp[dstruct_size];
	listData = temp;
 	get_random_bytes(listData,sizeof(temp));

 	printk("----------------\n");

 	//A//
 	constructLinkedList();
 	
 	//B//
 	printLinkedList();
 
 	//C//
 	freeLinkedList();

 	return 0; 
}

 

static void lab2_module_exit(void) 
{
	printk("Module Unloaded\n");
}


module_init (lab2_module_init);
module_exit(lab2_module_exit);