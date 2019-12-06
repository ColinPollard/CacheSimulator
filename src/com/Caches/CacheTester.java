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
     * Private helper method that calculates the number of hits for a given dataset.
     * @return Number of hits
     * @throws Exception Array of size 0 detected.
     */
    private int calculateHits() throws Exception {
        int hits = 0;
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
        }
        return hits;
    }

    /**
     * Calculates how many hits/total attempts occur using cache instance and test data.
     * @return Double representing percentage of hits.
     * @throws Exception
     */
    public double calculateHitPercentage() throws Exception {

        int hits = calculateHits();
        int totalAttempts = testData.length;
        return ((double)hits)/((double)totalAttempts);
    }

    /**
     * Calculates the average CPI for the dataset.
     * @return Average CPI.
     * @throws Exception Array of length 0 detected.
     */
    public double calculateAverageCPI() throws Exception {
        int hits = calculateHits();
        int misses = testData.length-hits;
        int totalAttempts = testData.length;
        return ((double)calculateCyclePenalty())/((double)totalAttempts);
    }

    /**
     * Calculates the penalty of missing in cycles.
     * @return Cycles wasted.
     * @throws Exception Array of size 0 detected.
     */
    public int calculateCyclePenalty() throws Exception {
        int hits = calculateHits();
        int totalAttempts = testData.length;
        int misses = totalAttempts-hits;

        return (misses*(10+(cacheInstance.getBlockSize())));
    }

    /**
     * Method that prints the hits and misses for a dataset. Prints 1 for hit, 0 for miss.
     * @throws Exception Array of size 0 detected.
     */
    public void printHitMissArray() throws Exception {
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

    /**
     * Prints the number of hits and misses.
     * @throws Exception Array of size 0 detected.
     */
    public void printHitsMisses() throws Exception {
        int hits = calculateHits();
        int totalAttempts = testData.length;
        int misses = totalAttempts-hits;

        System.out.println("Hits: " + hits + " Misses: " + misses);
    }
}
