package Code.gui;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class ImageLoader {
    private static final Map<String, Image> imagesMap = new HashMap<>();

    public static Image loadImage(String path) {
        Image image = null;
        if (imagesMap.get(path) != null) {
            image = imagesMap.get(path);
        } else {
            try {
                image = new Image(new FileInputStream(path));
            } catch (FileNotFoundException e) {
                out.println("couldn't find " + path);
                e.printStackTrace();
            }
        }
        imagesMap.put(path, image);
        return image;
    }
}
