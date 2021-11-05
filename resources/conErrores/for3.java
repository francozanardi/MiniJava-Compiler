///[Error:x|13]

class A {
	public B b;
	private B bPrivado;
	
	A(){

	}
	
	static A test(){
		for(A x = this; null != null; this.b = null){
			int x = 0;
		}
	}
}

class B extends A {
	
}



class Init{
	static void main(){
		
	}
}


