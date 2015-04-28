#include "serveur_audio.c"
#include "serveur.c"
#include "traceinstantsf.c"


int main() {
    SDL_Init(SDL_INIT_AUDIO);
    printf("creation du scheduler\n");
    ft_scheduler_t ordo = ft_scheduler_create();

    ft_thread_create(ordo,traceinstants,NULL,(void*) 5000);
    ft_thread_t ui = ft_thread_create(ordo,app_ui,NULL,NULL);
    ft_thread_t sound =  ft_thread_create(ordo,app_sound,NULL,NULL);



    ft_scheduler_start(ordo);
    printf("fin");
    SDL_Quit();
    return EXIT_SUCCESS;
}
