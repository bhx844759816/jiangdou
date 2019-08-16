package com.jxqm.jiangdou.ui.city.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment


import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator


import com.jxqm.jiangdou.ui.city.model.City


/**
 * Author Bro0cL on 2016/1/26.
 */
class DBManager(private val mContext: Context) {

    private val dbPath: String = (mContext.filesDir.absolutePath + File.separator
            + mContext.packageName + File.separator + "databases" + File.separator)

    val allCities: ArrayList<City>
        get() {
            val db = SQLiteDatabase.openOrCreateDatabase(dbPath + DBConfig.DB_NAME, null)
            val cursor = db.rawQuery("select * from  ${DBConfig.TABLE_NAME}", null)
            val result = ArrayList<City>()
            var city: City
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_NAME))
                val province = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_PROVINCE))
                val pinyin = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_PINYIN))
                val code = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_CODE))
                city = City(name, province, pinyin, code)
                result.add(city)
            }
            cursor.close()
            db.close()
            Collections.sort(result, CityComparator())
            return result
        }

    init {
        copyDBFile()
    }

    private fun copyDBFile() {
        val dir = File(dbPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        //创建新版本数据库
        val dbFile = File(dbPath + DBConfig.DB_NAME)
        if (!dbFile.exists()) {
            val inputStream: InputStream
            val os: OutputStream
            try {
                inputStream = mContext.resources.assets.open(DBConfig.DB_NAME)
                os = FileOutputStream(dbFile)
                someFunc(inputStream, os)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun someFunc(`in`: InputStream, output: OutputStream) {
        try {
            var read: Int = -1
            `in`.use { input ->
                output.use {
                    while (input.read().also { read = it } != -1) {
                        it.write(read)
                    }
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun searchCity(keyword: String): List<City> {
        val sql = ("select * from " + DBConfig.TABLE_NAME + " where "
                + DBConfig.COLUMN_C_NAME + " like ? " + "or "
                + DBConfig.COLUMN_C_PINYIN + " like ? ")
        val db = SQLiteDatabase.openOrCreateDatabase(dbPath + DBConfig.DB_NAME, null)
        val cursor = db.rawQuery(sql, arrayOf("%$keyword%", "$keyword%"))

        val result = ArrayList<City>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_NAME))
            val province = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_PROVINCE))
            val pinyin = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_PINYIN))
            val code = cursor.getString(cursor.getColumnIndex(DBConfig.COLUMN_C_CODE))
            val city = City(name, province, pinyin, code)
            result.add(city)
        }
        cursor.close()
        db.close()
        val comparator = CityComparator()
        Collections.sort(result, comparator)
        return result
    }

    /**
     * sort by a-z
     */
    private inner class CityComparator : Comparator<City> {
        override fun compare(lhs: City, rhs: City): Int {
            val a = lhs.pinyin.substring(0, 1)
            val b = rhs.pinyin.substring(0, 1)
            return a.compareTo(b)
        }
    }

    companion object {
        private const val BUFFER_SIZE = 1024

    }
}
