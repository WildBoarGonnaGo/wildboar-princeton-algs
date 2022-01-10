#include <iostream>
#include "board.h"
#include "minpq.h"

int main(int argc, char *argv[]) {
	int		size = 3;
	int**	tiles = new int*[3];
	tiles[0] = new int[3]; tiles[0][0] = 0; tiles[0][1] = 1; tiles[0][2] = 3;
	tiles[1] = new int[3]; tiles[1][0] = 4; tiles[1][1] = 2; tiles[1][2] = 5;
	tiles[2] = new int[3]; tiles[2][0] = 7; tiles[2][1] = 8; tiles[2][2] = 6;
	Board	*test = new Board(tiles, size);
	std::cout << "ORIGINAL BOARD:" << std::endl;
	std::cout << test->toString() << std::endl;
	std::cout << "test->hamming() = " + std::to_string(test->hamming()) << std::endl;
	std::cout << "test->manhattan() = " + std::to_string(test->manhattan()) << std::endl << std::endl;
	std::cout << "HIS NEIGHBORS: " << std::endl;
	std::queue<Board *> neighTest= test->neigbors();
	while (!neighTest.empty()) {
		Board *roll = neighTest.front();
		neighTest.pop();
		std::cout << roll->toString() << std::endl;
		std::cout << "roll.hamming() = " + std::to_string(roll->hamming()) << std::endl;
		std::cout << "roll.manhattan() = " + std::to_string(roll->manhattan()) << std::endl;
		delete roll;
	}
	std::cout << "TWIN BOARD" << std::endl;
	std::cout << test->twin()->toString() << std::endl;
	for (int i = 0; i < size; ++i) delete[] tiles[i];
	MinPQ<int>	*testPQ = new MinPQ<int>();
	testPQ->enqueue(5);
	testPQ->enqueue(4);
	testPQ->enqueue(3);
	testPQ->enqueue(2);
	testPQ->enqueue(1);
	std::cout << "Size of testPQ: " << testPQ->getSize() << std::endl;
	std::cout << "Elements of testPQ: ";
	while (!testPQ->isEmpty())
		std::cout << testPQ->dequeue() << ((testPQ->isEmpty()) ? '\n' : ' ');
	delete testPQ;
	delete[] tiles;
	delete test;
	return (0);
}
