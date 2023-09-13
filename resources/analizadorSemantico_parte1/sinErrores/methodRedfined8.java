// redefining method

class A {
    dynamic C x(int i, C c){}
    static B y(boolean i, B c){}
    dynamic A z(B i, B c){}
}

class B extends A {
    dynamic C x(int i, C c){}
    static B y(boolean i, B c){}
    dynamic A z(B i, B c){}
}

class C extends B {
    dynamic C x(int i, C c){}
    static B y(boolean i, B c){}
    dynamic A z(B i, B c){}
}


class Void{
    static void main()
    { }
}
