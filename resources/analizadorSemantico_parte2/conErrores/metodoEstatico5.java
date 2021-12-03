//[Error:a1|17]
// uso de atributo de instancia desde método estático

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	static void m2(int p1){
		{
			{
				int a1 = 5;
			}
			
			m3(a1);
		}
	}
	
	static void m3(int x){
		
	}

}



class Init{
	static void main(){
		
	}
}


