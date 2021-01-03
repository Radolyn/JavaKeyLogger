package FileReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Decryptor {
    static String[] keys = {"[alt]", "[tab]", "[shift]", "[escape]", "[backspace]", "[caps lock]", "[ctrl", "Win]", "[backslash]", "[up]", "[down]", "[right]", "[left]"};

    static String[] english = "q w e r t y u i o p [ ] a s d f g h j k l ; ' z x c v b n m , .".split(" ");
    static String[] russian = "й ц у к е н г ш щ з х ъ ф ы в а п р о л д ж э я ч с м и т ь б ю".split(" ");

    public static void main(String[] args) {
        try {
            File myFile = new File("test.txt");
            FileReader fileReader = new FileReader(myFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            ArrayList<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            String[] lines = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                lines[i] = list.get(i);
            }
            String language = lines[0].substring(0, 2);
            String object = optimization(lines);
            object.trim();
            translation(language, object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String optimization(String[] lines) {
        lines[0] = lines[0].substring(7);
        ArrayList<String[]> add = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            add.add(lines[i].split(""));
        }
        for (int i = 0; i < add.size(); i++) {
            for (int j = 0; j < add.get(i).length - 1; j++) {
                if (add.get(i)[j].equals(" ") && add.get(i)[j + 1].equals(" ")) {
                    add.get(i)[j] = "";
                }
            }
        }
        String main = "";
        for (int i = 0; i < add.size(); i++) {
            for (int j = 0; j < add.get(i).length; j++) {
                main += add.get(i)[j];
            }
            main += "\n";
        }
        return main;
    }

    public static void translation(String language, String object) {
        String[] slices = object.split("\n");
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
                if (language.equals("ru") && !Arrays.asList(keys).contains(add[j])) {
                    String[] app = add[j].split("");
                    StringBuilder str = new StringBuilder();
                    for (int k = 0; k < app.length; k++) {
                        int index = Arrays.asList(english).indexOf(app[k]);
                        if (index == -1) {
                            str.append(app[k]);
                            continue;
                        }
                        app[k] = Arrays.asList(russian).get(index);
                        str.append(app[k]);
                    }
                    add[j] = str.toString();
                }
            }
            Collections.addAll(s, add);
            s.add("\n");
        }
        s.remove(s.size() - 1);
        System.out.println(s);
    }
}