#include <stdio.h>
#include <stdlib.h>
#include <curses.h>

void print_Game( int row, int col)
{	
	addchr('@');
	move(row,col);
}


int main()
{
	int segs = 1;
	int deaths = 0;

	int mRows, mCols;
	getmaxyx(stdscr, mRows, mCols); 
	int row = mRows / 2, col = mCols / 2; //Starting pos for the snake/ head pos
	move(row,col);


	int snake[mRows][mCols]; //keep track of the location of the snake

	for(int i = 0; i  < mRows; i++)
	{	
		for(int j = 0; j < mCols; j++)
		{
			snake[i][j] = 0;
		}
	}	

	int ch;

	initscr();
    keypad(stdscr, TRUE);       // enable keyboard 
    raw();
    nonl();                 
    cbreak();               // take input chars one at a time
    noecho();               // do not echo input 
    curs_set(0);            // hide cursor 
    set_escdelay(0);        //eliminate esc delay

	while(1)
	{

		ch = getch();

		print_Game(row, col);

		switch(ch)
        {
            case 27:
            {
           
            }
        	case KEY_UP: //Up and down move through the rows
        	{

        	}
        	case KEY_DOWN:
        	{
        
        	}
        	case KEY_RIGHT: //Right and left move through the columns
        	{

        	}
        	case KEY_LEFT:
        	{
     
            }
            case ' ':
            {
            
            }
            case 13: //Enter
            {
            	
            }
		}
	}
endwin();
exit(0);
}