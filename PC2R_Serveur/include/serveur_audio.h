//
// Created by nizar on 27/04/15.
//

#ifndef PC2R_SERVEUR_SERVEUR_AUDIO_H
#define PC2R_SERVEUR_SERVEUR_AUDIO_H

#define PORT	 	2015
#define MAX_CLIENTS 	100

#define BUF_SIZE	1024

#include "client.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include "sound_effects.h"


static void app_sound(void);


static int init_audio_connection(void);
static void end_audio_connection(int sock);
static int read_audio_from_client(SOCKET sock, char *buffer);
static void send_audio_to_all_clients(Client *clients, Client client, int actual, const char *buffer, char from_server);
static void remove_client(Client *clients, int to_remove, int *actual);
static void clear_clients(Client *clients, int actual);


#endif //PC2R_SERVEUR_SERVEUR_AUDIO_H
