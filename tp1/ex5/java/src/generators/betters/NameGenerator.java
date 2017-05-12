package generators.betters;

import utils.ArrayUtils;
import utils.FileUtils;
import utils.RandomUtils;

import java.util.List;
import java.util.Random;

import static utils.FileUtils.*;

/**
 * Created on 02/05/2017.
 */
public class NameGenerator {

    public static final String FILES_PATH = "../files"; // change ENV variable?

    private static  final int OneNameOneSurname = 0;
    private static  final int TwoNamesOneSurname = 1;
    private static  final int TwoNamesTwoSurnames = 2;


    List<String> namesFile;
    List<String> surnameFile;

    public NameGenerator() {
        namesFile = readFile(FILES_PATH,"Nomes.txt");
        surnameFile = readFile(FILES_PATH, "Apelidos.txt");
    }

    public String get() {
        String name;
        //1 1 | 2 1 | 2 2
        int random = RandomUtils.randomInt(0, 2);
        switch (random){
            case OneNameOneSurname:
                name = getName(1) + getSurname(1);
                break;
            case TwoNamesOneSurname:
                name = getName(2) + getSurname(1);
                break;
            case TwoNamesTwoSurnames:
                name = getName(2) + getSurname(2);
                break;
            default:
                return null;
        }
        return name;
    }

    private String getSurname(int times) {
        String ret = "";
        LineGetter lg;
        int[] previous = ArrayUtils.initArray(times, -1);
        for (int j = 0; j < times; j++) {
            lg = getRandomLine(surnameFile, 0, surnameFile.size()-1, previous);
            ret+= lg.getLine() + " ";
            previous[j] = lg.getIndex();
        }
        return ret.substring(0, ret.length()-1);
    }


    private String getName(int times) {
        String ret = "";
        LineGetter lg;
        int[] previous = ArrayUtils.initArray(times, -1);
        for (int j = 0; j < times; j++) {
            lg = getRandomLine(namesFile, 0, namesFile.size()-1, previous);
            ret+= lg.getLine() + " ";
            previous[j] = lg.getIndex();
        }
        return ret;
    }


}
