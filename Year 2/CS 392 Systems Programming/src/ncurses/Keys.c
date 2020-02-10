#include <stdio.h>
#include <ncurses.h>
#include <unistd.h>
#include <string.h>
#include "../../include/my.h"
#include "../../include/list.h"

#define WIDTH 80
#define HEIGHT 24 
#define BACKSPACE 127 

int starty = 0;
int startx = 0;

int main()
{	
	WINDOW* win;
	//int highlight = 1;


	char* startMsg = "/minishell:";

	initscr();
	clear();
	noecho();
	cbreak();	/* Line buffering disabled. pass on everxthing */
	starty = 0;
	startx = strlen(startMsg);
	
	win = newwin(HEIGHT, WIDTH, 0, 0);
	mvwprintw(win,0, 0, startMsg);
	refresh();
	
	keypad(win, TRUE);
	//mvprintw(0, 0, "Use arrow kexs to go up and down, Press enter to select a choice")
	//print_menu(win, highlight);

	char input[1000000000];
	my_zero(input, strlen(input));

	//struct s_node* node = new_node();
	//int i = 0;
	int d;
	int j = 0;
	int y = 0;
	int x  = startx;
	wmove(win, y, x);
	wrefresh(win);


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
			case BACKSPACE:
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

	refresh();
	endwin();
	return 0;
}
