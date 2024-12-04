package Tools;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

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
}
