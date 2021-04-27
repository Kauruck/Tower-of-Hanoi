package com.kauruck.util;

public class Node<E> {
    private E content;
    private Node<E> pre;
    private Node<E> post;

    public Node(E content) {
        this.content = content;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public Node<E> getPre() {
        return pre;
    }

    public void setPre(Node<E> pre) {
        this.pre = pre;
    }

    public Node<E> getPost() {
        return post;
    }

    public void setPost(Node<E> post) {
        this.post = post;
    }
}
