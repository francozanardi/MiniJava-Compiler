class A {
	public int a1;
	
	dynamic void m1(int p1){
		B obj = new B(null, 0, "0", this);
		int b = (a1 - m2(this.a1, new B(obj, 0, "", new System()))) * p1;
	}
	
	dynamic int m2(int x, B b){
		return x % b.x;
	}

}


class B extends A {
	private A a;
	public int x;
	private String j;
	private Object o;
	
	B(A a, int x, String j, Object o){
		this.a = a;
		this.x = x;
		this.j = j;
		this.o = o;
	}
	
	dynamic Object getO(){
		return o;
	}
}


class Init{
	static void main(){
		
	}
}


