package it.elsalamander.loaderclass.calculator

import it.elsalamander.loaderclass.calculator.data.OperationDataHelper
import it.elsalamander.loaderclass.calculator.data.OperationDataParameters
import it.elsalamander.loaderclass.calculator.data.OperationDataResult
import org.json.JSONObject

/****************************************************************
 * Interfaccia che serve per definire le funzioni che devono essere
 * realizzate, per fare dei calcoli.
 *
 * I calcoli presenti sono descritti dalle funzioni, per eseguire
 * queste funzioni c'è bisgno di passare dei dati, tali
 * dati sono mappati in una mappa (chiave-valore)
 * il ritorno è sempre una mappa nella quale il risultato comprende
 * più soluzioni.
 *
 * Poichè pero le soluzioni e i dati possono essere vari, non si usa
 * una mappa data dal framework ma un oggetto "OperationData"
 *
 *
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
abstract class Operation {

    /**
     * Ritorna una lista di tutti i vari helper, che
     * descrivono le varie mappe per i calcoli
     * @return List<OperationDataHelper>
     */
    abstract fun getHelperList() : List<OperationDataHelper>

    /**
     * Restituisci l'helper con le chiavi passate se esiste
     */
    fun getHelperFor(keys : List<String>) : OperationDataHelper?{
        this.getHelperList().forEach { helper ->
            var check = true
            keys.forEach { key ->
                if(!helper.getKey().contains(key)){
                    check = false
                }
            }
            if(check){
                return helper
            }
        }
        return null
    }

    /**
     * Esegui il calcolo dati i parametri
     */
    @Throws(InconsistentDataException::class)
    abstract fun calcola(param : OperationDataParameters) : OperationDataResult

    /**
     * Aggiungi alle informazioni per la cache quelle sulle operation
     */
    @Deprecated("Serviva per la classe ManagerShareClass che è deprecata")
    fun addDataToJSONCache(json : JSONObject) : JSONObject{
        this.getHelperList().forEach {
            val tmp = JSONObject()
            tmp.put(it.getName(), it.getKey())
            json.accumulate("Operation", tmp)
        }
        return json
    }
}