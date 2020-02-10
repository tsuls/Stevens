#include "../../include/list.h"

void traverse_char(struct s_node* head)
{
	if(head != NULL)
	{
		if(head != NULL)
		{
			while(head != NULL)
			{
				print_char(head);
				my_char(' ');
				head = head -> next;
			}
		}
	}
	else
	{
	//	my_str("Error in traverse_char");
	}
}
