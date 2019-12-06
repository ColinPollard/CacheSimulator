package com.Caches;

public abstract class Cache {

    abstract int getBlockSize();
    abstract void ClearCache();
    abstract boolean ReadFromCache(int address);

    /**
     * Bit masks a portion of an address.
     * @param inputAddress The address to get data from.
     * @param length The number of bits to mask.
     * @param offset How far from the smallest bit to mask from.
     * @return Masked address.
     */
    int MaskedAddress(int inputAddress, int length, int offset)
    {
        //Generate bitmask number
        int bitMask = (int) Math.pow(2,length) - 1;
        bitMask = bitMask << offset;

        //And with bitmask, shift to zero position
        int output = inputAddress & bitMask;
        output = output >> offset;

        return output;
    }

    /**
     * Calculates the log base 2 of input
     * @param x Input integer
     * @return Base 2 of x
     */
    int CalculateLog2 (int x)
    {
        return (int) (Math.log(x) / Math.log(2) + 1e-10);
    }
}
