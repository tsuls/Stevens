SRCDIR=./
IDIR=../../include/
CFLAGS=-I$(IDIR) -Wall -Werror --pedantic 
SRCS=$(wildcard $(SRCDIR)*.c)
OBJS=$(SRCS:.c=.o)
all: $(OBJS)
	make -C ../my re
	gcc -o ./snek $(CFLAGS) Snek.c -lncurses -I../../include/ -L../../lib/ -lmy
clean:
	rm -f $(OBJS)
	
fclean: clean
	rm -f ../../lib/libmy.a
	rm -f ../../lib/liblist.a

re: fclean all


makeTest:
	gcc -o executable -Wall -Werror --pedantic ../../test/testFileTEMP.c -I../../include/ -L../../lib/ -lmy
	./executable
