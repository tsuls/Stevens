SRCDIR=./
IDIR=../../include/
CFLAGS=-I$(IDIR) -Wall -Werror --pedantic -std=c99
SRCS=$(wildcard $(SRCDIR)*.c)
OBJS=$(SRCS:.c=.o)
all: $(OBJS)
	gcc -c $(CFLAGS) $(SRCS)
	ar -rc liblist.a $(OBJS)
	ranlib liblist.a
	mv liblist.a ../../lib/liblist.a
	
clean:
	rm -f $(OBJS)

fclean: clean
	rm -f ../../lib/liblist.a
	
re: fclean all


makeTest:
	gcc -o executable -Wall -Werror --pedantic ../../test/testFileTEMP.c -I../../include/ -L../../lib/ -lmy
	./executable