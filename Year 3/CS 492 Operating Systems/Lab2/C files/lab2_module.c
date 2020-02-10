#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/sched.h>
#include <linux/moduleparam.h>
#include <linux/slab.h>
#include <linux/random.h>
#include <linux/list.h>
#include <linux/rbtree.h>
#define UPPER_BOUND 1000
#define LOWER_BOUND 0
#define TRUE 1
#define FALSE 0

void deconstructMap(void);
void constructLinkedList(void);
void printLinkedList(void);
void freeLinkedList(void);
struct Node * dequeue(void);
void constructQueue(void);
int insertIntoMap(int toAdd);
void displayMap(void);
void initializeMap(void);
void constructTree(void);
void diplayTree(struct rb_root *treeRoot);
void deconstructTree(struct rb_node *treeRoot);
void removeRangeFromTree(struct rb_root *treeRoot);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Tyler Sulsenti");

struct Node
{
	int data;
	struct Node *next;
};

struct mapNode
{
	int data;
	int valid;
};

struct Map 
{
   struct mapNode *data[UPPER_BOUND];
    int size;
};

struct treeNode
{
	int data;
	struct rb_node node;
};

struct Node *LLhead;
struct Node *LLcurNode;
struct Node *LLnewNode;

struct Node *Qhead;
struct Node *QcurNode;
struct Node *QnewNode;

struct Map *myMap;

struct rb_root treeHead;

int *listData;
int *queueData;
int *mapData;
int *treeData;
int *rangeData;

static int dstruct_size = 2;
module_param(dstruct_size, int,  S_IRUSR | S_IWUSR);
MODULE_PARM_DESC(dstruct_size,"this is the specified size"); 

//A//
void constructLinkedList(void)
{
	int i;
	LLhead = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	LLhead-> data = listData[0];
 	LLhead -> next = NULL;
 	LLcurNode = LLhead;
 	LLnewNode = NULL;

 	i = 1;
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
	struct Node *temp;
	struct Node *rtn;

	if(Qhead == NULL)
	{
		printk("The Queue is Empty\n");
		return NULL;
	}

	QcurNode = Qhead;

	rtn = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
	rtn -> data = QcurNode -> data;
	rtn -> next = QcurNode -> next;

	temp = QcurNode -> next;
	kfree(QcurNode);

	Qhead = temp;

	return rtn;
}

void printQueue(void)
{
	int i;

	QcurNode = Qhead;
	i = 0;
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
	int i;

	i = 0;
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
	int i;

	i = 0;
	while (NULL != Qhead)
	{
		printk("Just dequeued %i at index %i", dequeue() -> data, i);
		i++;
	}

	printk("The Queue has been freed\n");
}

/*Hash function cited from 
 *https://stackoverflow.com/questions/664014/what-integer-hash-function-are-good-that-accepts-an-integer-hash-key
 */
int hash(int val) 
{
	unsigned int x = val;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = (x >> 16) ^ x;

    x = x % UPPER_BOUND - LOWER_BOUND;

    return x;
}

//Inserts into map and returns the ID. -1 is returned if it fails.
int insertIntoMap(int toAdd)
{
	int id = hash(toAdd);

	//Nothing in map at location id
	if(myMap->data[id] == NULL)
	{
		//Add
		struct mapNode *nodeToAdd = (struct mapNode *) kmalloc(sizeof(struct mapNode), GFP_KERNEL);
		nodeToAdd-> data = toAdd;
		nodeToAdd-> valid = FALSE;

		myMap->data[id] = nodeToAdd;
		myMap->size++;
		
		return id;
	}//collision
	else
	{
		int i = id;
		while(i <  UPPER_BOUND)
		{
			if(myMap->data[i] == NULL)
			{
				//Add
				struct mapNode *nodeToAdd = (struct mapNode *) kmalloc(sizeof(struct mapNode), GFP_KERNEL);
				nodeToAdd-> data = toAdd;
				nodeToAdd-> valid = FALSE;

				myMap->data[i] = nodeToAdd;
				myMap->size++;
				return i;
			}
			i++;
		}
		//if we leave while it could not be added due to size constraints.
		return -1;
	}

}

void displayMap(void)
{
	int i;
	i= 0;
	while(i < UPPER_BOUND)
	{
		if(myMap->data[i] != NULL)
		{
			printk("The data at id: %i is: %i\n",i, myMap->data[i]->data);
		}
		i++;
	}
}

void constructMap(void)
{
	int i;
	initializeMap();
	i = 0;
	while(i < dstruct_size)
	{	
		insertIntoMap(mapData[i]);
		i++;
	}
}

void initializeMap(void)
{
	int i = 0;
	while(i < UPPER_BOUND)
	{
		myMap->data[i] = NULL;
		i++;
	}
}

void deconstructMap(void)
{
	int i = 0;
	while(i < UPPER_BOUND)
	{
		kfree(myMap->data[i]);
		i++;
	}

	kfree(myMap->data);
	kfree(myMap);
	printk("The Map has been freed\n");
}

//Kernel docs provided information on RB Trees and inspiration for implementation
//Returns NULL if fails to find.
struct treeNode * searchInRBTree(struct rb_root *treeRoot, int toFind)
{
	struct rb_node *aNode = treeRoot->rb_node;

