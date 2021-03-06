package app.ishizaki.ryu.examapplication.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.util.*

class HomeFragment : Fragment(){

    var calendar1: Calendar = Calendar.getInstance();
    var calendar2: Calendar = Calendar.getInstance()

    var yearSavedE: Int = 1000
    var monthSavedE: Int = 1
    var dateSavedE: Int = 1
    val realm: Realm = Realm.getDefaultInstance()

    var UntilExamDateSaved = readFirst()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        untilExamSaveButton.isVisible = false


        if (UntilExamDateSaved !=null) {
            yearSavedE = UntilExamDateSaved!!.yearE
            monthSavedE = UntilExamDateSaved!!.monthE
            dateSavedE = UntilExamDateSaved!!.dateE
            countDownShow()
        }else{
            countDownFirstShow()
        }


        untilExamSelectButton.setOnClickListener{

            DatePickerDialog(
                requireContext(),
                R.style.PickerStyle,

                { _, year, monthOfYear, dayOfMonth ->
                    Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }

                    yearSavedE = year
                    monthSavedE = monthOfYear
                    dateSavedE = dayOfMonth

                    countDownShow()

                },
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
            ).apply {
            }.show()


            untilExamSaveButton.isVisible = true

        }

        untilExamSaveButton.setOnClickListener{

            realm.executeTransaction{
                if (UntilExamDateSaved !=null){
                    UntilExamDateSaved!!.yearE = yearSavedE
                    UntilExamDateSaved!!.monthE = monthSavedE
                    UntilExamDateSaved!!.dateE = dateSavedE
                }else {
                    val newExamDate: UntilExamDate = it.createObject(UntilExamDate::class.java, UUID.randomUUID().toString())
                    newExamDate.yearE = yearSavedE
                    newExamDate.monthE = monthSavedE
                    newExamDate.dateE = dateSavedE
                }
            }

            untilExamSaveButton.isVisible = false


        }

        intentResultActivityButton.setOnClickListener {
            val intent = Intent(activity, ResultActivity::class.java)
            activity?.startActivity(intent)
        }




    }





    fun readFirst(): UntilExamDate? {
        return realm.where(UntilExamDate::class.java).findFirst()
    }


    fun countDownShow(){

        calendar1.set(yearSavedE, monthSavedE, dateSavedE)
        var timeMills1: Long = calendar1.timeInMillis
        var currentTimeMills: Long = System.currentTimeMillis()
        var timeDiff: Long = timeMills1 - currentTimeMills;
        timeDiff = timeDiff / 1000;
        timeDiff = timeDiff / 60;
        timeDiff = timeDiff / 60;


        if (timeDiff<0.1){

            if (timeDiff<-8760000){
                untilExamTitle.text="???????????????"
                untilExamNumber.setText("?????????????????????\n????????????????????????\n?????????????????????\n????????????????????????")
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusregular) }
                untilExamNumber.textSize = 24F
                untilExamNumber.setTextColor(getResources().getColor(R.color.blackorwhite))
                untilExamSelectButton.text="???????????????????????????"
            }else {
                untilExamNumber.setText("!???????????????!")
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusmedium) }
                untilExamNumber.textSize = 60F
                untilExamTitle.isVisible = false
                untilExamNumber.setTextColor(getResources().getColor(R.color.delete_red))
                untilExamSelectButton.text="${yearSavedE as Int}???${monthSavedE as Int+1}???${dateSavedE as Int}???~"
            }

        }
        else{
            timeDiff = timeDiff / 24;
            timeDiff = timeDiff + 1

            if(timeDiff<4){
                var str: String = timeDiff.toString() + "???"
                untilExamNumber.setText(str)
                untilExamNumber.textSize = 130F
                untilExamTitle.text="??????????????????"
                untilExamTitle.isVisible = true
                untilExamNumber.setTextColor(getResources().getColor(R.color.delete_red))
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusbold) }
                untilExamSelectButton.text="${yearSavedE as Int}???${monthSavedE as Int+1}???${dateSavedE as Int}???~"
            }else{
                var str: String = timeDiff.toString() + "???"
                untilExamNumber.setText(str)
                untilExamNumber.textSize = 100F
                untilExamTitle.text="??????????????????"
                untilExamTitle.isVisible = true
                untilExamNumber.setTextColor(getResources().getColor(R.color.blackorwhite))
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusbold) }
                untilExamSelectButton.text="${yearSavedE as Int}???${monthSavedE as Int+1}???${dateSavedE as Int}???~"
            }
        }



    }


    fun countDownFirstShow(){

        calendar2.set(yearSavedE, monthSavedE, dateSavedE)
        var timeMills1: Long = calendar2.timeInMillis
        var currentTimeMills: Long = System.currentTimeMillis()
        var timeDiff: Long = timeMills1 - currentTimeMills;
        timeDiff = timeDiff / 1000;
        timeDiff = timeDiff / 60;
        timeDiff = timeDiff / 60;


        if (timeDiff<0.1){

            if (timeDiff<-8760000){
                untilExamTitle.text="???????????????"
                untilExamNumber.setText("?????????????????????\n????????????????????????\n?????????????????????\n????????????????????????")
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusregular) }
                untilExamNumber.textSize = 24F
                untilExamNumber.setTextColor(getResources().getColor(R.color.blackorwhite))
                untilExamSelectButton.text="???????????????????????????"
            }else {
                untilExamNumber.setText("!???????????????!")
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusmedium) }
                untilExamNumber.textSize = 60F
                untilExamTitle.isVisible = false
                untilExamNumber.setTextColor(getResources().getColor(R.color.delete_red))
                untilExamSelectButton.text="${yearSavedE as Int}???${monthSavedE as Int+1}???${dateSavedE as Int}???~"
            }

        }
        else{
            timeDiff = timeDiff / 24;
            timeDiff = timeDiff + 1

            if(timeDiff<4){
                var str: String = timeDiff.toString() + "???"
                untilExamNumber.setText(str)
                untilExamNumber.textSize = 130F
                untilExamTitle.text="??????????????????"
                untilExamTitle.isVisible = true
                untilExamNumber.setTextColor(getResources().getColor(R.color.delete_red))
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusbold) }
                untilExamSelectButton.text="${yearSavedE as Int}???${monthSavedE as Int+1}???${dateSavedE as Int}???~"
            }else{
                var str: String = timeDiff.toString() + "???"
                untilExamNumber.setText(str)
                untilExamNumber.textSize = 100F
                untilExamTitle.text="??????????????????"
                untilExamTitle.isVisible = true
                untilExamNumber.setTextColor(getResources().getColor(R.color.blackorwhite))
                untilExamNumber.typeface = this.context?.let { ResourcesCompat.getFont(it, R.font.mplusbold) }
                untilExamSelectButton.text="${yearSavedE as Int}???${monthSavedE as Int+1}???${dateSavedE as Int}???~"
            }
        }



    }



}