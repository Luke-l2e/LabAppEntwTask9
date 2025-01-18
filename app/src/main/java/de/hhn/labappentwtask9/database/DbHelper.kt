package de.hhn.labappentwtask9.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream

/**
 * Helper class for managing the SQLite database.
 *
 * This class provides methods to handle database creation, upgrades, and accessing
 * the database, utilizing an existing database from the app's assets.
 *
 * @param context The application context used to access the database and assets.
 */
class DbHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Called when the database is created for the first time.
     *
     * This method remains empty because the app uses an existing database
     * from the assets folder.
     *
     * @param db The database being created.
     */
    override fun onCreate(db: SQLiteDatabase?) {
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * Deletes the old database and copies the updated version from the assets folder.
     *
     * @param db The database being upgraded.
     * @param oldVersion The old version number of the database.
     * @param newVersion The new version number of the database.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        context.deleteDatabase(DATABASE_NAME)
        copyDatabaseFromAssets()
    }

    /**
     * Provides a readable instance of the database.
     *
     * Ensures the database is copied from assets if it does not exist before opening it.
     *
     * @return A readable [SQLiteDatabase] instance.
     */
    override fun getReadableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getReadableDatabase()
    }

    /**
     * Provides a writable instance of the database.
     *
     * Ensures the database is copied from assets if it does not exist before opening it.
     *
     * @return A writable [SQLiteDatabase] instance.
     */
    override fun getWritableDatabase(): SQLiteDatabase {
        copyDatabaseFromAssets()
        return super.getWritableDatabase()
    }

    /**
     * Copies the database file from the assets folder to the application's database path.
     *
     * This method ensures the database is available for use if it does not already exist.
     */
    private fun copyDatabaseFromAssets() {
        val dbPath = context.getDatabasePath(DATABASE_NAME)
        if (!dbPath.exists()) {
            try {
                context.assets.open(DATABASE_NAME).use { inputStream ->
                    FileOutputStream(dbPath).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Log.d("DbHelper", "Database copied successfully to: ${dbPath.absolutePath}")
                Log.d("DbHelper", "Database size: ${dbPath.length()} bytes")
            } catch (e: Exception) {
                Log.e("DbHelper", "Error copying database", e)
            }
        }
    }

    companion object {
        /**
         * The name of the database file.
         */
        const val DATABASE_NAME = "todoDatabase.db"

        /**
         * The version of the database schema.
         */
        const val DATABASE_VERSION = 1
    }
}