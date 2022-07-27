package it.elsalamander.loaderclass.exception

import java.lang.RuntimeException

/****************************************************************
 * Eccezione per file json invalido
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class InvalidJSONDesc(msg : String) : RuntimeException(msg)