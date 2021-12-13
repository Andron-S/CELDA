package com.app.celda.Activities

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.app.celda.Adapter.ExpandableListAdapter
import com.app.celda.R
import kotlinx.android.synthetic.main.selected_course_layout.*

class SelectedCourseScreen : AppCompatActivity() {

    lateinit var listDataChild : HashMap<String, List<String>>
    lateinit var listDataHeader : List<String>
    lateinit var anime : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_course_layout)

        prepareListData()

        lvExpnd.setAdapter(ExpandableListAdapter(this,listDataHeader, listDataChild))

        lvExpnd.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener {
            override fun onGroupClick(
                p0: ExpandableListView?,
                p1: View?,
                p2: Int,
                p3: Long
            ): Boolean {
                return false
            }
        })

        lvExpnd.setOnGroupExpandListener(object : ExpandableListView.OnGroupExpandListener {
            override fun onGroupExpand(p0: Int) {

            }
        })

        lvExpnd.setOnGroupCollapseListener(object : ExpandableListView.OnGroupCollapseListener {
            override fun onGroupCollapse(p0: Int) {
            }

        })

        lvExpnd.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
            override fun onChildClick(
                p0: ExpandableListView?,
                p1: View?,
                p2: Int,
                p3: Int,
                p4: Long
            ): Boolean {
                return false
            }


        })


    }

    private fun prepareListData() {
        listDataHeader = ArrayList<String>()
        listDataChild = HashMap<String, List<String>>()

        (listDataHeader as ArrayList<String>).add("Крутая информация")
        (listDataHeader as ArrayList<String>).add("Гусев крут")

        val cuteInf : ArrayList<String> = ArrayList<String>()
        cuteInf.add("Real")
        cuteInf.add("Po factu")

        val gusevKrut : ArrayList<String> = ArrayList<String>()
        gusevKrut.add("Tupoy")
        gusevKrut.add("gniloy tarakan")

        listDataChild[listDataHeader[0]] = cuteInf
        listDataChild[listDataHeader[1]] = gusevKrut

    }
}