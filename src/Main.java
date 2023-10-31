import Tile.Board;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class Main extends JFrame {
    static JFrame frame;
    public static JButton stButton;
    public static byte dif = 0;
    public static int width = 9;
    public static int height = 9;
    public static int mines = 10;
    public static int time = 0;
    static JPanel difficultySelection = new JPanel(new GridLayout(1, 4));
    static JPanel start;
    public static JButton beginner = new JButton("Beginner");
    public static JButton intermediate = new JButton("Intermediate");
    public static JButton expert = new JButton("Expert");
    public static JButton custom = new JButton("Custom");
    public static JLabel[] tms = new JLabel[]{new JLabel(new ImageIcon("src\\sprites\\counter\\blank.png")), new JLabel(new ImageIcon("src\\sprites\\counter\\blank.png")), new JLabel(new ImageIcon("src\\sprites\\counter\\blank.png"))};
    public static TimerTask t;

    public Main() {
    }

    public static void main(String[] args) {
        frame = new JFrame("Minesweeper");
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Start Panel
        start = new JPanel();
        start.setLayout(new BorderLayout());
        frame.add(start, "North");

        //Start Button
        stButton = new JButton(new ImageIcon("src\\sprites\\start.png"));
        stButton.setBorder(null);
        stButton.addActionListener(e -> {
            stButton.setIcon(new ImageIcon("src\\sprites\\start.png"));
            startGame();
            frame.setVisible(true);
        });
        stButton.setMaximumSize(new Dimension(64, 64));
        start.add(stButton, "Center");

        //Difficulty Selection
        setUpButton(beginner, (byte) 0);
        setUpButton(intermediate, (byte) 1);
        setUpButton(expert, (byte) 2);
        setUpButton(custom, (byte) 3);
        start.add(difficultySelection, "First");

        //Mines Left
        JPanel ml = new JPanel(new GridLayout(1, 2));
        ml.add(Board.mineCountera);
        ml.add(Board.mineCounterb);
        ml.setVisible(true);
        start.add(ml, "West");

        //Mines Left
        JPanel tm = new JPanel(new GridLayout(1, 3));
        for (JLabel l : tms) {
            tm.add(l);
        }
        tm.setVisible(true);
        start.add(tm, "East");


        startGame();
        frame.add(Board.getJPanel(), "Center");
        frame.setVisible(true);
    }

    public static void startGame() {
        start.setSize(width * 33, 64);
        frame.setSize(width * 32, height * 32 + 96);
        try {t.cancel();} catch (Exception ignored){}
        t = new TimerTask() {
            @Override
            public void run() {
                countTimer();
            }
        };
        Board.newBoard(width, height, mines, stButton, t);
        time = 0;
        try {
            Board.timer.cancel();
        } catch (Exception ignored) {
        }
    }


    public static void setUpButton(JButton button, byte difficulty) {
        difficultySelection.add(button);
        button.setMaximumSize(new Dimension(10, 100));
        button.addActionListener((var1x) -> {
            dif = difficulty;
            switch (dif) {
                case 0 -> {
                    width = 9;
                    height = 9;
                    mines = 10;
                }
                case 1 -> {
                    width = 16;
                    height = 16;
                    mines = 40;
                }
                case 2 -> {
                    width = 30;
                    height = 16;
                    mines = 99;
                }
                default -> {
                    System.out.println("default");
                    JFrame custom = new JFrame("Custom Size");
                    custom.setSize(100, 300);
                    custom.setLocationRelativeTo(frame);
                    custom.setResizable(false);
                    custom.setLayout(new BorderLayout());
                    JPanel inputs = new JPanel();
                    inputs.setLayout(new GridLayout(3, 2));
                    JTextField wthIn = new JTextField();
                    JTextField hgtIn = new JTextField();
                    JTextField mnsIn = new JTextField();
                    inputs.add(new JLabel("Width:"));
                    inputs.add(wthIn);
                    inputs.add(new JLabel("Height:"));
                    inputs.add(hgtIn);
                    inputs.add(new JLabel("Mines:"));
                    inputs.add(mnsIn);
                    custom.add(inputs, BorderLayout.CENTER);
                    JButton submit = new JButton("Submit");
                    submit.addActionListener(e -> {
                        try {
                            width = Integer.parseInt(wthIn.getText());
                            width = Math.clamp(width, 5, 70);
                            height = Integer.parseInt(hgtIn.getText());
                            height = Math.clamp(height, 5, 40);
                            mines = Integer.parseInt(mnsIn.getText());
                            mines = Math.clamp(mines, 1, Math.min(99, width * height - 1));
                        } catch (Exception ignored) {
                        }

                        custom.dispose();
                    });
                    custom.add(submit, BorderLayout.PAGE_END);
                    custom.setVisible(true);
                }
            }

        });
    }

    public static void countTimer() {
        System.out.println("timer");
        if (Board.gameOver || time > 999) {
            t.cancel();
            return;
        }
        tms[0].setIcon(new ImageIcon("src\\sprites\\counter\\" + (time >= 100 ? time / 100 : "blank") + ".png"));
        tms[1].setIcon(new ImageIcon("src\\sprites\\counter\\" + (time >= 10 ? time % 100 / 10 : "blank") + ".png"));
        tms[2].setIcon(new ImageIcon("src\\sprites\\counter\\" + (time >= 0 ? time % 10 : "blank") + ".png"));
        time++;
    }
}
