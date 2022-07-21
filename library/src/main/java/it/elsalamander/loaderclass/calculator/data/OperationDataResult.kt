package it.elsalamander.loaderclass.calculator.data

/****************************************************************
 * Classe oggetto che contiene i vari valori di output di una
 * operation, contiene anche i valori di input in caso
 *
 *
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class OperationDataResult(val result: Map<String, List<Double?>>, val parame: OperationDataParameters, val infinitySolution: Boolean = false) : OperationData(){

    /**
     * Ritorna il numero di soluzioni trovate
     * -1 indica
     */
    fun getNumSolution() : Int{
        return if(infinitySolution){
            -1
        }else{
            var num = 0
            result.forEach {
                num += it.value.size
            }
            num
        }
    }
}