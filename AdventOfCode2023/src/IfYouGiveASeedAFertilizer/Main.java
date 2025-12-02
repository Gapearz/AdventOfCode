package IfYouGiveASeedAFertilizer;

import Tools.FileReaderAdvent;

import java.util.Arrays;

public class Main {
    public static int lowestLocation(String[] input){
        int lowestLocation = -1;
        String[] seeds = input[0].split(": ")[1].split(" ");
        for(String seed: seeds){
            int stage = 0;
            for(int i = 1; i < input.length; i++){
                int end = 0;
                if(input[i].isEmpty()){
                    i += 1;
                    stage++;
                    continue;
                }
                boolean nextStage = false;
                while(Character.isDigit(input[i + end].charAt(0)) && !nextStage){
                    String[] row = input[i + end].split(" ");
                    if(Integer.parseInt(seed) > Integer.parseInt(row[1]) && Integer.parseInt(seed) < (Integer.parseInt(row[1]) + Integer.parseInt(row[2]))){
                        seed = String.valueOf(Integer.parseInt(row[0]) + (Integer.parseInt(seed) - Integer.parseInt(row[1])));
                        nextStage = true;
                    }
                    end++;
                    if(i + end >= input.length) break;
                    if(input[i + end].isEmpty()) break;
                }
                i += end - 1;
            }
            if(stage == 7) System.out.println(seed);
            if(stage == 7 && (Integer.parseInt(seed) < lowestLocation) || lowestLocation == -1){
                lowestLocation = Integer.parseInt(seed);
            }
        }
        return lowestLocation;
    }
    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/IfYouGiveASeedAFertilizer/input.txt");
        String[] test = {
                "seeds: 79 14 55 13",
                "",
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48",
                "",
                "soil-to-fertilizer map:",
                "0 15 37",
                "37 52 2",
                "39 0 15",
                "",
                "fertilizer-to-water map:",
                "49 53 8",
                "0 11 42",
                "42 0 7",
                "57 7 4",
                "",
                "water-to-light map:",
                "88 18 7",
                "18 25 70",
                "",
                "light-to-temperature map:",
                "45 77 23",
                "81 45 19",
                "68 64 13",
                "",
                "temperature-to-humidity map:",
                "0 69 1",
                "1 0 69",
                "",
                "humidity-to-location map:",
                "60 56 37",
                "56 93 4"
        };
        System.out.println(lowestLocation(test));
    }
}
