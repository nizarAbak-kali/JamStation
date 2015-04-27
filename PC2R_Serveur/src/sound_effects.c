//
// Created by nizar on 27/04/15.
//

#include "../include/sound_effects.h"
Sint16 sample_mix(Sint16 src1, Sint16 src2)
{
    Sint32 mix = src1 + src2;

    /* Dépassement de la capacité de l'échantillon sur 16 bits? */
    if (mix > 32767) mix = 32767;
    if (mix < - 32768) mix = -32768;

    return (Sint16)mix;
}



Sint16 sample_volume(Sint16 sample, Uint8 volume)
{
    return (sample * volume) / 255;
}
