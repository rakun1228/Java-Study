package team;

import javax.swing.*;           // ���� ��Ű�� ����
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends JDialog     // ������ JDialog ���
  implements ActionListener, WindowListener
{
  // ���� ������Ʈ ����
  JPanel aboutPanel;  
  JButton ok;
  JLabel titleLabel, nameLabel;
  
  public AboutDialog(Nemonemo parent)
  {
    super(parent, "Nemonemo Logic", true);  // ���̾�α�(��ȭ����)�� Ÿ��Ʋ(����) ����
    this.setSize(240,190);                            // ���̾�α��� ũ�� ����
    this.addWindowListener(this);
    this.setLayout(new BorderLayout(15,15));
    this.setFont(new Font("SansSerif", Font.BOLD, 14));

    createAboutPanel();
  }  

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==ok){
      this.dispose();
    }
  }  
  
  public void createAboutPanel()
  {
    aboutPanel= new JPanel();
    aboutPanel.setLayout(null);
    
    // About Game ���� ���
    titleLabel= new JLabel("Nemonemo Logic 2011/06");
    aboutPanel.add(titleLabel);
    titleLabel.setBounds(40,30,200,25);
    
    nameLabel= new JLabel(" by Tong h. Lim (tong@bc.ac.kr)");
    aboutPanel.add(nameLabel);
    nameLabel.setBounds(25,60,200,25);

    // ���̾�α� ���� ��ư
    ok= new JButton("Okay");
    ok.addActionListener(this);
    aboutPanel.add(ok);
    ok.setBounds(80,110,80,25);

    this.add("Center", aboutPanel);
  }
        
  // the methods of the WindowListener object
  public void windowClosing(WindowEvent e){ this.dispose(); }
  public void windowOpened(WindowEvent e){}
  public void windowClosed(WindowEvent e){}
  public void windowIconified(WindowEvent e){}
  public void windowDeiconified(WindowEvent e){}
  public void windowActivated(WindowEvent e){}
  public void windowDeactivated(WindowEvent e){}  
}

