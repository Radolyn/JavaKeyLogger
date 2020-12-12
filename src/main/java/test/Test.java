package test;

import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try(FileWriter writer = new FileWriter("test.txt", false))
        {
            for(int i = 0; i < 5; i++){
                String text = "Hello!";
                writer.write(text);
                writer.write("\n");
            }

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
