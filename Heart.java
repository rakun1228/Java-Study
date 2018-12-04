package team;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Heart extends Canvas {
	private static int heartCnt=5;
	private int life;
	GameOver over;
	Nemonemo parent;   // Nemonemo Ŭ������ ��ü�� ����

	Image offScr;           // ������۸��� ���� ���� ȭ��
	Graphics offG;

	public Heart(Nemonemo parent)
	  {
	    this.parent= parent;  // Nemonemo Ŭ������ ��ü�� ����
	    setSize(121,81);
	    setBackground(Color.WHITE);
	    setLife(3);
	  }
	
	public void setLife(int a) {
		life=a;
	}
	
	public void plusHeart() {
		if(life<heartCnt)
			life++;
		else
			return;
		parent.heart.repaint();
	}
	
	public void minusHeart() {
		if(life==1) {
			life--;
			repaint();
			over = new GameOver(parent);
	    	parent.board.clearBoard();
			over.setVisible(true);
			}
		else {
			if(life>1)
				life--;
			else
				return;
		}
		repaint();
	}
	
	public void paint(Graphics g) {
		if(parent.re) {
			setLife(3);
		}
		offScr= createImage(121, 81);  // ���� ȭ�� ����
	    offG  = offScr.getGraphics();
	    
	    int i;
	    
	    offG.setColor(Color.BLACK);
	    offG.drawRect(0, 0, 120, 80);
		offG.drawString("Life",35,40);
		
		for(i=0;i<heartCnt;i++) {
			if(i<life) {
				offG.setColor(Color.RED);
				offG.drawString("��",i*15+20,65);
			}
			else {
				offG.setColor(Color.GRAY);
				offG.drawString("��",i*15+20,65);
			}
		}
		g.drawImage(offScr, 0, 0, this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}

}
