package team;

import javax.swing.*;           // 스윙 패키지 선언
import javax.swing.event.*;
import java.awt.*;               // Font 상수 등을 위한 awt 패키지 선언
import java.awt.event.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


public class Nemonemo extends CloseableFrame // JFrame으로부터 상속받은 CloseableFrame 상속
  implements ActionListener
{
  JPanel contentPane;

  // 메뉴
  JMenuBar menuBar= new JMenuBar();
  JMenu gameMenu= new JMenu("Game"); // Game 메뉴
  JMenu helpMenu= new JMenu("Help"); // Help 메뉴

  // 부착(add)할 클래스의 선언
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
  boolean re=false; //재시작


  // 마우스 커서의 좌표
  int mouseX=-1;
  int mouseY=-1;

  int[] temp; // 플레이어가 입력한 답

  int columnNums[][], rowNums[][];
  int numOfColumn[], numOfRow[];

  boolean endFlag= false; // 퍼즐이 풀렸는지 여부

  public static void main(String[] args)
  {
    Nemonemo nemo= new Nemonemo(); // 네모네모로직 게임 생성
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
		  data= "0001000000011100001101010000101111111110111111111000011111100001111100000100010000010001000011001100"; // 문제의 정답(초기값은 강아지)
	  }
	  else {
		  data="";
		  randominitialize();
	  }
	  this.setTitle("Nemonemo Logic"); // 애플리케이션 창의 타이틀(제목) 설정
	  this.setSize(20*(size+maxNum)+150, 20*(size+maxNum)+80);
//    this.setSize(331, 381); // 애플리케이션의 크기 설정

    // 변수 초기화
    temp= new int[size*size]; // 가로 10칸, 세로 10칸으로 총 100칸 선언
    for(int i=0; i<size*size; i++) temp[i]= 0; // 플레이어가 입력하기 전에 0으로 초기화
    columnNums= new int[size][size];
    numOfColumn= new int[size];
    rowNums= new int[size][size];
    numOfRow= new int[size];

    contentPane= (JPanel) getContentPane();
    contentPane.setBackground(Color.white);
    contentPane.setLayout(null); // null 레이아웃으로 설정

    createMenus(); // 메뉴 생성

    // 컬럼 생성
    col= new Column(this);
    contentPane.add(col);
    col.setFont(new Font("SansSerif", Font.BOLD, 14));
    col.setBounds(maxNum*20, 0, 20*size+1, 20*maxNum+1);
    col.repaint();

    // 로우 생성
    row= new Row(this);
    contentPane.add(row);
    row.setFont(new Font("SansSerif", Font.BOLD, 14));
    row.setBounds(0, 20*maxNum, 20*maxNum+1, 20*size+1);

    // 보드 생성
    board= new Board(this);
    contentPane.add(board);
    board.setFont(new Font("SansSerif", Font.BOLD, 14));
    board.setBounds(maxNum*20, maxNum*20, 20*size+1, 20*size+1); 
    
    //생명
    heart = new Heart(this);
    contentPane.add(heart);
    heart.setFont(new Font("SansSerif", Font.BOLD, 25));
    heart.setBounds(20*size+maxNum*20+10,20*maxNum+10,121,81);
    heart.repaint();
    
    //타이머
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

    // Game 메뉴의 서브메뉴 생성
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
    
    // Help 메뉴의 서브메뉴 생성
    JMenuItem aboutGame= new JMenuItem("About Game ...");
    aboutGame.addActionListener(this);
    aboutGame.setActionCommand("aboutGame");
    helpMenu.add(aboutGame);
  }

  public void showLocation(int mouseX, int mouseY) // 마우스 커서의 위치를 표시
  {
    if(mouseX!=this.mouseX){  // 마우스 커서가 위치한 열이 변한 경우
      this.mouseX= mouseX;
      col.repaint();
    }
    if(mouseY!=this.mouseY){  // 마우스 커서가 위치한 행이 변한 경우
      this.mouseY= mouseY;
      row.repaint();
    }
  }

  public void display() // 퍼즐이 풀렸는지 여부를 검사
  {
    boolean endFlag= true;
    for(int j=0; (j<size)&&endFlag; j++)
      for(int i=0; (i<size)&&endFlag; i++)
      {
/*        if((data.charAt(j*10+i)=='0')&&((temp[j*10+i]==1)||(temp[j*10+i]==4))) endFlag=false; // 채웡할 칸을 모두 채웠는지 검사
        else 
*/        	if((data.charAt(j*size+i)=='1')&&(temp[j*size+i]!=3)) endFlag=false; // 채우지 않아야 할 칸을 채웠는지 검사
      }

    if(endFlag)
    {
      this.endFlag= endFlag;
      board.repaint(); // 퍼즐이 다 풀렸으면 보드의 칸을 채움
    }
  }

  public void actionPerformed(ActionEvent e) // 선택한 메뉴에 따라 실행할 루틴을 호출
  {
    String cmd= e.getActionCommand();
    
    if(cmd.equals("newGame")){ // 네모네모로직 데이터를 불러와서 새 게임을 시작
    	timer.stop=true;
    	showOpenDialog();
    	if(f!=null) { //메뉴바 - new game - 새 게임을 불러온 경우	
    		timer.stop=false;
    		timer.timer.cancel();
    		timer.setNewTimer();
    		board.over=true; 
    		re=true;
	    	heart.repaint();
	    }
    	timer.stop=false;
      
    }else if(cmd.equals("answerGame")){ // Answer를 선택하면 정답을 출력
      this.endFlag= true;
      timer.stop=true;
      board.repaint(); 
    }else if(cmd.equals("changeMode")){ // 게임 종료
      showModeSelect();      
    }else if(cmd.equals("exitGame")){ // 게임 종료
      this.dispose();      
    }else if(cmd.equals("aboutGame")){ // 애플리케이션 정보를 출력
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
  
  // 메뉴에서 New Game 선택시, 퍼즐 데이터를 불러오는 메소드
  public void showOpenDialog() 
  {
	  
    fd= 
      new FileDialog(this, "Open a File", FileDialog.LOAD);
      
    fd.setFile("*.nemo;*.NEMO"); // 데이터 파일의 확장자는 nemo 또는 NEMO
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

      // 변수 초기화
      for(int i=0; i<size*size; i++) temp[i]= 0;      
      this.endFlag= false;

      // 불러온 데이터에 맞춰 컬럼, 로우의 숫자를 재생성하고 깨끗한 보드를 다시 출력
      col.getColumn(); 
      row.getRow();
      board.repaint();
    }
  }
	
  public void showAboutDialog() // 메뉴에서 About Game 선택시 출력하는 애플리케이션 정보
  {
    AboutDialog ad= new AboutDialog(this);
    ad.setVisible(true);
  }

  public void showModeSelect() // 메뉴에서 About Game 선택시 출력하는 애플리케이션 정보
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
