//[Error:this|12]
// uso del this desde método estático

class A {
	public int a1;
	
	dynamic void m1(int p){
		int v1 = 5;
	}

	static void m2(int p1){
		m3(this.a1);
	}
	
	static void m3(int x){
		
	}

}



class Init{
	static void main(){
		
	}
}


