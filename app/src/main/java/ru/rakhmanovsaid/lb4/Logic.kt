package ru.rakhmanovsaid.lb4

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView
import ru.rakhmanovsaid.lb4.databinding.ActivityMainBinding

class Logic {
    lateinit var result : TextView
    lateinit var display: TextView

        fun Initialize(binding: ActivityMainBinding) {
            result = binding.result
            display = binding.display
            binding.btn0.setOnClickListener { OnClick(it as Button)}
            binding.btn1.setOnClickListener { OnClick(it as Button)}
            binding.btn2.setOnClickListener { OnClick(it as Button)}
            binding.btn3.setOnClickListener { OnClick(it as Button)}
            binding.btn4.setOnClickListener { OnClick(it as Button)}
            binding.btn5.setOnClickListener { OnClick(it as Button)}
            binding.btn6.setOnClickListener { OnClick(it as Button)}
            binding.btn7.setOnClickListener { OnClick(it as Button)}
            binding.btn8.setOnClickListener { OnClick(it as Button)}
            binding.btn9.setOnClickListener { OnClick(it as Button)}
            binding.btnMod.setOnClickListener { OnClick(it as Button)}
            binding.btnErase.setOnClickListener { OnClick(it as Button)}
            binding.btnPi.setOnClickListener { OnClick(it as Button)}
            binding.btnLeftBr.setOnClickListener { OnClick(it as Button)}
            binding.btnRightBr.setOnClickListener { OnClick(it as Button)}
            binding.btnE.setOnClickListener { OnClick(it as Button)}
            binding.btnSqrt.setOnClickListener { OnClick(it as Button)}
            binding.btnPlus.setOnClickListener { OnClick(it as Button)}
            binding.btnMinus.setOnClickListener { OnClick(it as Button)}
            binding.btnMultiply.setOnClickListener { OnClick(it as Button)}
            binding.btnDivide.setOnClickListener { OnClick(it as Button)}
            binding.btnDot.setOnClickListener { OnClick(it as Button)}
            binding.btnEquals.setOnClickListener { OnClick(it as Button)}
            binding.btnClear.setOnClickListener {
                result.text = ""
                display.text = "0"
            }
        }

    //string can contain only 11 numbers
        @SuppressLint("SetTextI18n")
        fun OnClick(btn : Button) {
            var newDisplay = display.text.toString()
            when (btn.id)
            {
                R.id.btn_erase -> {
                    newDisplay = newDisplay.substring(0, display.text.length - 1)
                    if (newDisplay == "")
                        newDisplay = "0"
                }

                R.id.btn_equals -> {
                    var tmp = display.text.toString().replace(Regex("^-"), "0-")
                    var res = MathParser.evaluate(tmp).toString()
                    result.text = if(res.length > 16) res.substring(0,16) else res
                    //result.text = tmp
                }

                else -> {
                    newDisplay += btn.text.toString()
                }
            }

        newDisplay = newDisplay.replace(Regex("^0([\\dπe√\\-()])"), "$1")

        if (newDisplay.length > 11) newDisplay = newDisplay.substring(0, 11)

        display.text = newDisplay
        }
}