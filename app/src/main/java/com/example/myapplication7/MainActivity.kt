package com.example.myapplication7

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.util.rangeTo
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.stream.Collector
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnNumberOnClick(view: View){
        var btnChoose = view as Button
        var btnValue:String = btnChoose.text.toString()
        var shwText:String = showText.text.toString();
        if (btnValue.equals("AC"))showText.setText("0")
        else if(shwText.equals("0") && !btnValue.equals(","))showText.setText(btnValue)
        else showText.setText(showText.text.toString().plus(btnValue))
    }

    fun operatorOnclick(view: View) {

        var btnChoose = view as Button
        var btnValue: String = btnChoose.text.toString()
        var shwText: String = showText.text.toString();
        var numReg:Regex = Regex("(\\-?\\d*\\.?\\d+)")
        var opReg:Regex = Regex("[x,+,/,-]")
        var vReg:Regex = Regex("[x,+,/,.,-]")

        if(btnValue.equals("=")){
            var s = shwText.toCharArray();

            for (i in 0.rangeTo(s.size-2)){
                if(vReg.matches(s[i].toString()) and vReg.matches(s[i+1].toString()))
                    return

            }

            shwText = shwText.toCharArray().filter { i-> numReg.matches(i.toString()) or opReg.matches(i.toString()) or i.equals('.')}.joinToString("")

            println(shwText)
            if(shwText.isEmpty())return
            if(opReg.matches(shwText.first().toString()))shwText=shwText.drop(1)
            if(shwText.isEmpty())return
            if(opReg.matches(shwText.last().toString()))shwText=shwText.dropLast(1)
            if(shwText.isEmpty())return
            var ops: MutableList<String> = mutableListOf()
            for (i in shwText){
                if(opReg.matches(i.toString()))
                    ops.add(i.toString())
            }
            if(ops.isEmpty())return
            var nums: MutableList<Double> = arrayListOf()
            try {
                nums = shwText.split(opReg).stream().map { t -> t.toDouble() }.collect(Collectors.toList()).toMutableList()
            }catch (e:Exception){
                return
            }
            var q = true
            while (q) {
                if(ops.isEmpty())break
                for (i in ops.indices) {
                    if (ops.get(i) == "x") {
                        nums[i]=nums[i].times(nums[i+1])
                        nums.removeAt(i + 1)
                        ops.removeAt(i)
                        break
                    }
                    else if (ops.get(i) == "/") {
                        nums[i]=nums[i]/nums[i+1]
                        nums.removeAt(i + 1)
                        ops.removeAt(i)
                        break
                    }
                    if (i + 1 == ops.size) q = false
                }
            }
            q=true
            while (q) {
                if(ops.isEmpty())break
                for (i in ops.indices) {
                    if (ops.get(i) == "+") {
                        nums[i]=nums[i].plus(nums[i+1])
                        nums.removeAt(i + 1)
                        ops.removeAt(i)
                        break
                    }
                    else if (ops.get(i) == "-") {
                        nums[i]=nums[i].minus(nums[i+1])
                        nums.removeAt(i + 1)
                        ops.removeAt(i)
                        break
                    }
                    if (i + 1 == ops.size) q = false
                }
            }

            showText.setText(nums[0].toString())

        }
    }
}