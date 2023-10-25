package Tile;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;

public class Board extends JFrame {
    public final static JPanel frame = new JPanel();
    public static Tile[][] board;
    public static JButton stButton;
    public static JLabel mineCountera = new JLabel(new ImageIcon("src\\sprites\\counter\\blank.png")),
            mineCounterb = new JLabel(new ImageIcon("src\\sprites\\counter\\blank.png"));
    public static int width, height, mines, left;
    public static boolean gameOver, started;
    public static Timer timer = new Timer();
    public static TimerTask task;

    public static void newBoard(int width, int height, int mines, JButton stButton, TimerTask task) {
        System.out.println("New Board");
        Board.width = width;
        Board.height = height;
        Board.mines = mines;
        Board.stButton = stButton;
        gameOver = false;
        Board.task = task;
        frame.removeAll();
        frame.setLayout(new GridLayout(height, width, 1, 1));
        frame.setBackground(new Color(Integer.parseInt("626262", 16)));
        frame.setSize(width * 33, height * 33);


        board = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile t = new Tile(i, j);
                board[i][j] = t;
                frame.add(t.button);
            }
        }

        left = width * height - mines;
        for (int i = 0; i < mines; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            Tile posMine = board[y][x];
            if (posMine.mine) {
                i--;
                continue;
            }
            posMine.setMine();
            addMine(x, y);
        }


        mineCountera.setIcon(new ImageIcon("src\\sprites\\counter\\" + (mines >= 10 ? mines / 10 : "blank") + ".png"));
        ;
        mineCounterb.setIcon(new ImageIcon("src\\sprites\\counter\\" + (mines % 10) + ".png"));

        frame.setVisible(true);
    }

    public static void count() {
        left--;
        if (left == 0) {
            stButton.setIcon(new ImageIcon("src\\sprites\\win.png"));
            gameOver = true;
        }
        //System.out.println(left);
    }

    public static void flag(boolean flag) {
        if (flag) mines--;
        else mines++;
        mineCountera.setIcon(new ImageIcon("src\\sprites\\counter\\" + (mines >= 10 ? mines / 10 : "blank") + ".png"));
        mineCounterb.setIcon(new ImageIcon("src\\sprites\\counter\\" + (mines % 10) + ".png"));
        System.out.println(mines);
    }

    private static void addMine(int x, int y) {
        for (int i = y - 1; i <= y + 1; i++) {
            if (i < 0) continue;
            if (i >= height) continue;
            for (int j = x - 1; j <= x + 1; j++) {
                if (j < 0) continue;
                if (j >= width) continue;
                board[i][j].surroundingMines++;
            }
        }
    }

    public static void gameOver() {
        gameOver = true;
        for (Tile[] ts : board) {
            for (Tile t : ts) {
                t.reveal();
            }
        }
        stButton.setIcon(new ImageIcon("src\\sprites\\end.png"));
        started = false;
    }

    public static void timer() {
        if (started) return;
        started = true;
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static JPanel getJPanel() {
        return frame;
    }
}
