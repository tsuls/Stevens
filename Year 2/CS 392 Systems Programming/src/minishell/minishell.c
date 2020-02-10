#include "../../include/my.h"
#include <sys/wait.h>
#include <signal.h>
#include <unistd.h>

//Need these globals 
pid_t pid;
int sigFlag = 0;

//Signal Handling
/*void signalHandler(int signal)
{
	//Kill child proess, CNTRL + C signal recieved
	kill(pid, SIGINT);
	my_char('\n');
}*/

//processes
int main()
{
	//For interaction
	char usrinput[100];
	char termMesg[100];

	int readN = 0;
	char** arg;
	//int i = 0; 
	//Hanlde the Signals
	signal(SIGINT, SIG_IGN);

	//for signal capturing
	while(1)
	{
		if(getcwd(termMesg,100) == NULL)
		{
			my_str("Could not get the directory");
			return -1;
		}
		else
		{
			my_str(termMesg);
			my_str(": ");
		}

		readN = read(0, usrinput, 100); //Get the read from the console
		
		if (readN < 0) //Error checking
		{
			my_str("Read Failed\n");
			return -1;
		}
		
		usrinput[readN] = '\0'; // Add a null Ternimator to the input
		
		if (usrinput[readN - 1] == '\n') 
		{
			usrinput[readN - 1] = '\0';
		}

		//Now we take the user input and find out what to do with it
		arg = my_str2vect(usrinput);

		//Exit shell
		if (my_strcmp(arg[0], "exit") == 0) 
		{
			my_str("Ending Session...\n");
			return 0;
		} 

		//Ask for help
		else if (my_strcmp(arg[0], "help") == 0) 
		{
			my_str("cd <x>: Changes the current working directory to <x>.\nexit: Ends the session.\nhelp: Prints a message to show you what commnds the shell can hanlde. But you know this already.\n");
		} 

		//Change directory
		else if (my_strcmp(arg[0], "cd") == 0) 
		{	//chdir changes the directory
			if (chdir(arg[1]) == -1) 
			{
				my_str("Directory does not exist\n");
			}
		} 
		//./progName will launch an executable... so we look for . followed by a /
		else if (arg[0] != NULL) //Didnt just hit enter in shell 
		{
			//executeable handling. Start child process
			pid = fork();
			if (pid < 0) 
			{
				my_str("Fork failed\n");
				return -1;
			}
			else if (pid == 0) //Gucci Maine
			{
				//execv gets an executable
				my_str("The command is: ");
				printf("%s\n", arg[0]);
				my_str("\nnext is: ");
				printf("%s\n",arg[1]);
				if(execvp(arg[0], &arg[0]) == -1 && my_strcmp(arg[0], "") != 0)
				{
					my_str("Invalid Command. Try again.\n");
				};
				//my_str("Bad command\n");
				return - 1;
			}
			else //End process
			{
				sigFlag = 1;
				//Wait to kill
				wait(NULL);
				//restart
				sigFlag = 0;
			}
		}
		else 
		{
			my_str("Invalid Command. Try again.\n");
		}
	}
	return 1;
}	





