import generators.bets.GenerateBets;
import model.LotteryData;
import utils.ArrayUtils;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static utils.FileUtils.readFile;

/**
 * Created on 03/05/2017.
 */
public class LotteryPrizesChecker {


    private List<String> betsList;

    public LotteryPrizesChecker() {
        betsList = readFile(FileUtils.getWorkingDir(), GenerateBets.FILE_NAME);
    }

    public LotteryAnalytics check(HashMap<Integer, String> lotteryResults) {
        LotteryAnalytics analytics = new LotteryAnalytics(lotteryResults);

        for(String betLine: betsList) {
            String numID = getNum(betLine);
            int week = getWeek(betLine);
            String bets = getBets(betLine);
            String weekKey = lotteryResults.get(week);
            if(weekKey == null) continue;
            int prize = checkBet(bets, weekKey);

            LotteryData data = new LotteryData(numID, week, prize, bets);

            analytics.add(data);

        }
        return analytics;
    }

    private int checkBet(String bets, String weekKey) {
        String[] betsArray = bets.split(",");
        String[] keyArray = weekKey.split(",");
        int matches = 0;
        for (int i = 0; i < betsArray.length; i++) {
            if(ArrayUtils.stringArrayContains(keyArray, betsArray[i])) matches++;
        }
        return matches;
    }

    private String getBets(String line) {
        String bets = line.split("\\|")[1];
        bets = bets.substring(1, bets.length() - 1);
        return bets;
    }

    private int getWeek(String line) {
        String num = line.split("\\|")[3];
        num = num.substring(1, num.length());
        return Integer.parseInt(num);

    }

    private String getNum(String line) {
        String num = line.split("\\|")[0];
        return num.substring(0, num.length() - 1);
    }
}
