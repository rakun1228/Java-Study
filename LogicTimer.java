package team;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;

public class LogicTimer extends Canvas{
	
	int timeLimit;
	Image offScr;           // ������۸��� ���� ���� ȭ��
	Graphics offG;
	Timer timer;
	TimerTask timertask;
	
	Nemonemo parent;
	GameOver over;
	boolean stop;//Ÿ�̸� �Ͻ�����
	boolean end;//Ÿ�̸� ����


	
	public LogicTimer(Nemonemo parent){	
		
		stop=false;//Ÿ�̸� �Ͻ�����
		end=false;
		this.parent=parent;
		setSize(121,81);
		setBackground(Color.LIGHT_GRAY);
		
		setNewTimer();
	}
	
	public void setNewTimer() {
		
		timeLimit=284;
		
		timer = new Timer("Ÿ�̸�");
		timertask = new TimerTask() {
			public void run() {
				if(!stop) {//�Ͻ�����
					if(parent.re) {
						
					}
					if(timeLimit==0) {//�ð�0�Ȱ��
						repaint();
						stop=true;
						timer.cancel();
				    	parent.board.clearBoard();
						over = new GameOver(parent);
						over.setVisible(true);
					}
					else {
						repaint();
					}
				}
			}
			
		};
		timer.schedule(timertask, 1000, 1000);
	}
	
	
	public void closeTimer() {
		timer.cancel();
	}
	
	public void paint(Graphics g){
		
		offScr= createImage(121, 81);  // ���� ȭ�� ����
	    offG  = offScr.getGraphics();
	    
	    if(timeLimit==0) {
	    	offG.setColor(Color.BLACK);
		    offG.setFont(new Font("SansSerif", Font.BOLD, 18));
		    offG.drawString("time", 42, 23);
	    	offG.setColor(Color.RED);
	    	offG.setFont(new Font("SansSerif", Font.BOLD, 43));
	    	offG.drawString("00:00", 7, 62);
	    }
	    else {
	    	offG.setColor(Color.BLACK);
		    offG.setFont(new Font("SansSerif", Font.BOLD, 18));
		    offG.drawString("time", 42, 23);
		    offG.setFont(new Font("SansSerif", Font.BOLD, 40));
		    if(timeLimit/60<10)
		    	offG.drawString("0"+timeLimit/60, 12, 62);
		    else
		    	offG.drawString(""+timeLimit/60, 12, 62);
		    offG.drawString(":", 55, 60);
		    if(timeLimit%60<10)
		    	offG.drawString("0"+timeLimit%60, 70, 62);
		    else
		    	offG.drawString(""+timeLimit%60, 70, 62);
		    	
		    timeLimit--;
	    }
	    g.drawImage(offScr, 0, 0, this);
	}
}
