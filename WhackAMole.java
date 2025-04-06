import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WhackAMole {
   int boardWidth = 600;
   int boardHeight = 650; // 50px for the text panel on top

   JFrame frame = new JFrame("Mario: Whack A Mole");
   JLabel textLabel = new JLabel();
   JPanel textPanel = new JPanel();
   JPanel boardPanel = new JPanel();

   JButton[] board = new JButton[9];
   ImageIcon moleIcon;
   ImageIcon planIcon;

   JButton currMoleTile;
   JButton currPlantTile;

   Random random = new Random();
   Timer setMoleTimer;
   Timer setPlantTimer;

   int score;


   WhackAMole() {
//      frame.setVisible(true);
      frame.setSize(boardWidth,boardHeight);
      frame.setLocationRelativeTo(null);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());

      textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
      textLabel.setHorizontalAlignment(JLabel.CENTER);
      textLabel.setText("Score: 0");
      textLabel.setOpaque(true);

      textPanel.setLayout(new BorderLayout());
      textPanel.add(textLabel);
      frame.add(textPanel, BorderLayout.NORTH);

      boardPanel.setLayout(new GridLayout(3, 3));
//      boardPanel.setBackground(Color.cyan);
      frame.add(boardPanel);

//      planIcon = new ImageIcon(getClass().getResource("./piranha.png"));
      Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
      planIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

      Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
      moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

      score = 0;
      for (int i = 0; i < 9; i++) {
         JButton tile = new JButton();
         board[i] = tile;
         boardPanel.add(tile);
         tile.setFocusable(false);
//         tile.setIcon(moleIcon);
         tile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JButton tile = (JButton) e.getSource();

               if (tile == currMoleTile) {
                  score += 10;
                  textLabel.setText("Score: " + Integer.toString(score));
               }
               else if (tile == currPlantTile) {
                  textLabel.setText("Game Over: " + Integer.toString(score));
                  setMoleTimer.stop();
                  setPlantTimer.stop();

                  for (int i = 0; i < 9; i++) {
                    board[i].setEnabled(false);
                  }
               }
            }
         });

      }
      setMoleTimer = new Timer(1000, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // remove mole from current tile
            if (currMoleTile != null) {
               currMoleTile.setIcon(null);
               currMoleTile = null;
            }

            // randomly select another tile
            int num = random.nextInt(9); //0-8
            JButton tile = board[num];

            // if tile is occupied by plant, skip tile for this turn
            if (currPlantTile == tile) return;

            // set tile to mole
            currMoleTile = tile;
            currMoleTile.setIcon(moleIcon);

         }
      });

      setPlantTimer = new Timer(1500, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // remove plant from current tile
            if (currPlantTile != null) {
               currPlantTile.setIcon(null);
               currPlantTile = null;
            }
            // randomly select another tile
            int num = random.nextInt(9); //0-8
            JButton tile = board[num];

            // if tile is occupied by mole, skip tile for this turn
            if (currMoleTile == tile) return;

            // set tile to mole
            currPlantTile = tile;
            currPlantTile.setIcon(planIcon);

         }
      });

      setMoleTimer.start();
      setPlantTimer.start();
      frame.setVisible(true);
   }

}