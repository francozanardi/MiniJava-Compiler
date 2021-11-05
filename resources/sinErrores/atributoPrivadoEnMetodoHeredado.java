class A {
	public int a1;
	public B b;
	private B bPrivado;
	private int entero;
	
	A(){
		b = new B(null, 0, "", null);
	}
	
	dynamic B test(B otroB){
	}
	
	dynamic void m1(int p1){
		B obj = new B(null, 0, "0", this);
		int i = (p1 - m2(this.a1, new B(obj, 0, "", new System()))) * p1;
		
		A self = (B)b.getO();
		
	}
	
	dynamic int m2(int x, B b){
		return x % b.x;
	}

	dynamic void setEntero(){
		entero = 123;
	}
	

}


class B extends A {
	private A a;
	public int x;
	private String j;
	private Object o;
	
	B(B b, int x, String j, Object o){
		this.a = b;
		this.x = x;
		this.j = j;
		this.o = o;

		setEntero();
	}
	
	dynamic Object getO(){
		return o;
	}
}


class Init{
	static void main(){
		
	}
}


