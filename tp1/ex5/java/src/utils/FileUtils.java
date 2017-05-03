package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import static java.nio.file.Files.readAllLines;

/**
 * Created on 02/05/2017.
 */
public class FileUtils {


    public static final String FILES_CONFIG_PROPERTIES = "../files/config.properties";


    public static LineGetter getRandomLine(List<String> lines, int init, int last, int[] exclude) {
        int rnd = RandomUtils.randomInt(init, last);
        while(isInIntArray(exclude, rnd)) {
            rnd = RandomUtils.randomInt(init, last);
        }
        if(rnd > lines.size() || rnd < 0) return null;

        return new LineGetter(lines.get(rnd), rnd);
    }

    private static boolean isInIntArray(int[] exclude, int rnd) {
        if(exclude == null || exclude.length == 0 ) return false;
        if(rnd == -1) return true;
        for (int i = 0; i < exclude.length; i++) {
            if(exclude[i] == rnd) return true;
        }
        return false;
    }


    private static String[] CHARSETS = {"Cp1252", "UTF-8", "ISO-8859-1"};


    public static List<String> readFile(String fullPath) {

        Path filePath = Paths.get(fullPath);
        return _readFile(filePath);
    }

    public static List<String> readFile(String path, String fileName) {
        Path filePath = Paths.get(path, fileName);
        return _readFile(filePath);
    }

    private static List<String>_readFile(Path filePath) {
        int index = 0;
        List<String> lines = null;
        boolean found = false;
        Charset charset = Charset.forName(CHARSETS[index++]);
        while(!found) {
            try {
                lines =  readAllLines(filePath, charset);
                found = true;
            } catch (IOException e) {
                charset = Charset.forName(CHARSETS[index++]);
            }
        }
        return lines;
    }

    public static Properties getConfigFileProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(FILES_CONFIG_PROPERTIES);
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void writeFile(String fileName, Supplier<String> makeLine, int numberOfLines) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            String sep, l;
            for (int i = 0; i < numberOfLines; i++) {
                l = makeLine.get();
                sep = i == numberOfLines-1? "":"\n" ;
                writer.print(l + sep);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getWorkingDir() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }

    public static class LineGetter{

        private final String line;
        private final int index;

        public LineGetter(String s, int rnd) {
            this.line = s;
            this.index = rnd;
        }

        public String getLine() {
            return line;
        }

        public int getIndex() {
            return index;
        }
    }


}
