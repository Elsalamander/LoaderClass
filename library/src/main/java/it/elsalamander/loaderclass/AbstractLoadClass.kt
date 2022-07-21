package it.elsalamander.loaderclass

import it.elsalamander.loaderclass.calculator.Operation
import it.elsalamander.loaderclass.calculator.data.OperationDataHelper
import it.elsalamander.loaderclass.calculator.data.OperationDataParameters
import it.elsalamander.loaderclass.calculator.data.OperationDataResult
import org.json.JSONObject

/****************************************************************
 * Classe astratta che definisce la interfaccia pubblica delle
 * classi che verranno caricate e usate.
 *
 *
 *
 * @author: Elsalamander
 * @data: 13 luglio 2022
 * @version: v1.0
 ****************************************************************/
abstract class AbstractLoadClass : Operation(){

    /**
     * Aggiungi informazioni al file JSON in condivisione
     *
     * @param jsonObject: JSONObject - oggetto JSON
     */
    @Deprecated("Serviva per la classe ManagerSharedClass che è deprecata anchessa")
    abstract fun addInfoToSharedFile(jsonObject: JSONObject)
}