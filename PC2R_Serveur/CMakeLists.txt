cmake_minimum_required(VERSION 3.1)
project(PC2R_Serveur)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES src/main.c)
add_executable(PC2R_Serveur ${SOURCE_FILES} include/serveur.h src/serveur.c include/client.h)