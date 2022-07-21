package it.elsalamander.loaderclass

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import it.elsalamander.loaderclass.exception.EstensioneNotFound
import it.elsalamander.loaderclass.exception.ExtensionClassNotFound
import it.elsalamander.loaderclass.exception.InvalidJSONDesc
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.lang.ClassCastException
import java.util.*


/****************************************************************
 * Classe per gestire il caricamento delle classi esterne.
 *
 * Deve gestire due tipi di caricamento:
 * - Dalla cartella "Download" attraverso un filePicker:
 *   Viene lanciato il picker direttamente nella cartella Download
 *   dopo aver selezionato il file viene eseguita una verifica:
 *   - Contiene il file
 * - Dalla cartella interna dell'apk.
 *
 *
 * Caricamento di un nuovo APK.
 * - Attraverso il Picker ottengo un URI l'unico modo che ho trovato
 *   per accere e leggere l'apk è prima copiarlo in una cartella a me
 *   conosciuta ovvero quell'applicazione.
 *   Copia momentaneamento l'apk, dopo di chè verifica che
 *   l'apk è valido.
 * - Dopo aver veririficato l'apk lo sposta nella cartella per
 *   le estensioni.
 *
 *
 *
 * @author: Elsalamander
 * @data: 13 luglio 2022
 * @version: v1.0
 ****************************************************************/
class ManagerLoadExtentions(val activity : AppCompatActivity) {

    companion object{
        private const val NAME_FILE_DESC = "Estensione.json"
        const val PATH_FILE_DESC = "assets/$NAME_FILE_DESC"
        const val PATH_FOLDER_EXTENSION = "Estensioni"

        const val DESC_PATH_MAIN = "main"
        const val DESC_PATH_NAME = "name"
    }

    val extentions = TreeMap<String, Pair<JSONObject, AbstractLoadClass>>()


    init{
        //carica tutte le estensioni presenti
        this.loadAllExtension(File(activity.filesDir, PATH_FOLDER_EXTENSION))
    }

    /**
     * Carica tutte le estensioni nella cartella delle estensioni
     */
    private fun loadAllExtension(file: File) {
        file.listFiles()?.forEach {
            try{
                if(!it.isDirectory){
                    this.loadExtension(it)
                }
            }catch(e : EstensioneNotFound){
                e.printStackTrace()
                it.delete()
            }catch(e : ExtensionClassNotFound){
                e.printStackTrace()
                it.delete()
            }catch(e : InvalidJSONDesc){
                e.printStackTrace()
                it.delete()
            }
        }
    }

    /**
     * Carica l'estensione
     */
    private fun loadExtension(file: File?) {
        //crea il loader
        val loader = PathClassLoader(file!!.path, null, activity.classLoader)

        //ottieni il file JSON
        val json = this.getEstensioneJson(loader)

        //ottieni la classe
        val cl = this.getExtensionClass(loader, json)

        //inserisci nella mappa
        this.extentions[json.getString(DESC_PATH_NAME)] = Pair(json, cl.newInstance())
    }

