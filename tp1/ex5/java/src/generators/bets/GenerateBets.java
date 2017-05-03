package generators.bets;

import generators.betters.DateGenerator;
import model.User;
import utils.ArrayUtils;
import utils.FileUtils;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

/**
 * Created on 03/05/2017.
 */
public class GenerateBets {


    public static final String FILE_NAME = "apostas.txt";

    private ArrayList<String> betsList;

    private String initialDate;
    private int numberOfLotteryDraws;
    private int numberOfBetsPerPersonPerDraw;
    private boolean randomNumberOfBetsPErPersonPerDraw;
    private int maxNumberOfBetsPerPersonPerDraw;
    private int minNumberOfBetsPerPersonPerDraw;
    private int numberOfBetters;

    public GenerateBets setInitialDate(String initialDate) {
        this.initialDate = initialDate;
        return this;
    }

    public GenerateBets setNumberOfLotteryDraws(int numberOfLotteryDraws) {
        this.numberOfLotteryDraws = numberOfLotteryDraws;
        return this;
    }

    public GenerateBets setNumberOfBetsPerPersonPerDraw(int numberOfBetsPerPersonPerDraw) {
        this.numberOfBetsPerPersonPerDraw = numberOfBetsPerPersonPerDraw;
        if (numberOfBetsPerPersonPerDraw == -1) randomNumberOfBetsPErPersonPerDraw = true;
        return this;
    }

    public GenerateBets setMaxNumberOfBetsPerPersonPerDraw(int maxNumberOfBetsPerPersonPerDraw) {
        this.maxNumberOfBetsPerPersonPerDraw = maxNumberOfBetsPerPersonPerDraw;
        return this;
    }

    public GenerateBets setMinNumberOfBetsPerPersonPerDraw(int minNumberOfBetsPerPersonPerDraw) {
        this.minNumberOfBetsPerPersonPerDraw = minNumberOfBetsPerPersonPerDraw;
        return this;
    }


    public GenerateBets() {
        Properties configFile = FileUtils.getConfigFileProperties();
        if (configFile == null) return;

        this.initialDate = configFile.getProperty("betsInitialDate") == null ? "01-01-2017" : configFile.getProperty("betsInitialDate");
        this.numberOfLotteryDraws = 10;
        this.numberOfBetsPerPersonPerDraw = 1;
        this.randomNumberOfBetsPErPersonPerDraw = true;
        this.maxNumberOfBetsPerPersonPerDraw = 1;
        this.minNumberOfBetsPerPersonPerDraw = 0;
        this.numberOfBetters = 100;

        this.betsList = new ArrayList<>();
    }


    public void generate() {
        String[] prevUserId = new String[numberOfBetters];
        int[] index = {0};
        for (int i = 0; i < numberOfBetters; i++, index[0] = i) {
            User better = getRandomUser((user) ->{
               if( index[0] >= bettersList.size() ) return true;
               return !ArrayUtils.stringArrayContains(prevUserId, user.getNumId());
            });

            if( index[0] >= bettersList.size() ) break;
            if(better == null) break;
            prevUserId[i] = better.getNumId();

            int lotteryDraw = 1;
            for (; lotteryDraw <= numberOfLotteryDraws; lotteryDraw++) {
                makeBetsOnDraw(lotteryDraw, better);
            }
        }
        int fileIndex[] = {0};
        FileUtils.writeFile(FILE_NAME,
                () -> betsList.get(fileIndex[0]++),
                betsList.size()
        );

    }

    private void makeBetsOnDraw(int lotteryDraw, User better) {
        if (randomNumberOfBetsPErPersonPerDraw) {
            numberOfBetsPerPersonPerDraw = RandomUtils.randomInt(minNumberOfBetsPerPersonPerDraw, maxNumberOfBetsPerPersonPerDraw);
        }
        for (int i = 0; i < numberOfBetsPerPersonPerDraw; i++) {
            String date = makeDate(better.getRegistryDate(), lotteryDraw);
            if (date == null) {
                System.out.println("User " + better.getNumId() + " registed after the lottery draw #" + lotteryDraw);
                break;
            }
            String bet = makeBet();
            writeBet(better.getNumId(), bet, date, lotteryDraw);
        }
    }

    private void writeBet(String numId, String bet, String date, int lotteryDraw) {
        String line = numId + " | " + bet + " | " + date + " | " + lotteryDraw;
        betsList.add(line);
    }

    public static String makeBet() {
        int[] bets = new int[3];
        ArrayList<Integer> previousBets = new ArrayList<>(3);
        for (int i = 0; i < bets.length; i++) {
            bets[i] = RandomUtils.randomInt(1, 15, previousBets);
            previousBets.add(bets[i]);
        }
        return bets[0] + "," + bets[1] + "," + bets[2];
    }

    private String makeDate(String registryDate, int lotteryDraw) {
        return DateGenerator.calculateDate(registryDate, initialDate, lotteryDraw);
    }


    private List<String> bettersList;

    private User getRandomUser(Predicate<User> validate) {
        if (bettersList == null)
            bettersList = FileUtils.readFile(FileUtils.getWorkingDir(), "apostadores.txt");
        boolean found = false;
        String line;
        User u = null;
        while (!found) {
            line = FileUtils.getRandomLine(bettersList, 0, bettersList.size()-1, null).getLine();
            u = new User(getNum(line), getDate(line));
            if (validate.test(u)) {
                found = true;
            }
        }
        return u;
    }

    private String getDate(String line) {
        String num = line.split("\\|")[3];
        return num.replaceAll(" ", "");
    }

    private String getNum(String line) {
        String num = line.split("\\|")[0];
        return num.substring(0, num.length() - 1);
    }



}
