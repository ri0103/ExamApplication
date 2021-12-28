package app.ishizaki.ryu.examapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class UntilExamDatePicker: AppCompatActivity() {

    lateinit var selectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        selectButton = findViewById<Button>(R.id.untilExamSelectButton)              //EditText（入力エリア）

        //EditTextのクリックイベントを設定


    }

    /* 日付ピッカーダイアログを開くためのメソッド */


}