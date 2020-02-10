#include "../../include/list.h"

void debug_char(struct s_node* head)
{
	if(head != NULL)
	{
		if(head != NULL)
		{
			while(head != NULL)
			{
				my_char('(');
				if(head -> prev == NULL)
					my_str("NULL <- ");
				else
				{
					print_char(head->prev);
					my_str(" <- ");
				}

				print_char(head);

				if(head->next == NULL)
					my_str(" -> NULL)");
				else
				{
					my_str(" -> ");
					print_char(head -> next);
					my_str("), ");
				}

				head = head -> next;
			}
		}
	}
	else
	{
	//	my_str("Error in debug_char");
	}
}
