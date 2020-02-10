#include "../../include/list.h"

void traverse_int(struct s_node* head)
{
	if(head != NULL)
	{
		if(head != NULL)
		{
			while(head != NULL)
			{
				print_int(head);
				my_char(' ');
				head = head -> next;
			}
		}
	}
	else
	{
		//my_str("Error in traverse_int");
	}
}
