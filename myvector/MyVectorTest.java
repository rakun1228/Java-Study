package myvector;

public class MyVectorTest {

	public static void main(String[] args) {
		MyVector v = new MyVector();
		
		System.out.println(v.size());
		System.out.println(v.capacity());
		
		v.add("1");
		v.add("2");
		v.add("3");
		System.out.println(v);
		
		
		System.out.println(v.remove(1));
		System.out.println(v);
	}

}
