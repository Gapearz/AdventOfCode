package Day2_RedNosedReports;

import Tools.FileReaderAdvent;

public class Main {

    public static int checkSafeAmount(String[] input){
        int safeAmount = 0;
        for(int i = 0; i < input.length; i++) {
            String[] numbersString = input[i].split(" ");
            int[] numbers = new int[numbersString.length];
            for (int j = 0; j < numbersString.length; j++) {
                int numberInt = Integer.parseInt(numbersString[j]);
                numbers[j] = numberInt;
            }
            int position = 0;
            int direction = 0;
            boolean safe = true;
            while (position + 1 < numbers.length) {
                if (direction == 0) {
                    if (numbers[position] < numbers[position + 1]) {
                        direction = 1;
                    } else if (numbers[position] > numbers[position + 1]) {
                        direction = -1;
                    }
                }

                if (direction == 1 && numbers[position] < numbers[position + 1]) {
                    int result = Math.abs(numbers[position] - numbers[position + 1]);
                    if (result >= 1 && result <= 3) {
                    } else {
                        safe = false;
                    }
                } else if (direction == -1 && numbers[position] > numbers[position + 1]) {
                    int result = Math.abs(numbers[position] - numbers[position + 1]);
                    if (result >= 1 && result <= 3) {
                    } else {
                        safe = false;
                    }
                } else {
                    safe = false;
                }

                position++;
            }
            if(safe){
                safeAmount++;
            }
        }
        return safeAmount;
    }

    public static int checkSafeAmountWithDampener(String[] input){
        int safeAmount = 0;
        for(int i = 0; i < input.length; i++) {
            String[] numbersString = input[i].split(" ");
            int[] numbers = new int[numbersString.length];
            for (int j = 0; j < numbersString.length; j++) {
                int numberInt = Integer.parseInt(numbersString[j]);
                numbers[j] = numberInt;
            }
            if(checkIfSafe(numbers)){
                safeAmount++;
            } else {
                for(int j = 0; j < numbers.length; j++){
                    int[] newNumbers = copyWithoutIndex(numbers, j);
                    if(checkIfSafe(newNumbers)){
                        safeAmount++;
                        break;
                    }
                }
            }
        }
        return safeAmount;
    }

    public static boolean checkIfSafe(int[] numbers){
        int position = 0;
        int direction = 0;
        boolean safe = true;
        while (position + 1 < numbers.length) {
            if (direction == 0) {
                if (numbers[position] < numbers[position + 1]) {
                    direction = 1;
                } else if (numbers[position] > numbers[position + 1]) {
                    direction = -1;
                }
            }

            if (direction == 1 && numbers[position] < numbers[position + 1]) {
                int result = Math.abs(numbers[position] - numbers[position + 1]);
                if (result >= 1 && result <= 3) {
                } else {
                    safe = false;
                }
            } else if (direction == -1 && numbers[position] > numbers[position + 1]) {
                int result = Math.abs(numbers[position] - numbers[position + 1]);
                if (result >= 1 && result <= 3) {
                } else {
                    safe = false;
                }
            } else {
                safe = false;
            }

            position++;
        }
        return safe;
    }

    public static int[] copyWithoutIndex(int[] input, int index){
        int[] output = new int[input.length - 1];
        int position = 0;
        for(int i = 0; i < input.length; i++){
            if(i == index) continue;
            output[position] = input[i];
            position++;
        }
        return output;
    }

    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Day2_RedNosedReports/input.txt");
        String[] test = {
                "7 6 4 2 1",
                "1 2 7 8 9",
                "9 7 6 2 1",
                "1 3 2 4 5",
                "8 6 4 4 1",
                "1 3 6 7 9"
        };
        System.out.println(checkSafeAmount(fileReaderAdvent.readFile()));
        System.out.println(checkSafeAmountWithDampener(fileReaderAdvent.readFile()));
    }
}
