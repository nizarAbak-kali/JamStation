//
// Created by nizar on 27/04/15.
//

#include <stdio.h>
#include "../include/traceinstantsf.h"
#include "../libs/ft_v1.0/include/fthread.h"

void traceinstants (void* a)
{int i;
    int j = (int)a;
    for (i=0;i<j;i++) {
        fprintf (stdout,">>>>>>>>>>> instant %d :\n",i);
        ft_thread_cooperate ();
    }
    fprintf(stdout, "Last exit\n");
}