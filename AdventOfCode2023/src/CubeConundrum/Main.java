package CubeConundrum;

import Tools.FileReaderAdvent;

import java.util.Arrays;

public class Main {
    public static String[] colors = {
            "red",
            "green",
            "blue"
    };
    public static int possibleGames(String[] input){
        int sum = 0;

        int lastAdded = -1;
        boolean impossible = false;
        for(int i = 0; i < input.length; i++){
            String[] sets = input[i].split(": ")[1].split(";");
            for (String set : sets) {
                String[] color = set.split(" ");
                for (int g = 0; g < color.length; g++) {
                    if (color[g].chars().allMatch(Character::isDigit)) {
                        for (int h = 0; h < colors.length; h++) {
                            if (color[g + 1].contains(colors[h])) {
                                if (Integer.valueOf(color[g]) > (h + 12)) {
                                    if (i == lastAdded) continue;
                                    lastAdded = i;
                                    impossible = true;
                                }
                            }
                        }
                    }
                }
            }
            if(!impossible){
                sum += i + 1;
            }
            impossible = false;
        }
        return sum;
    }
    public static int powerOfLeastPossibleCubes(String[] input){
        int power = 0;
        for (String s : input) {
            int[] most = new int[3];
            Arrays.fill(most, 0);
            String[] sets = s.split(": ")[1].split(";");
            for (String set : sets) {
                String[] color = set.split(" ");
                for (int g = 0; g < color.length; g++) {
                    if (color[g].chars().allMatch(Character::isDigit)) {
                        for (int h = 0; h < colors.length; h++) {
                            if (color[g + 1].contains(colors[h])) {
                                if (Integer.parseInt(color[g]) > most[h]) {
                                    most[h] = Integer.parseInt(color[g]);
                                }
                            }
                        }
                    }
                }
            }
            power += most[0] * most[1] * most[2];
        }
        return power;
    }

    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/CubeConundrum/input.txt");
        String[] test = {
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        };
        System.out.println(possibleGames(fileReaderAdvent.readFile()));
        System.out.println(powerOfLeastPossibleCubes(fileReaderAdvent.readFile()));
    }
}
