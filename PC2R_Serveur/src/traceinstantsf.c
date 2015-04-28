//
// Created by nizar on 27/04/15.
//
#include "../include/traceinstantsf.h"

void traceinstants (void* a)
{
    int i;
    int j = (int)a;
    for (i=0;i<j;i++) {
        fprintf (stdout,">>>>>>>>>>> instant %d :\n",i);
        ft_thread_cooperate ();
    }
    fprintf(stdout, "Last exit\n");
}