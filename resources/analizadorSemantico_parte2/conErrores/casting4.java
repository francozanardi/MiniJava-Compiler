///[Error:=|11]

class A {
	public B b;
	
	A(){
		b = new B(null, 0, "", null);
	}
	
	dynamic B test(B otroB){
		((A)((A)otroB.getO()).b.getO()).b = this;
	}
	
	dynamic void m1(int p1){
		B obj = new B(null, 0, "0", this);
		int i = (a1 - m2(this.a1, new B(obj, 0, "", new System()))) * p1;
		
		A self = (B)obj.getO();
		
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
	
	B(B b, int x, String j, Object o){
		this.a = b;
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


