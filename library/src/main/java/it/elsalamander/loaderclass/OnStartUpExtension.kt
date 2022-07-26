package it.elsalamander.loaderclass

/****************************************************************
 * Interfaccia per specificare che l'estensione ha un startUp
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
interface OnStartUpExtension {

    /**
     * Esegui qualcosa mentre si avvia l'esensione
     */
    fun doOnStartUp(param: Holder, newLoad : Boolean = false)
}