package team;

import javax.swing.*;           // ���� ��Ű�� ����
import javax.swing.event.*;
import java.awt.*;               // Font ��� ���� ���� awt ��Ű�� ����
import java.awt.event.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


public class Nemonemo extends CloseableFrame // JFrame���κ��� ��ӹ��� CloseableFrame ���
  implements ActionListener
{
  JPanel contentPane;

  // �޴�
  JMenuBar menuBar= new JMenuBar();
  JMenu gameMenu= new JMenu("Game"); // Game �޴�
  JMenu helpMenu= new JMenu("Help"); // Help �޴�

  // ����(add)�� Ŭ������ ����
  Board board; 
  Column col;
  Row row;
  
  String data;
  
  Heart heart;
  LogicTimer timer;
  TimerTask timertask;
  FileDialog fd;
  
  int Mode=0; //mode=0 normal  mode=1 random
  int size=10;
  int maxNum=size/2+1;
  File f;
  boolean re=false; //�����


  // ���콺 Ŀ���� ��ǥ
  int mouseX=-1;
  int mouseY=-1;

  int[] temp; // �÷��̾ �Է��� ��

  int columnNums[][], rowNums[][];
  int numOfColumn[], numOfRow[];

  boolean endFlag= false; // ������ Ǯ�ȴ��� ����

  public static void main(String[] args)
  {
    Nemonemo nemo= new Nemonemo(); // �׸�׸���� ���� ����
    nemo.addWindowListener(new WindowAdapter(){
	   public void windowClosed(WindowEvent e)
	   {  System.exit(0);  }
	});

    nemo.setVisible(true);
    nemo.toFront();
  }

  public Nemonemo()
  {
	  if(Mode==0) {
		  data= "0001000000011100001101010000101111111110111111111000011111100001111100000100010000010001000011001100"; // ������ ����(�ʱⰪ�� ������)
	  }
	  else {
		  data="";
		  randominitialize();
	  }
	  this.setTitle("Nemonemo Logic"); // ���ø����̼� â�� Ÿ��Ʋ(����) ����
	  this.setSize(20*(size+maxNum)+150, 20*(size+maxNum)+80);
//    this.setSize(331, 381); // ���ø����̼��� ũ�� ����

    // ���� �ʱ�ȭ
    temp= new int[size*size]; // ���� 10ĭ, ���� 10ĭ���� �� 100ĭ ����
    for(int i=0; i<size*size; i++) temp[i]= 0; // �÷��̾ �Է��ϱ� ���� 0���� �ʱ�ȭ
    columnNums= new int[size][size];
    numOfColumn= new int[size];
    rowNums= new int[size][size];
    numOfRow= new int[size];

    contentPane= (JPanel) getContentPane();
    contentPane.setBackground(Color.white);
    contentPane.setLayout(null); // null ���̾ƿ����� ����

    createMenus(); // �޴� ����

    // �÷� ����
    col= new Column(this);
    contentPane.add(col);
    col.setFont(new Font("SansSerif", Font.BOLD, 14));
    col.setBounds(maxNum*20, 0, 20*size+1, 20*maxNum+1);
    col.repaint();

    // �ο� ����
    row= new Row(this);
    contentPane.add(row);
    row.setFont(new Font("SansSerif", Font.BOLD, 14));
    row.setBounds(0, 20*maxNum, 20*maxNum+1, 20*size+1);

    // ���� ����
    board= new Board(this);
    contentPane.add(board);
    board.setFont(new Font("SansSerif", Font.BOLD, 14));
    board.setBounds(maxNum*20, maxNum*20, 20*size+1, 20*size+1); 
    
    //����
    heart = new Heart(this);
    contentPane.add(heart);
    heart.setFont(new Font("SansSerif", Font.BOLD, 25));
    heart.setBounds(20*size+maxNum*20+10,20*maxNum+10,121,81);
    heart.repaint();
    
    //Ÿ�̸�
    timer = new LogicTimer(this);
    contentPane.add(timer);
    timer.setBounds(20*size+maxNum*20+10,20*maxNum+100,121,81);
    timer.repaint();
  
  }

  public void createMenus()
  {
    this.setJMenuBar(menuBar);
    menuBar.add(gameMenu);
    menuBar.add(helpMenu);

    // Game �޴��� ����޴� ����
    JMenuItem newGame= new JMenuItem("New Game ...");
    newGame.addActionListener(this);
    newGame.setActionCommand("newGame");
    gameMenu.add(newGame);

    JMenuItem answerGame= new JMenuItem("Answer");
    answerGame.addActionListener(this);
    answerGame.setActionCommand("answerGame");
    gameMenu.add(answerGame);

    JMenuItem changeMode= new JMenuItem("Change Mode");
    changeMode.addActionListener(this);
    changeMode.setActionCommand("changeMode");
    gameMenu.add(changeMode);
    
    JMenuItem exitGame= new JMenuItem("Exit");
    exitGame.addActionListener(this);
    exitGame.setActionCommand("exitGame");
    gameMenu.add(exitGame);
    
    // Help �޴��� ����޴� ����
    JMenuItem aboutGame= new JMenuItem("About Game ...");
    aboutGame.addActionListener(this);
    aboutGame.setActionCommand("aboutGame");
    helpMenu.add(aboutGame);
  }

  public void showLocation(int mouseX, int mouseY) // ���콺 Ŀ���� ��ġ�� ǥ��
  {
    if(mouseX!=this.mouseX){  // ���콺 Ŀ���� ��ġ�� ���� ���� ���
      this.mouseX= mouseX;
      col.repaint();
    }
    if(mouseY!=this.mouseY){  // ���콺 Ŀ���� ��ġ�� ���� ���� ���
      this.mouseY= mouseY;
      row.repaint();
    }
  }

  public void display() // ������ Ǯ�ȴ��� ���θ� �˻�
  {
    boolean endFlag= true;
    for(int j=0; (j<size)&&endFlag; j++)
      for(int i=0; (i<size)&&endFlag; i++)
      {
/*        if((data.charAt(j*10+i)=='0')&&((temp[j*10+i]==1)||(temp[j*10+i]==4))) endFlag=false; // ä���� ĭ�� ��� ä������ �˻�
        else 
*/        	if((data.charAt(j*size+i)=='1')&&(temp[j*size+i]!=3)) endFlag=false; // ä���� �ʾƾ� �� ĭ�� ä������ �˻�
      }

    if(endFlag)
    {
      this.endFlag= endFlag;
      board.repaint(); // ������ �� Ǯ������ ������ ĭ�� ä��
    }
  }

  public void actionPerformed(ActionEvent e) // ������ �޴��� ���� ������ ��ƾ�� ȣ��
  {
    String cmd= e.getActionCommand();
    
    if(cmd.equals("newGame")){ // �׸�׸���� �����͸� �ҷ��ͼ� �� ������ ����
    	timer.stop=true;
    	showOpenDialog();
    	if(f!=null) { //�޴��� - new game - �� ������ �ҷ��� ���	
    		timer.stop=false;
    		timer.timer.cancel();
    		timer.setNewTimer();
    		board.over=true; 
    		re=true;
	    	heart.repaint();
	    }
    	timer.stop=false;
      
    }else if(cmd.equals("answerGame")){ // Answer�� �����ϸ� ������ ���
      this.endFlag= true;
      timer.stop=true;
      board.repaint(); 
    }else if(cmd.equals("changeMode")){ // ���� ����
      showModeSelect();      
    }else if(cmd.equals("exitGame")){ // ���� ����
      this.dispose();      
    }else if(cmd.equals("aboutGame")){ // ���ø����̼� ������ ���
      showAboutDialog();
    }
  }

  public void retrySet() {
	re=true;
	heart.repaint();
	board.clearBoard();
	board.over=true;
	board.repaint();
	timer.end=false;
	timer.closeTimer();
	timer.setNewTimer();
	}
  
  // �޴����� New Game ���ý�, ���� �����͸� �ҷ����� �޼ҵ�
  public void showOpenDialog() 
  {
	  
    fd= 
      new FileDialog(this, "Open a File", FileDialog.LOAD);
      
    fd.setFile("*.nemo;*.NEMO"); // ������ ������ Ȯ���ڴ� nemo �Ǵ� NEMO
    fd.setVisible(true);
    
    if(fd.getFile()!=null)
    {
      String filename= fd.getFile();
      String logicDir= fd.getDirectory();
      if(filename.indexOf('.')!=-1){
        filename= (filename.substring(0, filename.indexOf('.'))).toLowerCase();
      }else{
        filename= filename.toLowerCase();
      }
      String logicName= filename;
    
                        
      FileInputStream from= null;
      BufferedReader d= null;
    
      try{
        f= new File(logicDir + logicName + ".nemo");
        from= new FileInputStream(f);
        d = new BufferedReader(new InputStreamReader(from));

        data= d.readLine();
        data.trim();

        d.close();
      }catch(IOException e){
        System.out.println("I/O ERROR: "+ e);
      }

      // ���� �ʱ�ȭ
      for(int i=0; i<size*size; i++) temp[i]= 0;      
      this.endFlag= false;

      // �ҷ��� �����Ϳ� ���� �÷�, �ο��� ���ڸ� ������ϰ� ������ ���带 �ٽ� ���
      col.getColumn(); 
      row.getRow();
      board.repaint();
    }
  }
	
  public void showAboutDialog() // �޴����� About Game ���ý� ����ϴ� ���ø����̼� ����
  {
    AboutDialog ad= new AboutDialog(this);
    ad.setVisible(true);
  }

  public void showModeSelect() // �޴����� About Game ���ý� ����ϴ� ���ø����̼� ����
  {
    ModeSelect md= new ModeSelect(this);
    md.setVisible(true);
  }
  public void windowIconified(WindowEvent e){
	  timer.stop=true;
  }
  public void windowDeiconified(WindowEvent e){
	  timer.stop=false;
  }
 
  public void randominitialize() {
	  for(int i=0;i<size*size;i++)
		  data+=Math.round(Math.random());
  }

  
}
