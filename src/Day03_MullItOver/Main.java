package Day03_MullItOver;

import Tools.FileReaderAdvent;

import java.util.regex.Pattern;

public class Main {

    public static int findAndCalculateMul(String input){
        int result = 0;
        int position;
        Pattern pattern = Pattern.compile("\\d+");
        while((position = input.indexOf("mul")) != -1){
            //System.out.println(input);
            int start = position + 3;
            if(input.charAt(start) != '('){
                input = input.substring(position + 3);
                System.out.println("start problem");
                continue;
            }
            int end = input.indexOf(")", start);
            if (end == -1){
                input = input.substring(position + 3);
                System.out.println("end problem");
                continue;
            }
            String[] numbers = input.substring(start + 1, end).split(",");
            if(numbers.length != 2){
                input = input.substring(position + 3);
                System.out.println("substring problem");
                continue;
            }
            if (!pattern.matcher(numbers[0]).matches() || !pattern.matcher(numbers[1]).matches()) {
                input = input.substring(position + 3);
                System.out.println("pattern problem");
                continue;
            }
            int num1 = Integer.parseInt(numbers[0]);
            int num2 = Integer.parseInt(numbers[1]);
            result += num1 * num2;
            input = input.substring(end + 1);
        }

        return result;
    }

    public static int findAndCalculateMulWithDo(String input){
        int result = 0;
        int position;
        Pattern pattern = Pattern.compile("\\d+");
        while((position = input.indexOf("mul")) != -1){
            //System.out.println(input);
            if(input.indexOf("do()") < position && input.contains("do()")){
                input = input.substring(input.indexOf("do()") + 4);
                continue;
            }
            if(input.indexOf("don't()") < position && input.contains("don't()")){
                int doPosition = input.indexOf("do()");
                if(doPosition != -1){
                    input = input.substring(input.indexOf("do()") + 4);
                } else {
                    return result;
                }
                continue;
            }

            int start = position + 3;
            if(input.charAt(start) != '('){
                input = input.substring(position + 3);
                System.out.println("start problem");
                continue;
            }
            int end = input.indexOf(")", start);
            if (end == -1){
                input = input.substring(position + 3);
                System.out.println("end problem");
                continue;
            }
            String[] numbers = input.substring(start + 1, end).split(",");
            if(numbers.length != 2){
                input = input.substring(position + 3);
                System.out.println("substring problem");
                continue;
            }
            if (!pattern.matcher(numbers[0]).matches() || !pattern.matcher(numbers[1]).matches()) {
                input = input.substring(position + 3);
                System.out.println("pattern problem");
                continue;
            }
            int num1 = Integer.parseInt(numbers[0]);
            int num2 = Integer.parseInt(numbers[1]);
            result += num1 * num2;
            input = input.substring(end + 1);
        }

        return result;
    }

    public static int sumMultipleMuls(String[] input){
        int result = 0;
        for (String s : input) {
            result += findAndCalculateMul(s);
        }
        return result;
    }
    public static int sumMultipleMulsWithDo(String[] input){
        StringBuilder sb = new StringBuilder();
        for (String s : input) {
            sb.append(s);
        }
        return findAndCalculateMulWithDo(sb.toString());
    }

    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Day03_MullItOver/input.txt");
        String test = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

        System.out.println(findAndCalculateMul(test));
        System.out.println(findAndCalculateMulWithDo(test));
        System.out.println(sumMultipleMuls(fileReaderAdvent.readFile()));
        System.out.println(sumMultipleMulsWithDo(fileReaderAdvent.readFile()));
    }
}
