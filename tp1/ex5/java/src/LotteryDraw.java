import generators.bets.GenerateBets;

import java.util.HashMap;

/**
 * Created on 03/05/2017.
 */
public class LotteryDraw {

    private int numberOfDraws;

    public LotteryDraw setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
        return this;
    }

    public HashMap<Integer, String> roll() {
        HashMap<Integer, String> list = new HashMap<>(numberOfDraws);
        for (int i = 0; i < numberOfDraws; i++) {
            list.put(i+1, makeKey());
        }
        return list;
    }

    private String makeKey() {
        return GenerateBets.makeBet();
    }
}
