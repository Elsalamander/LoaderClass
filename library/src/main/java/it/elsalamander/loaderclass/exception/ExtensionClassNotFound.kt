package it.elsalamander.loaderclass.exception

import java.lang.RuntimeException

/****************************************************************
 * Ecezzione per il caso non vanga trovata la classse da
 * caricare
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
class ExtensionClassNotFound(msg : String) : RuntimeException(msg)