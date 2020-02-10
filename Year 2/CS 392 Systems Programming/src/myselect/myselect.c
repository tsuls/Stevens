#include <stdlib.h>
#include <curses.h>
#include <signal.h>
#include <string.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>
#include "../../include/my.h"

#define SELECTED 1
#define DESELECTED 0

//print the menu and handle it
//Underline items, highlight to select
void print_menu(int argc, char* arg[], int wrapV, int* choices, int rows, int cols, int colLen)
{
	int r = 0;
	int c = 0;
    int i = 0;
    int j = 0;
    clear();

	for(i = 1; i < argc; i++)
	{
		if(choices[i] == SELECTED) //the item is selected
		{
			attron(A_STANDOUT); //Turn on the highlight
		}
		else //Turn off the highlight
		{	
			attroff(A_STANDOUT);
		}
		//the item should be underlined, the cursor is now on it
		if(i == wrapV)
		{
			//Underline that guy
			attron(A_UNDERLINE);
		}
		else //No longer need underline
		{
			attroff(A_UNDERLINE);
		}

		//Actually print out the options lol
		for(j = 0; j < colLen - 1 && arg[i][j] != '\0' && (c + colLen < cols); j++)
        {
            addch(arg[i][j]);
        }

        r++;
        if(r == rows)
        {
        	r = 0;
        	c += colLen;
        	if(c + colLen >= cols)
        	{
                clear();
        		move(0,0);
        		attroff(A_STANDOUT);
        		attroff(A_UNDERLINE);
        		addstr("Please enlarge the window");
        	}
        }
        move(r,c);
	}
}

int main(int argc, char *arg[])
{
	//argc is the number of arguments given
	int wrapV = 1; //keep track of where you are
	int wrapVs = 0;
	int ch = 0 ;
	int rows = 0, cols  = 0;

    int colLen = 0;

	if (argc <= 1)
	{
		endwin();
        printf("%s\n","You must specify items to select!");
        exit(0);
	}

    for(int i = 0; i < argc; i++)
    {
        if(strlen(arg[i]) > colLen)
        {
            colLen = strlen(arg[i]);
        }  
    }

    colLen += 2;

	int* choices  = (int*) malloc(sizeof(int) * argc); //size of the given input
	//intialize choices 
	for(int i = 0; i < argc; i++)
	{
		choices[i] = 0;
	}	

	signal(SIGINT, SIG_IGN); //Ignore cntrl C

    newterm(NULL, stderr, stdin);     //initialize the curses screen with less ability 
    keypad(stdscr, TRUE);       // enable keyboard 
    raw();
    nonl();                 
    cbreak();               // take input chars one at a time
    noecho();               // do not echo input 
    curs_set(0);            // hide cursor 
    set_escdelay(0);        //eliminate esc delay


	//Do stuff
	while(1)
	{
		//get the current max number of rows and columns, in case we resize the window
		getmaxyx(stdscr, rows, cols); 
		//print the menu
        print_menu(argc, arg, wrapV, choices, rows, cols, colLen); 

        //keyboard input
        ch = getch();

        switch(ch)
        {
            case 27:
            {
                endwin();
                exit(0);
            }
        	case KEY_UP: //Up and down move through the rows
        	{
        		wrapV--; //minus since we start from 0 in the top left corner
        		if(wrapV < 1)
        		{
        			//the cursor went above the list, Wrap down!!
        			wrapV = argc - 1; //to the bottom of the list
        		}
                break;
        	}
        	case KEY_DOWN:
        	{
        		wrapV++; 
        		if(wrapV >= argc)
        		{
        			//the cursor went under the list, wrap up!!
        			wrapV = 1; //reset the wrap variable
        		}
                break;
        	}
        	case KEY_RIGHT: //Right and left move through the columns
        	{
        		wrapVs = wrapV;
        		wrapV += rows; // move right.

        		if(wrapV >= argc) //Reached the end of output
        		{
					wrapV = wrapVs; //move back
        		}
                break;
        	}
        	case KEY_LEFT:
        	{
        		wrapVs = wrapV;
        		wrapV -= rows; // move left

        		if(wrapV < 1) //Reached left end
        		{
        			wrapV = wrapVs; //reset  variable
        		}
                break;
            }
            case ' ':
            {
            	if(choices[wrapV] == DESELECTED) //The choice is not selected
            	{
            		//select the choice
            		choices[wrapV] = SELECTED;
            		wrapV++;
            		if(wrapV >= argc)
            		{
            			wrapV = 1;
            		}
            	}
            	else //the choice is selected
            	{
            		//Deselect
            		choices[wrapV] = DESELECTED;
            	}
                break;
            }
            case 13: //Enter
            {
            	//close window 
            	endwin();
            	//Print the selected items only
            	for(int i = 0; i < argc; i++)
            	{
            		if(choices[i] == SELECTED)
            		{
                        write(1, arg[i], strlen(arg[i]));
                       // my_str(arg[i]);
            			my_char(' '); //whitespace
            		}
            	}
                my_char('\n');
                exit(0);
            }
		}
	}
endwin();
exit(0);
}
