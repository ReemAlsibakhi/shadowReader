package  com.r.foodproject.ui.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getBlobOrNull
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.Lesson
import com.reemsib.shadowreader.model.Paragraph
import com.reemsib.shadowreader.model.Semester


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var db: SQLiteDatabase
    init {
        db = writableDatabase
    }
    //called just one
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(Semester.CREATE_TABLE)
        p0.execSQL(Lesson.CREATE_TABLE)
        p0.execSQL(Paragraph.CREATE_TABLE)

        p0.execSQL("INSERT INTO " + Semester.TABLE_NAME + "(${Semester.COL_NAME}) " + " Values ('First Semester')")
        p0.execSQL("INSERT INTO " + Semester.TABLE_NAME + "(${Semester.COL_NAME}) " + " Values ('Second Semester')")


        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 1 My summer holiday','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 2 Good friends','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 3 Summer adventures','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 4 Films I like','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 5 Revision','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 6 Healthy food','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 7 The olive trees of Palestine','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 8 Signs around us','1')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 9 Revision','1')")


        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 10 A visit to the Dead Sea','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 11 Exciting things to do','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 12 At the clinic','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 13 Where does rain come from?','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 14 Revision','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 15 Great explorers','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 16 My friends and why I like them','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 17 Good manners','2')")
        p0.execSQL("INSERT INTO " + Lesson.TABLE_NAME + "(${Lesson.COL_NAME},${Lesson.COL_ID_SEMESTER}) " + " Values ('Unit 18 Revision','2')")

//        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('First Paragraph','${R.raw.a1}','0')")
//        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Second Paragraph','${R.raw.a1}','0')")
//        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Third Paragraph','${R.raw.a1}','0')")
//        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Fourth Paragraph','${R.raw.a1}','0')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','E4dDdPuUiSk','1')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','is7HusSGvrg','1')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','-i_6errLZbA','1')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','E7hPExbHNVQ','1')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','C9OC-CHjhhA','2')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','eP5y8PKYOJg','2')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','lMRfiGGcZR8','2')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','t89cc-YM820','2')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','XIMT_CWVan8','3')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','LU5jiGPOYz0','3')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','ptNJSoEzh2c','3')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','wuJjAw0q8p8','3')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','Ksga9XqK88g','4')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','bMMR_ybwG6c','4')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','YFQgsyodlmU','4')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','1L6Ej9XLXOE','4')")


        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','j32MSYNGsh8','5')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','sLpm5LRrmz0','5')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','7NJw9jpddh4','5')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','9Hkqv6sgihg','5')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','Msu44OoL_Mg','6')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','Kpm9AiDbe-w','6')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','cIS_m-_6qVw','6')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','GUMmo_Al5iU','6')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','v77MKT3XwoY','7')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','mJLA3pHD5_0','7')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','Vmf3wv7JhJo','7')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','1XsmpvLd02o','7')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','r0nPqjpKzC8','8')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','X0Kwv68fhsg','8')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','zXmrf2jaG5U','8')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','PHB3oXBrn7E','8')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','q2L_vamIwoY','9')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','dfFeUx6kJgI','9')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','Et4tiBKlK8k','9')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','Ep1ZQsfNeoY','9')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 5','wa7gu_Av6_k','9')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 6','ik_elXL4vak','9')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','10')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','10')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','10')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','10')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','11')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','11')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','11')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','11')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','12')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','12')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','12')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','12')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','13')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','13')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','13')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','13')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','14')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','15')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','15')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','15')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','15')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','16')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','16')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','16')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','16')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','17')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','17')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','17')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','17')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','${R.raw.a1}','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','${R.raw.a1}','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','${R.raw.a1}','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','${R.raw.a1}','18')")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS ${Semester.TABLE_NAME}")
        p0.execSQL("DROP TABLE IF EXISTS ${Lesson.TABLE_NAME}")
        p0.execSQL("DROP TABLE IF EXISTS ${Paragraph.TABLE_NAME}")
        onCreate(p0)
    }
    companion object {
        const val DATABASE_NAME = "LessonDB"
        const val DATABASE_VERSION = 1
    }

    fun getLesson(semesterId:Int): ArrayList<Lesson> {
        val lessons = ArrayList<Lesson>()
        val c = db.rawQuery(
            "select * from ${Lesson.TABLE_NAME} WHERE ${Lesson.COL_ID_SEMESTER}=$semesterId  order by ${Lesson.COL_ID}   ASC",
            null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val lesson = Lesson(
                c.getInt(0), c.getString(1), c.getInt(2))
            lessons.add(lesson)
            c.moveToNext()
        }
        c.close()
        return lessons
    }

    fun getParag(lessonId:Int): ArrayList<Paragraph> {
        val paragraphs = ArrayList<Paragraph>()
        val c = db.rawQuery(
            "select * from ${Paragraph.TABLE_NAME} WHERE ${Paragraph.COL_ID_LESSON} = $lessonId  order by ${Paragraph.COL_ID}  ASC",
            null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val paragr = Paragraph(
                c.getInt(0), c.getString(1), c.getString(2))
            paragraphs.add(paragr)
            c.moveToNext()
        }
        c.close()
        return paragraphs
    }
