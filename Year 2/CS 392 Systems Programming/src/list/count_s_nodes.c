#include "../../include/list.h"

int count_s_nodes(struct s_node* head)
{
	int count = 0;
	struct s_node *currentNode = head;

	if(head != NULL)
	{
		if(head != NULL)
		{
			while(currentNode->next != NULL)
			{
				count++;
				currentNode = currentNode -> next;
			}
			count++; /* returning length, not index */
			return count;
		}
		else
		{
			return 0;
		}
	}
	else
	{
		return 0;
	}
}
