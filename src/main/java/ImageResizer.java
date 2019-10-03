import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Project ImageResizer
 * Created by End on сент., 2019
 */
public class ImageResizer extends Thread {

    private final File file;
    private String dstFolder;
    private long start;
    private int targetSize;

    public ImageResizer(File file, String dstFolder, long start, int targetSize) {
        this.file = file;
        this.dstFolder = dstFolder;
        this.start = start;
        this.targetSize = targetSize;
    }

    @Override
    public void run() {
        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImage scaleImg = Scalr.resize(image, Scalr.Method.SPEED, targetSize*3);
            scaleImg = Scalr.resize(scaleImg, Scalr.Method.ULTRA_QUALITY, targetSize, Scalr.OP_ANTIALIAS);
            File newFile = new File(dstFolder + "/" + file.getName());
            ImageIO.write(scaleImg, "jpg", newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}