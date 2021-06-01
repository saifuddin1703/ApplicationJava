package com.example.applicationjava.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crewModel")
public class crewModel {
@PrimaryKey(autoGenerate = true)
      public int id;
    String name;
    String agency;
    String image;
    String wiki;
    String status;

    public crewModel(String name, String agency, String image, String wiki, String status) {
        this.name = name;
        this.agency = agency;
        this.image = image;
        this.wiki = wiki;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getAgency() {
        return agency;
    }

    public String getImage() {
        return image;
    }

    public String getWiki() {
        return wiki;
    }

    public String getStatus() {
        return status;
    }
}
