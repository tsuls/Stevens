#include "../../include/list.h"

void append(struct s_node* nodeIn, struct s_node** head)
{
	struct s_node *currentNode;

	if(nodeIn != NULL && head != NULL && nodeIn->elem != NULL)
	{
		if(nodeIn != NULL && head != NULL && nodeIn->elem != NULL)
		{
			if(*head == NULL)
			{
				add_node(nodeIn, head);
			}
			else
			{
				currentNode = *head;
				
				while(currentNode->next != NULL)
				{
					currentNode = currentNode->next;
				}

				nodeIn -> prev = currentNode;
				nodeIn -> next = currentNode->next;
				currentNode -> next = nodeIn;
			}
		}
	}
	else
	{
		//my_str("Error in append");
	}
}