	while(aNode != NULL)
	{
		struct treeNode *someNode = container_of(aNode, struct treeNode, node);
		
		if(toFind < someNode->data)
		{
			aNode = aNode->rb_left;
		}
		else if(toFind > someNode->data)
		{
			aNode = aNode->rb_right;
		}
		else
		{
			return someNode;
		}
	}
	return NULL; //Failed to find.
}

int insertIntoTree(struct rb_root *treeRoot, struct treeNode *toAdd)
{
	struct rb_node **newTreeNode = &(treeRoot->rb_node);
	struct rb_node *parent = NULL;

	while(*newTreeNode != NULL)
	{
		struct treeNode *curTreeNode = container_of(*newTreeNode, struct treeNode, node);
		parent = *newTreeNode;

		if(curTreeNode->data < toAdd->data)
		{
			newTreeNode = &((*newTreeNode)->rb_left);
		}
		else if(curTreeNode->data > toAdd->data)
		{
			newTreeNode = &((*newTreeNode)->rb_right);
		}
		else
		{	
			return 0;
		}
	}

	rb_link_node(&toAdd->node, parent, newTreeNode);
	rb_insert_color(&toAdd->node, treeRoot);
	return 1;
}

void displayTree(struct rb_root *treeRoot) 
{
    struct rb_node *cur = rb_first(treeRoot);

    while(cur != NULL)
     {
        printk("The data of this node is: %i\n", rb_entry(cur, struct treeNode, node)->data);
        cur = rb_next(cur);
    }
}

void constructTree(void)
{
	int i;
	i = 0;
	while(i < dstruct_size)
	{
		struct treeNode *toAdd = (struct treeNode *)kmalloc(sizeof(struct treeNode), GFP_KERNEL);
		toAdd->data = treeData[i];

		if(searchInRBTree(&treeHead, treeData[i]) == NULL)
		{
			insertIntoTree(&treeHead, toAdd);
		}
		i++;
	}
}

void removeRangeFromTree(struct rb_root *treeRoot)
{
	int treeRange[2];
	int begin, end, i;

	rangeData = treeRange;
	get_random_bytes(rangeData, sizeof(treeRange));

	if(treeRange[0] > treeRange[1])
	{
		begin = treeRange[1];
		end = treeRange[0];
	}
	else
	{
		begin = treeRange[0];
		end = treeRange[1];
	}

	i = begin;
	if(begin < 0 && end < 0)
	{
		while(i > end)
		{
			struct treeNode *toCheck = searchInRBTree(treeRoot, i);
			if(toCheck != NULL)
			{
				printk("Removing: %i\n", toCheck->data);
				rb_erase(&toCheck->node, treeRoot);
				kfree(toCheck);
			}
			i--;
		}
	}
	else
	{
		while(i < end)
		{
			struct treeNode *toCheck = searchInRBTree(treeRoot, i);
			if(toCheck != NULL)
			{
				printk("Removing: %i\n", toCheck->data);
				rb_erase(&toCheck->node, treeRoot);
				kfree(toCheck);
			}
			i++;
		}
	}
	printk("Just removed elements between the range of: %i and %i\n", begin, end);
}

void deconstructTree(struct rb_node *treeRoot) 
{
	if(treeRoot != NULL)
	{
		deconstructTree(treeRoot->rb_left);
   		deconstructTree(treeRoot->rb_right);
    	kfree(treeRoot);
	}
	else
	{
		return;
	}
}


static int lab2_module_init(void) 
{
	struct rb_node *treeFirst;
	int Ttemp[dstruct_size];
	int LLtemp[dstruct_size];
	int Qtemp[dstruct_size];
	int Mtemp[dstruct_size];
	printk("RBTree\n----------------\n");

	listData = LLtemp;
	queueData = Qtemp;
	mapData = Mtemp;
	treeData = Ttemp;
 	get_random_bytes(listData,sizeof(LLtemp));
 	get_random_bytes(queueData, sizeof(Qtemp));
 	get_random_bytes(mapData, sizeof(Mtemp));
 	get_random_bytes(treeData,sizeof(Ttemp));

 	printk("----------------\n");

 	/* Linked List */
 	constructLinkedList();
 	printLinkedList();
 	freeLinkedList();

 	/* Queue */
 	constructQueue();
 	printk("The queue is:\n");
 	printQueue();
 	deconstructQueue();

 	/* Map */
 	myMap = (struct Map *) kmalloc(sizeof(struct Map),  GFP_KERNEL);
 	myMap->size = 0;
 	constructMap();
 	printk("The Map is:\n");
 	displayMap();
 	deconstructMap();

 	/* Red and Black Tree */
 	treeHead = RB_ROOT;
 	constructTree();
 	printk("The Tree is: \n");
 	displayTree(&treeHead);
 	removeRangeFromTree(&treeHead);
 	printk("The Tree is now: \n");
 	displayTree(&treeHead);
 	treeFirst = rb_first(&treeHead);  
 	deconstructTree(treeFirst);
 	printk("The Tree was freed\n");

 	return 0;
}

 

static void lab2_module_exit(void) 
{
	printk("Module Unloaded\n");
}


module_init (lab2_module_init);
module_exit(lab2_module_exit);