package utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 03/05/2017.
 */
public class ArrayUtils {

    public static int[] initArray(int times, int value) {
        int[] toRet = new int[times];
        for (int j = 0; j < times; j++) {
            toRet[j] = value;
        }
        return toRet;
    }

    public static boolean stringArrayContains(String[] array, String numId) {
        for(String c: array){
            if(c == null) continue;
            if(c.equals(numId)) return true;
        }
        return false;
    }

    public static boolean stringArrayHasDuplicates(String[] array) {
        Set<String> lump = new HashSet<>();
        for (String i : array)
        {
            if (lump.contains(i)) return true;
            lump.add(i);
        }
        return false;
    }
}
