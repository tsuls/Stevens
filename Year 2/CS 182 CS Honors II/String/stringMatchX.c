#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>

/* Exercise:
 - implement the function below, using loop(s) and a brute force algorithm.
 - briefly discuss the time complexity of your implementation:
 + what is a good choice of basic operation to count
 + what is a good measure of the input size
 + do some inputs take more time than others?
 + if so, what inputs are worst case?
 + what is the (worst case) time complexity?
 */


/* String match.
 
 Assume text and pattern are null-terminated strings.
 Return the index of the beginning position of the first
 place in text where pattern matches exactly.  If pattern
 does not match anywhere, return -1.
 For examples, see the asserts in main(), which should all succeed.
 
 Hints:
 - Use array notation to access characters in the strings,
 e.g., text[0] is the first character of the text (or possibly null
 if it's the empty string).
 - To determine the range of possible indices, one option is to
 use strlen() from the standard library.  It returns the length not
 counting the \0 terminator.
 Another option is to check for null (in both text and pattern) as part
 of the matching process.
 - You don't need to use the strcmp function or other library functions.
 */

/*char* substring(char* strng, int num)
{
    char sbstrng[strlen(strng) - num];
    memcpy(sbstrng, &strng[num], strlen(sbstrng));
    return *sbstrng;
}*/

int match(char* text, char* pattern)
{
    int textlen = strlen(text);
    int patlen = strlen(pattern);
    int x ,i;
    int y = 0 , j;
    int z;
    
    if (patlen > textlen)
    {
        return -1;
    }
    
    for (i = 0; i <= textlen; i++)
    {
        z = x = i;
        
        for (j = 0; j < patlen; j++)
        {
            y = j;
            if (pattern[j] == text[x])
            {
                x++;
            }
            else
            {
                break;
            }
        }
        if (j == patlen)
        {
            return z;
        }
    }
    
    return -1;
}
int main() {
    assert(match("found", "found") == 0);
    assert(match("found", "lost") == -1);
    assert(match("found", "fount") == -1);
    assert(match("unfound", "found") == 2);
    assert(match("found", "") == 0);
    assert(match("there is more to life than its speed", "Ghandi") == -1);
    assert(match("nobody noticed him", "not") == 7);
    
    printf("tests successful\n");
}


