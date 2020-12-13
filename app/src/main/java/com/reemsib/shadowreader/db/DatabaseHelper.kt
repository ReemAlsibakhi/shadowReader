package  com.reemsib.shadowreader.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','x1W1aeQdgU8','10')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','sew8c3Ybukc','10')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','W4BUxCbr6G8','10')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','AGHJTdv6DJ0','10')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','OeZkdXRxhOY','11')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','ZfbNxVCaGeQ','11')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','r7bOHbNgmF8','11')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','v4mFozIWjtc','11')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','hz1XVRVA8S4','12')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','RTU06Vdnwxs','12')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','lnemDRxTU_M','12')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','Jc4KOnCk30E','12')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','X_j13KF8Ask','13')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','qDx2CH-LBFc','13')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','yee90rexWBY','13')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','HiVSIRnUHEg','13')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','xIpS1PT4QIA','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','Jvs3Bx2hcnY','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','RLq273RHk4c','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','-o48VQEKXg4','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 5','knxDoTDucjg','14')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 6','kfnFQMaHmKE','14')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','WiEp7AwkoJY','15')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','OC2Ra7Z7_WM','15')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','_CODSAkGd40','15')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','vdgPvikqmag','15')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','Td1dpbRLWE0','16')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','HHI69mCcjBk','16')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','zM0HeOncAzg','16')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','ZjtqzOH-tyw','16')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','36hVnt7GjOU','17')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','9rFWGvZFLEk','17')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','KwYEoM23jd4','17')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','UA1WzIDviW0','17')")

        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 1','wDPXsOgTHyY','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 2','_-1JXRwn0kc','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 3','K1S6e1j1pA8','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 4','LFmRyJqnkms','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 5','TWHTYODb5j8','18')")
        p0.execSQL("INSERT INTO " + Paragraph.TABLE_NAME + "(${Paragraph.COL_NAME},${Paragraph.COL_VIDEO},${Paragraph.COL_ID_LESSON}) " + " Values ('Paragraph 6','mbUm-RlI_bQ','18')")

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

    fun getVideo(videoId:Int):Paragraph{
        val c = db.rawQuery(
            "select * from ${Paragraph.TABLE_NAME} WHERE ${Paragraph.COL_ID} = $videoId  order by ${Paragraph.COL_ID}  ASC",
            null)
        c.moveToFirst()
        val paragr = Paragraph(c.getInt(0), c.getString(1), c.getString(2))

         return paragr
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
}
