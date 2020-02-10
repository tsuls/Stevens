SRCDIR=./
IDIR=../../include/
CFLAGS=-I$(IDIR) -Wall -Werror --pedantic -std=c99
SRCS=$(wildcard $(SRCDIR)*.c)
OBJS=$(SRCS:.c=.o)
all: $(OBJS)
	gcc -c $(CFLAGS) $(SRCS)
	ar -rc libmy.a $(OBJS)
	ranlib libmy.a
	mv libmy.a ../../lib/libmy.a
	
clean:
	rm -f $(OBJS)
	
fclean: clean
	rm -f ../../lib/libmy.a

re: fclean all


makeTest:
	gcc -o executable -Wall -Werror --pedantic ../../test/testFileTEMP.c -I../../include/ -L../../lib/ -lmy
	./executable