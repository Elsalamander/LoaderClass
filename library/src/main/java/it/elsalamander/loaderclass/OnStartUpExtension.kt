package it.elsalamander.loaderclass

import it.elsalamander.loaderclass.calculator.execute.Calculator

interface OnStartUpExtension {

    /**
     * Esegui qualcosa mentre si avvia l'esensione
     */
    fun doOnStartUp(param: Holder, newLoad : Boolean = false)
}