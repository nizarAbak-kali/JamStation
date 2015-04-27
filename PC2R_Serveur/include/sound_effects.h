#ifndef _SOUND_EFFECTS_H__
#define _SOUND_EFFECTS_H__

#include "audio.h"

/* Les paramètres d'echo */
Uint8 echo_volume_dec = 50; /* Décrémentation du volume */
Uint8 echo_repeat = 5; /* Nombre de répétitions */
Uint32 echo_delay = 35000; /* Délai entre chaque répétition */

/* Change le volume sur le sample passé en paramètre.
   Le volume retourné est égal au paramètre volume / 255. */
Sint16 sample_volume(Sint16 sample, Uint8 volume);
/* Retourne le mixage des deux échantillons src1 et src2 */
Sint16 sample_mix(Sint16 src1, Sint16 src2);

#endif