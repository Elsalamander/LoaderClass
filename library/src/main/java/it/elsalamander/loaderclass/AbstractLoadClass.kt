package it.elsalamander.loaderclass

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import dalvik.system.PathClassLoader
import it.elsalamander.loaderclass.calculator.Operation
import it.elsalamander.loaderclass.calculator.data.OperationDataHelper
import it.elsalamander.loaderclass.calculator.data.OperationDataParameters
import it.elsalamander.loaderclass.calculator.data.OperationDataResult
import org.json.JSONObject

/****************************************************************
 * Classe astratta che definisce la interfaccia pubblica delle
 * classi che verranno caricate e usate.
 *
 *
 *
 * @author: Elsalamander
 * @data: 13 luglio 2022
 * @version: v1.0
 ****************************************************************/
abstract class AbstractLoadClass(private val operation: Operation) {

    lateinit var img : Bitmap

    /**
     * Ritorna l'oggetto che descrive le operazioni
     * @return Operation
     */
    fun getOperation() : Operation{
        return this.operation
    }

    /**
     * Ritorna il fragment da aprire per questa estensione
     * @param context: Context
     * @return Fragment
     */
    abstract fun getFragment(context: Context) : Fragment

    /**
     * Immagine dell'estensione
     * @return Bitmap
     */
    fun getImage() : Bitmap{
        return img
    }

    fun setImage(img : Bitmap){
        this.img = img
    }

    /**
     * Titolo estensione
     * @return String
     */
    abstract fun getTitle() : String

    /**
     * Descrizione estensione
     * @return String
     */
    abstract fun getDescription() : String
}