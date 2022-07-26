package it.elsalamander.loaderclass.calculator.execute

import kotlin.math.pow

/****************************************************************
 * Builder per trasformare una stringa in un numero
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class NumberBuilder {

    var doNumber = false        //Sto costruendo un numero?
    var currentNumber = 0.0     //Il numero che sto costruendo
    var exp = -1.0               //esponente della base 10
    var neg = true              //decimali?
    var lastNumber = 0.0

    fun addElement(ele : Char){
        when(ele){
            '1','2','3','4','5','6','7','8','9','0' ->{
                //è un numero
                doNumber = true
                if(neg){
                    currentNumber *= 10
                    currentNumber += Character.getNumericValue(ele)
                }else{
                    currentNumber += Character.getNumericValue(ele) * 10.0.pow(exp)
                    exp--
                }
            }
            '.' ->{
                //punto
                //controlla se stavo facendo un numero
                if(doNumber){
                    neg = false
                    exp = -1.0
                }
            }
        }
    }

    fun isForNumber(ele : Char) : Boolean{
        return when(ele){
            '1','2','3','4','5','6','7','8','9','0' -> true
            '.' -> doNumber
            else -> false
        }
    }

    fun reset() {
        doNumber = false
        exp = -1.0
        neg = true
        lastNumber = currentNumber
        currentNumber = 0.0
    }
}