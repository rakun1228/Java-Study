import java.util.*;
class  test07231
{
	public static String strInput()
	{
		Scanner scan=new Scanner(System.in);  //문자열 입력
		String s;

		s=scan.nextLine();
		return s;
	}
  
	public static String reverse(String str)  //문자열을 거꾸로
	{
		String rel;
		
		rel="";    //문자열 초기화

		for(int i=0;i<str.length();i++)
		{
			rel+=str.charAt(str.length()-i-1);
		}
		return rel;

	}
	
	public static boolean gender(String str) //주민번호 남녀구분
	{
		if (str.charAt(7)=='1'||str.charAt(7)=='3')   //not ==1  
			return true;
		return false;
	}
	
	public static void main(String[] args) 
	{
		boolean b;

		b=gender(strInput());

		if(!b)
			System.out.println("여자");
		else
			System.out.println("자");
	}
}
