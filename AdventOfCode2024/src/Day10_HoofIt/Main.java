package Day10_HoofIt;

import Tools.FileReaderAdvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static FileReaderAdvent fileReaderAdvent = new FileReaderAdvent("src/Day10_HoofIt/input.txt");
    public static String[] lines;
    public static int[][] map;
    public static List<Point> visited = new ArrayList<>();

    public static void main(String[] args) {
        readInput();
        System.out.println(findPaths());
    }

    public static void readInput(){
        lines = fileReaderAdvent.readFile();
        map = fileReaderAdvent.toIntMatrix(lines);
    }

    public static int findPaths(){
        int paths = 0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 0){
                    visited.clear();
                    getPaths(i, j, 1);
                    paths += visited.size();
                }
            }
        }

        return paths;
    }

    public static void getPaths(int i, int j, int number){
        if(i > 0 && map[i - 1][j] == number){
            if(!isTop(number, i - 1, j)){
                getPaths(i - 1, j, number + 1);
            }
        }
        if(i < map.length - 1 && map[i + 1][j] == number){
            if(!isTop(number, i + 1, j)){
                getPaths(i + 1, j, number + 1);
            }
        }
        if(j > 0 && map[i][j - 1] == number){
            if(!isTop(number, i, j - 1)){
                getPaths(i, j - 1, number + 1);
            }
        }
        if(j < map[i].length - 1 && map[i][j + 1] == number){
            if(!isTop(number, i, j + 1)){
                getPaths(i, j + 1, number + 1);
            }
        }
    }

    public static boolean isTop(int number, int x, int y){
        if(number == 9){
            Point top = new Point(x, y);

//            part 1
//            if(!visited.contains(top)){
//                visited.add(top);
//                return true;
//            }

//          part 2
            visited.add(top);
            return true;
        }
        return false;
    }
}
