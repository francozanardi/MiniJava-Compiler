///[Error:debugPrint|15]

class A {
	public B b;
	private B bPrivado;
	
	A(){

	}
	
	dynamic A test(){
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


