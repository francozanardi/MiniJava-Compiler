//[Error:x|20]
// La variable x ya fue declarada

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	dynamic void m2(int p1){
		p1 = 4;
		{
			int x = 0;
			{
				int y = x;
				
				{
					char x = 5;
				}
			}
		}
	}

}



class Init{
	static void main()
    	{ }
}