//    ,c.getInt(3)>0,c.getInt(4)>0

}
    //==================================================================

//    fun addProduct(name: String, description: String, prodAmount: Int, price: Double, is_fav: Int, image: String, rate: Int, id_categ: Int): Boolean {
//        val cv = ContentValues()
//        cv.put(Product.COL_NAME, name)
//        cv.put(Product.COL_DESC, description)
//        cv.put(Product.COL_AMOUNT, prodAmount)
//        cv.put(Product.COL_PRICE, price)
//        cv.put(Product.COL_ISFAV, is_fav)
//        cv.put(Product.COL_IMG, image)
//        cv.put(Product.COL_RATE, rate)
//        cv.put(Product.COL_ID_CATEGORIES, id_categ)
//        return db.insert(Product.TABLE_NAME, null, cv) > 0
//    }

//    fun save(id: Int): Boolean {
//        val cv = ContentValues()
//        cv.put(Product.COL_ISFAV, 1)
//        Log.e("save", cv.toString())
//        return db.update(Product.TABLE_NAME, cv, "id = $id", null) > 0
//    }

//    fun getFavourite(): ArrayList<Product> {
//
//        val products = ArrayList<Product>()
//        val c =
//            db.rawQuery(
//                "select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ISFAV}=1  order by ${Product.COL_ID} DESC",
//                null
//            )
//        c.moveToFirst()
//        while (!c.isAfterLast) {
//            val prod = Product(
//                c.getInt(0), c.getString(1), c.getString(2),
//                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
//            )
//            products.add(prod)
//            c.moveToNext()
//        }
//        c.close()
//        return products
//    }


//    fun getBurger(): ArrayList<Product> {
//        val products = ArrayList<Product>()
//        val c =
//            db.rawQuery(
//                "select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ID_CATEGORIES}=0  order by ${Product.COL_ID}   DESC",
//                null
//            )
//        c.moveToFirst()
//        while (!c.isAfterLast) {
//            val prod = Product(
//                c.getInt(0), c.getString(1), c.getString(2),
//                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
//            )
//            products.add(prod)
//            c.moveToNext()
//        }
//        c.close()
//        return products
//    }

//    fun getPizza(): ArrayList<Product> {
//        val products = ArrayList<Product>()
//        val c =
//            db.rawQuery(
//                "select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ID_CATEGORIES}=1  order by ${Product.COL_ID}   DESC",
//                null
//            )
//        c.moveToFirst()
//        while (!c.isAfterLast) {
//            val prod = Product(
//                c.getInt(0), c.getString(1), c.getString(2),
//                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
//            )
//            products.add(prod)
//            c.moveToNext()
//        }
//        c.close()
//        return products
//    }

