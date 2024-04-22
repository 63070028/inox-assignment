package org.example;

public class Car {
    public String no;
    public long time;

    public Car(String no, long time){
        this.no = no;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("no:%s, time:%s", this.no, this.time);
    }
}
