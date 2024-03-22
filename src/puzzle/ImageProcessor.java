/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

/**
 *
 * @author luckyazumi
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageProcessor {
    
    public BufferedImage[] processImage(BufferedImage image) {
        // Determine image aspect ratio
        int width = image.getWidth();
        int height = image.getHeight();
        double aspectRatio = (double) width / height;
        
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height - 20; // Account for 20px margin
        
        // Define maximum dimensions based on conditions
        int maxWidth;
        int maxHeight;
        BufferedImage[] processedImages;
        if (isCloseToAspectRatio(aspectRatio, 1.0) || isCloseToAspectRatio(aspectRatio, 4.0 / 3)) {
            // Aspect ratio is close to 1:1 or 4:3
            // Split the image if height is less than screen height - 20px
            if (height < screenHeight) {
                try {
                    // Split the image into chunks
                    processedImages = separar_imagen.splitImage(image);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Scale the image to fit within the screen height - 20px
                maxHeight = screenHeight;
                maxWidth = (int) (maxHeight * aspectRatio);
                processedImages = new BufferedImage[1];
                processedImages[0] = scaleImage(image, maxWidth, maxHeight);
            }
        } else {
            // Aspect ratio is different
            // Check if either width or height exceeds screen boundaries
            if (width > screenSize.width || height > screenHeight) {
                // Resize the image
                maxWidth = screenSize.width;
                maxHeight = screenHeight;
                processedImages = new BufferedImage[1];
                processedImages[0] = resizeImage(image, maxWidth, maxHeight);
            } else {
                // Image fits within screen boundaries
                processedImages = new BufferedImage[1];
                processedImages[0] = image;
            }
        }
        
        return processedImages;
    }
    
    private boolean isCloseToAspectRatio(double aspectRatio, double targetAspectRatio) {
        double tolerance = 0.05; // You can adjust the tolerance as needed
        return Math.abs(aspectRatio - targetAspectRatio) < tolerance;
    }
    
    private BufferedImage scaleImage(BufferedImage image, int maxWidth, int maxHeight) {
        // Your logic to scale the image goes here
        // Return the scaled BufferedImage
    }
    
    private BufferedImage resizeImage(BufferedImage image, int maxWidth, int maxHeight) {
        // Your logic to resize the image goes here
        // Return the resized BufferedImage
    }
}
