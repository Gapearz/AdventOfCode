package Day5_PrintQueue;

import Tools.FileReaderAdvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static List<String> printQueue = new ArrayList<>();
    public static List<String> rules = new ArrayList<>();
    public static void inputToLists(String[] input){
        boolean emptyLine = false;
        for(String line: input){
            if(line.isEmpty()){
                emptyLine = true;
                continue;
            }
            if(!emptyLine){
                rules.add(line);
            }else{
                printQueue.add(line);
            }
        }
    }

    public static List<String> getRelevantRules(String printQueue){
        List<String> relevantRules = new ArrayList<>();
        for (String rule: rules){
            String[] queueParts = printQueue.split(",");
            for(String part: queueParts){
                if(rule.contains(part)){
                    relevantRules.add(rule);
                    break;
                }
            }
        }
        return relevantRules;
    }
    public static List<String> getRelevantRules(String printQueue, List<String> rules){
        List<String> relevantRules = new ArrayList<>();
        for (String rule: rules){
            if(rule.startsWith(printQueue)){
                relevantRules.add(rule);
            }
        }
        return relevantRules;
    }

    public static int findCorrectOrder(String print){
        List<String> relevantRules = getRelevantRules(print);
        List<String> printParts = new ArrayList<>(Arrays.stream(print.split(",")).toList());
        List<String> doneParts = new ArrayList<>();
        boolean correct = true;
        for(String part: printParts){
            List<String> relevantRulesForPart = getRelevantRules(part, relevantRules);
            if(relevantRulesForPart.isEmpty()){
                doneParts.add(part);
                continue;
            }
            for(String rule: relevantRulesForPart){
                String[] ruleParts = rule.split("\\|");
                if(!doneParts.contains(ruleParts[1])){
                    if(!doneParts.contains(part)){
                        doneParts.add(part);
                    }
                } else {
                    correct = false;
                    break;
                }
            }
        }
        if(correct){
            return Integer.parseInt(doneParts.get((doneParts.size() / 2)));
        } else {
            return 0;
        }
    }

    public static int correctOrder(String print){
        List<String> relevantRules = getRelevantRules(print);
        List<String> printParts = new ArrayList<>(Arrays.stream(print.split(",")).toList());
        List<String> doneParts = new ArrayList<>();
        for (String part: printParts){
            List<String> relevantRulesForPart = getRelevantRules(part, relevantRules);
            int maxIndex = 100;
            boolean works = true;
            for(String rule: relevantRulesForPart){
                String[] ruleParts = rule.split("\\|");
                if(!doneParts.contains(ruleParts[1])){
                } else {
                    works = false;
                    if(getElementIndex(doneParts, ruleParts[1]) < maxIndex){
                        maxIndex = getElementIndex(doneParts, ruleParts[1]);
                    }
                }
            }
            if(works){
                doneParts.add(part);
            } else {
                doneParts.add(maxIndex, part);
            }
        }
        return Integer.parseInt(doneParts.get(doneParts.size() / 2));
    }

    public static int getElementIndex(List<String> list, String element){
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).equals(element)){
                return i;
            }
        }
        return -1;
    }

    public static int getSumOfCorrectOnes(){
        int result = 0;
        for(String print: printQueue){
            if(findCorrectOrder(print) == 0){
                result += correctOrder(print);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FileReaderAdvent fileReader = new FileReaderAdvent("src/Day5_PrintQueue/input.txt");
        String[] test = {
                "47|53",
                "97|13",
                "97|61",
                "97|47",
                "75|29",
                "61|13",
                "75|53",
                "29|13",
                "97|29",
                "53|29",
                "61|53",
                "97|53",
                "61|29",
                "47|13",
                "75|47",
                "97|75",
                "47|61",
                "75|61",
                "47|29",
                "75|13",
                "53|13",
                "",
                "75,47,61,53,29",
                "97,61,53,29,13",
                "75,29,13",
                "75,97,47,61,53",
                "61,13,29",
                "97,13,75,29,47"
        };
        inputToLists(fileReader.readFile());
        System.out.println(getSumOfCorrectOnes());
    }
}
