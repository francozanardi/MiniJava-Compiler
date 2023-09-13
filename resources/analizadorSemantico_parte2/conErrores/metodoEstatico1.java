//[Error:a1|12]
// Asignación de atributo de instancia en método estático

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	static void m2(int p1){
		a1 = 4;
	}

}



class Init{
	static void main()
    	{ }
}


