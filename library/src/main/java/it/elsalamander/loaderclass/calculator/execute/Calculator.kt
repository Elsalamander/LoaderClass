package it.elsalamander.loaderclass.calculator.execute

import it.elsalamander.loaderclass.calculator.execute.operator.Operator
import it.elsalamander.loaderclass.calculator.execute.tree.CalculatorTree

class Calculator() {

    init{
        Operator.init()
    }

    fun calc(expression : String) : Double{
        if(this.validate(expression)){
            return exec(expression)
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
        tree.add(10.0)
        tree.add(Operator.getOperator("/")!!)
        tree.add(2.0)
        tree.add(Operator.getOperator("+")!!)
        tree.add(8.0)
        tree.add(Operator.getOperator("*")!!)
        tree.add(3.0)
        tree.add(Operator.getOperator("-")!!)
        tree.add(5.0)
        tree.add(Operator.getOperator("+")!!)
        tree.add(2.0)

        return tree.solve()
    }

    /**
     * Inserisci nell'albero
     * la risoluzione delle parentesi
     * e ritorna la posizione della parentesi che chiude questa
     */
    private fun resolveTonde(pos: Int, expression: String): Int {
        //cerca la parentesi chiudente
        //crea la subString
        //manda in risoluzione la substring ottenuta
        //ritorna la posizione della parentesi chiudente
        return 0
    }
}