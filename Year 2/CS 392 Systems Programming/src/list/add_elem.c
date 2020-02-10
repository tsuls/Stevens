#include "../../include/list.h"

void add_elem(void* elemIn, struct s_node** head)
{
	if(elemIn != NULL && head != NULL)
	{
		if(elemIn != NULL && head != NULL)
		{
			struct s_node* newNode = new_node(elemIn, NULL, NULL);

			add_node(newNode, head);
		}
	}
	else
	{
		//my_str("Error in add_elem");
	}
}
