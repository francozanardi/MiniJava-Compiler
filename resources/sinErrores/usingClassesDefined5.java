// controlando existencia de clases

class A {
    private B b;
    dynamic Object m1(System s){}
}


class B extends A{
    private A a;
    private B b1;
    private System s;
    public Object o;

    static void main()
    { }

    dynamic A a(A a){}

    static B getInstance(B b){
        return this;
    }
}
