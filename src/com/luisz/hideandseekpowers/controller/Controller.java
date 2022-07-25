package com.luisz.hideandseekpowers.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Controller<T> {
    protected final List<T> _data = new ArrayList<>();
    public final int size(){
        return this._data.size();
    }
    public boolean add(T a){
        return this._data.add(a);
    }
    public boolean remove(T a){
        return this._data.remove(a);
    }
    public boolean remove(int index){
        return this._data.remove(index) != null;
    }
    public final boolean has(T a){
        return this._data.contains(a);
    }
    public T get(int index) {
        return this._data.get(index);
    }
    public void foreach(Function<T, Void> f){
        for(T a : this._data)
            f.apply(a);
    }
}