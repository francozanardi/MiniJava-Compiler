///[Error:m|13]


class A {
    dynamic int m(int x, char y){} 
}

class B extends A {
    dynamic int m(int x, char y){} 
}

class C extends B {
    dynamic int m(char x, int y)
    {}
}


class Int {
	static void main(){}
}

