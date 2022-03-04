package app.ishizaki.ryu.examapplication

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {


    val realm: Realm = Realm.getDefaultInstance()
    var resultSaved = readFirst()
    var myScoreSum: Int = 0
    var averageScoreSum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if (resultSaved !=null){
            subjectResultET1.setText(resultSaved!!.subject1)
            subjectResultET2.setText(resultSaved!!.subject2)
            subjectResultET3.setText(resultSaved!!.subject3)
            subjectResultET4.setText(resultSaved!!.subject4)
            subjectResultET5.setText(resultSaved!!.subject5)
            subjectResultET6.setText(resultSaved!!.subject6)
            subjectResultET7.setText(resultSaved!!.subject7)
            subjectResultET8.setText(resultSaved!!.subject8)
            subjectResultET9.setText(resultSaved!!.subject9)
            subjectResultET10.setText(resultSaved!!.subject10)
            subjectResultET11.setText(resultSaved!!.subject11)
            subjectResultET12.setText(resultSaved!!.subject12)
            subjectResultET13.setText(resultSaved!!.subject13)
            subjectResultET14.setText(resultSaved!!.subject14)
            subjectResultET15.setText(resultSaved!!.subject15)
            subjectResultET16.setText(resultSaved!!.subject16)
            subjectResultET17.setText(resultSaved!!.subject17)
            subjectResultET18.setText(resultSaved!!.subject18)

            myScoreResultET1.setText(resultSaved!!.myScore1)
            myScoreResultET2.setText(resultSaved!!.myScore2)
            myScoreResultET3.setText(resultSaved!!.myScore3)
            myScoreResultET4.setText(resultSaved!!.myScore4)
            myScoreResultET5.setText(resultSaved!!.myScore5)
            myScoreResultET6.setText(resultSaved!!.myScore6)
            myScoreResultET7.setText(resultSaved!!.myScore7)
            myScoreResultET8.setText(resultSaved!!.myScore8)
            myScoreResultET9.setText(resultSaved!!.myScore9)
            myScoreResultET10.setText(resultSaved!!.myScore10)
            myScoreResultET11.setText(resultSaved!!.myScore11)
            myScoreResultET12.setText(resultSaved!!.myScore12)
            myScoreResultET13.setText(resultSaved!!.myScore13)
            myScoreResultET14.setText(resultSaved!!.myScore14)
            myScoreResultET15.setText(resultSaved!!.myScore15)
            myScoreResultET16.setText(resultSaved!!.myScore16)
            myScoreResultET17.setText(resultSaved!!.myScore17)
            myScoreResultET18.setText(resultSaved!!.myScore18)

            averageScoreResultET1.setText(resultSaved!!.averageScore1)
            averageScoreResultET2.setText(resultSaved!!.averageScore2)
            averageScoreResultET3.setText(resultSaved!!.averageScore3)
            averageScoreResultET4.setText(resultSaved!!.averageScore4)
            averageScoreResultET5.setText(resultSaved!!.averageScore5)
            averageScoreResultET6.setText(resultSaved!!.averageScore6)
            averageScoreResultET7.setText(resultSaved!!.averageScore7)
            averageScoreResultET8.setText(resultSaved!!.averageScore8)
            averageScoreResultET9.setText(resultSaved!!.averageScore9)
            averageScoreResultET10.setText(resultSaved!!.averageScore10)
            averageScoreResultET11.setText(resultSaved!!.averageScore11)
            averageScoreResultET12.setText(resultSaved!!.averageScore12)
            averageScoreResultET13.setText(resultSaved!!.averageScore13)
            averageScoreResultET14.setText(resultSaved!!.averageScore14)
            averageScoreResultET15.setText(resultSaved!!.averageScore15)
            averageScoreResultET16.setText(resultSaved!!.averageScore16)
            averageScoreResultET17.setText(resultSaved!!.averageScore17)
            averageScoreResultET18.setText(resultSaved!!.averageScore18)

            examNameResultET.setText(resultSaved!!.examName)


            calculateScoresSum()
            myScoreResultSum.text = myScoreSum.toString()
            averageScoreResultSum.text = averageScoreSum.toString()


        }



        saveResultButton.setOnClickListener {
            realm.executeTransaction{
                if (resultSaved !=null){
                    resultSaved!!.subject1 = subjectResultET1.text.toString()
                    resultSaved!!.subject2 = subjectResultET2.text.toString()
                    resultSaved!!.subject3 = subjectResultET3.text.toString()
                    resultSaved!!.subject4 = subjectResultET4.text.toString()
                    resultSaved!!.subject5 = subjectResultET5.text.toString()
                    resultSaved!!.subject6 = subjectResultET6.text.toString()
                    resultSaved!!.subject7 = subjectResultET7.text.toString()
                    resultSaved!!.subject8 = subjectResultET8.text.toString()
                    resultSaved!!.subject9 = subjectResultET9.text.toString()
                    resultSaved!!.subject10 = subjectResultET10.text.toString()
                    resultSaved!!.subject11 = subjectResultET11.text.toString()
                    resultSaved!!.subject12 = subjectResultET12.text.toString()
                    resultSaved!!.subject13 = subjectResultET13.text.toString()
                    resultSaved!!.subject14 = subjectResultET14.text.toString()
                    resultSaved!!.subject15 = subjectResultET15.text.toString()
                    resultSaved!!.subject16 = subjectResultET16.text.toString()
                    resultSaved!!.subject17 = subjectResultET17.text.toString()
                    resultSaved!!.subject18 = subjectResultET18.text.toString()
                    resultSaved!!.myScore1 = myScoreResultET1.text.toString()
                    resultSaved!!.myScore2 = myScoreResultET2.text.toString()
                    resultSaved!!.myScore3 = myScoreResultET3.text.toString()
                    resultSaved!!.myScore4 = myScoreResultET4.text.toString()
                    resultSaved!!.myScore5 = myScoreResultET5.text.toString()
                    resultSaved!!.myScore6 = myScoreResultET6.text.toString()
                    resultSaved!!.myScore7 = myScoreResultET7.text.toString()
                    resultSaved!!.myScore8 = myScoreResultET8.text.toString()
                    resultSaved!!.myScore9 = myScoreResultET9.text.toString()
                    resultSaved!!.myScore10 = myScoreResultET10.text.toString()
                    resultSaved!!.myScore11 = myScoreResultET11.text.toString()
                    resultSaved!!.myScore12 = myScoreResultET12.text.toString()
                    resultSaved!!.myScore13 = myScoreResultET13.text.toString()
                    resultSaved!!.myScore14 = myScoreResultET14.text.toString()
                    resultSaved!!.myScore15 = myScoreResultET15.text.toString()
                    resultSaved!!.myScore16 = myScoreResultET16.text.toString()
                    resultSaved!!.myScore17 = myScoreResultET17.text.toString()
                    resultSaved!!.myScore18 = myScoreResultET18.text.toString()
                    resultSaved!!.averageScore1 = averageScoreResultET1.text.toString()
                    resultSaved!!.averageScore2 = averageScoreResultET2.text.toString()
                    resultSaved!!.averageScore3 = averageScoreResultET3.text.toString()
                    resultSaved!!.averageScore4 = averageScoreResultET4.text.toString()
                    resultSaved!!.averageScore5 = averageScoreResultET5.text.toString()
                    resultSaved!!.averageScore6 = averageScoreResultET6.text.toString()
                    resultSaved!!.averageScore7 = averageScoreResultET7.text.toString()
                    resultSaved!!.averageScore8 = averageScoreResultET8.text.toString()
                    resultSaved!!.averageScore9 = averageScoreResultET9.text.toString()
                    resultSaved!!.averageScore10 = averageScoreResultET10.text.toString()
                    resultSaved!!.averageScore11 = averageScoreResultET11.text.toString()
                    resultSaved!!.averageScore12 = averageScoreResultET12.text.toString()
                    resultSaved!!.averageScore13 = averageScoreResultET13.text.toString()
                    resultSaved!!.averageScore14 = averageScoreResultET14.text.toString()
                    resultSaved!!.averageScore15 = averageScoreResultET15.text.toString()
                    resultSaved!!.averageScore16 = averageScoreResultET16.text.toString()
                    resultSaved!!.averageScore17 = averageScoreResultET17.text.toString()
                    resultSaved!!.averageScore18 = averageScoreResultET18.text.toString()
                    resultSaved!!.examName = examNameResultET.text.toString()

                }else{
                    val newResult: Result = it.createObject(Result::class.java, UUID.randomUUID().toString())
                    newResult.subject1 = subjectResultET1.text.toString()
                    newResult.subject2 = subjectResultET2.text.toString()
                    newResult.subject3 = subjectResultET3.text.toString()
                    newResult.subject4 = subjectResultET4.text.toString()
                    newResult.subject5 = subjectResultET5.text.toString()
                    newResult.subject6 = subjectResultET6.text.toString()
                    newResult.subject7 = subjectResultET7.text.toString()
                    newResult.subject8 = subjectResultET8.text.toString()
                    newResult.subject9 = subjectResultET9.text.toString()
                    newResult.subject10 = subjectResultET10.text.toString()
                    newResult.subject11 = subjectResultET11.text.toString()
                    newResult.subject12 = subjectResultET12.text.toString()
                    newResult.subject13 = subjectResultET13.text.toString()
                    newResult.subject14 = subjectResultET14.text.toString()
                    newResult.subject15 = subjectResultET15.text.toString()
                    newResult.subject16 = subjectResultET16.text.toString()
                    newResult.subject17 = subjectResultET17.text.toString()
                    newResult.subject18 = subjectResultET18.text.toString()
                    newResult.myScore1 = myScoreResultET1.text.toString()
                    newResult.myScore2 = myScoreResultET2.text.toString()
                    newResult.myScore3 = myScoreResultET3.text.toString()
                    newResult.myScore4 = myScoreResultET4.text.toString()
                    newResult.myScore5 = myScoreResultET5.text.toString()
                    newResult.myScore6 = myScoreResultET6.text.toString()
                    newResult.myScore7 = myScoreResultET7.text.toString()
                    newResult.myScore8 = myScoreResultET8.text.toString()
                    newResult.myScore9 = myScoreResultET9.text.toString()
                    newResult.myScore10 = myScoreResultET10.text.toString()
                    newResult.myScore11 = myScoreResultET11.text.toString()
                    newResult.myScore12 = myScoreResultET12.text.toString()
                    newResult.myScore13 = myScoreResultET13.text.toString()
                    newResult.myScore14 = myScoreResultET14.text.toString()
                    newResult.myScore15 = myScoreResultET15.text.toString()
                    newResult.myScore16 = myScoreResultET16.text.toString()
                    newResult.myScore17 = myScoreResultET17.text.toString()
                    newResult.myScore18 = myScoreResultET18.text.toString()
                    newResult.averageScore1 = averageScoreResultET1.text.toString()
                    newResult.averageScore2 = averageScoreResultET2.text.toString()
                    newResult.averageScore3 = averageScoreResultET3.text.toString()
                    newResult.averageScore4 = averageScoreResultET4.text.toString()
                    newResult.averageScore5 = averageScoreResultET5.text.toString()
                    newResult.averageScore6 = averageScoreResultET6.text.toString()
                    newResult.averageScore7 = averageScoreResultET7.text.toString()
                    newResult.averageScore8 = averageScoreResultET8.text.toString()
                    newResult.averageScore9 = averageScoreResultET9.text.toString()
                    newResult.averageScore10 = averageScoreResultET10.text.toString()
                    newResult.averageScore11 = averageScoreResultET11.text.toString()
                    newResult.averageScore12 = averageScoreResultET12.text.toString()
                    newResult.averageScore13 = averageScoreResultET13.text.toString()
                    newResult.averageScore14 = averageScoreResultET14.text.toString()
                    newResult.averageScore15 = averageScoreResultET15.text.toString()
                    newResult.averageScore16 = averageScoreResultET16.text.toString()
                    newResult.averageScore17 = averageScoreResultET17.text.toString()
                    newResult.averageScore18 = averageScoreResultET18.text.toString()
                    newResult.examName = examNameResultET.text.toString()
                }
            }
            calculateScoresSum()
            myScoreResultSum.text = myScoreSum.toString()
            averageScoreResultSum.text = averageScoreSum.toString()
        }


        saveResultButton.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setTitle("結果の一括削除")
                .setMessage("すべての点数・結果を削除してもよろしいですか。")
                .setPositiveButton("はい"){ _,_ ->
                    deleteResultFromRealm()
                }
                .setNegativeButton("キャンセル"){_,_ -> }
                .show()

            true
        }

    }

    fun readFirst(): Result? {
        return realm.where(Result::class.java).findFirst()
    }

    fun deleteResultFromRealm() {
        val task = realm.where(Result::class.java).findAll()
        realm.executeTransaction {
            task.deleteAllFromRealm()
        }
    }

    fun calculateScoresSum(){
        myScoreSum = listOf(

            if (myScoreResultET1.text.toString() == "") {
                0.0
            } else {
                myScoreResultET1.text.toString().toDouble()
            },if (myScoreResultET2.text.toString() == "") {
                0.0
            } else {
                myScoreResultET2.text.toString().toDouble()
            },if (myScoreResultET3.text.toString() == "") {
                0.0
            } else {
                myScoreResultET3.text.toString().toDouble()
            },if (myScoreResultET4.text.toString() == "") {
                0.0
            } else {
                myScoreResultET4.text.toString().toDouble()
            },if (myScoreResultET5.text.toString() == "") {
                0.0
            } else {
                myScoreResultET5.text.toString().toDouble()
            },if (myScoreResultET6.text.toString() == "") {
                0.0
            } else {
                myScoreResultET6.text.toString().toDouble()
            },if (myScoreResultET7.text.toString() == "") {
                0.0
            } else {
                myScoreResultET7.text.toString().toDouble()
            },if (myScoreResultET8.text.toString() == "") {
                0.0
            } else {
                myScoreResultET8.text.toString().toDouble()
            },if (myScoreResultET9.text.toString() == "") {
                0.0
            } else {
                myScoreResultET9.text.toString().toDouble()
            },if (myScoreResultET10.text.toString() == "") {
                0.0
            } else {
                myScoreResultET10.text.toString().toDouble()
            },if (myScoreResultET11.text.toString() == "") {
                0.0
            } else {
                myScoreResultET11.text.toString().toDouble()
            },if (myScoreResultET12.text.toString() == "") {
                0.0
            } else {
                myScoreResultET12.text.toString().toDouble()
            },if (myScoreResultET13.text.toString() == "") {
                0.0
            } else {
                myScoreResultET13.text.toString().toDouble()
            },if (myScoreResultET14.text.toString() == "") {
                0.0
            } else {
                myScoreResultET14.text.toString().toDouble()
            },if (myScoreResultET15.text.toString() == "") {
                0.0
            } else {
                myScoreResultET15.text.toString().toDouble()
            },if (myScoreResultET16.text.toString() == "") {
                0.0
            } else {
                myScoreResultET16.text.toString().toDouble()
            },if (myScoreResultET17.text.toString() == "") {
                0.0
            } else {
                myScoreResultET17.text.toString().toDouble()
            },if (myScoreResultET18.text.toString() == "") {
                0.0
            } else {
                myScoreResultET18.text.toString().toDouble()
            }

        ).sum().toInt()


        averageScoreSum = listOf(

            if (averageScoreResultET1.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET1.text.toString().toDouble()
            },if (averageScoreResultET2.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET2.text.toString().toDouble()
            },if (averageScoreResultET3.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET3.text.toString().toDouble()
            },if (averageScoreResultET4.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET4.text.toString().toDouble()
            },if (averageScoreResultET5.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET5.text.toString().toDouble()
            },if (averageScoreResultET6.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET6.text.toString().toDouble()
            },if (averageScoreResultET7.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET7.text.toString().toDouble()
            },if (averageScoreResultET8.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET8.text.toString().toDouble()
            },if (averageScoreResultET9.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET9.text.toString().toDouble()
            },if (averageScoreResultET10.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET10.text.toString().toDouble()
            },if (averageScoreResultET11.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET11.text.toString().toDouble()
            },if (averageScoreResultET12.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET12.text.toString().toDouble()
            },if (averageScoreResultET13.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET13.text.toString().toDouble()
            },if (averageScoreResultET14.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET14.text.toString().toDouble()
            },if (averageScoreResultET15.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET15.text.toString().toDouble()
            },if (averageScoreResultET16.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET16.text.toString().toDouble()
            },if (averageScoreResultET17.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET17.text.toString().toDouble()
            },if (averageScoreResultET18.text.toString() == "") {
                0.0
            } else {
                averageScoreResultET18.text.toString().toDouble()
            }

        ).sum().toInt()
    }


}