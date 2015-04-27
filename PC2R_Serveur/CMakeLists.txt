cmake_minimum_required(VERSION 3.1)
project(PC2R_Serveur)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall -W -pedantic -ansi `sdl-config --cflags --libs`")
find_package(SDL REQUIRED)

set(SOURCE_FILES src/main.c)
include_directories(libs/ft_v1.0/include)
include_directories(include)
link_directories(libs/ft_v1.0/lib)
add_executable(PC2R_Serveur ${SOURCE_FILES} include/serveur.h  include/client.h src/serveur.c include/traceinstantsf.h src/traceinstantsf.c include/audio.h  src/audio.c src/sound_effects.c  include/serveur_audio.h  src/serveur_audio.c)