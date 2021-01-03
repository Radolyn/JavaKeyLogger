package test;

import java.awt.im.InputContext;
import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        String str = "привет";
        String[] list = str.split("");
        System.out.println(Arrays.toString(list));
    }
}
