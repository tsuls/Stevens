#include <fcntl.h> 
#include <stdio.h> 
#include <string.h>
#include <unistd.h> 
#include <string.h>
#include <stdlib.h>

char* concat(const char *s1, const char *s2);
int main()
{
	char buffer[3];
	char finalBuf[100];
	int bufTrack = 0;
	char* solution = "";
	char* code = "";
	int key = 5;
	int fd = open("./encrypted.txt", O_RDONLY);
  	//printf("%c\n", char(65) );
		
	int bytesRead;
	do
	{
		bytesRead = (int) read(fd, buffer, 3);
        printf("%s%i\n","Bytes Read: ", bytesRead);
        printf("%s%i\n","Key ", key);
		for(int i = 0; i < bytesRead; i++)
		{
			printf("%c", buffer[i]);

			int val = ((int) buffer[i] - key);

			char newChar = (char) ((int) buffer[i] - key);
			printf("%s%c\n","New Char: ", newChar );
		}
		key = key + 2;
		printf("\n");
	}while(bytesRead > 0);

//printf("%s%s\n", "Solution is: ",solution );

}
