cmake_minimum_required(VERSION 3.7.2)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_COMPILER g++)
project(8puzzle)

file(GLOB SRC ${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp)
file(GLOB INCLUDE ${CMAKE_CURRENT_SOURCE_DIR}/include/*.h)

add_executable(${PROJECT_NAME} main.cpp ${SRC} ${INLCUDE})
target_include_directories(${PROJECT_NAME} PRIVATE "${CMAKE_CURRENT_SOURCE_DIR}/include")
