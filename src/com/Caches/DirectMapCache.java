/**
 * Author: Colin Pollard
 * 12/5/2019
 */
package com.Caches;

import java.util.Arrays;

/**
 * Structure to represent a direct mapping cache.
 * Simulates hits and misses, but does not store data.
 */
public class DirectMapCache extends Cache{

    private int cacheLines;
    private int cacheIndexSize;
    private int blockOffsetSize;
    private int tagBitSize;

    /**
     * Variable to represent the number of storage bits used by this structure.
     */
    public int size;
    public int blockSize;

    private int[] tagBitArray;

    /**
     * Creates a new direct mapping cache.
     * @param maximumCacheBits the maximum number of storage bits allotted
     * @param blockSize the size of each memory block in bytes (assuming byte addressing)
     * @param addressSize the length of the physical address in bits
     */
    public DirectMapCache(int maximumCacheBits, int blockSize, int addressSize)
    {
        this.blockSize = blockSize;

        cacheLines = CalculateMaxCacheLength(blockSize, addressSize, maximumCacheBits);
        blockOffsetSize = CalculateLog2(blockSize);
        cacheIndexSize = CalculateLog2(cacheLines);
        int cacheAddressSize = cacheIndexSize + blockOffsetSize;
        tagBitSize = addressSize - cacheAddressSize;

        //Fill array with dummy data.
        tagBitArray = new int[cacheLines];
        Arrays.fill(tagBitArray, -1);
    }

    public void ClearCache()
    {
        tagBitArray = new int[cacheLines];
    }

    /**
     * "Reads" from the cache.
     * @param addressToAccess Address to read from.
     * @return True if hit, false otherwise.
     */
    public boolean ReadFromCache(int addressToAccess) {
        int blockOffset = MaskedAddress(addressToAccess, blockOffsetSize, 0);

        int cacheIndex = MaskedAddress(addressToAccess, cacheIndexSize, blockOffsetSize);

        int tagBit = MaskedAddress(addressToAccess, tagBitSize, (blockOffsetSize + cacheIndexSize));

        if (tagBitArray[cacheIndex] == tagBit)
            return true;
        else {
            tagBitArray[cacheIndex] = tagBit;
            return false;
        }
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
        for(int newCacheLength = 0; newCacheLength < 2048; newCacheLength++)
        {
            newSize = (newCacheLength * blockSize * 8) + ((physicalAddressSize-CalculateLog2(newCacheLength * blockSize)) * (newCacheLength));
            if(newSize < maximumSize)
                largestCacheLength = newCacheLength;
        }

        //Return a power of two

        int res = 0;
        for (int i = largestCacheLength; i >= 1; i--)
        {
            // If i is a power of 2
            if ((i & (i - 1)) == 0)
            {
                res = i;
                break;
            }
        }

        size = ((res * blockSize * 8) + ((physicalAddressSize-CalculateLog2(res * blockSize)) * (res)));
        System.out.println("New direct mapping cache uses " + size + " bits of data.");
        return res;
    }
}
