#include "../../include/list.h"

struct s_node* node_at(struct s_node* head, int n)
{
	int i = 0;

	if(head != NULL && n >= 0 && n < count_s_nodes(head))
	{
		if(head != NULL)
		{
			for(i = 0; i < n && head->next != NULL; i++)
			{
				head = head->next;
			}

			return head;
		}
		else
		{
			return NULL;
		}
	}
	else
	{
		//my_str("Error in node_at");
		return NULL;
	}
}
