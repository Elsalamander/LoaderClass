package it.elsalamander.loaderclass.calculator.execute

import java.util.*

class SpecialBuilder {

    var building = false
    val currentMap = TreeMap<String, Double>()
    val oldMap = TreeMap<String, Double>()

    fun reset(){
        oldMap.clear()
        oldMap.putAll(currentMap)
        currentMap.clear()
        building = false
    }

}