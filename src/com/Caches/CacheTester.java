/**
 * Author: Colin Pollard
 * 12/5/2019
 */
package com.Caches;

/**
 * A class to test various types of caches using a set of sample data.
 */
public class CacheTester {

    public Cache cacheInstance;
    public int[] testData;

    /**
     * Creates a new CacheTester.
     * @param cacheInstance Instance of cache to test.
     * @param testData Data to test cache on.
     */
    public CacheTester(Cache cacheInstance, int[] testData)
    {
        this.cacheInstance = cacheInstance;
        this.testData = testData;
    }

    /**
     * Calculates how many hits/total attempts occur using cache instance and test data.
     * @return Double representing percentage of hits.
     * @throws Exception
     */
    public double calculateHitPercentage() throws Exception {
        int hits = 0;
        int misses = 0;
        int totalAttempts = testData.length;

        if(totalAttempts == 0)
            throw new Exception("Cannot execute with array of length 0.");

        for (int i = 0; i < totalAttempts; i++)
        {
            if(cacheInstance.ReadFromCache(testData[i]))
            {
                hits++;
                continue;
            }
            misses++;
        }

        return ((double)hits)/((double)totalAttempts);
    }

    /**
     * Calculates the penalty of missing in cycles.
     * @return Cycles wasted.
     * @throws Exception
     */
    public int calculateCyclePenalty() throws Exception {
        int hits = 0;
        int misses = 0;
        int totalAttempts = testData.length;

        if(totalAttempts == 0)
            throw new Exception("Cannot execute with array of length 0.");

        for (int i = 0; i < totalAttempts; i++)
        {
            if(cacheInstance.ReadFromCache(testData[i]))
            {
                hits++;
                continue;
            }
            misses++;
        }
        return (misses*(10+(cacheInstance.getBlockSize())));
    }

    public void printHitsMisses() throws Exception {
        int totalAttempts = testData.length;

        if(totalAttempts == 0)
            throw new Exception("Cannot execute with array of length 0.");

        for (int i = 0; i < totalAttempts; i++)
        {
            if(cacheInstance.ReadFromCache(testData[i]))
            {
                System.out.print(1);
                continue;
            }
            System.out.print(0);
        }
        System.out.println();
    }
}
