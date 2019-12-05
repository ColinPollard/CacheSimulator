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

        //Direct Map Cache ---------------------------------------------------------------
        //Create tester
	    CacheTester DirectTester = new CacheTester(directCache, POS9Data);
	    //Run first without recording
	    DirectTester.calculateHitPercentage();
        //Record results
	    double result = DirectTester.calculateHitPercentage();
        System.out.println("\n" + "DirectCache has a hit/total percentage of " + result);
        System.out.println("DirectCache took a cycle penalty of " + DirectTester.calculateCyclePenalty() + "" + "\n");

        //Fully Associative Cache ---------------------------------------------------------------
        CacheTester AssociativeTester = new CacheTester(associativeCache, POS9Data);
        AssociativeTester.calculateHitPercentage();
        result = AssociativeTester.calculateHitPercentage();
        System.out.println("FullyAssociative has a hit/total percentage of " + result);
        System.out.println("FullyAssociative took a cycle penalty of " + AssociativeTester.calculateCyclePenalty() + "\n");

        //Set Associative Cache ---------------------------------------------------------------
        CacheTester SetAssociativeTester = new CacheTester(setAssociativeCache, POS9Data);
        SetAssociativeTester.calculateHitPercentage();
        result = SetAssociativeTester.calculateHitPercentage();
        System.out.println("SetAssociative has a hit/total percentage of " + result);
        System.out.println("SetAssociative took a cycle penalty of " + SetAssociativeTester.calculateCyclePenalty() + "\n");
    }
}
