package ru.otus;

import java.util.Collections;
import java.util.List;

public class Starter {
    public static void main(String[] args) {
//        Проверить, что на ней работают методы из java.util.Collections:
//        Collections.addAll(Collection<? super T> c, T... elements)
//        Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
//        Collections.static <T> void sort(List<T> list, Comparator<? super T> c)

        DIYArrayList<Integer> firstList = new DIYArrayList<>();
        for (int i = 0; i < 50; i++) {
            firstList.add((int) (Math.random() * 1000));
        }

        for (int i = 0; i < firstList.size(); i++) {
            System.out.println(i + "_" + firstList.get(i));
        }

        Collections.sort(firstList);

        for (int i = 0; i < firstList.size(); i++) {
            System.out.println(i + "_" + firstList.get(i));
        }

        DIYArrayList<Integer> listForAddToFirst = new DIYArrayList<>();
        listForAddToFirst.add(777);
        listForAddToFirst.add(888);
        listForAddToFirst.add(999);

        firstList.addAll(listForAddToFirst);

        for (int i = 0; i < firstList.size(); i++) {
            System.out.println(i + "_" + firstList.get(i));
        }

        DIYArrayList<Integer> listForCopy = new DIYArrayList<>();
        listForCopy.add(000);
        listForCopy.add(111);
        listForCopy.add(222);
        listForCopy.add(333);

        Collections.copy(firstList, listForCopy);

        for (int i = 0; i < firstList.size(); i++) {
            System.out.println(i + "_" + firstList.get(i));
        }

    }
}
