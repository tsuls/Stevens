#include "Tree.hpp"

LFile::LFile(int ts, int bs) {
    totalSize = ts;
    blockSize = bs;
    start=NULL;
}

FNode* LFile::getHead() {
    return start;
}

//simple linked list
void LFile::append(int numBytes, int startingAddress) {
    FNode* temp;
    int tempBy;
    double tempVal;
    totalSize+=numBytes;
    while(numBytes) {
        if(numBytes>blockSize) {
            tempBy = blockSize;
            numBytes = numBytes - blockSize;
        } else {
            tempBy = numBytes;
            numBytes=0;
        }
        if(start==NULL) {
            start = new FNode(startingAddress, tempBy, NULL, NULL);
        } else {
            temp = start;
            while(temp->next!=NULL) {
                temp = temp->next;
            }
            temp->next = new FNode(startingAddress, tempBy, temp, NULL);
        }
    }
}

void LFile::remove(int numBytes) {
    int check, temp;
    while(numBytes) {
        temp = numBytes;
        check = removeLastNode(temp);
        if(check==-1) {
            return;
        }
        if(numBytes > blockSize) {
            numBytes = numBytes - check;
        }
    }
}

int LFile::removeLastNode(int bytes) {
    if(start==NULL)
        return -1;
    FNode* curr;
    FNode* prev;
    
    curr = start;
    while(curr->next!=NULL) {
        prev = curr;
        curr = curr->next;
    }

    if(totalSize<=blockSize && bytes >= totalSize) {
        start = NULL;
        return -1;
    }

    if(curr->capacity < bytes) {
        prev->next = NULL;
        totalSize = totalSize - curr->capacity;
        return curr->capacity;
    } else if(curr->capacity == bytes) {
        prev->next = NULL;
        totalSize = totalSize - bytes;
        return -1;
    } else {
        curr->capacity = curr->capacity - bytes;
        totalSize = totalSize - bytes;
        return -1;
    }
}

void LFile::print(std::string indent) {
    FNode* temp = start;
    while(temp!=NULL) {
        std::cout << indent << "\t" << "Block Address: " << temp->blockAddress << " Capacity: " << temp->capacity << std::endl;
        temp=temp->next;
    }
}

int LFile::getSize() {
    return totalSize;
}

LFile::~LFile() {

}