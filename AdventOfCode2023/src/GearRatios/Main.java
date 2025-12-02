package GearRatios;

import Tools.FileReaderAdvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static int partNumberSum(String[] input){
        int sum = 0;
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length(); j++){
                int k = 1;
                boolean add = false;
                if(Character.isDigit(input[i].charAt(j))){
                    int start = j;
                    int end = j;
                    if(end >= input[i].length() - 1) break;
                    while(Character.isDigit(input[i].charAt(j + k))){
                        end = (j + k);
                        k++;
                        if(end >= input[i].length() - 1) break;
                    }
                    String num = "";
                    for(int n = 0; n <= (end - start); n++){
                        num = num.concat(input[i].charAt(n + start) + "");
                    }
                    for(int l = i - 1; l <= i + 1; l++){
                        if(l == -1 || l == input.length) continue;
                        for(int m = start - 1; m <= end + 1; m++){
                            if(m == -1 || m == input[l].length() || add) continue;
                            if(input[l].charAt(m) != '.' && !Character.isDigit(input[l].charAt(m))){
                                add = true;
                                j = end + 1;
                            }
                        }
                    }
                    if(add){
                        sum += Integer.parseInt(num);
                    }
                }
            }
        }
        return sum;
    }
    public static int gearRatio(String[] input){
        int sum = 0;
        for(int i = 0; i < input.length; i++){
            if(!input[i].contains("*")) continue;
            for(int j = 0; j < input[i].length(); j++){
                List<Integer> numbers = new ArrayList<>();
                boolean add = false;
                if(input[i].charAt(j) == '*'){
                    for(int l = i - 1; l <= i + 1; l++){
                        if(l == -1 || l == input.length) continue;
                        for(int m = j - 1; m <= j + 1; m++){
                            if(m == -1 || m == input[l].length()) continue;
                            if(Character.isDigit(input[l].charAt(m))){
                                int start = m;
                                int end = 0;
                                String num = "";
                                while(Character.isDigit(input[l].charAt(start))){
                                    end = start;
                                    if(start == 0) break;
                                    start--;
                                }
                                while(Character.isDigit(input[l].charAt(end))){
                                    num = num.concat(input[l].charAt(end) + "");
                                    end++;
                                    if(end >= input[l].length()) break;
                                };
                                m = end;
                                if(!num.equals("")){
                                    numbers.add(Integer.parseInt(num));
                                    System.out.println(numbers);
                                }
                            }
                        }
                    }
                }
                if(numbers.size() == 2){
                    sum += numbers.get(0) * numbers.get(1);
                }
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/GearRatios/input.txt");
        String[] test = {
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        };
        System.out.println(partNumberSum(fileReaderAdvent.readFile()));
        System.out.println(gearRatio(fileReaderAdvent.readFile()));
    }
}
