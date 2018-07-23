import java.util.*;
class test07191 
{

	public static void normalsort(int [] arr)
	{		
		int i,j,index=0,tmp=0;
		for(i=0;i<arr.length-1;i++)
		{	
			for(j=i+1;j<arr.length;j++)
			{
				if(arr[j]<arr[i])
				{	
					index=arr[j];
					arr[j]=arr[i];
					arr[i]=index;
				}
			}
		}
	}

	public static void selectsort(int [] arr)
	{
		int i,j,index=0,tmp=0;
		for(i=0;i<arr.length-1;i++)
		{
			index=i;
			for(j=i+1;j<arr.length;j++)
			{
				if(arr[j]<arr[index])
					index=j;
			}
			tmp=arr[i];
			arr[i]=arr[index];
			arr[index]=tmp;
			
		}
	}

	public static void bubblesort(int [] arr)
	{
		int i,j,index=0,tmp=0;
		for(i=0;i<arr.length-1;i++)
		{
			for(j=0;j<arr.length-1-i;j++)
			{
				if(arr[j]>(arr[j+1]))
				{
					tmp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=tmp;
				}
			}
		}
	}

	public static void main(String[] args) 
	{
		int[] a={6,4,5,2,3};
		int[] b={6,4,5,2,3};
		int[] c={6,4,5,2,3};

		normalsort(a);
		for(int i=0;i<a.length;i++)
			System.out.print(a[i]+" ");

		System.out.println();

		selectsort(b);
		for(int i=0;i<b.length;i++)
			System.out.print(b[i]+" ");

		System.out.println();

		bubblesort(c);
		for(int i=0;i<c.length;i++)
			System.out.print(c[i]+" ");
	}
}
