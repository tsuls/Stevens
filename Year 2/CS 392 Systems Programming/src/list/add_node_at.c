#include "../../include/list.h"

void add_node_at(struct s_node* node, struct s_node** head, int n)
{
	int i = 0;
	struct s_node *currentNode;

	if(node != NULL && head != NULL && n >= 0 && n < count_s_nodes(*head))
	{
		if(node != NULL && head != NULL)
		{
			if(*head == NULL || n <= 0)
			{
				add_node(node, head);
			}
			else if(n >= count_s_nodes(*head))
			{
				append(node, head);
			}
			else
			{
				currentNode = *head;
				
				for(i = 0; i < n && currentNode->next != NULL; i++)
				{
					currentNode = currentNode->next;
				}
				/* currentNode is now in location where new node needs to be */
				
				currentNode->prev->next = node;
				node->prev = currentNode->prev;
				node->next = currentNode;
				currentNode->prev = node;
			}
		}
	}
	else
	{
		//my_str("Error in add_node_at");
	}
}
