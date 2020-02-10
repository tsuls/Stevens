#include "../../include/my.h" 

 int my_atoi(char* str) //48 -> 57 is nums 65 -> 90 Captials 97 -> 122 Lc
    {
        int negCounter = 0;
        int NumberFound = -1;
        char* numStr = (char*) malloc(sizeof(char));
        int numStrCouter = 0;

        for(int i = 0; str[i] != '\0'; i++)
        {
            int val = (int) str[i];
            //printf("%s%i\n","Val is: ", val );

            if(val >= 48 && val <= 57) //Number
            {   
                NumberFound = 0;
                numStr[numStrCouter] = (char) val;
            //  printf("%s%c\n","Num str is: ", numStr[numStrCouter] );
                numStrCouter ++;
            }

            if(val == 45)
            {
            //  printf("%s\n","Found a Negative, increasing count");
                negCounter++;
            }   

            if( NumberFound == 0 && ((val >= 65 && val <= 90) || (val >= 97 && val <= 122))) //Lette
            {
                //Found number. This is letter. End
            //  printf("%s\n","Found a Letter After a number. Ending Search");
                break;
            }
        }

        //printf("%s\n","Converting our string to an int");
        int num = 0;
        for(int i = 0; i < numStrCouter; i++)
        {
            num = num * 10 + (numStr[i] - '0');
        }
        
        //printf("%s%i\n","Our int is: ", num);
       // printf("%s%i\n","The number of Negatives is: ", negCounter);


        if(NumberFound == -1)
        {
            return 0;
        }
        else if(negCounter % 2 != 0)
        {

            return -1 *  num;
        }
        else
        {
            return num;
        }
    }
