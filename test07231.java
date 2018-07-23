import java.util.*;
class  test07231
{
	public static String reverse(String str)
	{
		String rel;
		
		rel="";

		for(int i=0;i<str.length();i++)
		{
			rel+=str.charAt(str.length()-i-1);
		}
		return rel;

	}
	public static void main(String[] args) 
	{
		String str;
		Scanner scan=new Scanner(System.in);

		str=scan.nextLine();
		System.out.println(reverse(str));
	}
}
