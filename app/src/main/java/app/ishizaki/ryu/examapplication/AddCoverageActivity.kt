package app.ishizaki.ryu.examapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_coverage.*
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class AddCoverageActivity : AppCompatActivity(){

    var realm: Realm = Realm.getDefaultInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_coverage)



        coverageSaveButton.setOnClickListener {
            realm.executeTransaction{
                    val newCoverage: Coverage = it.createObject(Coverage::class.java, UUID.randomUUID().toString())
                    newCoverage.subject = subjectCoverage.text.toString()
                    newCoverage.content = contentCoverage.text.toString()

                }
            finish()

            }

        cancelAddingCoverage.setOnClickListener{
            finish()
        }
        }

    }

