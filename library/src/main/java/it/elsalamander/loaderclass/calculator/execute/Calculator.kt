package it.elsalamander.loaderclass.calculator.execute

import it.elsalamander.loaderclass.ManagerLoadExtentions
import it.elsalamander.loaderclass.calculator.InconsistentDataException
import it.elsalamander.loaderclass.calculator.Operation
import it.elsalamander.loaderclass.calculator.data.OperationData
import it.elsalamander.loaderclass.calculator.data.OperationDataParameters
import it.elsalamander.loaderclass.calculator.execute.operator.Operator
import it.elsalamander.loaderclass.calculator.execute.tree.CalculatorTree
import java.util.*
import kotlin.jvm.Throws

/****************************************************************
 * Classe per descrivere e realizzare la calcolatrice
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class Calculator(val extension : ManagerLoadExtentions) {

    init{
        Operator.init()
    }

    fun calc(expression : String) : Double{
        if(this.validate(expression)){
            try{
                return exec(expression)
            }catch(e : InconsistentDataException){
                return Double.NaN
            }
        }
        return 0.0;
    }

    /**
     * Controlla se il numero delle parentesi aperte è lo stesso delle chiuse
     * per le () e le []
     */
    private fun validate(expression: String): Boolean {

        return true
    }

    /**
     * Avendo una stringa valida, scansiona ogni elemento della stringa
     *
     */
    private fun exec(expression: String): Double {
        val tree = CalculatorTree()
        val doNumber = NumberBuilder()
        val doOp = OperatorBuilder()

        //scansiona ogni elemento della stringa
        var pos = 0
        while(pos < expression.length){
            val it = expression[pos]

            //ho 4 casi particolari, numeri, operatori, (, [
            if(doNumber.isForNumber(it)){
                //non è per un operatore
                if(doOp.doOperator){
                    doOp.reset()
                    tree.add(Operator.getOperator(doOp.lastOperator)!!)
                }
                doNumber.addElement(it)
            }else{
                //non è per un numero
                if(doNumber.doNumber){
                    doNumber.reset()
                    tree.add(doNumber.lastNumber)
                }

                if(doOp.isOperatorChar(it)){
                    doOp.addOperatorChar(it)
                }else{
                    if(doOp.doOperator){
                        doOp.reset()
                        tree.add(Operator.getOperator(doOp.lastOperator)!!)
                    }
                    if(it == '('){
                        //tonda
                        val pair = this.resolveTonde(pos, expression)
                        pos = pair.first
                        tree.add(pair.second)
                    }else if(it == '['){
                        val pair = this.resolveQuadre(pos, expression)
                        pos = pair.first
                        tree.add(pair.second)
                    }
                }
                //
            }

            //incrementa la posizione
            pos++
        }

        if(doNumber.doNumber){
            doNumber.reset()
            tree.add(doNumber.lastNumber)
        }

        return tree.solve()
    }



    /**
     * Inserisci nell'albero
     * la risoluzione delle parentesi
     * e ritorna la posizione della parentesi che chiude questa
     */
    private fun resolveTonde(pos: Int, expression: String): Pair<Int, Double> {
        //cerca la parentesi chiudente
        val closePos = this.findClose(pos, expression, '(', ')')

        //crea la subString
        val sub = expression.substring(pos+1,closePos)

        //manda in risoluzione la substring ottenuta
        val result = this.exec(sub)

        //ritorna la posizione della parentesi chiudente
        return Pair(closePos, result)
    }

    private fun resolveQuadre(pos: Int, expression: String): Pair<Int, Double> {
        //cerca la parentesi chiudente
        val closePos = this.findClose(pos, expression, '[', ']')

        //crea la subString
        val sub = expression.substring(pos+1,closePos)

        //faccio il parsing con il ";"
        val scan = Scanner(sub)
        scan.useDelimiter(";")

        var subScan : Scanner?
        val map = TreeMap<String, Double?>()
        while(scan.hasNext()){
            val str = scan.next()
            subScan = Scanner(str)
            subScan.useDelimiter("=")

            val key = subScan.next()
            val value = subScan.next()
            if(value == "?" || value.toDoubleOrNull() != null){
                map[key] = value.toDoubleOrNull()
            }else{
                map[key] = this.calc(value)
            }

            subScan.close()
        }
        scan.close()

        var result = Double.NaN
        //ho la mappa chiave valore, ora devo eseguire la mappa
        //prima devo trovare l'estensione che risolve la possibile mappa
        extension.extentions.forEach {
            val extension = it.value.second
            val helper = extension.getOperation().getHelperFor(map.keys.toList())
            var operation : OperationDataParameters? = null
            if(helper != null){
                val builder = helper.createOperationDataParametersBuilder()

                map.forEach{ input ->
                    builder.putValue(input.key, input.value)
                }

                operation = builder.build()

                val res = extension.getOperation().calcola(operation)

                result = res.result[operation.getFirstNullKey()!!]!!.first()!!

                //esci dal forEach
                return@forEach
            }
        }



        return Pair(closePos, result)
    }

    @Throws(InconsistentDataException::class)
    private fun findClose(from : Int, expression: String, open: Char, close : Char) : Int{
        var countUp = 0
        var countDown = 0
        var pos = from
        while(pos < expression.length){
            val it = expression[pos]
            if(it == open){
                countUp++
            }
            if(it == close){
                countDown++
            }
            if(countDown == countUp){
                return pos
            }
            pos++
        }
        throw InconsistentDataException("Stringa non valida")
    }
}