//[Error:v1|12]
// La variable v1 no fue declarada

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	dynamic void m2(int p1){
		v1 = 4;
	}

}



class Init{
	static void main()
    	{ }
}


