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
struct Node * dequeue(void);
void constructQueue(void);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Tyler Sulsenti");

struct Node
{
	int data;
	struct Node *next;
};

struct Node *LLhead;
struct Node *LLcurNode;
struct Node *LLnewNode;

struct Node *Qhead;
struct Node *QcurNode;
struct Node *QnewNode;

int *listData;
int *queueData;

static int dstruct_size = 2;
module_param(dstruct_size, int,  S_IRUSR | S_IWUSR);
MODULE_PARM_DESC(dstruct_size,"this is the specified size");

//A//
void constructLinkedList(void)
{
	LLhead = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	LLhead-> data = listData[0];
 	LLhead -> next = NULL;
 	LLcurNode = LLhead;
 	LLnewNode = NULL;

 	int i = 1;
 	while(i < dstruct_size) 
 	{
 		LLnewNode = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 		LLnewNode -> data = listData[i];
 		LLcurNode -> next = LLnewNode;
 		LLcurNode = LLnewNode;
 		i++;
 	}
 	LLcurNode -> next = NULL;
}

//B//
void printLinkedList(void)
{
	LLcurNode = LLhead;
 	while(NULL != LLcurNode)
 	{
 		printk("The data is: %i\n", LLcurNode->data);
 		LLcurNode = LLcurNode->next; 
 	}
}

//C//
void freeLinkedList(void)
{
	while(LLhead != NULL)
	{
		LLcurNode = LLhead;
		LLhead = LLhead -> next;
		kfree(LLcurNode);
	}
	printk("The linked list was freed\n");
}

//Add from back of list
void enqueue(struct Node *toAdd)
{
	if(Qhead == NULL)
	{
		Qhead = toAdd;
		toAdd -> next = NULL;
	}
	else
	{
		QcurNode = Qhead;
		
		while(QcurNode->next != NULL)
		{
			QcurNode = QcurNode->next;
		}

		toAdd -> next = QcurNode->next;
		QcurNode -> next = toAdd;
	}
}

//Remove from front. returns NULL if the list is empty
struct Node * dequeue(void)
{
	//printk("QcurNode is %i\n", QcurNode -> data);

	if(Qhead == NULL)
	{
		printk("The Queue is Empty\n");
		return NULL;
	}

	QcurNode = Qhead;

	struct Node *temp;
	struct Node *rtn = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
	rtn -> data = QcurNode -> data;
	rtn -> next = QcurNode -> next;

	temp = QcurNode -> next;
	kfree(QcurNode);

//	printk("QcurNode is NOW %i\n", QcurNode -> data);

	Qhead = temp;

	return rtn;
}

void printQueue(void)
{
	QcurNode = Qhead;
	int i = 0;
 	while(NULL != QcurNode)
 	{
 		printk("The data is: %i at index %i\n", QcurNode->data, i);
 		QcurNode = QcurNode->next; 
 		i++;
 	}
}

//Constructs a Queue of dstruct_size elements of random values
void constructQueue(void)
{
	int i = 0;
	while(i < dstruct_size)
	{
		struct Node *toAdd = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
		toAdd -> data = queueData[i];

		enqueue(toAdd);
		i++;
	}
}

void deconstructQueue(void)
{
	int i = 0;
	while (NULL != Qhead)
	{
		printk("Just dequeued %i at index %i", dequeue() -> data, i);
		i++;
	}

	printk("The Queue has been freed\n");
}

static int lab2_module_init(void) 
{
	printk("LL2\n----------------\n");

	printk("dstruct_size: %i\n", dstruct_size);

	int LLtemp[dstruct_size];
	int Qtemp[dstruct_size];
	listData = LLtemp;
	queueData = Qtemp;
 	//get_random_bytes(listData,sizeof(LLtemp));
 	get_random_bytes(queueData, sizeof(Qtemp));

 	printk("----------------\n");

 	/* Linked List
 	constructLinkedList();
 	printLinkedList();
 	freeLinkedList();*/

 	/*Queue*/
 	constructQueue();
 	printk("The queue is:\n");
 	printQueue();
 	deconstructQueue();


 	/*struct Node * tempN  = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	tempN-> data = listData[0];
 	tempN -> next = NULL;
 	enqueue(tempN);

 	struct Node * tempM  = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	tempM-> data = listData[1];
 	tempM -> next = NULL;
 	enqueue(tempM);

 	struct Node * tempP  = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	tempP-> data = listData[1];
 	tempP -> next = NULL;
 	enqueue(tempP);

 	printk("The list is: \n");

 	printQueue();

 	printk("-----------\n");

 	printk("the head is %i\n", Qhead -> data);

 	printk("Just DQ'd %i\n", dequeue()-> data); 
 	printk("head is NOW %i\n", Qhead -> data);

 	printk("The list now is: \n");
 	printQueue();

	printk("Just DQ'd %i\n", dequeue()-> data); 
 	printk("head is NOW %i\n", Qhead -> data);

 	printk("Just DQ'd %i\n", dequeue()-> data);
 	dequeue(); 
 	if(Qhead == NULL){printk("head is null");}

 	printk("The list now is: \n");
 	printQueue();*/


 	return 0; 
}

 

static void lab2_module_exit(void) 
{
	printk("Module Unloaded\n");
}


module_init (lab2_module_init);
module_exit(lab2_module_exit);