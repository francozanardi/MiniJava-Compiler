///[Error:!|12]

class A {
	public B b;
	private B bPrivado;
	
	A(){

	}
	
	dynamic A test(){
		for(A x = this; !null; x=null);
	}
}

class B extends A {
	
}



class Init{
	static void main(){
		
	}
}


