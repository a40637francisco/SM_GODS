import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("1- Percentagem ocurrência Simbolos \n2- Percentagem de ocorrência do primeiro símbolo de cada palavra \n" +
                "3-Probabilidade de ocorrência de cada símbolo, numa palavra, após a ocorrência dos símbolos ‘c’, ‘h’ e ‘q’");

        char option = in.nextLine().charAt(0);

        String filePath = new File("").getAbsolutePath()+"/ListaPalavras";
        String porFilePath = filePath + "/ListaPalavrasPT.txt";
        String engFilePath = filePath + "/ListaPalavrasEN.txt";

        FileInputStream in1,in2;
        BufferedReader brPor,brEng;
        try {
            in1 = new FileInputStream(new File(porFilePath));
            in2 = new FileInputStream(new File(engFilePath));
            brPor = new BufferedReader ( new InputStreamReader(in1, StandardCharsets.ISO_8859_1));
            brEng =  new BufferedReader ( new InputStreamReader(in2, StandardCharsets.ISO_8859_1));

            switch(option){
                case '1' : probSimbolos(brPor,brEng); break;
                case '2' : probPrimeiroSimbolo(brPor,brEng); break;
                case '3' : probOcurAposPalavra(in1,in2); break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void probSimbolos(BufferedReader brPorFile, BufferedReader brEngFile) throws IOException {
        System.out.println("Probabilidades da Lingua Portuguesa");
        getProbs(brPorFile);
        System.out.println("Probabilidades da Lingua Inglesa");
        getProbs(brEngFile);
    }

    private static void getProbs(BufferedReader br) throws IOException {

        Map<Character,Integer> map = new HashMap<>();

        float totalnumberofsymbols = readAllSymbolsFromFile(br, map);
        map = MapUtil.sortByValue(map);
        float finalTotalnumberofsymbols = totalnumberofsymbols;
        map.forEach((key, value) -> {
            float res = (float)value/ finalTotalnumberofsymbols;
            System.out.printf("%c -> %.5f%% \n",key,res * 100F);
        });
    }

    private static void probPrimeiroSimbolo(BufferedReader brPorFile, BufferedReader brEngFile) throws IOException{
        System.out.println("Probabilidades da Lingua Portuguesa");
        getProbFirstChar(brPorFile);
        System.out.println("Probabilidades da Lingua Inglesa");
        getProbFirstChar(brEngFile);
    }

    private static void getProbFirstChar(BufferedReader br) throws IOException {
        float totalnumberofsymbols = 0F;
        Map<Character,Integer> map = new HashMap<>();

        String line;
        while((line = br.readLine()) != null){
            if(line.length() > 0){
                ++totalnumberofsymbols;
                char c = line.charAt(0);
                if(map.containsKey(c)) map.put(c,map.get(c)+1);
                else map.put(c,1);
            }
        }
        map = MapUtil.sortByValue(map);
        float finalTotalnumberofsymbols = totalnumberofsymbols;
        map.forEach((key, value) -> {
            float res = (float)value/ finalTotalnumberofsymbols;
            System.out.printf("%c -> %.5f%% \n",key,res * 100F);
        });
    }

    private static void probOcurAposPalavra(FileInputStream inPorFile, FileInputStream inEngFile) throws IOException {

        System.out.println("Probabilidades da Lingua Portuguesa");
        probOfCHQchars(inPorFile);
        System.out.println("Probabilidades da Lingua Inglesa");
        probOfCHQchars(inEngFile);
    }

    private static void probOfCHQchars(FileInputStream in) throws IOException {
        BufferedReader br = new BufferedReader ( new InputStreamReader(in, StandardCharsets.ISO_8859_1));
        System.out.println("Character C");
        getProbAfterCharOccur(br,'c');
        in.getChannel().position(0);
        br = new BufferedReader(new InputStreamReader(in));
        System.out.println("Character H");
        getProbAfterCharOccur(br,'h');
        in.getChannel().position(0);
        System.out.println("Character Q");
        br = new BufferedReader(new InputStreamReader(in));
        getProbAfterCharOccur(br,'q');
    }

    private static void getProbAfterCharOccur(BufferedReader br, char char_to_search) throws IOException {
        Map<Character,Integer> map = new HashMap<>();

        float totalnumberofsymbols = 0F;
        String line;
        while((line = br.readLine()) != null){
            if(line.indexOf(char_to_search) != -1) {
                totalnumberofsymbols ++;
                for(int i=1; i<line.length();++i){
                    if(line.charAt(i) == char_to_search) {
                        char c = line.charAt(i-1);
                        if (map.containsKey(c)) map.put(c, map.get(c) + 1);
                        else map.put(c, 1);
                    }
                }
            }
        }
        map = MapUtil.sortByValue(map);
        float finalTotalnumberofsymbols = totalnumberofsymbols;
        map.forEach((key, value) -> {
            float symbProb = (value/ finalTotalnumberofsymbols);
            System.out.printf("%c%c -> %.3f%% \n",char_to_search,key,symbProb * 100F);
        });
    }

    private static float readAllSymbolsFromFile(BufferedReader br, Map<Character, Integer> map) throws IOException {
        float totalnumberofsymbols = 0F;
        String line;
        while((line = br.readLine()) != null){
            totalnumberofsymbols += line.length();
            for (char c:line.toCharArray())
                if(map.containsKey(c)) map.put(c,map.get(c)+1);
                else map.put(c,1);
        }
        return totalnumberofsymbols;
    }

}
