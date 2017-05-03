import model.LotteryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 03/05/2017.
 */
public class LotteryAnalytics {


    private final HashMap<Integer, String> lotteryResults;

    private final HashMap<String, List<LotteryData>> dataMap;

    public LotteryAnalytics(HashMap<Integer, String> lotteryResults) {
        this.lotteryResults = lotteryResults;
        this.dataMap = new HashMap<>();
    }


    public void add(LotteryData data) {
        List<LotteryData> l = dataMap.get(data.getNumID());
        if (l == null)
            l = new ArrayList<>();
        l.add(data);
        dataMap.put(data.getNumID(), l);
    }

    public void printPrizes() {
        for (Map.Entry<String, List<LotteryData>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<LotteryData> list = entry.getValue();
            System.out.print("user: " + key + " Prizes: ");
            for (LotteryData data : list) {
                if (data.getPrize() > 0)
                    System.out.print(" lottery draw:#" + data.getWeek() +" -"+ data.getPrize() +"º prize |");
            }
            System.out.println();
        }
    }
}
