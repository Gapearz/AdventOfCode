package Trebuchet;
import Tools.FileReaderAdvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static int getCalibrationNumber(String[] a){
        int sum = 0;
        for(String b:a){
            char first = 0;
            char last = ' ';
            for(int i = 0; i < b.length(); i++){
                if(Character.isDigit(b.charAt(i))){
                    if(first == 0){
                        first = b.charAt(i);
                    }
                    last = b.charAt(i);
                }
            }
            sum += Integer.parseInt(first + "" + last);
        }
        return sum;
    }
    public static int getCalibrationNumberString(String[] a){
        int sum = 0;
        String[] numbers = {
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine"
        };
        for(String b:a){
            char first = 0;
            int firstPosition = -1;
            char last = ' ';
            int lastPosition = -1;
            for(int i = 0; i < numbers.length; i++){
                if(!b.contains(numbers[i])) continue;
                String substring = b;
                int cut = 0;
                int lastSub = 0;
                if(b.indexOf(numbers[i]) < firstPosition || firstPosition == -1){
                    first = String.valueOf(i + 1).charAt(0);
                    firstPosition = b.indexOf(numbers[i]);
                }
                while(substring.length() > numbers[i].length()){
                    if(substring.contains(numbers[i])) {
                        substring = substring.substring(substring.indexOf(numbers[i]) + numbers[i].length());
                        cut = b.length() - substring.length();
                        lastSub = substring.indexOf(numbers[i]) + cut;
                        if (lastSub > lastPosition || lastPosition == -1) {
                            last = String.valueOf(i + 1).charAt(0);
                            lastPosition = lastSub;
                        }
                    } else {
                        break;
                    }
                }
            }
            for(int i = 0; i < b.length(); i++){
                if(Character.isDigit(b.charAt(i))){
                    if(first == 0 || i < firstPosition){
                        first = b.charAt(i);
                        firstPosition = i;
                    }
                    if(i > lastPosition){
                        last = b.charAt(i);
                        lastPosition = i;
                    }
                }
            }
            sum += Integer.parseInt(first + "" + last);
        }
        return sum;
    }
    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Trebuchet/input.txt");
        String[] test = {
                "7mhjsq7ninetwo3tbnkglngltwo"
        };
        //System.out.println(getCalibrationNumber(fileReaderAdvent.readFile()));
        System.out.println(getCalibrationNumberString(fileReaderAdvent.readFile()));
    }
}