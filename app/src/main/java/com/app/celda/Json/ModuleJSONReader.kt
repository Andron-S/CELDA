package com.app.celda.Json

import android.util.Log
import com.app.celda.Model.Course
import com.app.celda.Model.Lesson
import com.app.celda.Model.Module
import org.json.JSONObject

class ModuleJSONReader : JSONReader<Module> {
    val URL = "https://api.npoint.io/50aa76918a8ebde24c71"
    val TAG = "courses"

    fun getList() : MutableList<MutableList<Module>> {
        return parse2(download())
    }

    override fun download(): String? {
        return JSONGet().execute(URL).get()
    }

    override fun parse2(data: String?): MutableList<MutableList<Module>> {
        val mainList = mutableListOf<MutableList<Module>>()
        val moduleList = mutableListOf<Module>()
        var lst = mutableListOf<Lesson>()
        var s : String
        var lesson : Lesson
        if (data?.isNotEmpty() == true) {
            val json = JSONObject(data)
            val jsonArray = json.getJSONArray(TAG)
            //Log.i("AUF:","$jsonArray")
            for (i in 0 until jsonArray.length()) {

                for (j in 0 until jsonArray.getJSONObject(i).getJSONArray("modules").length()) {

                    s = jsonArray.getJSONObject(i).getJSONArray("modules").getJSONObject(j).getString("nameModule")

                    for (k in 0 until jsonArray.getJSONObject(i).getJSONArray("modules").getJSONObject(j).getJSONArray("lessons").length()) {

                        lesson = Lesson(jsonArray.getJSONObject(i).getJSONArray("modules").getJSONObject(j).getJSONArray("lessons").getJSONObject(k).getString("nameLesson"),
                            jsonArray.getJSONObject(i).getJSONArray("modules").getJSONObject(j).getJSONArray("lessons").getJSONObject(k).getString("discript"),
                            jsonArray.getJSONObject(i).getJSONArray("modules").getJSONObject(j).getJSONArray("lessons").getJSONObject(k).getString("myUrl"))
                        lst.add(lesson)
                        Log.i("AUF:::","$lst")

                    }

                    Log.i("FUA2:::", "$lst")
                    moduleList.add(Module(s,lst))
                    Log.i("ListModuleAdd::", "$moduleList")
                    lst = mutableListOf<Lesson>()
                    Log.i("CLear:", "$lst, $moduleList")

                }

                mainList.add(moduleList)

            }

        }
//        Log.i("DANNIE:","$lst")
        Log.i("ITOGOVIY:","$mainList")
        return mainList
    }

    override fun parseObject(jsonObject: JSONObject): Module? {
        return Module("", ArrayList())
    }

    override fun parse(data: String?): MutableList<Module> {
        TODO("Not yet implemented")
    }

}