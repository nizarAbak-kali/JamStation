//
// Created by nizar on 27/04/15.
//

#ifndef PC2R_SERVEUR_SERVEUR_H
#define PC2R_SERVEUR_SERVEUR_H

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h> /* close */
#include <netdb.h> /* gethostbyname */
#define INVALID_SOCKET -1
#define SOCKET_ERROR -1
#define closesocket(s) close(s)
typedef int SOCKET;
typedef struct sockaddr_in SOCKADDR_IN;
typedef struct sockaddr SOCKADDR;
typedef struct in_addr IN_ADDR;



#define CRLF		"\r\n"
#define PORT	 	2014
#define PORT_AUDIO	 	2015

#define MAX_CLIENTS 	100

#define BUF_SIZE	1024

#include "client.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>





static void app_ui(void);
static void app_sound(void);


static int init_connection(void);
static void end_connection(int sock);
static int read_client(SOCKET sock, char *buffer);
static void write_client(SOCKET sock, const char *buffer);
static void send_message_to_all_clients(Client *clients, Client client, int actual, const char *buffer, char from_server);
static void remove_client(Client *clients, int to_remove, int *actual);
static void clear_clients(Client *clients, int actual);




static void app_sound(void);


static int init_audio_connection(void);
static void end_audio_connection(int sock);
static int read_audio_from_client(SOCKET sock, char *buffer);
static void send_audio_to_all_clients(Client *clients, Client client, int actual, const char *buffer, char from_server);
static void remove_audio_client(Client *clients, int to_remove, int *actual);
static void clear_audio_clients(Client *clients, int actual);



#endif //PC2R_SERVEUR_SERVEUR_H
