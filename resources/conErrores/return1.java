///[Error:return|12]

class A {
	public B b;
	private B bPrivado;
	
	A(){

	}
	
	dynamic A test(){
		return;
	}
}

class B extends A {
	public int i;
	
	
	dynamic int getI(){
		return i;
	}
}



class Init{
	static void main(){
		
	}
}


