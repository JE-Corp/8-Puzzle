/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author luckyazumi
 */
public class PuzzleGame extends JFrame{

    /**
     * @param args the command line arguments
     */
    private List<JButton> buttons, originalOrder = new ArrayList<>();
    private BufferedImage[] imageChunks;
    private JButton emptyButton; // Empty space button
    private JPanel puzzlePanel = new JPanel(new GridLayout(4, 4));

    public PuzzleGame() {
        Boolean dev = false;
        if (!dev){
            initialize(!dev);
            return;
        }
        int shuff = JOptionPane.showConfirmDialog(rootPane, "Desea mezclar la imagen?", "Mezclar?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        Boolean shuffle = shuff == JOptionPane.YES_OPTION ? true : false;
        initialize(shuffle);
    }

    private void initialize(Boolean shuffle) {
        setTitle("Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        final String fileName = "catCry.jpg";

        try {
            imageChunks = separar_imagen.splitImage(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cargando la imágen. \nPor favor asegúrate que el archivo de imágen '" + fileName + "' existe.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        buttons = new ArrayList<>();

        // Calculate the scaling factor
        int desiredWidth = 150; // Desired width of the puzzle pieces
        int desiredHeight = 150; // Desired height of the puzzle pieces
        
        for (BufferedImage chunk : imageChunks) {
            // Scale the image chunk to fit within the button size
            BufferedImage scaledImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaledImage.createGraphics();
            g.drawImage(chunk, 0, 0, desiredWidth, desiredHeight, null);
            g.dispose();

            JButton button = new JButton(new ImageIcon(scaledImage));
            button.setPreferredSize(new Dimension(desiredWidth, desiredHeight));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    movePiece(clickedButton);
                }
            });
            puzzlePanel.add(button);
            buttons.add(button);
        }
        
        add(puzzlePanel, BorderLayout.CENTER);
        
        buttons.remove(buttons.size() - 1);
        
        emptyButton = new JButton();
        buttons.add(emptyButton);
        
        // Almacenar las posiciones iniciales
        originalOrder.addAll(buttons);
        if (shuffle){
            shufflePuzzle();
        }
        pack();
        setLocationRelativeTo(null); // Center the window
    }



    private void shufflePuzzle() {
        Collections.shuffle(buttons);
        puzzlePanel = (JPanel) getContentPane().getComponent(0); // Get the puzzle panel
        puzzlePanel.removeAll(); // Remove all components from the puzzle panel
        for (JButton button : buttons) {            
            puzzlePanel.add(button); // Add shuffled buttons back to the puzzle panel
        }
        puzzlePanel.revalidate(); // Revalidate the puzzle panel to update the layout
        puzzlePanel.repaint(); // Repaint the puzzle panel to reflect the changes
    }
    
    public void movePiece(JButton buttonCalled) {
        if (buttonCalled == emptyButton) {
            // This button is already in the empty space
            return;
        }

        // Check if the clicked button is adjacent to the empty space
        int indexClicked = buttons.indexOf(buttonCalled);
        int indexEmpty = buttons.indexOf(emptyButton);

        if (isAdjacent(indexClicked, indexEmpty)) {
            JButton temp = buttons.get(indexClicked);
            // Swap the clicked button with the empty space
            buttons.set(indexClicked, emptyButton);
            buttons.set(indexEmpty, temp);

            puzzlePanel.removeAll();
            for (JButton button : buttons) {
                puzzlePanel.add(button);
            }

            puzzlePanel.revalidate();
            puzzlePanel.repaint();

            // Check for puzzle completion after each move
            if (isPuzzleComplete()){
                JOptionPane.showMessageDialog(this, "Felicidades! Resolviste el puzzle!", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private boolean isAdjacent(int index1, int index2) {
        // Check if two buttons are adjacent in the grid
        int row1 = index1 / 4;
        int col1 = index1 % 4;
        int row2 = index2 / 4;
        int col2 = index2 % 4;
        Boolean is = (row1 == row2 && Math.abs(col1 - col2) == 1) || 
               (col1 == col2 && Math.abs(row1 - row2) == 1);
        // Buttons are adjacent if they share the same row and their columns differ by 1, or vice versa
        return is;
    }

    private boolean isPuzzleComplete() {
        for (int i = 0; i < buttons.size(); i++) {
            // Get the original button at index i
            JButton originalButton = originalOrder.get(i);

            // Get the current button at index i
            JButton currentButton = buttons.get(i);

            // If the current button is not equal to the original button, the puzzle is not complete
            if (currentButton != originalButton) {
                return false;
            }
        }
        return true; // All puzzle pieces are in their correct positions
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PuzzleGame puzzle = new PuzzleGame();
            puzzle.setVisible(true);
        });
    }
    
}
