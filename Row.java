package team;

import java.awt.*;  // Color ��� ���� ���� awt ��Ű�� ����

public class Row extends Canvas // Canvas Ŭ������ ���
{
  Nemonemo parent;   // Nemonemo Ŭ������ ��ü�� ����

  Image offScr;           // ������۸��� ���� ���� ȭ��
  Graphics offG;

  public Row(Nemonemo parent)
  {
    this.parent= parent;  // Nemonemo Ŭ������ ��ü�� ����
    getRow();
  }

  public void getRow()   // �����Ϳ� ���� �ο��� ���ڸ� ����
  {
    for(int i=0; i<parent.size; i++)  // ��� �࿡ ������ '1'�� ������ ���
      parent.numOfRow[i]= getNumber(i);
  }

  int getNumber(int start)  // �ش��ϴ� ���� ������ '1'�� ������ ���
  {
    int count= 0;  // ���ӵ� '1'�� ����
    int pos= 0;  // �� ��° ���ӵ� '1'�� ������ ��Ÿ���� �������� ǥ��

    for(int i=start*parent.size; i<(start+1)*parent.size; i++)  // ���� �࿡ ���� data�� ���� ��
    {
      if(parent.data.charAt(i)=='0' && count>0){ // �������� ���� ���('0'�� ���)
        parent.rowNums[start][pos++]= count;
        count= 0;
      }else if(parent.data.charAt(i)=='1' && count>=0){ // ������ ���('1'�� ���)
        count++;
      }
    }

    if(count>0) parent.rowNums[start][pos++]= count;
    if(pos==0)  parent.rowNums[start][pos++]= 0;

    return pos;
  }

  public void paint(Graphics g)
  {
    offScr= createImage(20*parent.maxNum+1, 20*parent.size+1); // ���� ȭ�� ����
    offG  = offScr.getGraphics();
    if(parent.mouseY!=-1){
      offG.setColor(Color.yellow);
      offG.fillRect(0, 20*parent.mouseY, 20*parent.maxNum, 19); // ���콺 Ŀ���� �ִ� ���� ���
    }

    offG.setColor(Color.black);

    for(int i=0; i<parent.size; i++)
    {
      offG.drawLine(0, i*20, 20*parent.maxNum, i*20);
      for(int j=0; j<parent.numOfRow[i]; j++)  // ���� ���
        if(String.valueOf(parent.rowNums[i][j]).length()<2){
          offG.drawString(String.valueOf(parent.rowNums[i][j]), (20*parent.maxNum - parent.numOfRow[i]*20) + j*20+7, i*20+18);
        }else{
          offG.drawString(String.valueOf(parent.rowNums[i][j]), (20*parent.maxNum - parent.numOfRow[i]*20) + j*20+1, i*20+18);
        }
    }

    for(int i=0; i<=20*parent.size; i+=20*5)
    {
      offG.drawLine(0, i-1, 20*parent.maxNum, i-1);
      offG.drawLine(0, i+1, 20*parent.maxNum, i+1);
    }

    offG.drawLine(0, 20*parent.size, 20*parent.maxNum, 20*parent.size);
    offG.drawLine(20*parent.maxNum, 0, 20*parent.maxNum, 20*parent.size);

    g.drawImage(offScr, 0, 0, this);
  }

  public void update(Graphics g)
  {
    paint(g);
  }
}

