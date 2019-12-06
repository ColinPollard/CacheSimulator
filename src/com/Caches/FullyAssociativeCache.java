/**
 * Author: Colin Pollard
 * 12/5/2019
 */
package com.Caches;
import java.util.Arrays;

/**
 * Structure that represents fully associative caches.
 * Simulates hits and misses, but does not store data.
 */
public class FullyAssociativeCache extends Cache {

    private int physicalAddressSize;
    private int cacheLines;
    private int blockOffsetSize;
    private int blockIndexSize;
    private int blockSize;

    /**
     * Variable to represent the number of storage bits used by this structure.
     */
    public int size;

    //Create arrays to store data into.
    private int[] tagBitArray;
    private int[] LRUArray;

    /**
     * Creates a new FullyAssociativeCache.
     * @param maximumCacheBits Largest storage area allotted.
     * @param blockSizeInput Size of each data block in bytes.
     * @param addressSize Size of the physical address used in bits.
     */
    public FullyAssociativeCache(int maximumCacheBits, int blockSizeInput, int addressSize)
    {
        blockSize = blockSizeInput;
        this.physicalAddressSize = addressSize;

        blockOffsetSize = CalculateLog2(blockSize);
        blockIndexSize = physicalAddressSize - blockOffsetSize;

        cacheLines = CalculateMaxCacheLength(blockSize, physicalAddressSize, maximumCacheBits);

        //Fill arrays with dummy data.
        tagBitArray = new int[cacheLines];
        Arrays.fill(tagBitArray, -1);

        LRUArray = new int[cacheLines];
        Arrays.fill(LRUArray, cacheLines-1);
    }

    /**
     * Creates a new FullyAssociativeCache to be part of a set-Associative cache.
     * @param ways Number of lines in cache. (N-way set associative).
     * @param blockSize Size of memory blocks in bytes.
     * @param addressSize Size of physical address in bits.
     * @param isPartOfSetAssociative Boolean to change method signature.
     */
    public FullyAssociativeCache(int ways, int blockSize, int addressSize, boolean isPartOfSetAssociative)
    {
        this.blockSize = blockSize;
        this.physicalAddressSize = addressSize;

        cacheLines = ways;

        blockOffsetSize = CalculateLog2(blockSize);
        blockIndexSize = physicalAddressSize - blockOffsetSize;

        //Fill arrays with dummy data
        tagBitArray = new int[cacheLines];
        Arrays.fill(tagBitArray, -1);

        LRUArray = new int[cacheLines];
        Arrays.fill(LRUArray, cacheLines-1);

        //Calculate the size of the cache
        int blockStorageSize = (cacheLines * blockSize * 8);
        int tagBitStorageSize = blockIndexSize*cacheLines;
        int LRUStorageSize = cacheLines * CalculateLog2(cacheLines);
        size = blockStorageSize + tagBitStorageSize + LRUStorageSize;
    }

    /**
     * Clears the cache backing arrays.
     */
    void ClearCache() {
        tagBitArray = new int[cacheLines];
        Arrays.fill(tagBitArray, -1);

        LRUArray = new int[cacheLines];
        Arrays.fill(LRUArray, cacheLines-1);
    }

    /**
     * Attempts to read a value from the cache.
     * @param addressToAccess Address to read from.
     * @return True if hit, false otherwise.
     */
    boolean ReadFromCache(int addressToAccess) {

        int blockIndex = MaskedAddress(addressToAccess, blockIndexSize, blockOffsetSize);

        for(int i = 0; i < tagBitArray.length; i++)
        {
            if(tagBitArray[i] == blockIndex)
            {
                //Increment all priorities
                for(int j = 0; j < LRUArray.length-1; j++)
                {
                    if(LRUArray[j] == cacheLines-1)
                        continue; //The priority is already the lowest possible

                    LRUArray[j]++;
                }
                //Reset priority of hit
                LRUArray[i] = 0;
                return true; //Hit
            }
        }

        //Miss
        for(int i = 0; i < LRUArray.length-1; i++)
        {
            //Iterate through LRUs and pick first one with lowest priority
            if(LRUArray[i] == cacheLines-1)
            {
                //Increment all priorities
                for(int j = 0; j < LRUArray.length-1; j++)
                {
                    if(LRUArray[j] == cacheLines-1)
                        continue; //The priority is already the lowest possible

                    LRUArray[j]++;
                }
                //Store the item, and set priority to highest (0)
                tagBitArray[i] = blockIndex;
                LRUArray[i] = 0;
                break;
            }
        }
        return false;
    }

    /**
     * Calculates the largest cache length for a given number of bits
     * @param blockSize Size of each block in bytes (assuming byte addressing)
     * @param physicalAddressSize Length of physical addresses
     * @param maximumSize Maximum number of bits allowed
     * @return Length of the cache (number of lines)
     */
    private int CalculateMaxCacheLength(int blockSize, int physicalAddressSize, int maximumSize)
    {
        int largestCacheLength = 0;
        int newSize = 0;
        int largestSize = 0;

        //Iterate through cache lengths up to a maximum of 2048
        for(int newCacheLength = 0; newCacheLength < 2048; newCacheLength++)
        {
            //Calculate sizes of components
            int blockStorageSize = (newCacheLength * blockSize * 8);
            int tagBitStorageSize = blockIndexSize*newCacheLength;
            int LRUStorageSize = newCacheLength * CalculateLog2(newCacheLength);

            //Calculate total size
            newSize = blockStorageSize + tagBitStorageSize + LRUStorageSize;

            //If size fits
            if(newSize < maximumSize)
            {
                largestCacheLength = newCacheLength;
                largestSize = newSize;
            }
        }

        //Largest size found
        size = largestSize;
        System.out.println("New Associative cache uses " + largestSize + " bits of data.");

        return largestCacheLength;
    }

    public int getBlockSize()
    {
        return blockSize;
    }
}
