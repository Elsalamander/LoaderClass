package it.elsalamander.loaderclass.calculator

/****************************************************************
 * Eccezione quando la stringa passata per la calcolatrice non è
 * valida
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class InconsistentDataException(msg : String) : RuntimeException(msg)