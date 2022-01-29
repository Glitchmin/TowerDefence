package Code.map_handling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

import static java.lang.System.out;

public class Map {
    private Integer width;
    private Integer height;
    private Landscape[][] landscapesArray;
    //private final MapObject[][] mapObjectsArray;

    public Map(int file_number) {
        getLandscapeFromFile(file_number);
    }

    private void getLandscapeFromFile(int file_number) {
        //based on https://techvidvan.com/tutorials/read-csv-file-in-java/
        try {
            File file = new File("src/main/resources/landscape/landscape" + file_number + ".csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            String size_line = br.readLine();
            String[] size_array = size_line.split(";");
            width = Integer.parseInt(size_array[0]);
            height = Integer.parseInt(size_array[1]);
            landscapesArray = new Landscape[width][height];

            int j = 0;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(";");
                int i = 0;
                for (String tempStr : tempArr) {
                    landscapesArray[i++][j] = new Landscape(LandscapeType.values()[Integer.parseInt(tempStr)]);
                }
                j++;
            }
            br.close();
        } catch (IOException ioe) {
            out.println("couldn't find src/main/resources/landscape/landscape" + file_number + ".csv");
            ioe.printStackTrace();
        }
    }

    public Landscape getLandscape(int x, int y) {
        return landscapesArray[x][y];
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
