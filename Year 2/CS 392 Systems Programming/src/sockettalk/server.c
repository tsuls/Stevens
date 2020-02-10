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
#include "../../include/my.h"
    
#define TRUE   1 
#define FALSE  0 
#define PORT 8080 
#define max_clients 1000

void printUserNames(char** un)
{
    for(int i = 0; strcmp(un[i], "") != 0; i++)
    {
        printf("%i%s\n",i, un[i]);
    }
}

char* getBuff(char buffs[max_clients][1024], int sd, int* client_socket, char* userNames[max_clients])
{
    for(int k = 0; k < max_clients; k++)
    {
        if(client_socket[k] == sd)
        {
            return buffs[k];
        }
    }
    return NULL;
}

int sendToAllClients(char* usr, char* msg, int* cSockets, char** userNames)
{
    char* toClients = my_strconcat(usr, ":  ");
    toClients = my_strconcat(toClients, msg);
    printf("%s%s\n","toClients is noww: ", toClients);

    for(int i = 0; i < max_clients; i++)
    {
        if(cSockets[i] != 0)
        {
           if(send(cSockets[i], toClients, strlen(toClients), 0) < 0)
           {
                printf("%s%i\n","send error to: ", cSockets[i]);
                return -1;
           }
       }
    }

    return 0;
}

int sendToOtherClients(char* usr, char* msg, int* cSockets, char** userNames)
{
    char* toClients = my_strconcat(usr, "  ");
    toClients = my_strconcat(toClients, msg);

    printf("%s%s\n","toClients is noww: ", toClients);

    for(int i = 0; i < max_clients; i++)
    {
       if(cSockets[i] != 0)
       {
            if(strcmp(userNames[i], usr) != 0)
            {
                if(send(cSockets[i], toClients, strlen(toClients), 0) < 0)
                {
                   printf("%s%i\n","send error to: ", cSockets[i]);
                    return -1;
                }
            }
        }
    }
    return 0;
}


