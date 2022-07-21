package it.elsalamander.loaderclass

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.*


/****************************************************************
 * Classe per gestire le classi condivise fra le app.
 *
 * Come identificativo di una app si metterà il nome si essa.
 *
 *
 * @author: Elsalamander
 * @data: 13 luglio 2022
 * @version: v1.0
 ****************************************************************/
@Deprecated("Classe non più necessaria")
class ManagerShareClass(private val context : Context, private val loadClass : AbstractLoadClass?) {

    companion object{
        const val NAME_SHARED_FILE = "Calculator.json"
        const val PATH_PACKAGE = "path-package"
        const val PATH_APP = "path-app"
    }

    /**
     * Costruttore, verifica la presenza dell'app nel file in share
     * in caso crea il file e/o inserisci l'app nel file.
     */
    init{
        this.registerApp()
    }

    /**
     * Constrolla se esiste il file nella shared storage,
     * verifica se l'app è registrata, se non lo è si
     * registra.
     *
     * Permessi richiesti
     * Manifest.permission.WRITE_EXTERNAL_STORAGE
     */
    private fun registerApp(){
        val file = Util.getFileShared()

        try{
            //controlla se il file esiste, altrimenti crealo
            if(!file.exists()){
                file.createNewFile()
            }

            //ottieni l'oggetto JSON con tutti i dati contenuti
            val json = Util.getJSON(file)

            //controlla che questa app è registrata
            if(!this.isRegistered(json)){
                //non è registrata registrala
                this.regToSharedFile(json, file)
            }
        }catch(e : IOException){
            //non dovrebbe mai entrare in questo blocco
            //se entra è un problema, stampa l'errore
            e.printStackTrace()
        }
    }

    /**
     * Scrivi su file la presenza di questa app.
     * @param json : JSONObject - oggetto json su cui scrivere
     * @param file : File - File su cui salvare il tutto
     */
    private fun regToSharedFile(json: JSONObject, file: File) {
        // JSONObject temporaneo
        val tmp = JSONObject()

        //informazioni aggiuntive
        //this.loadClass?.addInfoToSharedFile(tmp)

        //informazioni necessarie:
        //path package
        tmp.put(PATH_PACKAGE, this.loadClass?.javaClass?.name)

        tmp.put(PATH_APP, this.context.packageCodePath)

        //aggiungi sull'oggetto JSON finale le informazioni
        json.accumulate(this.getKeyApp(), tmp)

        //salva su file
        Util.saveJson(json, file)
    }

    /**
     * Ritorna TRUE se questa app è registrata
     * altrimenti FALSE
     * @param json : JSONObject - File JSON dove cercare
     * @return Boolean - Registrato o no
     */
    private fun isRegistered(json : JSONObject): Boolean {
        return try {
            //prendi i dati se ci sono, se non ci sono lancia una
            //eccezione gestita
            json.getJSONObject(this.getKeyApp())

            //controlla se è tutto valido
            ManagerLoadClass().checkApp(this.getKeyApp())

            //dato che esiste è registrata
            //true
        }catch(e : JSONException){
            false
        }
    }

    /**
     * Restituisce la chiave da usare per cercare questa app
     * nel file JSON
     *
     * @return String - Key da usare nel file JSON
     */
    private fun getKeyApp() : String {
        return this.context.getString(this.context.applicationInfo.labelRes)
    }
}