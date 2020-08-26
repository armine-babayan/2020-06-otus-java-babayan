package ru.otus;

import java.util.*;

public class DIYArrayList<T> implements List<T> {
    private final int DEFAULT_GROWTH_SIZE = 10;
    private Object[] arrElements;
    private int size = 0;

    public DIYArrayList() {
        arrElements = new Object[DEFAULT_GROWTH_SIZE];
    }

    public DIYArrayList(int defaultSize) {
        if (defaultSize < 0) {
            throw new IllegalArgumentException("Default size can't be smaller than zero: " + defaultSize);
        }
        arrElements = new Object[defaultSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T t) {
        int nextIndex = size;
        add(nextIndex, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index > size) {
            throw new IndexOutOfBoundsException("index can't be bigger than array size: " + index);
        }
        int oldSize = size;
        grow();
        if (index < oldSize) {
            System.arraycopy(arrElements, index, arrElements, index + 1, oldSize - index);
        }
        set(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] externalArr = c.toArray();
        int externalSize = externalArr.length;
        if (externalSize == 0)
            return false;
        int internalSize = size;
        grow(externalSize);
        System.arraycopy(externalArr, 0, arrElements, internalSize, externalSize);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        if (size == 0 || index < 0 || index >= size) {
            throw new NoSuchElementException();
        }

        T oldValue = (T) arrElements[index];

        int newSize = size - 1;

        if (newSize > 0) {
            int countAfterIndex = newSize - index;
            System.arraycopy(arrElements, index + 1, arrElements, index, countAfterIndex);

            int defaultGrowthSize = Math.max(DEFAULT_GROWTH_SIZE, arrElements.length >> 1);
            int overSize = (size + defaultGrowthSize) - arrElements.length;

            if (overSize > 0) {
                arrElements = reduce(overSize);
            }
        }

        set(newSize, null);
        size = newSize;

        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        return (T) arrElements[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        T oldElement = (T) arrElements[index];
        arrElements[index] = element;
        return oldElement;
    }

    private Object[] grow() {
        return grow(1);
    }

    private Object[] grow(int growthSize) {
        if (growthSize <= 0) {
            throw new InternalError("growth size can't be equal to or less than zero: " + growthSize);
        }

        int realSize = arrElements.length;
        int newSize = size + growthSize;

        if (newSize > realSize) {
            int newGrowthSize = Math.max(Math.max(DEFAULT_GROWTH_SIZE, growthSize), realSize >> 1);
            if (realSize > 0) {
                arrElements = resize(newGrowthSize);
            } else {
                arrElements = new Object[newGrowthSize];
            }
        }

        size = newSize;
        return arrElements;
    }

    private Object[] reduce(int changeSize) {
        return resize(-changeSize);
    }

    private Object[] resize(int changeSize) {
        if (changeSize == 0) {
            throw new InternalError("changeSize can't be equal to zero: " + changeSize);
        }

        Object[] arrChanged = new Object[arrElements.length + changeSize];

        System.arraycopy(arrElements, 0, arrChanged, 0, size);

        return arrChanged;
    }

    @Override
    public Object[] toArray() {
        return reduce(arrElements.length - size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYArrayList.DIYListIterator();
    }

    private class DIYListIterator implements ListIterator<T> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("currentIndex:" + currentIndex);
            }
            int nextIndex = currentIndex;
            currentIndex = nextIndex + 1;
            return (T) arrElements[nextIndex];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex != 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int index = currentIndex - 1;
            currentIndex = index;
            return (T) arrElements[index];
        }

        @Override
        public int nextIndex() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException();
            }
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            if (!hasPrevious()) {
                throw new IndexOutOfBoundsException();
            }
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            DIYArrayList.this.remove(currentIndex);
            if (!hasNext() && hasPrevious()) {
                currentIndex = previousIndex();
            }
        }

        @Override
        public void set(T e) {
            DIYArrayList.this.set(previousIndex(), e);
        }

        @Override
        public void add(T e) {
            DIYArrayList.this.add(e);
            currentIndex = currentIndex + 1;
        }
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
