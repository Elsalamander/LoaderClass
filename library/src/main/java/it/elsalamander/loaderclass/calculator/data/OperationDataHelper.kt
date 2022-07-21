package it.elsalamander.loaderclass.calculator.data

import it.elsalamander.loaderclass.calculator.data.builder.OperationDataParametersBuilder

/****************************************************************
 * Serve per definire come deve essere fatta una OperationData
 * da passare come parametro.
 *
 * Sono una mappa che deve avere determinate chiavi
 *
 *
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class OperationDataHelper(private val name: String, private val key : List<String>) {

    /**
     * Nome di questo schema
     * @return String
     */
    fun getName() : String{
        return this.name
    }

    /**
     * Ritorna le chiavi per la OperationData
     * @return List<String>
     */
    fun getKey() : List<String>{
        return this.key
    }

    /**
     * Crea la OperationDataParametersBuilder
     * @return OperationDataParametersBuilder
     */
    fun createOperationDataParametersBuilder() : OperationDataParametersBuilder {
        return OperationDataParametersBuilder(this)
    }
}