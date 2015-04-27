//
// Created by nizar on 27/04/15.
//

#include "../include/audio.h"
#include <stdlib.h>
#include <string.h>
#include "../include/sound_effects.h"

int sound_load(sound * loaded_sound, char * filename, SDL_AudioSpec * hw_spec) {
    SDL_AudioSpec filespec;
    SDL_AudioCVT cvt;

    Uint8 * data;
    Uint32 length;

    /* Chargement du fichier .wav */
    if (SDL_LoadWAV(filename, &filespec, &data, &length) == NULL) {
        fprintf(stderr, "Erreur lors du chargement de s\n", filename, SDL_GetError());
        return -1;
    }

    /* Conversion vers le format supporté par le matériel */
    if (SDL_BuildAudioCVT(&cvt, filespec.format, filespec.channels, filespec.freq,
                          hw_spec->format, hw_spec->channels, hw_spec->freq) < 0) {
        fprintf(stderr, "Impossible de construire le convertisseur audio!\n");
        SDL_FreeWAV(data);
        return -1;
    }

    /* Création du tampon utilisé pour la conversion */
    cvt.buf = malloc(length * cvt.len_mult);
    cvt.len = length;
    memcpy(cvt.buf, data, length);

    /* Conversion... */
    if (SDL_ConvertAudio(&cvt) != 0) {
        fprintf(stderr, "Erreur lors de la conversion du fichier audio: %s\n", SDL_GetError());
        SDL_FreeWAV(data);
        return -1;
    }

    /* Libération de l'ancien tampon, création du nouveau,
       copie des données converties, effacement du tampon de conversion */
    SDL_FreeWAV(data);
    loaded_sound->data = malloc(cvt.len_cvt);
    memcpy(loaded_sound->data, cvt.buf, cvt.len_cvt);
    free(cvt.buf);

    loaded_sound->length = cvt.len_cvt;
    return 0;
}

void sound_free(sound * loaded_sound) {
    free(loaded_sound->data);
    loaded_sound->data = NULL;
    loaded_sound->length = 0;
}

void soundplayer_play(soundplayer * player, sound * toplay) {
    player->played_sound = toplay;
    player->pos = 0;
}

void soundplayer_stop(soundplayer * player) {
    player->played_sound = NULL;
    player->pos = 0;
}

void soundplayer_update(soundplayer * player) {
    if (player->pos >= player->played_sound->length + (echo_delay * echo_repeat))
        soundplayer_stop(player);
}

Uint8 soundplayer_isplaying(soundplayer * player) {
    return (player->played_sound != NULL);
}