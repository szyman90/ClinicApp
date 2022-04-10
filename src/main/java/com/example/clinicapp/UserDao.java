package com.example.clinicapp;

import java.util.List;

public interface UserDao<T, V> {
    T loginAndPasswordCheck(String login, String password);
    List<V> getVisitsToTable(int i);
}
