package it.elsalamander.loaderclass

import androidx.appcompat.app.AppCompatActivity

/****************************************************************
 * Interfaccia per specificare che l'estensione esegue qualcosa
 * quando viene caricata per la prima volta
 *
 * @author: Elsalamander
 * @data: 14 luglio 2022
 * @version: v1.0
 ****************************************************************/
interface OnFirstLoad {

    fun onFirstLoad(holder : Holder, activity : AppCompatActivity)
}