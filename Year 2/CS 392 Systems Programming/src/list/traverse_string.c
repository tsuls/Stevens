#include "../../include/list.h"

void traverse_string(struct s_node* head)
{
	if(head != NULL)
	{
		if(head != NULL)
		{
			while(head != NULL)
			{
				print_string(head);
				my_char(' ');
				head = head -> next;
			}
		}
	}
	else
	{
		//my_str("Error in traverse_string");
	}
}
