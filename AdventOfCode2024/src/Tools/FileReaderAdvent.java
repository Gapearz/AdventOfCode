package Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileReaderAdvent {
    static String file;
    public FileReaderAdvent(String file){
        this.file = file;
    }
    public String[] readFile(){
        List<String> lines = new ArrayList<>();
        try{
            BufferedReader bf = new BufferedReader(new java.io.FileReader(file));
            String line;
            while((line = bf.readLine()) != null){
                lines.add(line);
            }
            bf.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lines.toArray(new String[0]);
    }

    public String readFileAsString(){
        return readFile()[0];
    }

    public List<String> readFileAsList(String file){
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach(lines::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public String[][] toMatrix(String[] input){
        String[][] matrix = new String[input.length][];
        for (int i = 0; i < input.length; i++){
            matrix[i] = input[i].split("");
        }
        return matrix;
    }

    public int[][] toIntMatrix(String[] input){
        int[][] matrix = new int[input.length][];
        for (int i = 0; i < input.length; i++){
            String[] inputLine = input[i].split("");
            int[] intLine = new int[inputLine.length];
            for (int j = 0; j < inputLine.length; j++){
                intLine[j] = Integer.parseInt(inputLine[j]);
            }
            matrix[i] = intLine;
        }
        return matrix;
    }
}
