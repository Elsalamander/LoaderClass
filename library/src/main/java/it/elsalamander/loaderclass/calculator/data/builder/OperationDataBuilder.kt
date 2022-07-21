package it.elsalamander.loaderclass.calculator.data.builder

/****************************************************************
 * Classe astratta su come deve essere fatto un builder
 * per i seguenti builder
 *
 * - OperationDataParametersBuilder
 * - OperationDataResultBuilder
 *
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
abstract class OperationDataBuilder<T> {

    /**
     * Costruisci l'oggetto OperationData
     * @return T
     */
    abstract fun build() : T
}