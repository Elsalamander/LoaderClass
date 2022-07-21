package it.elsalamander.loaderclass

import dalvik.system.PathClassLoader
import org.json.JSONException
import org.json.JSONObject
import java.io.File


/****************************************************************
 * Classe per gestire il caricamento delle classi esterne.
 *
 * So che la classe che caricherò estenderà "AbstractLoadClass"
 *
 *
 *
 * @author: Elsalamander
 * @data: 13 luglio 2022
 * @version: v1.0
 ****************************************************************/
@Deprecated("Classe non più necessaria")
class ManagerLoadClass {

    var file : File = Util.getFileShared()          //ottieni il file in shared storage
    var json : JSONObject = Util.getJSON(this.file) //ottieni l'oggetto JSON del file

    /**
     * Ritorna la lista delle app registrate, senza controllare se l'app
     * è ancora presente nel dispositivo
     * @return List<String> - Lista dei nomi delle app registrate
     */
    fun getListApp() : List<String>{
        val list = ArrayList<String>()

        json.keys().forEach {
            list.add(it)
        }
        return list
    }

    /**
     * Verifica che l'app che si vuole caricare esiste oppure no
     * @param nameApp : String - Nome dell'app da caricare
     * @return Boolean - TRUE se esiste altrimenti FALSE
     */
    fun checkApp(nameApp : String) : Boolean{
        try{
            this.loadClass(nameApp, PathClassLoader.getSystemClassLoader())
            return true
        }catch(e : JSONException){
        }catch(e : ClassNotFoundException){
        }catch(e : ClassNotFoundException){
        }

        //rimuovi la voce perche non è presente.
        this.json.remove(nameApp)

        Util.saveJson(this.json, this.file)

        return false
    }

    /**
     * Carica la classe dell'app con il nome
     * @param nameApp : String - Nome dell'app da caricare
     * @return Class<out AbstractLoadClass> - Oggetto che descrive la classe
     * @throws JSONException - Se l'app non è registrata
     * @throws ClassNotFoundException - Se non si è riusciti a caricare la classe
     * @throws ClassNotFoundException - Se il cast non è andato a buon fine
     */
    @Throws(JSONException::class, ClassNotFoundException::class)
    fun loadClass(nameApp: String, classLoader: ClassLoader) : Class<out AbstractLoadClass>{
        val path = this.getPathForApk(nameApp)
        //val loader = PathClassLoader(path, PathClassLoader.getSystemClassLoader())
        //val loader = DexClassLoader(path, null,null, DexClassLoader.getSystemClassLoader())
        val loader = PathClassLoader(path, classLoader)
        return loader.loadClass(this.getPathForClass(nameApp)) as Class<out AbstractLoadClass>
    }

    /**
     * Instanzia l'oggetto della classe da caricare
     * @param cl : Class<out AbstractLoadClass> - Classe da instanziare
     * @return AbstractLoadClass - Oggetto instanziato
     * @throws IllegalAccessException - Errore di Accesso alla classe o costruttore
     * @throws InstantiationException - Errore nella instanziazione
     */
    @Throws(IllegalAccessException::class, InstantiationException::class)
    fun instanceClass(cl : Class<out AbstractLoadClass>) : AbstractLoadClass {
        //Log.d("TestSuperClass", cl.newInstance().javaClass.superclass.name)
        //if(cl.newInstance() is AbstractLoadClass){
            return cl.newInstance()
        //}
        //return null
    }

    /**
     * Ritorna il path per il file APK per l'app richiesta
     * @param nameApp : String - Nome dell'app
     * @return String - Path del file apk
     * @throws JSONException - Se non esiste l'app con il nome dato nel json
     */
    @Throws(JSONException::class)
    private fun getPathForApk(nameApp : String) : String{
        return this.json.getJSONObject(nameApp).getString(ManagerShareClass.PATH_APP)
    }

    /**
     * Ritorna il path del package per la classe da caricare
     * @param nameApp : String - Nome dell'app
     * @return String - Path del file apk
     * @throws JSONException - Se non esiste l'app con il nome dato nel json
     */
    @Throws(JSONException::class)
    private fun getPathForClass(nameApp : String) : String{
        return this.json.getJSONObject(nameApp).getString(ManagerShareClass.PATH_PACKAGE)
    }
}