package generators.betters;

import utils.RandomUtils;

import java.util.List;

import static utils.FileUtils.readFile;

/**
 * Created on 03/05/2017.
 */
public class LocationGetter {

    public static final String FILES_PATH = "../files"; // change ENV variable?

    private List<String> locationsFile;

    public LocationGetter() {
        locationsFile = readFile(FILES_PATH, "Localidades.txt");
    }

    public String get() {
        int random = RandomUtils.randomInt(2, locationsFile.size() - 1);
        return locationsFile.get(random);
    }
}
