#include "../../include/list.h"

void* remove_node(struct s_node** node)
{
	void *elem;
	//struct s_node** temp;

	if(node != NULL || *node == NULL)
	{
			return NULL;
	}

	//*node = NULL;

	elem = (*node) -> elem;

	if((*node) -> next != NULL) /* if there exists a next it should become new node */
	{
		(*node) -> next -> prev = (*node) -> prev;
	}
	if((*node) -> prev != NULL)
	{
		(*node) -> prev -> next = (*node) -> next;
	}
	else
	{
		(*node) = (*node) -> next ? (*node) -> next : NULL; 
	}
	
	free(*node);
	return elem;
}

