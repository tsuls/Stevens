#include <stdio.h> 
#include <string.h>  
#include <stdlib.h> 
#include <errno.h> 
#include <unistd.h>   
#include <arpa/inet.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <netinet/in.h> 
#include <sys/time.h> 
#include <netdb.h>
#include "../../include/my.h"
  
void sendSock(int socketfd, char* toServ)
{
    //Send data to the server
        struct timeval tv;
        tv.tv_sec = 20;
        tv.tv_usec = 20;
        int setN = setsockopt(socketfd, SOL_SOCKET, SO_SNDTIMEO,(char *)&tv,sizeof(tv));
        if(setN < 0)
        {
            printf("%s\n", "Time out");
            exit(-1);
        }
        int sendN = send(socketfd, toServ, strlen(toServ), 0);
        if(sendN < 0)
        {
            printf("%s\n","Send failed");
            exit(1);
        }
}


int main(int argc, char *argv[])
{
    char buffer[1024] = {0};
    char* userName = (char*) malloc(sizeof(char) * 1024);
    char toServ[1024] = {0};
    int sock = 0;
    int  valread = 1;

    struct hostent *hostn;

    struct sockaddr_in serv_addr;
    struct in_addr** ip;
    fd_set master;
    fd_set readfd;

    char** args = &argv[1];
    char* hostName = args[0];
    char* ipAdd = (char*) malloc(sizeof(char) * 1024);
    int PORT = my_atoi(args[1]);


    printf("%s%s\n","Hostname: ", hostName);
    printf("%s%i\n","PORT: ", PORT);
    printf("%s\n","Enter a User Name: ");
    fgets(userName, sizeof(userName), stdin);

    //Get the IP of the hostname given
    if((hostn = gethostbyname(hostName)) == NULL)
    {
        printf("%s\n","No hostname found!");
        return -1;
    }

    ip = (struct in_addr **) hostn->h_addr_list;
    strcpy(ipAdd, inet_ntoa(*ip[0]));

    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        printf("%s\n", "Socket creation error \n");
        return -1;
    }
  
    memset(&serv_addr, '0', sizeof(serv_addr));
  
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);
      
    //Convert IPv4 and IPv6 addresses from text to binary form
    if(inet_pton(AF_INET, ipAdd, &serv_addr.sin_addr)<=0) 
    {
        printf("%s\n", "Invalid address or Address not supported \n");
        return -1;
    }
  
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
        printf("%s\n", "Connection Failed \n");
        return -1;
    }
    
    send(sock, userName, strlen(userName), 0 );
    printf("%s\n", "User name sent\n");
    FD_ZERO(&master);
    FD_ZERO(&readfd);

    FD_SET(sock, &master);
    FD_SET(0, &master);


    //my_str("> ");

    while(1)
    {
        readfd = master;
        //Now we wait to communicate with sever
        int activity = select( sock + 1 , &readfd , NULL , NULL , NULL);
        if ((activity < 0) && (errno!=EINTR))  
        {  
            printf("select error");  
        }
        
        if(FD_ISSET(0, &readfd))
        {
            fgets(toServ, 1024, stdin);
            sendSock(sock, toServ);

            if(strcmp(toServ, "/exit\n") == 0)
            {
                printf("%s\n","Disconnected");
                close(sock);
                return 0;
            }
        }
        else if(FD_ISSET(sock, &readfd)) 
        {
            valread = read(sock, buffer, 1024);
            my_str(buffer);

            if(valread ==  0)
            {
                printf("%s\n","Server Disconnected");
                close(sock);
                return 0;
            }

           if(strcmp(buffer, "Invalid Command") == 0)
            {
                my_str("\n");
            }
           // printf("%i\n", strcmp(my_substr(toServ, 0, 2), "/me"));
           // printf("%s%s\n","toServ is: ", toServ);
           /* if(strcmp(my_substr(toServ, 0, 2), "/me") != 0 && strcmp(my_substr(toServ, 0, 4), "/nick") != 0)
            {
              // printf("%s\n","Here");
               my_str("> ");
            }
            if((strcmp(my_substr(toServ, 0, 2), "/me") == 0 || strcmp(my_substr(toServ, 0, 4), "/nick") == 0))// && strcmp(buffer, "Invalid Command") != 0)
            {
                my_str("> ");
            }*/
        }
         /*if((strcmp(my_substr(toServ, 0, 4), "/nick") == 0 && toServ[5] == ' ') || (strcmp(my_substr(toServ, 0, 2), "/me") == 0 && toServ[3] == ' '))
            {
                printf("%s\n","Here");
                my_str("> ");
            }*/

        if(strcmp(toServ, "/exit\n") == 0)
        {
            my_str("Disconnected");
            break;
        }
        my_zero(buffer, 1024);
    }
    return 0;
}

