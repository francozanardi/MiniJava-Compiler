// controlando existencia de clases

class A {
    dynamic Object m1(System s){}
}


class B extends A{
    static void main()
    { }

    dynamic A a(A a){}

    static B getInstance(B b){
        return this;
    }
}
