package com.example.tobyspring3.dao;

import java.sql.Connection;

public interface ConnectionMaker {
    Connection makeConnection();
}