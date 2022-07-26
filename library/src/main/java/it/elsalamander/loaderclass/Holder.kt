package it.elsalamander.loaderclass

import it.elsalamander.loaderclass.calculator.execute.Calculator

interface Holder {

    fun getLoaderExtension() : ManagerLoadExtentions

    fun getMyCalculator() : Calculator
}