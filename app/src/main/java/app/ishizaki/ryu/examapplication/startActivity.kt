package app.ishizaki.ryu.examapplication

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.ishizaki.ryu.examapplication.fragments.CoverageFragment
import app.ishizaki.ryu.examapplication.fragments.HomeFragment
import app.ishizaki.ryu.examapplication.fragments.TodoFragment
import kotlinx.android.synthetic.main.activity_start.*

class startActivity : AppCompatActivity() {






    private val todoFragment = TodoFragment()
    private val homeFragment = HomeFragment()
    private val coverageFragment = CoverageFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        replaceFragment(homeFragment)







bottom_navigation.setOnNavigationItemSelectedListener {
    when(it.itemId){
        R.id.ic_todo -> replaceFragment(todoFragment)
        R.id.ic_home -> replaceFragment(homeFragment)
        R.id.ic_coverage -> replaceFragment(coverageFragment)
    }
    true
}



    }




    private fun replaceFragment (fragment: Fragment){
        if (fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }




}