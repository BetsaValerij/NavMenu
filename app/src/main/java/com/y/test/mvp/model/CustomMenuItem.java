package com.y.test.mvp.model;

public class CustomMenuItem {
    private String name;
    private String function;
    private String param;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomMenuItem withName(String name) {
        this.name = name;
        return this;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public CustomMenuItem withFunction(String function) {
        this.function = function;
        return this;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public CustomMenuItem withParam(String param) {
        this.param = param;
        return this;
    }
}
