package com.example.healthmonitor;

public interface ISubject {
    void registerObserver(IObserver o);
    void unregisterObserver(IObserver o);
    void notifyObservers();
}
