package ru.otus;

import java.util.NoSuchElementException;

public class Tests {
    @Before
    public void beforeEach(){
        System.out.println("Before each method");
    }

    @After
    public void afterEach(){
        System.out.println("After each method");
    }

    @Test
    public void simpleTest(){
        System.out.println("Тест #1");
    }

    @Test
    public void secondTest(){
        System.out.println("Тест #2");
    }

    @Test
    public void someTest(){
        System.out.println("Тест #3");
        throw new NoSuchElementException();
    }

    @Test
    public void anotherTest(){
        System.out.println("Тест #4");
    }
}
