package Day06_GuardGallivant;

import Tools.FileReaderAdvent;
public class Main {
    public static int x = 0;
    public static int y = 0;
    public static int obstacles = 0;
    public static String[][] map;
    public static FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Day06_GuardGallivant/input.txt");
    public static String[] test = {
            "....#.....",
            ".........#",
            "..........",
            "..#.......",
            ".......#..",
            "..........",
            ".#..^.....",
            "........#.",
            "#.........",
            "......#..."
    };
    public static void findStart(String[][] map){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                if (map[i][j].equals("^") || map[i][j].equals("v") || map[i][j].equals("<") || map[i][j].equals(">")){
                    x = i;
                    y = j;
                }
                if(map[i][j].equals("#")){
                    obstacles++;
                }
            }
        }
    }

    public static int moveAround(String[][] map){
        int locations = 1;
        int stepsSinceLastLocation = 0;
        //System.out.println("X: " + x + " Y: " + y);
        while (x > 0 && x < map.length - 1 && y > 0 && y < map[0].length - 1){
            //printMap();
            if (stepsSinceLastLocation >= map.length){
                return -1;
            }
            switch (map[x][y]){
                case "^":
                    if (map[x-1][y].equals(".") || map[x-1][y].equals("X")){
                        if(!map[x-1][y].equals("X")){
                            locations++;
                            stepsSinceLastLocation = 0;
                        } else {
                            stepsSinceLastLocation++;
                        }
                        map[x][y] = "X";
                        x--;
                        map[x][y] = "^";
                    } else {
                        map[x][y] = ">";
                    }
                    break;
                case "v":
                    if (map[x+1][y].equals(".") || map[x+1][y].equals("X")){
                        if(!map[x+1][y].equals("X")){
                            locations++;
                            stepsSinceLastLocation = 0;
                        } else {
                            stepsSinceLastLocation++;
                        }
                        map[x][y] = "X";
                        x++;
                        map[x][y] = "v";
                    } else {
                        map[x][y] = "<";
                    }
                    break;
                case "<":
                    if (map[x][y-1].equals(".") || map[x][y-1].equals("X")){
                        if(!map[x][y-1].equals("X")){
                            locations++;
                            stepsSinceLastLocation = 0;
                        } else {
                            stepsSinceLastLocation++;
                        }
                        map[x][y] = "X";
                        y--;
                        map[x][y] = "<";
                    } else {
                        map[x][y] = "^";
                    }
                    break;
                case ">":
                    if (map[x][y+1].equals(".") || map[x][y+1].equals("X")){
                        if(!map[x][y+1].equals("X")){
                            locations++;
                            stepsSinceLastLocation = 0;
                        } else {
                            stepsSinceLastLocation++;
                        }
                        map[x][y] = "X";
                        y++;
                        map[x][y] = ">";
                    } else {
                        map[x][y] = "v";
                    }
                    break;
            }
        }
        return locations;
    }

    public static void createMatrix(String[] input){
        String[][] matrix = new String[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++){
            matrix[i] = input[i].split("");
        }
        map = matrix;
    }
    public static void printMap(){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int putObstacles(){
        int loops = 0;
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                map = null;
                createMatrix(fileReaderAdvent.readFile());
                findStart(map);
                if (map[i][j].equals(".")){
                    map[i][j] = "#";
                    if(moveAround(map) == -1){
                        loops++;
                    }
                }

            }
        }
        return loops;
    }
    public static void main(String[] args) {
        createMatrix(fileReaderAdvent.readFile());
        findStart(map);
        System.out.println(putObstacles());

    }
}
