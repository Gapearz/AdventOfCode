package Day9_DiskFragment;

import Tools.FileReaderAdvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<String> data = new ArrayList<>();
    private static List<String> emptySpace = new ArrayList<>();
    private static int index = 0;
    private static int fileId = 0;
    private static int pointerEnd;
    private static int fileIdEnd = 0;
    private static int dataEndRemaining = -1;
    private static List<Integer> addedFiles = new ArrayList<>();
    //part 1
    public static double compactData() {
        double checkSum = 0;
        int i = 0;
        while(i < pointerEnd){
            //data first
            if(!data.get(i).equals("0")){
                for(int j = 0; j < Integer.parseInt(data.get(i)); j++){
                    checkSum += fileId * index;
                    index++;
                    System.out.print(fileId + " ");
                    System.out.println(checkSum);
                }
            }
            fileId++;

            //empty space handling
            if(i < emptySpace.size() && !emptySpace.get(i).equals("0")){
                for(int j = 0; j < Integer.parseInt(emptySpace.get(i)); j++){

                    if(dataEndRemaining == -1){
                        dataEndRemaining = Integer.parseInt(data.get(pointerEnd));
                    }

                    if(dataEndRemaining > 0){
                        checkSum += fileIdEnd * index;
                    } else {
                        pointerEnd--;
                        fileIdEnd--;
                        while(pointerEnd >= 0 && data.get(pointerEnd).equals("0")){
                            pointerEnd--;
                            fileIdEnd--;
                        }
                        if(pointerEnd <= i){
                            break;
                        }
                        dataEndRemaining = Integer.parseInt(data.get(pointerEnd));
                        checkSum += fileIdEnd * index;
                    }
                    System.out.print(fileIdEnd+ " ");
                    System.out.println(checkSum);
                    dataEndRemaining--;
                    index++;
                }
            }

            i++;
        }
        System.out.println();
        return checkSum;
    }

    public static int pointer = 0;

    //part 2
    public static double compactDataKeepFiles(){
        index = 0;
        fileId = 0;

        fileIdEnd = data.size() - 1;

        double checkSum = 0;
        pointer = 0;
        while(pointer < (data.size())){
            //data first
            if(!addedFiles.contains(pointer)){
                for(int j = 0; j < Integer.parseInt(data.get(pointer)); j++){
                    checkSum += fileId * index;
                    index++;
                }
            } else if(addedFiles.contains(pointer)){
                index += Integer.parseInt(data.get(pointer));
            }
            fileId++;

            //empty space handling
            if(pointer < emptySpace.size()){
                int emptySpaceSize = Integer.parseInt(emptySpace.get(pointer));

                checkSum = fillGaps(checkSum, emptySpaceSize);
            }
            pointer++;
        }
        System.out.println();
        return checkSum;
    }

    public static double fillGaps(double checkSum, int emptySpaceSize){

        boolean fillerFound = false;
        for(int j = data.size() - 1; j >= 0; j--){
            if(j > pointer && Integer.parseInt(data.get(j)) <= emptySpaceSize && !addedFiles.contains(j)){
                fillerFound = true;
                addedFiles.add(j);
                emptySpaceSize -= Integer.parseInt(data.get(j));
                for(int k = 0; k < Integer.parseInt(data.get(j)); k++){
                    checkSum += (fileIdEnd - (data.size() - 1 - j)) * index;
//                    System.out.print(fileIdEnd - (data.size() - 1 - j) + " index: " + index + " ");
                    index++;
                }
                break;
            }
        }

        if(!fillerFound){
            index += emptySpaceSize;
            return checkSum;
        }

        if(emptySpaceSize == 0){
            return checkSum;
        } else {
            return fillGaps(checkSum, emptySpaceSize);
        }
    }

    public static String toReadableString(double result){
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(result);
    }

    public static void readInput(){
        FileReaderAdvent fileReader = new FileReaderAdvent("src/Day9_DiskFragment/input.txt");
        String[] input = fileReader.readFile();
        String[] line = input[0].split("");
        for(int i = 0; i < line.length; i += 2){
            data.add(line[i]);
            if(i + 1 < line.length){
                emptySpace.add(line[i + 1]);
            }
        }
        pointerEnd = line.length / 2;
        fileIdEnd = data.size() - 1;
        System.out.println("Data: " + data);
        System.out.println("Empty Space: " + emptySpace);
        System.out.println("MaxPointer: " + pointerEnd);
    }
    public static void main(String[] args) {
        readInput();
//        System.out.println("Checksum: " + toReadableString(compactData()));
        System.out.println("Checksum: " + toReadableString(compactDataKeepFiles()));
    }
}
