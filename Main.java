package edu.lab5;

public class  Main{
    public static void main(String[] args)  {
        SomeBean b=new SomeBean();
        (new Injector()).inject(b);
        b.foo();
    }
}