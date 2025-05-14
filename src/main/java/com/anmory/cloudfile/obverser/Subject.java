package com.anmory.cloudfile.obverser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午11:01
 */

public abstract class Subject {
    List<Observer> observers = new ArrayList<>();

    public abstract List<Observer> getObservers();

    public abstract void notifyObservers();

    public abstract void addObserver(Observer observer);

    public abstract void removeObserver(Observer observer);
}
