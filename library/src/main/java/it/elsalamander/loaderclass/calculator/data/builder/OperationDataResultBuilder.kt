package it.elsalamander.loaderclass.calculator.data.builder

import it.elsalamander.loaderclass.calculator.data.OperationDataParameters
import it.elsalamander.loaderclass.calculator.data.OperationDataResult
import java.util.*

class OperationDataResultBuilder(val parameters: OperationDataParameters) : OperationDataBuilder<OperationDataResult>() {

    val result = TreeMap<String, MutableList<Double?>>()
    var infinitySolution = false

    /**
     * Aggiungi un valore come risultato all'interno di una lista per
     * eventuali più risultati concorrenti alla chiave data
     * @param key : String
     * @param value : Double?
     */
    fun addResult(key : String, value : Double?){
        val list = this.result[key]
        if(list == null){
            val tmp = ArrayList<Double?>()
            tmp.add(value)
            this.result[key] = tmp
        }else{
            list.add(value)
        }
    }

    /**
     * Imposta che ci sono soluzioni infinite
     */
    fun infinitySolution(){
        this.infinitySolution = true
    }

    /**
     * Costruisci l'oggetto OperationData
     * @return T
     */
    override fun build(): OperationDataResult {
        return OperationDataResult(this.result, parameters, this.infinitySolution)
    }
}