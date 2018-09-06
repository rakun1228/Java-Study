package myvector;

public class MyVector {   //Vector를 직접 구현하기
	int size;  //저장된 객체의 개수
	Object[] objArr;  //객체 배열
	
	MyVector(int capacity) {
		if(capacity<0)
			throw new IllegalArgumentException("유효하지 않은 값입니다.");
		objArr = new Object[capacity];
	}
	
	MyVector(){
		this(10);
	}
	
	int size() { return size; }
	int capacity() { return objArr.length; }
	
	boolean isEmpty() {
		return size==0 ? true : false;   // return size==0;  와 같다.
		}
	
	void clear() {
		for(int i=0;i<objArr.length;i++)
			objArr[i]=null;
		size = 0;
	}
	
	Object get(int index) {
		return objArr[index];
	}
	
	int indexOf(Object obj) {
		for(int i=0;i<size;i++)
			if(obj.equals(objArr[i])) return i;
		return -1;
		
	}
	
	void setCapacity(int capacity) {
		Object[] tmp = new Object[capacity];
		System.arraycopy(objArr, 0, tmp, 0, Math.min(size, capacity));
		objArr=tmp;
	}
	
	void ensureCapacity(int minCapacity) {
		if(minCapacity > objArr.length)
			setCapacity(minCapacity);
	}
	
	Object remove(int index) {
		if(index<0 || index>=size) 
			throw new IndexOutOfBoundsException("범위를 벗어났습니다.");
		
		
		Object tmp=objArr[index];
		
		if(index!=size-1)
			System.arraycopy(objArr, index+1, objArr, index, size-index-1);
		
		objArr[--size]=null;
		
		return tmp;
	}
	
	boolean add(Object obj) {
		ensureCapacity(size+1);
		objArr[size]=obj;
		size++;
		return true;
	}
	
	@Override
	public String toString() {
		String tmp="[";
		for(int i=0;i<size;i++){
			tmp+=objArr[i];
			if(i!=size-1) tmp += " , ";
		}
		return tmp + "]";
	}
}
