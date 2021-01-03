package Translation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Decryptor {
    public static Set<String> keys = Set.of("[alt]", "[tab]", "[shift]", "[escape]", "[backspace]", "[caps lock]", "[ctrl", "Win]", "[backslash]", "[up]", "[down]", "[right]", "[left]");

    public static List<String> english = Arrays.asList("q w e r t y u i o p [ ] a s d f g h j k l ; ' z x c v b n m , .".split(" "));
    public static List<String> russian = Arrays.asList("й ц у к е н г ш щ з х ъ ф ы в а п р о л д ж э я ч с м и т ь б ю".split(" "));

    public static void main(String[] args) throws IOException {
        File myFile;
        FileReader fileReader = null;
        BufferedReader reader = null;

        try {
            myFile = new File("test.txt");

            fileReader = new FileReader(myFile);
            reader = new BufferedReader(fileReader);

            String line;
            ArrayList<String> list = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            String[] lines = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                lines[i] = list.get(i);
            }

            String language = lines[0].substring(0, 2);
            String object = removeSpaces(lines);
            object = object.trim();

            List<String> res = translate(language, object);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    public static String removeSpaces(String[] lines) {
        lines[0] = lines[0].substring(7);

        String s = String.join("\n", lines);
        StringBuilder sb = new StringBuilder(s.length());
        boolean previousSpace = false;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (previousSpace && ch == ' ') {
                continue;
            } else if (previousSpace) {
                previousSpace = false;
            } else if (ch == ' ') {
                previousSpace = true;
            }

            sb.append(ch);
        }

        return sb.toString();
    }

    public static List<String> translate(String language, String object) {

        String[] slices = object.replace("\r\n", "\n").split("\n");

        ArrayList<String> s = new ArrayList<>();

        for (int i = 0; i < slices.length; i++) {

            String[] add = slices[i].split(" ");

            for (int j = 0; j < add.length; j++) {

                if (j + 1 < add.length && add[j].equals("[shift]") && add[j + 1].equals("[alt]")) {

                    if (language.equals("en")) {
                        language = "ru";
                    } else {
                        language = "en";
                    }

                    j += 2;
                }

                if (language.equals("ru") && !keys.contains(add[j])) {
                    String[] app = add[j].split("");

                    StringBuilder str = new StringBuilder();

                    for (int k = 0; k < app.length; k++) {
                        int index = english.indexOf(app[k]);

                        if (index == -1) {
                            str.append(app[k]);
                            continue;
                        }

                        app[k] = russian.get(index);
                        str.append(app[k]);
                    }
                    add[j] = str.toString();
                }
            }

            Collections.addAll(s, add);
            s.add("\n");
        }

        s.remove(s.size() - 1);

        return s;
    }
}