package com.example.mobileporg;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class}, version = 1, exportSchema = false)
public abstract class ProfileDB extends RoomDatabase {

    public abstract ProfileDAO userDao();
}
