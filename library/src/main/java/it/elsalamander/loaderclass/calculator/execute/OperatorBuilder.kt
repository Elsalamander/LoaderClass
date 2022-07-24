package it.elsalamander.loaderclass.calculator.execute

class OperatorBuilder{

    var doOperator = false
    var currentOperator = ""
    var lastOperator = ""

    fun addOperatorChar(char: Char){
        doOperator = true
        currentOperator += char
    }

    fun isOperatorChar(char: Char) : Boolean{
        return when(char){
            '1','2','3','4','5','6','7','8','9','0','.' -> false
            '(',')','[',']' -> false
            else -> true
        }
    }

    fun reset(){
        doOperator = false
        lastOperator = currentOperator
        currentOperator = ""
    }

}