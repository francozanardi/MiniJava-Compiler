//[Error:v1|11]
// La variable v1 no fue declarada

class A {
	public int a1;

	dynamic void m1(int p1){
		{
			int v1 = 5;
		}
		v1 = 4;
	}

}



class Init{
	static void main()
    	{ }
}


