// redefining method

class A {
    dynamic C x(int i, C c){}
}

class B extends A {
    dynamic C x(int i, C c){}
}

class C extends B {
    dynamic C x(int i, C c){}
}


class Void{
    static void main()
    { }
}
