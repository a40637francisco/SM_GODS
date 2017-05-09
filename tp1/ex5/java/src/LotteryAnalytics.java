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
        HashMap<Integer, WeekData[]> results = new HashMap<>();
        WeekData[] weekDataList;

        for (Map.Entry<String, List<LotteryData>> entry : dataMap.entrySet()) {
            List<LotteryData> list = entry.getValue();

            for (LotteryData data : list) {
                if (data.getPrize() > 0) {
                    if(!results.containsKey(data.getWeek())){
                        results.put(data.getWeek(), new WeekData[3]);
                    }
                    weekDataList = results.get(data.getWeek());
                    WeekData ed = weekDataList[data.getPrize() - 1];
                    if(ed == null) {
                        ed = new WeekData(data.getPrize());
                    }
                    ed.winners.add(data.getNumID());
                    weekDataList[data.getPrize() - 1 ] = ed;
                }
            }


        }

        for (Map.Entry<Integer, String> weekDraw : lotteryResults.entrySet()) {
            int week = weekDraw.getKey();
            String key = weekDraw.getValue();

            System.out.print("Week: " + week + " Key: "+ key + " Prizes: ");
            if(results.containsKey(week)){
                WeekData[] wd = results.get((week));
                for(WeekData data: wd){
                    if(data == null) continue;
                    System.out.print(data.prize+"ºprize(" + data.winners.size() + ") users: {");
                    String users = "";
                    for(String user: data.winners)
                        users += user+",";
                    System.out.print(users.substring(0, users.length() - 1));
                    System.out.print("} | ");
                }

            }


            //1ºprize(3) {id, id, id} |

            System.out.println();

        }


    }


    private class WeekData{
        private List<String> winners;
        private int prize;

        public WeekData(int prize) {
            this.prize = prize;
            this.winners = new ArrayList<>();
        }
    }
}
