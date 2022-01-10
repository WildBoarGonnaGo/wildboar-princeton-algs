//
// Created by WildBoarGonnaGo on 20.12.2021.
//

#ifndef MINPQ_H
#define MINPQ_H
#include <functional>
#include <memory>

template<class Key, class Compare = std::less<Key>, class Allocator = std::allocator<Key>>
	class	MinPQ {
	struct	Node {
		Key		key;
		Node	*next;
		Node(const Key &key) { this->key = key; next = nullptr; }
		Node(const Key &key, Node *next) { this->key = key; this->next = next; }
	};
	Node														*root;
	size_t														size;
	Compare														less;
	typedef typename Allocator::template rebind<Node>::other	allocNode;
	allocNode													alloc;
public:
	MinPQ() {
		size = 0; root = nullptr;
		less = Compare();
		alloc = allocNode();
	}

	void	enqueue(const Key &key) {
		if (!root) {
			//root = new Node(key);
			root = alloc.allocate(1);
			alloc.construct(root, Node(key));
			++size;
			return ;
		}
		Node	*roll = root;
		Node	*prev = nullptr;
		while (roll) {
			if (less(key, roll->key)) break ;
			prev = roll;
			roll = roll->next;
		}
		//Node	*v =new Node(key, roll);
		Node	*v = alloc.allocate(1);
		alloc.construct(v, Node(key, roll));
		if (prev) prev->next = v;
		else root = v;
		++size;
	}

	Key		dequeue() {
		if (root == nullptr) throw std::exception();
		Key		result = root->key;
		Node	*roll = root;
		root = root->next;
		alloc.destroy(roll);
		alloc.deallocate(roll, 1);
		roll = nullptr;
		--size;
		return result;
	}

	Key		min() { return root->key; }

	bool	isEmpty() { return size == 0; }

	int		getSize() { return size; }

	~MinPQ() { ; }
};

#endif //MINPQ_H
