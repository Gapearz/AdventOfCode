package ScratchCards;

import Tools.FileReaderAdvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static int cardPoints(String[] input){
        int sum = 0;
        for (String s : input) {
            int rowSum = 0;
            String[] winning = s.replace("  ", " ").split(": ")[1].split(" \\| ")[0].split(" ");
            String[] my = s.replace("  ", " ").split(": ")[1].split(" \\| ")[1].split(" ");
            List<String> myList = Arrays.asList(my);
            for (String winNum : winning) {
                if (myList.contains(winNum)) {
                    if (rowSum == 0) {
                        rowSum++;
                    } else {
                        rowSum *= 2;
                    }
                }
            }
            sum += rowSum;
        }
        return sum;
    }
    public static int scratchCardAmount(String[] input){
        int sum = 0;
        Map<Integer, Integer> cards = new HashMap<>();
        int[] count = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            int rowSum = 0;
            String[] winning = input[i].replace("  ", " ").split(": ")[1].split(" \\| ")[0].split(" ");
            String[] my = input[i].replace("  ", " ").split(": ")[1].split(" \\| ")[1].split(" ");
            List<String> myList = Arrays.asList(my);
            for (String winNum : winning) {
                if (myList.contains(winNum)) {
                    rowSum++;
                }
            }
            cards.put(i, rowSum);
            count[i] = 1;
        }
        for(int i = 0; i < cards.size(); i++){
            for(int j = 1; j <= cards.get(i); j++){
                count[i+j] += count[i];
            }
            sum += count[i];
        }
        return sum;
    }
    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/ScratchCards/input.txt");
        String[] test = {
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        };
        System.out.println(cardPoints(fileReaderAdvent.readFile()));
        System.out.println(scratchCardAmount(fileReaderAdvent.readFile()));
    }
}
