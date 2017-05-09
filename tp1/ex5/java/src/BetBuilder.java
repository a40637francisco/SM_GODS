import generators.bets.GenerateBets;
import generators.betters.GenerateBetters;
import utils.ArrayUtils;
import utils.FileUtils;
import utils.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created on 03/05/2017.
 */
public class BetBuilder {


    private int numID;
    private String bet;
    private String date;
    private int lotteryDrawNumber;


    public BetBuilder setNumID(int numID) {
        this.numID = numID;
        return this;
    }

    public BetBuilder setBet(String bet) {
        this.bet = bet;
        return this;
    }
    public BetBuilder setDate(String date) {
        this.date = date;
        return this;
    }
    public BetBuilder setLotteryDraw(int val) {
        this.lotteryDrawNumber = val;
        return this;
    }

    public boolean addToFile() {
        if(!isValid()){
            return false;
        }
        try {
            Files.write(Paths.get(FileUtils.getWorkingDir(), GenerateBets.FILE_NAME), ("\n" + this.toString()).getBytes(), StandardOpenOption.APPEND);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValid() {
        String[] bets = bet.split(",");
        if(ArrayUtils.stringArrayHasDuplicates(bets)) return false;
        // todo add more!
        return true;
    }


    @Override
    public String toString() {
        String num = NumberUtils.fillLeftZeros(numID, GenerateBetters.NUM_ID_SIZE);
        return num
                + " | " + bet
                + " | " + date
                + " | " + lotteryDrawNumber;
    }



}
