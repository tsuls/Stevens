#include "../../include/my.h"
#include <sys/wait.h>

int main(int argc, char* arg[])
{
	char* info = my_vect2str(&arg[1]);
	if(info == NULL)
	{
		perror("There cannot be zero arguments");
		exit(1);
	}

	int fd[2]; //First Pipe for Parent Process
	int fd2[2]; //Second Pipe for Child Process
	int fd3[2]; //Third pipe for Grandchild Process

	pid_t p;

	if(pipe(fd) == -1)
	{
		my_str("Pipe 1 Failed");
		return 1;
	}
	if(pipe(fd2) == -1)
	{
		my_str("Pipe 2 failed");
		return 1;
	}

	p = fork();

	if(p < 0)
	{
		my_str("Fork failed");
		return 1;
	}
	//Parent process
	else if(p > 0)
	{
		my_str("Parent: ");
		my_str(info);
		my_char('\n');
		//Close Reading end of first pipe
		close(fd[0]);
		//Write the info string
		write(fd[1], info, my_strlen(info) + 1);
		//Close writing end of first pipe
		close(fd[1]);

		/*my_str("Parent: ");
		my_str(info);
		my_char('\n');*/
		
		//Wait for the child to send a string
		wait(NULL);

		//Close writing end of second pipe
		close(fd2[1]);

		read(fd2[0], info, 100);

		close(fd2[0]);
	}
	else
	{
		close(fd[1]);
		char buf[100];
		
		read(fd[0],buf,100);

		close(fd[0]);

		//Create Granchild.

		pid_t p2;

		if(pipe(fd3) == -1)
		{
			my_str("Pipe 3 failed");
			return 1;
		}

		p2 = fork();


		if(p2 < 0)
		{
			my_str("Fork2 failed");
			return 1;
		}
		else if(p2 > 0)
		{
			my_str("Child: ");
			my_str(buf);
			my_char('\n');
			//Close Reading end of first pipe
			close(fd2[0]);
			//Write the info string
			write(fd2[1], buf, my_strlen(buf) + 1);
			//Close writing end of first pipe
			close(fd2[1]);
			
		/*	my_str("Child: ");
			my_str(buf);
			my_char('\n');*/

			//Wait for the child to send a string
			wait(NULL);

			//Close writing end of second pipe
			close(fd3[1]);

			//read(fd3[0], buf, 100);

			close(fd3[0]);
		}
		else
		{
			char buf2[100];
			close(fd2[1]);
			
			read(fd2[0],buf2,100);

			close(fd2[0]);

			my_str("Granchild: ");
			my_str(buf2);
			my_char('\n');
		}

	}
	close(fd[0]);
	close(fd[1]);
	return 0;
}
