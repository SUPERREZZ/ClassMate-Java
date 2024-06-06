package com.eduapp.edumanagerapp.Controller;

public abstract class Dashboard {
    abstract void initialize();

    abstract void loadMessagesFromDatabase();

    abstract void startDataUpdateTimeline();
}
