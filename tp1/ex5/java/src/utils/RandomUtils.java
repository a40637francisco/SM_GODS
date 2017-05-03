package utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 03/05/2017.
 */
public class RandomUtils {


    public static int randomInt(int low, int high) {  //[low ; high]
        Random r = new Random();
        high+= 1;
        return r.nextInt(high-low) + low;
    }

    public static int randomInt(int high) {  //[low ; high]
        return randomInt(0, high);
    }

    public static int randomInt(int low, int high, ArrayList<Integer> exclude) {
        int rnd = randomInt(low, high);
        while(exclude.contains(rnd)){
            rnd = randomInt(low, high);
        }
        return rnd;
    }
}
