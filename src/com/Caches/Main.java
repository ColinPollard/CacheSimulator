/**
 * Author: Colin Pollard
 * 12/5/2019
 */
package com.Caches;

public class Main {

    //Data to test caches on
    public static int[] POS9Data = {4, 8, 12, 16, 20, 32, 36, 40, 44, 20, 32, 36, 40, 44, 64, 68, 4, 8, 12, 92, 96, 100, 104, 108, 112, 100, 112, 116, 120, 128, 140, 144};

    public static void main(String[] args) throws Exception {
	    //Create caches
        DirectMapCache directCache = new DirectMapCache(840, 4, 16);
        FullyAssociativeCache associativeCache = new FullyAssociativeCache(840, 4, 16);
        SetAssociativeCache setAssociativeCache = new SetAssociativeCache(840, 4, 16, 8);
        System.out.println();

        //Direct Map Cache ---------------------------------------------------------------
	    CacheTester DirectTester = new CacheTester(directCache, POS9Data); //Create tester
	    DirectTester.calculateHitPercentage(); //Run first without recording
	    double result = DirectTester.calculateHitPercentage(); //Record results
        DirectTester.printHitsMisses();
        System.out.println("DirectCache has a hit/total percentage of " + result);
        System.out.println("DirectCache has a CPI of " + DirectTester.calculateAverageCPI());
        System.out.println("DirectCache took a cycle penalty of " + DirectTester.calculateCyclePenalty());
        DirectTester.printHitMissArray();
        System.out.println();

        //Fully Associative Cache ---------------------------------------------------------------
        CacheTester AssociativeTester = new CacheTester(associativeCache, POS9Data);
        AssociativeTester.calculateHitPercentage();
        result = AssociativeTester.calculateHitPercentage();
        AssociativeTester.printHitsMisses();
        System.out.println("FullyAssociative has a hit/total percentage of " + result);
        System.out.println("FullyAssociative has a CPI of " + AssociativeTester.calculateAverageCPI());
        System.out.println("FullyAssociative took a cycle penalty of " + AssociativeTester.calculateCyclePenalty());
        AssociativeTester.printHitMissArray();
        System.out.println();

        //Set Associative Cache ---------------------------------------------------------------
        CacheTester SetAssociativeTester = new CacheTester(setAssociativeCache, POS9Data);
        SetAssociativeTester.calculateHitPercentage();
        result = SetAssociativeTester.calculateHitPercentage();
        SetAssociativeTester.printHitsMisses();
        System.out.println("SetAssociative has a hit/total percentage of " + result);
        System.out.println("SetAssociative has a CPI of " + SetAssociativeTester.calculateAverageCPI());
        System.out.println("SetAssociative took a cycle penalty of " + SetAssociativeTester.calculateCyclePenalty());
        SetAssociativeTester.printHitMissArray();
        System.out.println();

        //Test all combinations of set ways ----------------------------------------------------------------------------------------
        for(int ways = 1; ways < 16; ways++)
        {
            SetAssociativeCache setAssociative = new SetAssociativeCache(840, 1, 16, ways);
            CacheTester tester = new CacheTester(setAssociative, POS9Data);
            tester.calculateCyclePenalty();
            System.out.println("Set Ways: " + ways + " Penalty: " + tester.calculateCyclePenalty());
        }

        System.out.println("\n");

        //Test all combinations of block sizes Direct Mapping -------------------------------------------------------------------------------------
        for(int i = 1; i < 32; i++)
        {
            DirectMapCache direct = new DirectMapCache(840, i, 16);
            CacheTester tester = new CacheTester(direct, POS9Data);
            tester.calculateCyclePenalty();
            System.out.println("Block Size: " + i + " Penalty: " + tester.calculateCyclePenalty());
        }

        System.out.println("\n");

        //Test all combinations of block sizes Fully Associative -------------------------------------------------------------------------------------
        for(int i = 1; i < 32; i++)
        {
            FullyAssociativeCache associative = new FullyAssociativeCache(840, i, 16);
            CacheTester tester = new CacheTester(associative, POS9Data);
            tester.calculateCyclePenalty();
            System.out.println("Block Size: " + i + " Penalty: " + tester.calculateCyclePenalty());
        }

        System.out.println("\n");

        //Test all combinations of block sizes SetAssociative -------------------------------------------------------------------------------------
        for(int i = 1; i < 32; i++)
        {
            SetAssociativeCache setAssociative = new SetAssociativeCache(840, i, 16, 2);
            CacheTester tester = new CacheTester(setAssociative, POS9Data);
            tester.calculateCyclePenalty();
            System.out.println("Block Size: " + i + " Penalty: " + tester.calculateCyclePenalty());
        }

        System.out.println("\n");
    }
}
