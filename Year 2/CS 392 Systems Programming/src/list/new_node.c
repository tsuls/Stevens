#include "../../include/list.h"

struct s_node* new_node(void* elemIn, struct s_node* nextIn, struct s_node* prevIn)
{
	struct s_node *newNode = (struct s_node *)malloc(sizeof(struct s_node));

	newNode -> elem = elemIn;
	newNode -> next = nextIn;
	newNode -> prev = prevIn;

	return newNode;
}
