package com.company.component;

import com.company.annotation.Component;

@Component
public class Test {

    private TestSon testSon;

    public String hello(String name){
        System.out.println(testSon.test("nqy"));
        return "hello:"+name;
    }

}
