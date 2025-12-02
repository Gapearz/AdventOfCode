package Day04_CaresSearch;

import Tools.FileReaderAdvent;

public class Main {

    public static int findXmas(String[][] input){
        int results = 0;
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                if(input[i][j].equals("X")){
                    if(i >= 3){ //search up
                        if(input[i-1][j].equals("M") && input[i-2][j].equals("A") && input[i-3][j].equals("S")){
                            results++;
                        }
                    }
                    if (i >= 3 && j >= 3){ //search up left
                        if(input[i-1][j-1].equals("M") && input[i-2][j-2].equals("A") && input[i-3][j-3].equals("S")){
                            results++;
                        }
                    }
                    if(j >=3){ //search left
                        if(input[i][j-1].equals("M") && input[i][j-2].equals("A") && input[i][j-3].equals("S")){
                            results++;
                        }
                    }
                    if(i <= input.length - 4 && j >= 3){ //search down left
                        if(input[i+1][j-1].equals("M") && input[i+2][j-2].equals("A") && input[i+3][j-3].equals("S")){
                            results++;
                        }
                    }
                    if(i <= input.length - 4){ //search down
                        if(input[i+1][j].equals("M") && input[i+2][j].equals("A") && input[i+3][j].equals("S")){
                            results++;
                        }
                    }
                    if(i <= input.length - 4 && j <= input[i].length - 4){ //search down right
                        if(input[i+1][j+1].equals("M") && input[i+2][j+2].equals("A") && input[i+3][j+3].equals("S")){
                            results++;
                        }
                    }
                    if(j <= input[i].length - 4){ //search right
                        if(input[i][j+1].equals("M") && input[i][j+2].equals("A") && input[i][j+3].equals("S")){
                            results++;
                        }
                    }
                    if(i >= 3 && j <= input[i].length - 4){ //search up right
                        if(input[i-1][j+1].equals("M") && input[i-2][j+2].equals("A") && input[i-3][j+3].equals("S")){
                            results++;
                        }
                    }
                }
            }
        }
        return results;
    }

    public static int findMasInX(String[][] input){
        int results = 0;
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                boolean uldr = false;
                boolean dlur = false;
                if(input[i][j].equals("A")){
                    if (i >= 1 && j >= 1 && i <= input.length - 2 && j <= input[i].length - 2){ //search up left - down right
                        if((input[i-1][j-1].equals("M") && input[i+1][j+1].equals("S")) || (input[i-1][j-1].equals("S") && input[i+1][j+1].equals("M"))){
                            uldr = true;
                        }
                    }
                    if(i <= input.length - 2 && j >= 1 && i >= 1 && j <= input[i].length - 2){ //search down left - up right
                        if((input[i+1][j-1].equals("M") && input[i-1][j+1].equals("S")) || (input[i+1][j-1].equals("S") && input[i-1][j+1].equals("M"))){
                            dlur = true;
                        }
                    }
                }
                if (uldr && dlur){
                    results++;
                }
            }
        }
        return results;
    }

    public static String[][] createMatrix(String[] input){
        String[][] matrix = new String[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++){
            matrix[i] = input[i].split("");
        }
        return matrix;
    }
    public static void main(String[] args) {
        FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Day04_CaresSearch/input.txt");
        String[] test = {
                "MMMSXXMASM",
                "MSAMXMSMSA",
                "AMXSXMAAMM",
                "MSAMASMSMX",
                "XMASAMXAMM",
                "XXAMMXXAMA",
                "SMSMSASXSS",
                "SAXAMASAAA",
                "MAMMMXMMMM",
                "MXMXAXMASX"
        };

        System.out.println(findXmas(createMatrix(test)));
        System.out.println(findXmas(createMatrix(fileReaderAdvent.readFile())));
        System.out.println(findMasInX(createMatrix(test)));
        System.out.println(findMasInX(createMatrix(fileReaderAdvent.readFile())));

    }
}
