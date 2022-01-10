#ifndef BOARD_H
#define BOARD_H
#include <string>
#include <queue>

class Board {
	int**	_tiles;
	int		_size;
	int*	manhattan(int value, int lo, int hi);
	void	exch(int **arr, int irow, int icol, int jrow, int jcol);
	int		_move;
public:
	Board(int **tiles, int size);
	std::string			toString();
	const int			&dimension() const;
	int					hamming();
	int					manhattan();
	bool				isGoal();
	std::queue<Board *>	neigbors();
	friend bool			operator==(const Board &lval, const Board &rval);
	Board*				twin();
	~Board();
};

#endif // BOARD_H
