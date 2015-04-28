//
// Created by nizar on 27/04/15.
//

#include "../include/serveur.h"
#include "SDL/SDL.h"

#define DEBUG 1





static void app_sound(void)
{
    SOCKET sock = init_audio_connection();
    char buffer[BUF_SIZE];
    /* the index for the array */
    int actual = 0;
    int max = sock;
    /* an array for all clients */
    Client clients[MAX_CLIENTS];

    fd_set rdfs;

    while(1)
    {
        int i = 0;
        FD_ZERO(&rdfs);

        /* add STDIN_FILENO */
        FD_SET(STDIN_FILENO, &rdfs);

        /* add the connection socket */
        FD_SET(sock, &rdfs);

        /* add socket of each client */
        for(i = 0; i < actual; i++)
        {
            FD_SET(clients[i].sock, &rdfs);
        }

        if(select(max + 1, &rdfs, NULL, NULL, NULL) == -1)
        {
            perror("select()");
            exit(errno);
        }

        /* something from standard input : i.e keyboard */
        if(FD_ISSET(STDIN_FILENO, &rdfs))
        {
            /* stop process when type on keyboard */
            break;
        }
        else if(FD_ISSET(sock, &rdfs))
        {
            /* new client */
            SOCKADDR_IN csin = { 0 };
            size_t sinsize = sizeof csin;
            int csock = accept(sock, (SOCKADDR *)&csin, &sinsize);
            if (DEBUG) printf("Client ajouté " );
            if(csock == SOCKET_ERROR)
            {
                perror("accept()");
                continue;
            }

            /* Apres connections  */
            if(read_audio_from_client(csock, buffer) == -1)
            {
                /* disconnected */
                continue;
            }

            /* what is the new maximum fd ? */
            max = csock > max ? csock : max;

            FD_SET(csock, &rdfs);

            Client c = { csock };
            strncpy(c.name, buffer, BUF_SIZE - 1);
            clients[actual] = c;
            actual++;
        }
        else
        {
            int i = 0;
            for(i = 0; i < actual; i++)
            {
                /* a client is talking */
                if(FD_ISSET(clients[i].sock, &rdfs))
                {
                    Client client = clients[i];
                    int c = read_audio_from_client(clients[i].sock, buffer);
                    /* client disconnected */
                    if(c == 0)
                    {
                        closesocket(clients[i].sock);
                        remove_audio_client(clients, i, &actual);
                        strncpy(buffer, client.name, BUF_SIZE - 1);
                        strncat(buffer, " disconnected !", BUF_SIZE - strlen(buffer) - 1);
                        send_audio_to_all_clients(clients, client, actual, buffer, 1);
                    }
                    else
                    {
                        send_audio_to_all_clients(clients, client, actual, buffer, 0);
                    }
                    break;
                }
            }
        }
    }

    clear_audio_clients(clients, actual);
    end_audio_connection(sock);
}


static void clear_audio_clients(Client *clients, int actual)
{
    int i = 0;
    for(i = 0; i < actual; i++)
    {
        closesocket(clients[i].sock);
    }
}

static void remove_audio_client(Client *clients, int to_remove, int *actual)
{
    /* we remove the client in the array */
    memmove(clients + to_remove, clients + to_remove + 1, (*actual - to_remove - 1) * sizeof(Client));
    /* number client - 1 */
    (*actual)--;
}

static void send_audio_to_all_clients(Client *clients, Client sender, int actual, const char *buffer, char from_server)
{
    int i = 0;
    char message[BUF_SIZE];
    message[0] = 0;
    for(i = 0; i < actual; i++)
    {
        /* we don't send message to the sender */
        if(sender.sock != clients[i].sock)
        {
            if(from_server == 0)
            {
                strncpy(message, sender.name, BUF_SIZE - 1);
                strncat(message, " : ", sizeof message - strlen(message) - 1);
            }
            strncat(message, buffer, sizeof message - strlen(message) - 1);
           //FIXME sa sert à rien
           // write_client(clients[i].sock, message);
        }
    }
}


static int init_audio_connection(void)
{
    SOCKET sock_audio = socket(AF_INET, SOCK_STREAM, 0);
    SOCKADDR_IN sin_audio = { 0 };

    if(sock_audio == INVALID_SOCKET)
    {
        perror("socket_audio()");
        exit(errno);
    }

    sin_audio.sin_addr.s_addr = htonl(INADDR_ANY);
    sin_audio.sin_port = htons(PORT_AUDIO);
    sin_audio.sin_family = AF_INET;

    if(bind(sock_audio,(SOCKADDR *) &sin_audio, sizeof sin_audio) == SOCKET_ERROR)
    {
        perror("bind_audio()");
        exit(errno);
    }

    if(listen(sock_audio, MAX_CLIENTS) == SOCKET_ERROR)
    {
        perror("listen()");
        exit(errno);
    }

    return sock_audio;
}

static void end_audio_connection(int sock)
{
    closesocket(sock);
}

static int read_audio_from_client(SOCKET sock, char *buffer)
{
    int n = 0;

    if((n = (int) recv(sock, buffer, BUF_SIZE - 1, 0)) < 0)
    {
        perror("recv()");
        /* if recv error we disonnect the client */
        n = 0;
    }
    if(DEBUG)printf(" message du client %d = %s",sock, buffer);
    buffer[n] = 0;

    return n;
}

