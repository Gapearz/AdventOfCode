package Day7_BridgeRepair;

import Tools.FileReaderAdvent;

import java.text.DecimalFormat;

public class Main {

    public static double bridgeRepair(String[] input){
        double result = 0;
        for(String s : input){
            String[] parts = s.split(": ");
            if(isFixable(Double.parseDouble(parts[0]), parts[1].split(" "))){
                result += Double.parseDouble(parts[0]);
            }
        }
        return result;
    }

    public static boolean isFixable(double result, String[] row){
        double[] rowDouble = new double[row.length];
        for(int i = 0; i < row.length; i++){
            rowDouble[i] = Double.parseDouble(row[i]);
        }
        char[][] operations = generateOperations(row.length - 1);
        for (char[] operationSet : operations) {
            double currentResult = rowDouble[0];
            for (int j = 0; j < operationSet.length; j++) {
                if (operationSet[j] == '+') {
                    currentResult += rowDouble[j + 1];
                } else if (operationSet[j] == '*') {
                    currentResult *= rowDouble[j + 1];
                } else if (operationSet[j] == '|') {
                    int digits = (int) Math.log10(rowDouble[j + 1]) + 1;
                    currentResult = currentResult * Math.pow(10, digits) + rowDouble[j + 1];
                }
            }
            if(currentResult == result){
                return true;
            }
        }
        return false;
    }

    public static char[][] generateOperations(int length){
        int totalCombinations = (int) Math.pow(3, length);
        char[][] operations = new char[totalCombinations][length];
        for (int i = 0; i < totalCombinations; i++) {
            int temp = i;
            for (int j = 0; j < length; j++) {
                int remainder = temp % 3;
                temp /= 3;
                if (remainder == 0) {
                    operations[i][j] = '+';
                } else if (remainder == 1) {
                    operations[i][j] = '*';
                } else {
                    operations[i][j] = '|';
                }
            }
        }
        return operations;
    }

    public static String toReadableString(double result){
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(result);
    }

    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Day7_BridgeRepair/input.txt");
        String[] test = {
                "190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20"
        };
        System.out.println(toReadableString(bridgeRepair(test)));
        System.out.println(toReadableString(bridgeRepair(fileReaderAdvent.readFile())));
    }
}
