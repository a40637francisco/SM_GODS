import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("1- Percentagem ocurrência Simbolos \n 2- Percentagem de ocorrência do primeiro símbolo de cada palavra \n" +
                "3-Probabilidade de ocorrência de cada símbolo, numa palavra, após a ocorrência dos símbolos ‘c’, ‘h’ e ‘q’");

        char option = in.nextLine().charAt(0);

        String filePath = new File("").getAbsolutePath()+"/ListaPalavras";
        String porFilePath = filePath + "/ListaPalavrasPT.txt";
        String engFilePath = filePath + "/ListaPalavrasEN.txt";

        InputStream in1,in2;
        BufferedReader brPor,brEng;
        try {
            in1 = new FileInputStream(new File(porFilePath));
            in2 = new FileInputStream(new File(engFilePath));
            brPor = new BufferedReader ( new InputStreamReader(in1, StandardCharsets.ISO_8859_1));
            brEng =  new BufferedReader ( new InputStreamReader(in2, StandardCharsets.ISO_8859_1));

            switch(option){
                case '1' : probSimbolos(brPor,brEng); break;
                case '2' : probPrimeiroSimbolo(brPor,brEng); break;
                case '3' : probOcurAposPalavra(brPor,brEng); break;
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
        float totalnumberofsymbols = 0F;
        Map<Character,Integer> map = new HashMap<>();

        totalnumberofsymbols = readAllSymbolsFromFile(br, totalnumberofsymbols, map);
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

    private static void probOcurAposPalavra(BufferedReader brPorFile, BufferedReader brEngFile) throws IOException {
        System.out.println("Probabilidades da Lingua Portuguesa");
        probOfCHQchars(brPorFile);
        System.out.println("Probabilidades da Lingua Inglesa");
        probOfCHQchars(brEngFile);
    }

    private static void probOfCHQchars(BufferedReader br) throws IOException {
        float totalnumberofsymbols = 0F;
        Map<Character,Integer> map = new HashMap<>();

        totalnumberofsymbols = readAllSymbolsFromFile(br, totalnumberofsymbols, map);
        map = MapUtil.sortByValue(map);
        float finalTotalnumberofsymbols = totalnumberofsymbols;
        float probCChar = (map.get('c')/finalTotalnumberofsymbols);
        float probHChar = (map.get('h')/finalTotalnumberofsymbols);
        float probQChar = (map.get('q')/finalTotalnumberofsymbols);
        map.forEach((key, value) -> {
            float symbProb = (value/ finalTotalnumberofsymbols);
            float symbCProb = symbProb * probCChar;
            float symbHProb = symbProb * probHChar;
            float symbQProb = symbProb * probQChar;
            System.out.printf("c%c -> %.3f%% h%c -> %.3f%% q%c -> %.3f%% \n",key,symbCProb * 100F,key,symbHProb*100F,key,symbQProb*100F);
        });
    }

    private static float readAllSymbolsFromFile(BufferedReader br, float totalnumberofsymbols, Map<Character, Integer> map) throws IOException {
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
