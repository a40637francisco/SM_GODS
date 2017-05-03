import generators.bets.GenerateBets;
import generators.betters.GenerateBetters;
import model.LotteryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class App {


    private static final int TERMINATOR = 5;
    private static final int GEN_BETTERS = 1;
    private static final int GEN_BETS = 2;
    public static final int MAKE_BET = 3;
    public static final int CHECK_PRIZES = 4;


    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        int key = -1;
        while (key != TERMINATOR) {

            key = printConsoleGUI(scanner);
            switch (key) {
                case GEN_BETTERS:
                    generateBettersConsole(scanner);
                    break;
                case GEN_BETS:
                    generateBetsConsole(scanner);
                    break;
                case MAKE_BET:
                    generateMakeBetConsole(scanner);
                    break;
                case CHECK_PRIZES:
                    generateCheckPrizesConsole(scanner);
                    break;
            }
            Thread.sleep(1000);
            clearConsole();
        }
    }

    private static void clearConsole() {
        for (int i = 0; i < 13; ++i) System.out.println();

    }

    private static int printConsoleGUI(Scanner scanner) {
        System.out.println("!---------------Menu---------------!");
        System.out.println(" [1] - Generate betters file");
        System.out.println(" [2] - Generate bets file");
        System.out.println(" [3] - Make a bet");
        System.out.println(" [4] - Check prizes");
        System.out.println(" [5] - Close app");
        System.out.println("!----------------------------------!");
        System.out.print("Key: ");
        int key;
        while (true) {
            try {
                key = Integer.parseInt(new Scanner(System.in).next());
                break;
            } catch (NumberFormatException ne) {
                System.out.println("Insert a valid key...");
                System.out.print("Key:");
            }
        }
        return key;
    }


    private static void generateCheckPrizesConsole(Scanner scanner) {
        System.out.print("Number of lottery draws:");
        int number = scanner.nextInt();

        HashMap<Integer, String> lotteryResults = new LotteryDraw()
                .setNumberOfDraws(number)
                .roll();
        LotteryAnalytics analytics = new LotteryPrizesChecker()
                .check(lotteryResults);
        analytics.printPrizes();
    }

    private static void generateMakeBetConsole(Scanner scanner) {

        System.out.print("Num ID:");
        int num = scanner.nextInt(15);
        System.out.print("Bet x1,x2,x3:");
        String bet = scanner.next();
        System.out.print("Date of bet:");
        String date = scanner.next();
        System.out.print("Lottery draw number:");
        int draw = scanner.nextInt();

        boolean result = new BetBuilder()
                .setNumID(num)
                .setBet(bet)
                .setDate(date)
                .setLotteryDraw(draw)
                .addToFile();
        String msg = result ? "Bet added!" : "Can't add bet, not valid";
        System.out.println(msg);
    }

    private static void generateBetsConsole(Scanner scanner) {

        GenerateBets gb = new GenerateBets();

        System.out.print("Initial date:");
        String date = scanner.next();
        gb.setInitialDate(date);
        System.out.print("Number of lottery draws:");
        int drawsNr = scanner.nextInt();
        gb.setNumberOfLotteryDraws(drawsNr);
        System.out.print("Random number of bets per draw per person(Y|N):");
        String random = scanner.next();
        if (random.equals("N")) {
            System.out.print("Number of bets per draw per person:");
            int nrBets = scanner.nextInt();
            gb.setNumberOfBetsPerPersonPerDraw(nrBets);
        } else if (random.equals("Y")) {
            System.out.print("Min number of bets per draw per person:");
            int min = scanner.nextInt();
            gb.setMinNumberOfBetsPerPersonPerDraw(min);
            System.out.print("Max number of bets per draw per person:");
            int max = scanner.nextInt();
            gb.setMaxNumberOfBetsPerPersonPerDraw(max);
        }

        gb.generate();
        System.out.println("Bets file created!");
    }

    private static void generateBettersConsole(Scanner scanner) {

        System.out.print("Number of lines: ");
        int number = scanner.nextInt();
        new GenerateBetters()
                .setNumberOfLines(number)
                .setMinDate("01-01-2016")
                .setMaxDate("31-12-2016")
                .generate();
        System.out.println("Betters file created!");

    }


}
