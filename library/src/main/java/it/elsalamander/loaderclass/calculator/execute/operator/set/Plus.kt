package it.elsalamander.loaderclass.calculator.execute.operator.set

import android.util.Log
import it.elsalamander.loaderclass.calculator.execute.operator.Operator

/****************************************************************
 * Operazione somma
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class Plus : Operator("+", 1, 2){
    override fun execute(sx: Double?, dx: Double?): Double {
        Log.d("Operazione +", "$sx + $dx")
        return sx!! + dx!!
    }
}