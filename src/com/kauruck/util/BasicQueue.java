package com.kauruck.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

public class BasicQueue<E> implements Queue<E> {

    //Head of the queue
    private Node<E> head;
    //Tail of the queue
    private Node<E> tail;

    //Size of the queue
    private int size = 0;

    @Override
    public int size() {
        return size;
    }


    /**
     * Returns an iterator that works like the normal one, but instead of returning the element, it returns the node holding it.
     * This is only intended for internal use.
     * @return The iterator
     */
    private Iterator<Node<E>> getNodeIterator(){
        return new QueueNodeIterator(this.head);
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        for(E current : this){
            if(current.equals(o)){
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new QueueIterator(this.head);
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        Object[] out = new Object[this.size];
        int i = 0;
        for(E current : this){
            out[i] = current;
            i++;
        }
        return out;
    }


    @NotNull
    @Override
    //This methode contains cast which are deemed not to be save, but which need to be done, so for the compiler not to anyone me this is done
    @SuppressWarnings("unchecked")
    public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
        //This one is save because we create the array form T
        //Creates a new array form a, with the size of this list
        T[] out = (T[]) Array.newInstance(a.getClass(), this.size);
        int i = 0;
        for (E current : this){
            //This one can go wrong because E must not be castable into T
            out[i] = (T) current;
            i++;
        }
        return out;
    }

    @Override
    public boolean add(E e) {
        Node<E> toAdd = new Node<>(e);
        if(tail == null){
            if(head == null){
                head = toAdd;
            }
            tail = toAdd;
        }
        else {
            this.tail.setPost(toAdd);
            toAdd.setPre(this.tail);
            tail = toAdd;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (Iterator<Node<E>> it = this.getNodeIterator(); it.hasNext(); ) {
            Node<E> current = it.next();
            //This one
            if(current.getContent().equals(o)){
                current.getPre().setPost(current.getPost());
                current.getPost().setPre(current.getPre());
                size --;
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        //TODO Reduce amount of looping necessary
        for(Object current: c){
            if(!this.contains(current)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        for(E current : c){
            this.add(current);
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        boolean allSuccess = true;
        //TODO Find way to reduce loops
        for(Object current : c){
            if(!this.remove(c)){
                allSuccess = false;
            }
        }
        return allSuccess;
    }

    //We have some cast deemed to be problematic again
    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        List<E> allContained = new ArrayList<>();
        for(Object current : c){
            if(this.contains(current)){
                //This is the cast, but it is fine because we contain that element, so it needs to be E or one of its subclasses
                allContained.add((E) current);
            }
        }
        //No we can make it that we only contain the wanted elements
        this.clear();
        this.addAll(allContained);
        return true;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;

    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(o.getClass() != BasicQueue.class){
            return false;
        }
        BasicQueue<?> oQueue = (BasicQueue<?>) o;
        if(oQueue.size() != this.size) {
            return false;
        }
        if(oQueue.containsAll(this)){
            return true;
        }
        return false;
    }

    private int hashElements(){
        int out = 0;
        for(E current : this){
            out += current.hashCode();
        }
        return out;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.size) + hashElements();
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if(head == null)
            throw new NoSuchElementException("There is no head, so you cant get it");
        Node<E> out = head;
        if(head.getPost() == null){
            head = null;
            return out.getContent();
        }
        head.getPost().setPre(null);
        head = head.getPost();
        return out.getContent();
    }

    @Override
    public E poll() {
        if(head == null)
            return null;
        Node<E> out = head;
        head.getPost().setPre(null);
        head = head.getPost();
        return out.getContent();
    }

    @Override
    public E element() {
        if(head == null)
            throw new NoSuchElementException("There is no head, so you cant get it");
        return head.getContent();
    }

    @Override
    public E peek() {
        if(head == null)
            return null;
        return head.getContent();
    }

    private class QueueIterator implements Iterator<E>{

        private Node<E> current;

        public QueueIterator(Node<E> head){
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            Node<E> out = current;
            current = current.getPost();
            return out.getContent();
        }
    }

    private class QueueNodeIterator implements Iterator<Node<E>>{

        private Node<E> current;

        public QueueNodeIterator(Node<E> head){
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Node<E> next() {
            Node<E> out = current;
            current = current.getPost();
            return out;
        }
    }
}
