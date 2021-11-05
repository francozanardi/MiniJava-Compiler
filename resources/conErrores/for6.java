///[Error:x|13]

class A {
	public B b;
	private B bPrivado;
	
	A(){

	}
	
	static A test(){
		for(A x = this; null != null; this.b = null){
			{
				{
					debugPrint(x.b.i);
				}
			}
		}
	}
}

class B extends A {
	public char i;
}



class Init{
	static void main(){
		
	}
}


