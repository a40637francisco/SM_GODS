package model;

/**
 * Created on 03/05/2017.
 */
public class LotteryData {

    private final String bets;
    private final String numID;
    private final int week;
    private final int prize;

    public LotteryData(String numID, int week, int prize, String bets) {
        this.numID = numID;
        this.week = week;
        this.prize = prize;
        this.bets = bets;
    }


    public String getBets() {
        return bets;
    }

    public String getNumID() {
        return numID;
    }

    public int getWeek() {
        return week;
    }

    public int getPrize() {
        return prize;
    }
}
