package com.rafal.text_detection_using_camera.data

import android.content.Context
import androidx.room.*
import com.rafal.text_detection_using_camera.utils.Constants


@Database(
    entities = [ImageEntity::class],
    version = Constants.VERSION_NUMBER,
)
@TypeConverters(ImageConverters::class)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {


        //to assure only one variable will have no matter how many threads we have
        @Volatile
        private var INSTANCE: ImageDatabase? = null


        fun getData(context: Context): ImageDatabase = INSTANCE ?: synchronized(this) {
            buildDatabase(context).also { database -> INSTANCE = database }
        }

        fun getInstanceWithoutContext(): ImageDatabase = INSTANCE!!

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ImageDatabase::class.java, Constants.TABLE_NAME
        ).build()


    }
}