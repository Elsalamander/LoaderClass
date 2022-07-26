package it.elsalamander.loaderclass.calculator.execute.tree

import it.elsalamander.loaderclass.calculator.execute.operator.Operator

/****************************************************************
 * Nodo albero
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class Node (var hasNumber : Boolean = false, var num : Double = 0.0, var op : Operator? = null,
            var sx : Node? = null, var dx : Node? = null, var padre : Node?){

    constructor(num : Double, padre : Node?) : this(true, num, padre = padre)

    constructor(op: Operator?, padre : Node?) : this(false, 0.0, op, null, null, padre)

    fun getNumber() : Double{
        return if(hasNumber){
            num
        }else{
            val numSx = sx?.getNumber()
            val numDx = dx?.getNumber()
            op?.execute(numSx, numDx)!!
        }
    }
}