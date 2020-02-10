#include "../../include/list.h"

void* elem_at(struct s_node* head, int n)
{
	struct s_node *node = node_at(head, n);

	if(node != NULL)
	{
		return node -> elem;
	}
	else
	{
		return NULL;
	}
}
