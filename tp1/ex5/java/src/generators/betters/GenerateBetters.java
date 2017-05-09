package generators.betters;

import utils.FileUtils;

/**
 * Created on 02/05/2017.
 */
public class GenerateBetters {


    public static final int NUM_ID_SIZE = 8;
    private String fileName = "apostadores.txt";

    private int numberOfLines = 10;
    private String minDate;
    private String maxDate;

    public GenerateBetters() {
        this.numberOfLines = 10;
        this.minDate = "01-01-2015";
        this.maxDate = "31-12-2015";
    }

    public GenerateBetters setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
        return this;
    }

    public GenerateBetters setOutputFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public GenerateBetters setMinDate(String minDate) {
        this.minDate = minDate;
        return this;
    }

    public GenerateBetters setMaxDate(String maxDate) {
        this.maxDate = maxDate;
        return this;
    }


    public void generate() {
        FileUtils.writeFile(fileName, this::makeLine, numberOfLines);
    }


    private String makeLine() {
        return makeUniqueNum() + " | "+
                makeName() + " | " +
                makeLocation() + " | " +
                makeDate();
    }


    SimpleUniqueNumberIDGenerator numberGen;

    private String makeUniqueNum() {
        if (numberGen == null)
            numberGen = new SimpleUniqueNumberIDGenerator(NUM_ID_SIZE);
        return numberGen.get();
    }

    NameGenerator nameGen;

    private String makeName() {
        if (nameGen == null)
            nameGen = new NameGenerator();
        return nameGen.get();
    }

    LocationGetter locationGetter;
    private String makeLocation() {
        if (locationGetter == null)
            locationGetter = new LocationGetter();
        return locationGetter.get();
    }

    DateGenerator dateGen;
    private String makeDate() {
        if (dateGen == null)
            dateGen = new DateGenerator().setMin(minDate).setMax(maxDate);
        return dateGen.get();

    }



}
