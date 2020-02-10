#include <stdio.h>
#include <linux/kernel.h>
#include <sys/syscall.h>
#include <unistd.h>
int main()
{
	char input_1[] = "NoH5qk6qWICFohIuphv2hatXsdQ6HjbYuCB7lY98FR54lzMBlY0qsV7UlSvx3852oaZxkuZ8lqVtGWPbOTTBO28Lx8dcsVuLWWhBhwUNCDtcpdxeL2sxqVt6Yerl0Qb3b6";
	char input_2[] = " ooOOooasddkkdkma";
	char input_3[] = "Hello World!";

	int sys1 = syscall(548, 1, 2);

    int sys2_1 = syscall(549, input_1);
    int sys2_2 = syscall(549, input_2);
    int sys2_3 = syscall(549, input_3);

   	 printf("System call my_syscall returned %i\n", sys1);
  	 printf("System call my_syscall2 returned %i\n", sys2_1);
 	 printf("System call my_syscall2 returned %i\n", sys2_2);
 	 printf("System call my_syscall2 returned %i\n", sys2_3);



    return 0;
}