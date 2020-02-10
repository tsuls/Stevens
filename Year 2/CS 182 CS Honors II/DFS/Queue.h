

struct queue
{
	int size;
	int* arr;
};
typedef struct queue* Queue;

int size();

Queue enqueue(Queue q, int toAdd);

int dequeue(Queue q);

int empty(Queue q);

int peek(Queue q);

void display(Queue q);

