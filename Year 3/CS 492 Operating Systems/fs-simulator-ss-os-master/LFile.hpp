#ifndef _LFILE_HPP_
#define _LFILE_HPP_

#include "FNode.hpp"


class LFile
{
    public:
        LFile(int ts, int bs);
        void append(int numBytes, int startingAddress);
        void remove(int numBytes);
        void print(std::string indent);
        int getSize();
        FNode* getHead();
        ~LFile();
    private:
        FNode* start;
        int totalSize;
        int blockSize;
        int capacity;
        int removeLastNode(int bytes);
};

#endif
