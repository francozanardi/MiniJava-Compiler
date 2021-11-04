// redefining method

class A {
    static void x(int p){}
}

class B extends A {
    static void x(int i){}
}

class C extends B {
    static void x(int i){}
}


class Void{
    static void main()
    { }
}
