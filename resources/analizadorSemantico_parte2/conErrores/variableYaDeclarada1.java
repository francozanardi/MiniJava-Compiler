//[Error:p1|14]
// La variable p1 fue declarada como parámetro

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	dynamic void m2(int p1){
		p1 = 4;
		{
			char p1 = 'a';
		}
	}

}



class Init{
	static void main()
    	{ }
}