int main(int argc , char *argv[])  
{  
    int opt = TRUE;  
    int master_socket , addrlen , new_socket , client_socket[1000] , 
          activity, i , valread , sd;  
    int max_sd;  
    struct sockaddr_in address;  

    int j = 0;
        
    char buffer[1024] = {0};  //data buffer of 1K 
    char unBuffs[max_clients][1024];
        
    //set of socket descriptors 
    fd_set readfds;  
        
    //a message 
    //char* message = "Hello From Server!\r\n";  

    //Username Storage
    char* userNames[max_clients];
    
    //initialize all client_socket[] to 0 so not checked and
    //all usernames to "" for empty.
    for (i = 0; i < max_clients; i++)  
    {  
        client_socket[i] = 0; 
        userNames[i] = ""; 
    }  
        
    //create a master socket 
    if((master_socket = socket(AF_INET , SOCK_STREAM , 0)) == 0)  
    {  
        perror("socket failed");  
        exit(EXIT_FAILURE);  
    }  
    
    //set master socket to allow multiple connections , 
    //this is just a good habit, it will work without this 
    if(setsockopt(master_socket, SOL_SOCKET, SO_REUSEADDR, (char *)&opt, sizeof(opt)) < 0 )  
    {  
        perror("setsockopt");  
        exit(EXIT_FAILURE);  
    }  
    
    //type of socket created 
    address.sin_family = AF_INET;  
    address.sin_addr.s_addr = INADDR_ANY;  
    address.sin_port = htons( PORT );  
        
    //bind the socket to localhost port 8080
    if (bind(master_socket, (struct sockaddr *)&address, sizeof(address))<0)  
    {  
        perror("bind failed");  
        exit(EXIT_FAILURE);  
    }  
    printf("Listener on port %d \n", PORT);  
        
    //try to specify maximum of 3 pending connections for the master socket 
    if (listen(master_socket, 3) < 0)  
    {  
        perror("listen");  
        exit(EXIT_FAILURE);  
    }  
        
    //accept the incoming connection 
    addrlen = sizeof(address);  
    puts("Waiting for connections ...");  
        
    while(TRUE)  
    {  
        //clear the socket set 
        FD_ZERO(&readfds);  
    
        //add master socket to set 
        FD_SET(master_socket, &readfds);  
        max_sd = master_socket;  
            
        //add child sockets to set 
        for ( i = 0 ; i < max_clients ; i++)  
        {  
            //socket descriptor 
            sd = client_socket[i];  
                
            //if valid socket descriptor then add to read list 
            if(sd > 0)  
                FD_SET( sd , &readfds);  
                
            //highest file descriptor number, need it for the select function 
            if(sd > max_sd)  
                max_sd = sd;  
        }  
    
        //wait for an activity on one of the sockets , timeout is NULL , 
        //so wait indefinitely 
        activity = select( max_sd + 1 , &readfds , NULL , NULL , NULL);  
      
        if ((activity < 0) && (errno!=EINTR))  
        {  
            printf("select error");  
        }  
            
        //If something happened on the master socket , 
        //then its an incoming connection 
        if (FD_ISSET(master_socket, &readfds))  
        {  
            if ((new_socket = accept(master_socket, 
                    (struct sockaddr *)&address, (socklen_t*)&addrlen))<0)  
            {  
                perror("accept");  
                exit(EXIT_FAILURE);  
            } 
            my_zero(buffer, 1024); 
            
            //inform user of socket number - used in send and receive commands 
            printf("New connection , socket fd is %d , ip is : %s , port : %d \n" , new_socket , inet_ntoa(address.sin_addr) , ntohs(address.sin_port));  
          
            //send new connection greeting message 
                
            puts("Welcome message sent successfully");  

            //Need to recieve the userName
            int unRead = read(new_socket, unBuffs[j], 1024);
            if(unRead < 0)
            {
                printf("%s\n","User Name read error");
                return -1;
            }

            printf("%s%s\n","The Client username is: ", unBuffs[j]);                
            //add new socket to array of sockets 
            for (i = 0; i < max_clients; i++)  
            {  
                //if position is empty 
                if( client_socket[i] == 0 && strcmp(userNames[i] , "") == 0)  
                {  
                    client_socket[i] = new_socket;
                    userNames[i] = unBuffs[j]; 
                    printf("Adding to list of sockets as %d\n" , i);
                    printf("Adding to list of UserNames as %s\n", unBuffs[j]);
                   //my_zero(unBuff, 1024); 
                        
                    break;  
                }  
            } 
            j++; 
        }  

        //else its some IO operation on some other socket
        for (i = 0; i < max_clients; i++)  
        {  
            sd = client_socket[i];  
                
            if (FD_ISSET( sd , &readfds))  
            {  
                //Check if it was for closing , and also read the 
                //incoming message 

                if ((valread = read( sd , buffer, 1024)) == 0)  
                {  
                    //Somebody disconnected , get his details and print 
                    getpeername(sd , (struct sockaddr*)&address , \
                        (socklen_t*)&addrlen);  
                    printf("Client disconnected , ip %s , port %d \n", inet_ntoa(address.sin_addr) , ntohs(address.sin_port));  
                        
                    //Close the socket and mark as 0 in list for reuse 
                    close( sd );  
                    client_socket[i] = 0;  
                }  
                    
                //Echo back the message that came in 
                else
                {  
                    //set the string terminating NULL byte on the end 
                    //of the data read 
                    buffer[valread] = '\0';
                    printf("%s%s\n","Message recieved: ", buffer);  
                    printf("%s%s\n","From: ", userNames[i]);

                    //Time to handle those SPECIAL ASS MESSAGES
                    //nick
                    if(strcmp(my_substr(buffer, 0, 4), "/nick") == 0 && buffer[5] == ' ')
                    {
                        char* newName = my_substr(buffer, 6, strlen(buffer) - 1);
                        printf("%s%s\n","The new user name is:",newName);
                        userNames[i] = newName;

                        for(int i = 0; strcmp(userNames[i], "") == 0; i++)
                        {
                            printf("%s%s\n", "Username is: ", userNames[i]);
                        }
                    }//exit
                    else if(strcmp(my_substr(buffer, 0, 4), "/exit") == 0)
                    {   
                        send(sd,"Disconnected", strlen("Disconnected"),0);
                        shutdown(sd, 2);
                    }//me
                    else if(strcmp(my_substr(buffer, 0, 2), "/me") == 0 && buffer[3] == ' ')
                    {
                        if(sendToOtherClients(userNames[i], my_substr(buffer, 4, strlen(buffer)),client_socket, userNames) < 0)
                        {
                            return -1;
                        }
                    }//other / bs
                    else if(buffer[0] == '/')
                    {   
                        printf("%s\n","Invalid Command Recived");
                        send(sd, "Invalid Command", strlen("Invalid Command"),0);
                    }
                    else//normal message
                    {
                        printf("%s\n","Here, normal message" );
                        if(sendToAllClients(userNames[i], buffer, client_socket, userNames) < 0)
                        {
                            return -1;
                        }
                    }
                }  
            }  
        }  
    }  
        
    return 0;  
}  
