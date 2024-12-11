package Day11_PlutonianPebbles;

import Tools.FileReaderAdvent;
import com.sun.source.tree.NewArrayTree;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static FileReaderAdvent reader = new FileReaderAdvent("src/Day11_PlutonianPebbles/input.txt");
    public static String input;
    public static List<Double> pebbles = new ArrayList<>();
    public static HashMap<Double, Long> pebbleMap = new HashMap<>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        readFile();
        blink(75);
        long count = 0;
        for(long l : pebbleMap.values()){
            count += l;
        }
        System.out.println(count);
        long end = System.currentTimeMillis();
        System.out.println("(" + (end - start) + " ms)");
    }

    public static void readFile(){
        input = reader.readFileAsString();
        String[] inputArray = input.split(" ");
        for(String s : inputArray){
            pebbles.add(Double.parseDouble(s));
            pebbleMap.putIfAbsent(Double.parseDouble(s), 0L);
            pebbleMap.put(Double.parseDouble(s), pebbleMap.get(Double.parseDouble(s))+1);
        }
    }

    public static void blink(int amount){
        for(int i = 0; i < amount; i++){
            long start = System.currentTimeMillis();
            System.out.print(i + " ");
//            doChange();
            part2();
            long end = System.currentTimeMillis();
            System.out.println("(" + (end - start) + " ms)");
        }
    }

    public static void doChange(){
        for(int i = 0; i < pebbles.size(); i++){
            double pebble = pebbles.get(i);
            long digits = (long) Math.log10(pebble) + 1;
            pebbles.remove(i);
            if(pebble == 0){
                pebbles.add(1.0);
            } else if(digits % 2 == 0){
                pebbles.add((Double) (pebble % (Math.pow(10, ((double) digits / 2)))));
                pebbles.add((Double) Math.floor((double) pebble / Math.pow(10, ((double) digits / 2))));
                i++;
            } else {
                pebbles.add(pebble * 2024);
            }
        }
    }

    public static void part2(){
        HashMap<Double, Long> tempMap = new HashMap<>();
        for(int i = 0; i < pebbleMap.size(); i++){
            double pebble = pebbleMap.keySet().toArray(new Double[0])[i];
            long pebbleAmount = pebbleMap.get(pebble);
            long digits = (long) Math.log10(pebble) + 1;
            if(pebble == 0){
                tempMap.merge(1.0, pebbleAmount, Long::sum);
            } else if(digits % 2 == 0){
                Double part1  = (pebble % (Math.pow(10, ((double) digits / 2))));
                tempMap.merge(part1, pebbleAmount, Long::sum);
                Double part2 = Math.floor(pebble / Math.pow(10, ((double) digits / 2)));
                tempMap.merge(part2, pebbleAmount, Long::sum);
            } else {
                Double part1 = pebble * 2024;
                tempMap.merge(part1, pebbleAmount, Long::sum);
            }
        }
        pebbleMap.clear();
        pebbleMap.putAll(tempMap);
    }
}
