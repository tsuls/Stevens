#include "Tree.hpp"
//TO DO: Add a merging function
LDisk::LDisk(int blocks, const int blockSize_) {
    totalBlocks = blocks;
    blockSize = blockSize_;

    DNode *new_h = new DNode(0, totalBlocks-1, 0);
    head = new_h; 
}

/*
    insert new data into the LDisk LL if it will fit
    size: size of file in blocks
    takes in the LFile linked list that corresponds to the file being inserted so we can keep track of what blocks each file is in on the disk
*/
int LDisk::insert(int size, LFile* fhead) {
    DNode* temp = head;
    int available = 0;
    int blockSplit;
    //first check there will be enough space to add
    while(temp!=NULL) {
        if(!temp->usedBit)
            available += temp->end - temp->start + 1;

        if(available >= (size + blockSize - 1)/blockSize)
            break;

        temp=temp->next;
    }
    if(available < (size + blockSize - 1)/blockSize)
        return 1;
    //if we are here there is enough space to add the file in question
    temp = head;
    while(temp!=NULL) {
        if(!temp->usedBit) {
            blockSplit = temp->end-temp->start+1;
            //for each situation we append a new node to the LFile head so we can keep track of the memory locations that the data is being stored in
            if(blockSplit > (size + blockSize - 1)/blockSize) {
                //if the block has 30 blocks available and we only need 2 then we split the node and it turns into
                split(temp, blockSplit-((size + blockSize - 1)/blockSize), 1);
                fhead->append(size, (temp->start)*blockSize);
                return 0;
            } else if(blockSplit == (size + blockSize - 1)/blockSize) {
                //take up entire node
                fhead->append(size, (temp->start)*blockSize);
                temp->usedBit = 1;
                return 0;
            } else {
                //take up entire node and continue to look for more space elsewhere for rest of file
                temp->usedBit = 1;
                size=(((size + blockSize - 1)/blockSize)-blockSplit)*blockSize;
                fhead->append(blockSplit*blockSize, (temp->start)*blockSize);
            }
        }
        temp=temp->next;
    }


    return 0;
}

void LDisk::remove(int size, LFile* fhead) {
    FNode* temp;
    DNode* disk_node = head;
    DNode* disk_node_prev = NULL;
    int address;
    int bytes;
    if(size > fhead->getSize()) {
        size = fhead->getSize();
    }
    while(size > 0) {
        temp = fhead->getHead();
        if(temp==NULL)
            return;
        while(temp->next!=NULL) {
            temp = temp->next;
        }
        address = temp->blockAddress/blockSize;
        if(temp==NULL)
            return;
        //28-33 used
        //address=28
        //end=33
        //start=28
        //end goal=32

        //33-28 -> 5
        //
        
        while(disk_node!=NULL) {
            if(address >= disk_node->start && address <= disk_node->end) {
                break;
            }
            disk_node_prev = disk_node;
            disk_node=disk_node->next;
        }

        if(disk_node==NULL) {
            std::cout << "Could not find block address" << std::endl;
            return;
        }
        //temp->cap = 1200
        //bs = 1600
        //removing 1200
        //totalSize = 1000


        if(temp->capacity <= size && size <= blockSize) {
            fhead->remove(temp->capacity);
            size-=temp->capacity;
        } else if(temp->capacity < blockSize && size >= blockSize) {
            fhead->remove(temp->capacity);
            size-=temp->capacity;
        } else if(size>=blockSize) {
            //split(disk_node, 1, 1);
            bytes = blockSize;
            fhead->remove(bytes);
            size-=bytes;
        } else {
            fhead->remove(size);
            size=0;
            return;
        }
        if(disk_node->start==disk_node->end) {
            disk_node->usedBit=0;
        } else {
            split(disk_node, 1, 1);
        }
    }


}

void LDisk::merge() {
    DNode* temp = head;
    while(temp->next!=NULL) {
        if(!temp->usedBit && !temp->next->usedBit) {
            temp->end = temp->next->end;
            temp->next = temp->next->next;
        } else {
            temp=temp->next;
        }
    }
}

//print linked list
void LDisk::print(bool debug) {
    DNode *temp = head;
    int fragmentation = 0;
    if(debug) {
        while(temp!=NULL) {
            printNode(temp);
            if(!temp->usedBit) {
                fragmentation+=temp->end-temp->start+1;
            }
            temp=temp->next;
        }
    }
    std::cout << "Fragmentation: " << fragmentation << std::endl;
}

LDisk::~LDisk() {

}

/*
    node: node to be split
    toSplit: how many blocks will be in second (new) node
    toStatus: status of original node

    this funtion is slightly confusing bc the original node gets the toStatus but the difference of (node->size)-toSplit
    where the new node (node->next once it is created) gets the toSplit size, but the inverse of toStatus
*/
void LDisk::split(DNode *node, int toSplit, int toStatus) {
    int available;
    int oldEnd, oldStart;
    int new_status = 1 - toStatus;
    DNode *new_node;

    oldStart = node->start;
    oldEnd = node->end;
    new_node = new DNode(oldEnd-toSplit+1, oldEnd, new_status);
    node->end = oldEnd-toSplit;
    node->usedBit = toStatus;

    new_node->next = node->next;
    node->next = new_node;
}

void LDisk::printNode(DNode *node) {
    if(node->usedBit) {
        std::cout << "Used: " << node->start << " - " << node->end << std::endl;
    } else {
        std::cout << "Free: " << node->start << " - " << node->end << std::endl;
    }
}