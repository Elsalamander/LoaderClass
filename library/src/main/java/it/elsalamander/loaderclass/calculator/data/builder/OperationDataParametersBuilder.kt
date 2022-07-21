package it.elsalamander.loaderclass.calculator.data.builder

import it.elsalamander.loaderclass.calculator.data.OperationDataHelper
import it.elsalamander.loaderclass.calculator.data.OperationDataParameters
import java.util.*

class OperationDataParametersBuilder(private val helper : OperationDataHelper) : OperationDataBuilder<OperationDataParameters>() {

    private val map = TreeMap<String, Double?>()

    init{
        helper.getKey().forEach {
            map[it] = null
        }
    }

    /**
     * Inserisci il valore alla chiave passata se la chiave è valida
     * per questa OperationData
     */
    fun putValue(key : String, value : Double?) : Boolean{
        return if(this.map.containsKey(key)){
            this.map[key] = value
            true
        }else{
            false
        }
    }

    /**
     * Costruisci l'oggetto OperationData
     * @return T
     */
    override fun build(): OperationDataParameters {
        return OperationDataParameters(this.map, helper)
    }
}