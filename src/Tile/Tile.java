package Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tile extends Board {
    public int x, y, surroundingMines, surroundingFlags;
    public JButton button;

    public boolean mine, flagged, clicked;

    private MouseListener ml;

    public Tile(int x, int y) {
        super();
        setML();
        this.x = x;
        this.y = y;
        mine = false;

        button = new JButton(new ImageIcon("src\\sprites\\unknown.png"));
        button.setBorder(null);
        button.setBackground(new Color(Integer.parseInt("626262", 16)));


        button.addMouseListener(ml);
    }

    public void setMine()
    {
        mine = true;
        //button.setIcon(new ImageIcon("src\\sprites\\mine.png"));
    }


    public void reveal()
    {
        if(flagged && !mine) button.setIcon(new ImageIcon("src\\sprites\\wrong.png"));
        else if (mine && !flagged) button.setIcon(new ImageIcon("src\\sprites\\mine.png"));
        button.setDisabledIcon(button.getIcon());
        button.setEnabled(false);
    }
    private void click()
    {
        if(clicked) return;
        if(flagged) return;
        Board.count();
        Board.timer();
        clicked = true;
        if(mine) {
            Board.gameOver();
            button.setIcon(new ImageIcon("src\\sprites\\exploded.png"));
        }
        else
        {
            if(surroundingMines == 0)
            {
                clickSurrounding();
            }
            button.setIcon(new ImageIcon("src\\sprites\\"+surroundingMines+".png"));
        }
        button.setDisabledIcon(button.getIcon());
        button.setEnabled(false);
    }

    private void clickSurrounding() {
        for (int i = x-1; i <= x+1; i++) {
            if(i < 0) continue;
            if(i >= height) continue;
            for (int j = y-1; j <= y+1; j++) {
                if(j < 0) continue;
                if(j >= width) continue;
                board[i][j].click();
            }
        }
    }

    private void setML() {
        ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent event) {
                if(event.getButton() == MouseEvent.BUTTON2 && !gameOver && surroundingMines==surroundingFlags)
                {
                    clickSurrounding();
                }
                if(!button.isEnabled() || gameOver) return;
                if (event.getButton() == MouseEvent.BUTTON1) {
                    if (!flagged) {
                        click();
                    }
                }
                if (event.getButton() == MouseEvent.BUTTON3) {
                    if(!button.isEnabled()) return;
                    flagged = !flagged;
                    if (!flagged)
                    {
                        button.setIcon(new ImageIcon("src\\sprites\\unknown.png"));
                        flag(false);
                        for (int i = x-1; i <= x+1; i++) {
                            if(i < 0) continue;
                            if(i >= height) continue;
                            for (int j = y-1; j <= y+1; j++) {
                                if(j < 0) continue;
                                if(j >= width) continue;
                                board[i][j].surroundingFlags--;
                            }
                        }
                    }
                    else
                    {
                        button.setIcon(new ImageIcon("src\\sprites\\flag.png"));
                        flag(true);
                        for (int i = x-1; i <= x+1; i++) {
                            if(i < 0) continue;
                            if(i >= height) continue;
                            for (int j = y-1; j <= y+1; j++) {
                                if(j < 0) continue;
                                if(j >= width) continue;
                                board[i][j].surroundingFlags++;
                            }
                        }
                    }
                    button.setDisabledIcon(button.getIcon());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
}
