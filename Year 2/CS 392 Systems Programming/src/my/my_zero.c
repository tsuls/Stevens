void my_zero(char *toZero, int count)
{
    while(count > 0)
    {
        count--;
        *toZero++ = 0;
    }
}
