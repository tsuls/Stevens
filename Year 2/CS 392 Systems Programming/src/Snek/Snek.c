#include <stdio.h>
#include <stdlib.h>
#include <curses.h>
#include <time.h>
#include "../../include/my.h"

void print_Game( int row, int col)
{	
	addch(' ');
	move(row,col);
    addch('@');
    refresh();
}


int main()
{
	//int segs = 1;
	//int deaths = 0;
    srand(time(NULL)); 
    initscr();
    keypad(stdscr, TRUE);       // enable keyboard 
    raw();
    nonl();                 
    cbreak();               // take input chars one at a time
    noecho();               // do not echo input 
    curs_set(0);            // hide cursor 
    set_escdelay(0);        //eliminate esc delay

	int mRows, mCols;
	getmaxyx(stdscr, mRows, mCols); 
	int row = mRows / 2, col = mCols / 2; //Starting pos for the snake/ head pos
	move(row,col);
    //addch('@');

	int snake[mRows][mCols]; //keep track of the location of the snake
    int pellet[mRows][mCols]; //keep track of pellet loc

	for(int i = 0; i  < mRows; i++)
	{	
		for(int j = 0; j < mCols; j++)
		{
	       	snake[i][j] = 0;
            pellet[i][j] = 0;
		}
	}	

    snake[row][col] = 1;

    //Set up pellets
    int pRow = rand() % (mRows + 1);
    int pCol = rand() % (mCols + 1);
    pellet[pRow][pCol] = 1;

    //Spawn first pellet
    //spawn a pellet at a random location
    move(pRow, pCol);
    addch('.');
    //move back
    move(row,col);
 
	int ch;

	while(1)
	{
        refresh();
        // addch(' ');
        ch = getch();
        //print_Game(row, col);
         snake[row][col] = 0;
        getyx(stdscr, row, col);
         snake[row][col] = 1;




		switch(ch)
        {
            case 27:
            {
                break;
            }
        	case KEY_UP: //Up and down move through the rows
        	{
                if(row >= 0)
                {
                    move(row, col - 1);
                    delch();
                    move(row - 1, col - 1);
                    addch('@');
                }


                if(snake[row][col] == 1 && pellet[row][col] == 1) //collision
                {
                    move(row - 1, col);
                    addch('#');
                    move(row, col);
                }   
                break;
        	}
        	case KEY_DOWN:
        	{  
                if(row < mRows)
                {   
                    move(row, col - 1);
                    delch();
                    move(row + 1, col - 1);
                    addch('@');
                }

                if(snake[row][col] == 1 && pellet[row][col] == 1) //collision
                {
                    move(row + 1, col);
                    addch('#');
                    move(row, col);
                }   
                break;
        	}
        	case KEY_RIGHT: //Right and left move through the columns
        	{
                if(col < mCols)
                {
                    move(row, col - 1);
                    delch();
                    move(row, col + 1);
                    addch('@');
                }

                if(snake[row][col] == 1 && pellet[row][col] == 1) //collision
                {
                    move(row, col - 1);
                    addch('#');
                    move(row, col);
                } 
                break;
        	}
        	case KEY_LEFT:
        	{
                if(col >= 0)
                {
                    move(row, col - 1);
                    delch();
                    move(row, col - 2);
                    addch('@');

                }

                if(snake[row][col] == 1 && pellet[row][col] == 1) //collision
                {
                    //move(row, col + 1);
                    addch('#');
                    pellet[row][col] = 0;
                   //move(row, col);
                } 
                break;
            }
            case ' ':
            {
                break;
            }
            case 13: //Enter
            {
            	break;
            }
		}
	}
endwin();
exit(0);
}