    /**
     * Carica una nuova estensione dalla cartella "Download"
     */
    fun loadNewExtension(){
        val startForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->

                if (result.resultCode == Activity.RESULT_OK) {
                    //ho il pick
                    val uri: Uri = result.data?.data!!

                    //crea una copia nella cartella di cache
                    var tmp = File(activity.filesDir,  "cache")
                    Log.d("PICK",   tmp.path)
                    tmp.mkdirs()
                    tmp = File(tmp, "temp.apk")
                    tmp.createNewFile()
                    Util.copyStreamToFile(activity.contentResolver.openInputStream(uri)!!, tmp)

                    //verifica che l'apk ha la firma giusta per fare da estensione
                    //deve avere nelle resources il file "Estensione.xml"
                    //uso il loader per caricare l'apk
                    val loader = PathClassLoader(tmp.path, null, activity.classLoader)

                    //ottieni l'inputStream del file di descrizione
                    try{
                        //carica il file di descrizione
                        val desc = this.getEstensioneJson(loader)

                        if(!this.checkJSONDesc(desc)){
                            throw InvalidJSONDesc("Contenuto del file di estensione non valido")
                        }

                        //carica la classe main dell'estensione
                        val cl = this.getExtensionClass(loader, desc)

                        //se sono qua è tutto ok
                        //il file apk in questione è una estensione valida


                        //ora carica l'estensione
                        this.loadExtension(this.moveToExtensionFolder(tmp, desc))

                    }catch(e : EstensioneNotFound){
                        e.printStackTrace()
                        return@registerForActivityResult
                    }catch(e : ExtensionClassNotFound){
                        e.printStackTrace()
                        return@registerForActivityResult
                    }catch(e : InvalidJSONDesc){
                        e.printStackTrace()
                        return@registerForActivityResult
                    }finally{
                        //in ogni caso alla fine elimina il file
                        tmp.delete()
                    }
                }
        }

        val inte = Intent(Intent.ACTION_OPEN_DOCUMENT)
        inte.setType("*/*")
        startForResult.launch(inte)
    }

    /**
     * Controlla il contenuto del file JSON della descrizione
     * @param desc: JSONObject
     * @return Boolean - TRUE se è valido altrimenti FALSE
     */
    private fun checkJSONDesc(desc: JSONObject): Boolean {
        return try{

            desc.getString(DESC_PATH_MAIN)
            desc.getString(DESC_PATH_NAME)
            true
        }catch(e : JSONException){
            e.printStackTrace()
            throw InvalidJSONDesc("JSON di descrizione non valido")
        }
    }

    /**
     * Muovi questo file nella cartella dove sono contenute tutte le
     * estensioni
     * @param tmp: File - File da spostare
     * @return File - File di destinazione
     */
    private fun moveToExtensionFolder(tmp: File, desc: JSONObject) : File{
        val to = File(activity.filesDir, PATH_FOLDER_EXTENSION + "/" + desc.getString(DESC_PATH_NAME) + ".apk")
        tmp.copyTo(to , true, 4 * 1024)
        return to
    }

    /**
     * Ottieni il file di estensione nel formato JSONObject
     * @param loader : ClassLoader - Apk da caricare
     * @return JSONObject - Oggetto JSON
     * @throws EstensioneNotFound - Se il file non c'è
     */
    @Throws(EstensioneNotFound::class)
    fun getEstensioneJson(loader : ClassLoader) : JSONObject{
        val desc = loader.getResourceAsStream(PATH_FILE_DESC)

        //Ci sono 2 casi, =NULL e diverso da NULL
        if(desc == null){
            //questo apk non fa da estensione
            throw EstensioneNotFound("Non c'è il file \"Estensione.json\"")
        }

        //desc != NULL
        //ottieni l'oggetto JSON dato l'inputStream
        return Util.getJSONFromInputStram(desc)
    }

    /**
     * Carica la classe main
     * @param loader : ClassLoader - Loader
     * @param desc : JSONObject - JSONObject dove c'è il persorso della classe da caricare
     */
    @Throws(ExtensionClassNotFound::class)
    fun getExtensionClass(loader : ClassLoader, desc : JSONObject) : Class<out AbstractLoadClass>{
        try{
            return loader.loadClass(desc.getString(DESC_PATH_MAIN)) as Class<out AbstractLoadClass>
        }catch(e : ClassNotFoundException){
            e.printStackTrace()
            throw ExtensionClassNotFound("Classe non trovata")
        }catch (e : ClassCastException){
            e.printStackTrace()
            throw ExtensionClassNotFound("Classe non trovata, ma non castabile")
        }
    }


    /**
     * Ritorna una mappa immutabile delle estensioni presenti
     */
    fun getExtension() : Map<String, Pair<JSONObject, AbstractLoadClass>>{
        return this.extentions
    }


}