#include "board.h"
#include <cmath>
#include <exception>

Board::Board(int **tiles, int size) : _size(size) {
	if (tiles == nullptr) throw new std::exception();
	_tiles = new int*[_size];
	for (int i = 0; i < _size; ++i) {
		_tiles[i] = new int[_size];
		for (int j = 0; j < _size; ++j) _tiles[i][j] = tiles[i][j];
	}
}

//string representation of this board
std::string	Board::toString() {
	std::string	result = "";
	result += std::to_string(_size) + '\n';
	for (int i = 0; i < _size; ++i) {
		for (int j = 0; j < _size; ++j) result.append(" " + std::to_string(_tiles[i][j]));
		if (i != _size - 1) result.append("\n");
	}
	return result;
}

//board dimension n
const int	&Board::dimension() const { return _size; }

//number of tiles out of place
int			Board::hamming() {
	int	hamm = 0;

	for (int i = 0; i < _size; ++i) {
		for (int j = 0; j < _size; ++j) {
			if (i == _size - 1 && j == _size - 1) continue ;
			else hamm += (_tiles[i][j] != i * _size + j + 1);
		}
	}
	return hamm;
}

int*			Board::manhattan(int value, int lo, int hi) {
	int *result = nullptr;
	if (lo > hi) return nullptr;
	int mid = lo + (hi - lo) / 2,
	pos = (value == 0) ? _size * _size - 1 : value - 1,
	dstrow = pos / _size, dstcol = pos % _size,
	row = mid / _size, col = mid % _size;
	if (_tiles[row][col] == value) {
		result = new int[2];
		result[0] = std::abs(row - dstrow);
		result[1] = std::abs(col - dstcol);
		return result;
	}
	if (!result) result = manhattan(value, lo, mid - 1);
	if (!result) result = manhattan(value, mid + 1, hi);
	return result ;
}

int				Board::manhattan() {
	int	manh = 0, initHi = _size * _size - 1;
	int	*tilePtr = nullptr;

	for (int i = 0; i < _size; ++i) {
		for (int j = 0; j < _size; ++j) {
			if (i == _size - 1 && j == _size - 1) continue ;
			tilePtr = manhattan(i * _size + j + 1, 0, initHi);
			if (tilePtr) {
				manh += tilePtr[0] + tilePtr[1];
				delete tilePtr; tilePtr = nullptr;
			}
		}
	}
	return manh;
}

bool				Board::isGoal() { return manhattan() == 0; }

std::queue<Board *>	Board::neigbors() {
	std::queue<Board *>	result;
	int				initHi = _size * _size - 1;
	int*			tilePtr = manhattan(0, 0, initHi);
	int zeroRow = _size - tilePtr[0] - 1, zeroCol = _size - tilePtr[1] - 1;
	if (zeroCol > 0) {
		exch(_tiles, zeroRow, zeroCol, zeroRow, zeroCol - 1);
		result.push(new Board(_tiles, _size));
		exch(_tiles, zeroRow, zeroCol, zeroRow, zeroCol - 1);
	}
	if (zeroRow < _size - 1) {
		exch(_tiles, zeroRow, zeroCol, zeroRow + 1, zeroCol);
		result.push(new Board(_tiles, _size));
		exch(_tiles, zeroRow, zeroCol, zeroRow + 1, zeroCol);
	}
	if (zeroCol < _size - 1) {
		exch(_tiles, zeroRow, zeroCol, zeroRow, zeroCol + 1);
		result.push(new Board(_tiles, _size));
		exch(_tiles, zeroRow, zeroCol, zeroRow, zeroCol + 1);
	}
	if (zeroRow > 0) {
		exch(_tiles, zeroRow, zeroCol, zeroRow - 1, zeroCol);
		result.push(new Board(_tiles, _size));
		exch(_tiles, zeroRow, zeroCol, zeroRow - 1, zeroCol);
	}
	return result;
}

bool				operator==(const Board &lval, const Board &rval) {
	if (&lval == &rval || lval._tiles == rval._tiles) return true;
	if (lval._size != rval._size) return false;
	for (int i = 0; i < lval._size; ++i) {
		for (int j = 0; j < rval._size; ++j)
			if (lval._tiles[i][j] != rval._tiles[i][j]) return false;
	}
	return true;
}

void				Board::exch(int **arr, int irow,
							int icol, int jrow, int jcol) {
	if (arr == nullptr) throw new std::exception();
	int buffer = arr[irow][icol];
	arr[irow][icol] = arr[jrow][jcol];
	arr[jrow][jcol] = buffer;
}

Board				*Board::twin() {
	int	**copy = new int*[_size];
	for (int i = 0; i < _size; ++i) {
		copy[i] = new int[_size];
		for (int j = 0; j < _size; ++j) copy[i][j] = _tiles[i][j];
	}
	int pos = 0;
	if (!copy[pos / _size][pos % _size]) ++pos;
	int irow = pos / _size, icol = pos++ % _size;
	if (!copy[pos / _size][pos % _size]) ++pos;
	int jrow = pos / _size, jcol = pos % _size;
	exch(copy, irow, icol, jrow, jcol);
	Board	*result = new Board(copy, _size);
	for (int i = 0; i < _size; ++i) delete[] copy[i];
	delete[] copy;
	return result;
}

Board::~Board() {
	for (int i = 0; i < _size; ++i) { delete[] _tiles[i]; _tiles[i] = nullptr; }
	if (_tiles) delete[] _tiles;
}
