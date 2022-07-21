package it.elsalamander.loaderclass.calculator.data
/****************************************************************
 * Classe oggetto che contiene i vari valori di input
 * per una Operazione.
 *
 * E' una mappa Non mutabile restituita da un builder o costruita
 * direttamente con il costruttore.
 *
 *
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class OperationDataParameters(private val parameters: Map<String, Double?>, private val helper: OperationDataHelper) {

    /**
     * Ritorna la mappa dei valori passati
     */
    fun getParameters() : Map<String, Double?>{
        return this.parameters
    }

    /**
     * Ritorna l'helper di base di questa Operation
     */
    fun getHelper() : OperationDataHelper{
        return this.helper
    }
}