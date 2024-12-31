package com.lava.server.shell;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("carPark")
public class CarPark {
    int test=1;
    int test2=2;

    String family="carFamily";

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getTest2() {
        return test2;
    }

    public void setTest2(int test2) {
        this.test2 = test2;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
}