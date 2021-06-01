package com.example.applicationjava.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.applicationjava.models.crewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.rxjava3.core.Completable;

@Dao
public interface crewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllcrew(ArrayList<crewModel> list);

    @Query("SELECT * from crewModel")
    Flowable<List<crewModel>> getAllCrew();

    @Query("DELETE FROM crewModel")
    io.reactivex.Completable deleteUsers();

}
