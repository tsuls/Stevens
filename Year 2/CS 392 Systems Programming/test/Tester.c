#include "../../include/my.h"


int main()
{
	//my_char(NULL); //Good
    //my_str(NULL);// Good
    //my_int(); Good
	//my_alpha(); Good
	//my_digits(); Good
	//printf("%i",my_strindex(NULL, 'c'));// Good
	//printf("%i",my_strrindex("abacabaca", 'c')); Good
	//char tester[] = "abcdef";
	//my_revstr(tester);
    //char s[] = NULL;
	//my_num_base(9, NULL);

	char stra[] = "abcdef";
	char strb[] = "xyzzzz";
	char strc[] =  "a-b54sc7-d";
	//printf("%i\n", my_strncmp(stra, strb, 5));
	//printf("%s\n", my_strcat(strb, stra));

	//printf("%s\n", my_strdup(stra));

	//printf("%s\n", my_strnconcat(stra,strb, 3));
	printf("%i\n",my_atoi(stra));
}