//    fun getDessert(): ArrayList<Product> {
//        val products = ArrayList<Product>()
//        val c =
//            db.rawQuery(
//                "select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ID_CATEGORIES}=2  order by ${Product.COL_ID}   DESC",
//                null
//            )
//        c.moveToFirst()
//        while (!c.isAfterLast) {
//            val prod = Product(
//                c.getInt(0), c.getString(1), c.getString(2),
//                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
//            )
//            products.add(prod)
//            c.moveToNext()
//        }
//        c.close()
//        return products
//    }

//
//    fun deleteOrder(id: Int): Boolean {
//         return db.delete(Orders.TABLE_NAME, "id = $id", null) > 0
//        return true
//    }

//    fun updateProduct(oldId: Int, name: String, des: String): Boolean {
//        val cv = ContentValues()
//        cv.put(Product.COL_NAME, name)
//        cv.put(Product.COL_DESC, des)
//        return db.update(Product.TABLE_NAME, cv, "id = $oldId", null) > 0
//    }

    /////////// categories
//    fun addCategory(name: String): Boolean {
//        val cv = ContentValues()
//        cv.put(Categories.COL_NAME, name)
//        return db.insert(Categories.TABLE_NAME, null, cv) > 0
//    }
    /////////users

//    fun addUser(image: String, name: String, email: String, password: String): Boolean {
//        val cv = ContentValues()
//        cv.put(Users.COL_IMG, image)
//        cv.put(Users.COL_NAME, name)
//        cv.put(Users.COL_EMAIL, email)
//        cv.put(Users.COL_PASSWORD, password)
//        return db.insert(Users.TABLE_NAME, null, cv) > 0
//    }

//    fun userAuth(email: String, password: String): Int {
//        var i: Int = -1
//        val selectionArgs = arrayOf<String>(email, password)
//        val c =
//            db.rawQuery(
//                "select * from ${Users.TABLE_NAME} where ${Users.COL_EMAIL}=? and ${Users.COL_PASSWORD}=?",
//                selectionArgs
//            )
//        c.moveToFirst()
//        if (!c.isAfterLast) {
//            i = c.getInt(0)
//        }
//        return if (i > 0) {
//            i
//        } else {
//            -1
//        }
//    }

    /////////orders
//    fun addOrder( id_prod: Int, user_id: Int, name:String ,image:String, price:Double): Boolean {
//        val cv = ContentValues()
//        cv.put(Orders.COL_PRODUCT_ID, id_prod)
//        cv.put(Orders.COL_USER_ID, user_id)
//        cv.put(Orders.COL_NAME, name)
//        cv.put(Orders.COL_IMG, image)
//        cv.put(Orders.COL_PRICE, price)
//        return db.insert(Orders.TABLE_NAME, null, cv) > 0
//    }

//    fun getOrder(id_user:Int): ArrayList<Orders> {
//        val order = ArrayList<Orders>()
//        val c =
//            db.rawQuery(
//                "select * from ${Orders.TABLE_NAME} WHERE ${Orders.COL_USER_ID}=$id_user order by ${Orders.COL_ID}   DESC",
//                null
//            )
//        c.moveToFirst()
//        while (!c.isAfterLast) {
//            val ord = Orders(
//                c.getInt(0), c.getInt(1), c.getInt(2),c.getString(3),
//                c.getInt(4),c.getDouble(5))
//            order.add(ord)
//            c.moveToNext()
//        }
//        c.close()
//        return order
   // }
//    fun addPrices(price: Double): Boolean{
//        val cv = ContentValues()
//   //     cv.put(Price.COL, price)
//        cv.put(Price.COL_PRICE, price)
//        return db.insert(Price.TABLE_NAME, null, cv) > 0
//    }
//    fun getPrices(): ArrayList<Price> {
//   //     var i: Int = -1
//       val price = ArrayList<Price>()
//        val c =
//            db.rawQuery(
//                "select ${Price.COL_PRICE} from ${Price.TABLE_NAME}",null)
//        c.moveToFirst()
//        while (!c.isAfterLast) {
//            val pri = Price(c.getDouble(0))
//             price.add(pri)
//            c.moveToNext()
//        }
//        c.close()
//        return price
//    }


