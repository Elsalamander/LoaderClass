package it.elsalamander.loaderclass.calculator.execute.operator.set

import android.util.Log
import it.elsalamander.loaderclass.calculator.execute.operator.Operator

class Less : Operator("-", 1, 2){
    override fun execute(sx: Double?, dx: Double?): Double {
        Log.d("Operazione -", "$sx - $dx")
        return sx!! - dx!!
    }
}