///[Error:x|16]

class A {
	public B b;
	private B bPrivado;
	
	A(){

	}
	
	static A test(){
		for(A x = this; null != null; this.b = null){
			int y = 0;
		}
		
		boolean b = x == null;
	}
}

class B extends A {
	
}




class Init{
	static void main(){
		
	}
}


