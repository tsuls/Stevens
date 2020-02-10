#include "../../include/my.h"
#include "../../include/list.h"
#include <sys/wait.h>
#include <signal.h>
#include <unistd.h>
#include <stdio.h>
#include <curses.h>
#include <string.h>

//Need these globals 
pid_t pid;
int sigFlag = 0;
int key;

#define WIDTH 80
#define HEIGHT 24 

//Signal Handling
/*void signalHandler(int signal)
{
	//Kill child proess, CNTRL + C signal recieved
	kill(pid, SIGINT);
	my_char('\n');
}*/
//Gets input and decides what to do with it
char* getInput(char* input, WINDOW* win, char* startMsg)
{	
	int d, j = 0;
	//int i = 0;
	int x = strlen(startMsg), y = 0;
	while(1)
	{
		d = wgetch(win);
			
				mvprintw(6,0,"x is: %i " , x);
				mvprintw(8, 0, "y is: %i " , y);
				//wmove(win, x, y);
				//mvprintw(8, 0, "Mayx is: %i " , getmayx(win));

				//mvprintw(8, 0, "len of input is:  %i " , strlen(input));
		refresh();



		switch(d)
		{
			//if(d ==  KEY_UP)

			case KEY_UP:
			{
				mvprintw(20, 0, "UP");
				wrefresh(win);
				break;
			}
			// if(d == KEY_DOWN)
			case KEY_DOWN:
			{
				mvprintw(15, 0, "Down");
				wrefresh(win);
				break;
			}
			// if( d== KEY_RIGHT)
			case KEY_RIGHT:
			{
				if(x == getmaxx(win) - (strlen(startMsg)))
				{	
					x = 0;
					wmove(win,y++, x);
					wrefresh(win);
				}

				wmove(win,y,x++);
				wrefresh(win);
				break;
			}
			// if(d == KEY_LEFT)
			case KEY_LEFT:
			{	
				if(x == 1 && y > 0)
				{	
					x = getmaxx(win);
					wmove(win,y--, x);
					wrefresh(win);
				}
				if(x > 0)
				{	
					if(y == 0 && x > strlen(startMsg))
					{
						wmove(win,y,x--);
						wrefresh(win);
					}
				}
				break;
			}
			//else if(d == BACKSPACE)
			case 127:
			{
				nocbreak();
				if(j > 1)
				{
					j--;
				}
				
				input[j] = '\0';
				
				if( j > 0 )
				{
					//if((y == 0 && x > strlen(startMsg)))
					{
						mvwdelch(win,y,x);
						if(x > strlen(startMsg))
						{
							x--;
						
						}
					}
					/*else
					{
						mvwdelch(win,y,x);
						if(x > strlen(startMsg))
						{
							x--;
						
						}
					}*/
				}
				
				cbreak();
				wrefresh(win);
				break;
			}
				
			case KEY_ENTER:
			{
				//add_node();
			}

			default:
			{
				input[j] = d;

				if(x == getmaxx(win))// - (strlen(startMsg)))
				{	
					x = 0;
					wmove(win,y++, x);
				}

				mvwaddch(win,y,x++,d);
				wrefresh(win);
				j++;
				break;
			}
	}
}


	return input;
}

//processes
int main()
{
	//(void) signal(SIGINT, finish);      /* arrange interrupts to terminate */

   	initscr();      /* initialize the curses library */
   	cbreak();
   	keypad(stdscr, TRUE);

   	WINDOW* win;
   	win = newwin(HEIGHT, WIDTH, (80 - WIDTH) / 2, (24 - HEIGHT) / 2);


   	char* startMsg = "/minishell:";
	//For interaction
	char* usrinput = (char*) malloc(sizeof(char) * 1024);
	char termMesg[100];

	//Hanlde the Signals
//	signal(SIGINT, SIG_IGN);

	//for signal capturing
	while(1)
	{
		//refresh();
		if(getcwd(termMesg,100) == NULL)
		{
			printw("Could not get the directory");
			return -1;
		}
		else
		{
			printw(termMesg);
			printw(": ");
		}

		usrinput = getInput(usrinput, win, startMsg);
		/*usrinput[readN] = '\0'; // Add a null Ternimator to the input
		
		if (usrinput[readN - 1] == '\n') 
		{
			usrinput[readN - 1] = '\0';
		}*/


		//Exit shell
		printw(usrinput);
		printw("\n");


		if (strlen(usrinput) >= 4 && my_strcmp(my_substr(usrinput,0 ,3) , "exit") == 0) 
		{
			printw("Ending Session...\n");
			endwin();
			return 0;
		} 
		//Ask for help
		if (strlen(usrinput) >= 4 && my_strcmp(my_substr(usrinput,0 ,3) , "help") == 0) 
		{
			printw("cd <x>: Changes the current working directory to <x>.\nexit: Ends the session.\nhelp: Prints a message to show you what commnds the shell can hanlde. But you know this already.\n");
		} 
		
		//Change directory
		 if (strlen(usrinput) >= 2 && my_strcmp(my_substr(usrinput,0 ,1) , "cd") == 0) 
		{	//chdir changes the directory
			printw("The Directory is: ");
			printw((my_substr(usrinput,3 ,strlen(usrinput))));
			if (chdir(my_substr(usrinput,3 ,strlen(usrinput))) == -1) 
			{
				printw("Directory does not exist\n");
			}
		} 
		
		 if (usrinput != NULL) //Didnt just hit enter in shell 
		{
			//executeable handling. Start child process
			pid = fork();
			if (pid < 0) 
			{
				printw("Fork failed\n");
				return -1;
			}

			 if (pid >= 0) //Gucci Maine
			{
				//execvp gets an executable
				char** args = (char**) malloc(sizeof(char) * 1024);
				args = my_str2vect(usrinput);
				
				if(execvp(args[0], &args[0]) == -1 && my_strcmp(usrinput, "") != 0)
				{
					printw("OInvalid Command. Try again.\n");
				};
				refresh();
				//return -1;
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
		if(key == KEY_BACKSPACE)
		{
			my_str("BackSpace pressed");
		}
		else 
		{
			printw("Invalid Command. Try again.\n");
		}
	}
	endwin();
	return 0;
}	

