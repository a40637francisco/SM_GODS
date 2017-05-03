package generators.betters;

import utils.NumberUtils;

/**
 * Created on 02/05/2017.
 */
public class SimpleUniqueNumberIDGenerator {


    private final int size;
    private int counter = -1;

    public SimpleUniqueNumberIDGenerator(int size) {
        this.size = size;
    }

    public String get() {
        counter+= 1;
        return NumberUtils.fillLeftZeros(counter, size);
    }

}
