#include <stdio.h>
#include <sys/types.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>

int start();

 int USR1received = 0;
 int USR2received = 0;
 int SIGINTrecieved = 0;
static int childIs = 0;

void signalHandler(int sig)
{
    if (sig == SIGUSR1)
    {
        USR1received = 1;
    }
    else if(sig == SIGTSTP)
    {
    	start();
    	USR1received = 1;
    }
    else if(sig == SIGINT)
    {
		SIGINTrecieved = 1;
    }
    else if(sig == SIGUSR2)
    {
    	USR2received = 1;
    }
}

void childSH(int sig)
{
	if(sig == SIGTSTP)
	{
		USR1received = 1;
	}
}

void parentHandler(int sig)
{
	if(sig == SIGINT)
	{
		SIGINTrecieved = 0;
	}
}

int start()
{
	//Handle  SIGUSR1 
    signal(SIGUSR1,signalHandler);
    signal(SIGINT, signalHandler);
    signal(SIGUSR2, signalHandler);
    signal(SIGTSTP, childSH);

    pid_t pid2 = fork(); 
    if (pid2 < 0)
        printf("Can't create child process\n");
    else if (pid2==0)
    {
      //  printf("Process2, pid=%d\n",getpid());
    	printf("%s\n","Here" );
    	printf("%s%i\n","USR1received: ",USR1received );
    	USR1received = 0;	
    	printf("%s%i\n","USR1received: ",USR1received );
        while(USR1received == 0)
        {
          //  alarm(10);
			printf("%s%i\n","USR1received: ",USR1received );
			if (USR1received == 1)
			{
				printf("SIGUSR1 received.\n");
				break;
			}
			else
			{
				USR1received = 0;	
			}
        }
   	printf("%s\n","Fuck" );

        while(!USR2received)
        {
        	alarm(10);
			pause();
        }
        USR2received = 0;
        	
        //printf("Sig \n");

        if(SIGINTrecieved == 1)
        {
        	printf("SIGUSR2 received \n");
        }
        SIGINTrecieved = 0;

    }
    else 
    {
    	//SIGINTrecieved = 0;
        kill(pid2,SIGUSR1);
		//SIGINTrecieved = 0;

        while (!SIGINTrecieved)
        {
        	alarm(10);
		pause();
        };

        printf("%s\n","GoodBye!");
        return 0;
    }
   return 0;
}

int main()
{
	//Overwrite SIGTSTP
	signal(SIGTSTP,signalHandler);
	while(1)
	{
		alarm(10);
		pause();
	}

	return 0;
}