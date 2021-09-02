package com.company;

import com.company.component.Test;

public class Main {

    public static void main(String[] args) throws Exception {
        MyApplicationContext context = new MyApplicationContext();
        context.init();
        Test test  = (Test) context.getBean("test");
        System.out.println(test.hello("wxk"));
    }
}
