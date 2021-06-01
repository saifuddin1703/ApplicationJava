package com.example.applicationjava.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.applicationjava.daos.crewDAO;
import com.example.applicationjava.models.crewModel;

@Database(entities = {crewModel.class} , version = 1)
public abstract class crewdatabase extends RoomDatabase {
    public abstract crewDAO getDAO();
}
