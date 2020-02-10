#include <stdio.h>
#include <ncurses.h>

WINDOW *create_newwin(int height, int width, int starty, int startx)
{
  WINDOW *local_win = newwin(height, width, starty, startx);
  box(local_win, 0, 0);
  wrefresh(local_win);

  return (local_win);
}

int main(int argc, char **argv)
{
  WINDOW *my_win;

  int ch;
  int x = 2;
  int y = 2;

  initscr();
  cbreak();
  keypad(stdscr, TRUE);
  noecho();

  printw("Press q to exit");
  refresh();
  my_win = create_newwin(10, 20, y, x);
  wmove(my_win, y, x);
  wrefresh(my_win);

  while((ch = getch()) != 'q')
    {
        switch (ch)
        {
          case KEY_LEFT:
            x--;
            break;
          case KEY_RIGHT:
            x++;
            break;
          case KEY_UP:
            y--;
            break;
          case KEY_DOWN:
            y++;
            break;
        }

        wmove(my_win, y, x);
        wrefresh(my_win);
    }

  endwin();
  return 0;
}
