package HistorianHysteria;

import Tools.FileReaderAdvent;

import java.rmi.dgc.VMID;
import java.util.Arrays;

public class Main {

    public static int getDifference(String[] input){
        int[] left = new int[input.length];
        int[] right = new int[input.length];
        for(int i = 0; i < input.length; i++){
            String[] split = input[i].split("   ");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            left[i] = a;
            right[i] = b;
        }
        int[] sortedLeft = Arrays.stream(left).sorted().toArray();
        int[] sortedRight = Arrays.stream(right).sorted().toArray();

        int difSum = 0;
        for(int i = 0; i < sortedLeft.length; i++){
            difSum += Math.abs(sortedLeft[i] - sortedRight[i]);
        }
        return difSum;
    }

    public static int getOccurrences(String[] input){
        int[] left = new int[input.length];
        int[] right = new int[input.length];
        for(int i = 0; i < input.length; i++){
            String[] split = input[i].split("   ");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            left[i] = a;
            right[i] = b;
        }
        int[] sortedLeft = Arrays.stream(left).sorted().toArray();
        int[] sortedRight = Arrays.stream(right).sorted().toArray();

        int difSum = 0;
        int rightCounter = 0;

        for(int i = 0; i < sortedLeft.length; i++){
            int occurrenceCount = 0;
            while(rightCounter < sortedRight.length - 1 && sortedLeft[i] > sortedRight[rightCounter]){
                rightCounter++;
            }
            while(sortedLeft[i] == sortedRight[rightCounter]){
                rightCounter++;
                occurrenceCount++;
            }
            int leftCounter = 1;
            int medResult = occurrenceCount * sortedLeft[i];
            if(i + 1 < sortedLeft.length && sortedLeft[i] == sortedLeft[i + 1]){
                while(i + 1 < sortedLeft.length && sortedLeft[i] == sortedLeft[i + 1]){
                    leftCounter++;
                    i++;
                }
                medResult = occurrenceCount * sortedLeft[i] * leftCounter;
            }
            difSum += medResult;
        }

        return difSum;
    }


    public static void main(String[] args){
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/HistorianHysteria/input.txt");
        String[] testInput = {
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3"
        };

        System.out.println(getDifference(fileReaderAdvent.readFile()));
        System.out.println(getOccurrences(fileReaderAdvent.readFile()));
    }
}
