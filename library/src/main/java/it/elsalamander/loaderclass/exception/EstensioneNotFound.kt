package it.elsalamander.loaderclass.exception

import java.lang.RuntimeException

/****************************************************************
 * Ecezzione quando non si trova la estensione
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class EstensioneNotFound(msg : String) : RuntimeException(msg)