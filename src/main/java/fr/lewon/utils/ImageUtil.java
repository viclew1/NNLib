package fr.lewon.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum ImageUtil {

    INSTANCE;

    public Image resizeImageConserveWidth(String imageName, int width) throws IOException {
        BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream(imageName));
        return this.resizeImageConserveWidth(img, width);
    }

    public Image resizeImageConserveWidth(BufferedImage img, int width) {
        float ratio = (float) img.getWidth() / (float) width;
        return this.resizeImage(img, width, (int) (img.getHeight() / ratio));
    }

    public Image resizeImageConserveHeight(String imageName, int height) throws IOException {
        BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream(imageName));
        return this.resizeImageConserveHeight(img, height);
    }

    public Image resizeImageConserveHeight(BufferedImage img, int height) {
        float ratio = (float) img.getHeight() / (float) height;
        return this.resizeImage(img, (int) (img.getWidth() / ratio), height);
    }

    public Image resizeImage(String imageName, int width, int height) throws IOException {
        BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream(imageName));
        return this.resizeImage(img, width, height);
    }

    public Image resizeImage(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

}
