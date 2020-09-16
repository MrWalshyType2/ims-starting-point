package com.qa.ims.utils;

import java.util.ArrayList;

public abstract class Observable {
	
	protected ArrayList<Observer> observers = new ArrayList<Observer>();


	public void add(Observer o) {
		this.observers.add(o);
	}
	
	public void remove(Observer o) {
		this.observers.remove(o);
	}
	
	public void notifyObservers() {
		this.observers.forEach(observer -> {
			observer.update();
		});
	}
}
