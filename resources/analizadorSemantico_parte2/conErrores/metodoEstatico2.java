//[Error:m1|13]
// llamada a método dinámico desde método estático

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	static void m2(int p1){
		int a1 = 4;
		m1(p1);
	}

}



class Init{
	static void main()
	{ }
}


