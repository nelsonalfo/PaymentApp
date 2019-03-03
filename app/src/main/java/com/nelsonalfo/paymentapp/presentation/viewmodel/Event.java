package com.nelsonalfo.paymentapp.presentation.viewmodel;

public class Event<T> {
    private T content;
    private boolean hasBeenHandled;


    public Event(T content) {
        this.content = content;
        hasBeenHandled = false;
    }

    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        }

        hasBeenHandled = true;
        return content;
    }

    public T peekContent() {
        return content;
    }
}
