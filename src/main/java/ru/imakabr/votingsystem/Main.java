package ru.imakabr.votingsystem;


import ru.imakabr.votingsystem.model.AbstractBaseEntity;

class A extends AbstractBaseEntity {
    protected Integer id = 5;
}

class B extends A {
    public Integer id = 10;
}
public class Main {
    public static void main(String[] args) {
        AbstractBaseEntity a = new B();
        System.out.println(a.getId());
    }
}
