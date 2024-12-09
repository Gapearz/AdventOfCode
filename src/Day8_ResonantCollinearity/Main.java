package Day8_ResonantCollinearity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static List<String> lines = new ArrayList<>();
    private static final HashMap<String, Set<AntennaPosition>> antennas = new HashMap<>();
    private static List<AntennaPosition> antiNodes = new ArrayList<>();
    private static int mapSizeX;
    private static int mapSizeY;

    private static void readInput() {
        try (Stream<String> stream = Files.lines(Paths.get("src/Day8_ResonantCollinearity/input.txt"))) {
            stream.forEach(lines::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapSizeX = lines.getFirst().split("").length;
        mapSizeY = lines.size();

        for(int j = 0; j < lines.size(); j++) {
            String[] parts = lines.get(j).split("");
            for(int i = 0; i < parts.length; i++) {
                if(!parts[i].equals(".")) {
                    antennas.putIfAbsent(parts[i], new HashSet<>());
                    antennas.get(parts[i]).add(new AntennaPosition(i, j));
                }
            }
        }
    }

    private static int calculateAntinodes(){
        int antinodes = 0;
        for(Map.Entry<String, Set<AntennaPosition>> entry : antennas.entrySet()) {
            Set<AntennaPosition> positions = entry.getValue();
            List<AntennaPosition> positionList = new ArrayList<>(positions);
            for (int i = 0; i < positionList.size(); i++) {
                for (int j = i + 1; j < positionList.size(); j++) {
                    antinodes += resonantHarmonics(positionList.get(i), positionList.get(j), 0);
                }
            }
        }
        return antinodes;
    }

    public static int resonantHarmonics(AntennaPosition antenna1, AntennaPosition antenna2, int harmonic) {
        int antinodes = 0;
        boolean isInMap1 = true;
        boolean isInMap2 = true;
        int dx = antenna2.x - antenna1.x; //separation in x direction
        int dy = antenna2.y - antenna1.y; //separation in y direction
        int antiNodes1x = antenna1.x - (dx * harmonic);
        int antiNodes1y = antenna1.y - (dy * harmonic);
        int antiNodes2x = antenna2.x + (dx * harmonic);
        int antiNodes2y = antenna2.y + (dy * harmonic);

        if(antiNodes1x >= 0 && antiNodes1x < mapSizeX && antiNodes1y >= 0 && antiNodes1y < mapSizeY) {
            if (!antiNodes.contains(new AntennaPosition(antiNodes1x, antiNodes1y))){
                System.out.println((antiNodes1x + 1) + " " + (antiNodes1y + 1));
                antiNodes.add(new AntennaPosition(antiNodes1x, antiNodes1y));
                antinodes++;
            }
        } else {
            isInMap1 = false;
        }

        if(antiNodes2x >= 0 && antiNodes2x < mapSizeX && antiNodes2y >= 0 && antiNodes2y < mapSizeY) {
            if(!antiNodes.contains(new AntennaPosition(antiNodes2x, antiNodes2y))) {
                System.out.println((antiNodes2x + 1) + " " + (antiNodes2y + 1));
                antiNodes.add(new AntennaPosition(antiNodes2x, antiNodes2y));
                antinodes++;
            }
        } else {
            isInMap2 = false;
        }

        if(isInMap1 || isInMap2){
            antinodes += resonantHarmonics(antenna1, antenna2, harmonic + 1);
        }

        return antinodes;
    }

    public static void main(String[] args) {
        readInput();
        System.out.println(calculateAntinodes());
    }

    public static class AntennaPosition{
        public int x;
        public int y;
        private AntennaPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AntennaPosition that = (AntennaPosition) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
