package com.anmory.cloudfile.obverser;

import java.util.List;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午11:03
 */

public class VersionUpdate extends Subject{
    String subjectState;

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }

    public String getSubjectState() {
        return subjectState;
    }
    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
