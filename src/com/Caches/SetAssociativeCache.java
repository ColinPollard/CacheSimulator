/**
 * Author: Colin Pollard
 * 12/5/2019
 */
package com.Caches;

import java.util.Arrays;

/**
 * Structure to represent a set associative cache.
 * Simulates hits and misses, but does not store data.
 */
public class SetAssociativeCache extends Cache {

    private int blockSize;
    private int physicalAddressSize;
    private int maximumCacheBits;
    private int blockOffsetSize;
    private int numberOfCaches;
    private int setWay;
    private int setIndexSize;

    /**
     * Variable to represent the number of storage bits used by this structure.
     */
    public int size;

    private FullyAssociativeCache[] AssociativeCaches;

    /**
     * Creates a new SetAssociative cache.
     * @param maximumCacheBits Maximum number of bits available for use.
     * @param blockSize Size of each memory block in bytes.
     * @param addressSize Size of physical address in bits.
     * @param setWay #setWay
     */
    public SetAssociativeCache(int maximumCacheBits, int blockSize, int addressSize, int setWay)
    {
        this.blockSize = blockSize;
        this.physicalAddressSize = addressSize;
        this.maximumCacheBits = maximumCacheBits;
        blockOffsetSize = CalculateLog2(blockSize);
        this.setWay = setWay;

        //Calculate the number of Associative caches possible
        numberOfCaches = CalculateMaxCacheLength(maximumCacheBits, setWay);

        //Calculate the number of bits required to calculate which cache to place values into
        setIndexSize = CalculateLog2(numberOfCaches);

        //Create new set of associative caches
        AssociativeCaches = new FullyAssociativeCache[numberOfCaches];
        Arrays.fill(AssociativeCaches, new FullyAssociativeCache(setWay, blockSize, addressSize, true));
    }

    /**
     * Clears the cache of current data.
     */
    void ClearCache() {
        AssociativeCaches = new FullyAssociativeCache[numberOfCaches];
        Arrays.fill(AssociativeCaches, new FullyAssociativeCache(setWay, blockSize, physicalAddressSize, true));
    }

    /**
     * Reads from the Cache.
     * @param address Physical address to read at
     * @return True if hit, false otherwise.
     */
    boolean ReadFromCache(int address) {

        //Calculate which associative cache to store in.
        int setIndex = MaskedAddress(address, setIndexSize, blockOffsetSize);

        //Read from the appropriate FullyAssociativeCache
        return AssociativeCaches[setIndex].ReadFromCache(address);
    }

    /**
     * Calculates the maximum number of caches that fit inside allotted number of bits.
     * @param maximumCacheBits Maximum number of bits available
     * @param setWay #SetWay
     * @return Number of caches.
     */
    private int CalculateMaxCacheLength(int maximumCacheBits, int setWay)
    {
        int newSize = 0;
        int numberOfCaches = 0;

        //Create example associative cache using setway size
        FullyAssociativeCache newCache = new FullyAssociativeCache(setWay, blockSize, physicalAddressSize, true);

        //Increment the number of caches and see if it will still fit.
        for(int newCaches = 0; newCaches < 2048; newCaches++)
        {
            //Calculate new total size
            newSize = newCaches * newCache.size;
            if(newSize < maximumCacheBits)
            {
                numberOfCaches = newCaches;
                size = newSize;
            }

            //New size exceeds allotted amount
            else
                break;
        }

        System.out.println("New SetAssociativeCache uses " + size + " bits of storage.");
        return numberOfCaches;
    }
}
