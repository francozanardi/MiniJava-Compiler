// llamada a un método estático desde un método dinámico

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
		m2(v1+1);
		System s = new System();
		s.printS("Testing");
	}

	static void m2(int p1){
		A a = new A();
		Init i = new Init();
		a.a1 = 123;
	}
	
	static void m3(int x){
		
	}

}



class Init{
	static void main(){
		
	}
}


