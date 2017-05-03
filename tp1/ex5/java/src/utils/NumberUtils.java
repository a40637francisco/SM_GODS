package utils;

/**
 * Created on 03/05/2017.
 */
public class NumberUtils {


    public static String fillLeftZeros(int counter, int size) {
        StringBuilder sb = new StringBuilder(Integer.toString(counter));
        int len = sb.length();
        for (int i = 0; i < size - len; i++) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }
}
