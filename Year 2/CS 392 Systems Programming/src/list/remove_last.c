#include "../../include/list.h"

void* remove_last(struct s_node** head)
{
	struct s_node *currentNode;
	void *temp;

	if(head != NULL && *head != NULL)
	{
		if(head != NULL && *head != NULL)
		{
			if((*head)->next == NULL)
			{
				temp = (*head) -> elem;
				free(*head);
				*head = NULL;
				return temp;
			}
			else
			{
				currentNode = *head;

				while(currentNode -> next != NULL)
				{
					currentNode = currentNode -> next;
				}

				currentNode -> prev -> next = NULL;
				temp = currentNode -> elem;
				free(currentNode);
			
				return temp;
			}
		}
		else
		{
			return NULL;
		}
	}
	else
	{
		my_str("Error in remove_last");
		return NULL;
	}
}
