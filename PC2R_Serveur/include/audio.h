//
// Created by nizar on 27/04/15.
//

#ifndef PC2R_SERVEUR_AUDIO_H
#define PC2R_SERVEUR_AUDIO_H

#include "SDL/SDL.h"
/* Contient un son chargé */
typedef struct
{
    /* Données sonores */
    Uint8 * data;
    /* Longueur du son */
    Uint32 length;
} sound;

/* Chargement d'un son à partir d'un fichier .wav. Le son est converti au format
   donné par la variable hw_spec, qui doit être le format utilisé par le matériel.
   Le son chargé est retourné dans loaded_sound. La fonction retourne 0 en cas de
   succès, une valeur négative en cas d'erreur. */

int sound_load(sound * loaded_sound, char * filename, SDL_AudioSpec * hw_spec);

/* Libère un son précédemment chargé par sound_load */
void sound_free(sound * loaded_sound);

/* Informations sur la lecture d'un son */
typedef struct
{
    /* Le son en train d'être joué */
    sound * played_sound;
    /* Position du lecteur dans le son */
    Uint32 pos;
} soundplayer;

/* Affecte un son à jouer à un soundplayer */
void soundplayer_play(soundplayer * player, sound * toplay);
/* Cesse la lecture du son */
void soundplayer_stop(soundplayer * player);
/* Mise à jour de l'état du player en fonction de sa position */
void soundplayer_update(soundplayer * player);
/* Retourne non-zéro si le lecteur est en train de jouer un son */
Uint8 soundplayer_isplaying(soundplayer * player);



#endif //PC2R_SERVEUR_AUDIO_H
