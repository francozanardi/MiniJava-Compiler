//[Error:p|21]
// La variable p ya fue declarada

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	dynamic void m2(int p1){
		p1 = 4;
		{
			char p = 'a';
			{
				int x = 0;
				{
					p1 = 5;
				}
				{
					String p = "";
				}
			}
		}
		{
			
		}
	}

}



class Init{
	static void main()
    	{ }
}


