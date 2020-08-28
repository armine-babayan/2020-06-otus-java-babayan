package ru.otus;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestsLauncher {
    public static void main(String[] args) {
        run(Tests.class);
    }

    public static void run(Class className) {

        Method[] allMethods = className.getMethods();
        ArrayList<Method> allTests = new ArrayList();
        ArrayList<Method> allBefore = new ArrayList();
        ArrayList<Method> allAfter = new ArrayList();
        int testsCounter = 0;
        int errorCounter = 0;
        int successCounter = 0;

        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Test.class)) {
                allTests.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                allBefore.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                allAfter.add(method);
            }
        }

        for (Method testMethod : allTests) {
            testsCounter++;
            try {
                Object testInstance = className.getConstructor().newInstance();

                for (Method beforeMethod : allBefore) {
                    beforeMethod.invoke(testInstance);
                }

                testMethod.invoke(testInstance);

                for (Method afterMethod : allAfter) {
                    afterMethod.invoke(testInstance);
                }

                successCounter++;
            } catch (Exception e) {
                errorCounter++;
                System.out.println("Что-то пошло не так...");
                e.printStackTrace();
            }
        }
        System.out.println("Всего тестов: " + testsCounter);
        System.out.println("Упавших тестов: " + errorCounter);
        System.out.println("Успешных тестов: " + successCounter);
    }
}
