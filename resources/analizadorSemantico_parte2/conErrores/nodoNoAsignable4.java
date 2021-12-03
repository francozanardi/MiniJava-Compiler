///[Error:=|7]
// El lado izquierdo de la asignación es no asignable
class A {
	public int a1;
	
	dynamic void m1(int p1){
		m2() = 5; 
	}
	
	dynamic void m2(){
		
	}

}


class B extends A {
	
}


class Init{
	static void main()
	{ }
}


