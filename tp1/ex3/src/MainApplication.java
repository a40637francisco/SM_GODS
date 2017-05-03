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

    private static void getProbs(BufferedReader brPorFile) throws IOException {
        float totalnumberofsymbols = 0F;
        Map<Character,Integer> map = new HashMap<>();

        totalnumberofsymbols = readAllSymbolsFromFile(brPorFile, totalnumberofsymbols, map);
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

    private static void getProbFirstChar(BufferedReader brPorFile) throws IOException {
        float totalnumberofsymbols = 0F;
        Map<Character,Integer> map = new HashMap<>();

        String line;
        while((line = brPorFile.readLine()) != null){
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
        float totalnumberofsymbols = 0F;
        Map<Character,Integer> map = new HashMap<>();

        totalnumberofsymbols = readAllSymbolsFromFile(brPorFile, totalnumberofsymbols, map);
        map = MapUtil.sortByValue(map);
        float finalTotalnumberofsymbols = totalnumberofsymbols;
        float probOfSequence = (map.get('c')/finalTotalnumberofsymbols)*
                            (map.get('h')/finalTotalnumberofsymbols)*
                (map.get('q')/finalTotalnumberofsymbols);
        map.forEach((key, value) -> {
            float res = (value*probOfSequence)/ finalTotalnumberofsymbols;
            System.out.printf("chq%c -> %.10f%% \n",key,res * 100F);
        });
    }

    private static float readAllSymbolsFromFile(BufferedReader brPorFile, float totalnumberofsymbols, Map<Character, Integer> map) throws IOException {
        String line;
        while((line = brPorFile.readLine()) != null){
            totalnumberofsymbols += line.length();
            for (char c:line.toCharArray())
                if(map.containsKey(c)) map.put(c,map.get(c)+1);
                else map.put(c,1);
        }
        return totalnumberofsymbols;
    }

}
