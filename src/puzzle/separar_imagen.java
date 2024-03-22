/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;
import java.awt.Graphics2D;
/**
 *
 * @author luckyazumi
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class separar_imagen {
    
    public static BufferedImage[] splitAndProcessImage(File file) throws IOException {
        BufferedImage sourceImage = ImageIO.read(file);

        ImageProcessor imageProcessor = new ImageProcessor();
        BufferedImage[] chunks = splitImage(sourceImage);

        
        for (BufferedImage chunk : chunks) {
            imageProcessor.processImage(chunk);
        }
        
        return chunks;
    }

    public static BufferedImage[] splitImage(BufferedImage sourceImage) throws IOException {
        int rows = 4;
        int cols = 4;
        int chunks = rows * cols;

        int chunkWidth = sourceImage.getWidth() / cols;
        int chunkHeight = sourceImage.getHeight() / rows;
        int count = 0;
        BufferedImage[] imageArray = new BufferedImage[chunks];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (count < chunks - 1){
                    // Initialize the image array with image chunks
                    imageArray[count] = new BufferedImage(chunkWidth, chunkHeight, sourceImage.getType());

                    // draws the image chunk
                    Graphics2D gr = imageArray[count++].createGraphics();
                    gr.drawImage(sourceImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
                            chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }
            }
        }
        return imageArray;
    }
}