package com.antifraud.controller;

public class Model {
    final String name;
    final String eventName;
    final String tableName;
    final String path;
    final String modelRoot;

    public String getName() {
        return name;
    }

    public String getEventName() {
        return eventName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPath() {
        return path;
    }

    public Model(final String modelRoot, final String name, final String eventName, final String tableName) {
        this.name = name;
        this.eventName = eventName;
        this.tableName = tableName;
        this.modelRoot = modelRoot;
        this.path = calculateResultFile();
    }

    private String calculateResultFile() {
        return modelRoot + this.toString() + ".pmml";
    }


    public String toString() {
        return name + "_" + tableName;
    }
}
