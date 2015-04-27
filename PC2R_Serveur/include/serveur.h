//
// Created by nizar on 17/04/15.
//

#ifndef PC2R_SERVEUR_SERVEUR_H
#define PC2R_SERVEUR_SERVEUR_H

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include "../libs/ft_v1.0/include/fthread.h"
#include <string.h>
#include <errno.h>

#define INVALID_SOCKET -1
#define SOCKET_ERROR -1
#define closesocket(s) close(s)
#define CRLF		"\r\n"
#define PORT	 	2013
#define MAX_CLIENTS 100
#define BUF_SIZE	1024

typedef int SOCKET;
typedef struct sockaddr_in SOCKADDR_IN;
typedef struct sockaddr SOCKADDR ;
typedef struct in_addr IN_ADDR;

#include "client.h"

struct args{
     int argc ;
     char ** argv;
};

typedef struct args* cmd;

/* Fonction d'interpretation des message des clients*/
char** split_commande(char* cmd);
void init_commande(cmd msg);
void interprete_commande_user(char *cmd);



/* Fonction d'interneterie*/
static void app_ui(void);
static void app_sound(void);

static int init_connection();
static void end_connection(int sock);
static int read_client(SOCKET sock, char *buffer);
static void write_client(SOCKET sock, const char *buffer);
static void send_message_to_all_clients(Client *clients, Client client, int actual, const char *buffer, char from_server);
static void remove_client(Client *clients, int to_remove, int *actual);
static void clear_clients(Client *clients, int actual);


#endif //PC2R_SERVEUR_SERVEUR_H
