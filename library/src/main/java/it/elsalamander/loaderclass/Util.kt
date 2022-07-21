package it.elsalamander.loaderclass

import android.os.Environment
import org.json.JSONException
import org.json.JSONObject
import java.io.*

class Util {

    companion object{
        /**
         * Leggi il file e ritorna un oggetto JSON
         * @param file : File - File da leggere
         * @return JSONObject - contenuto del file passato
         */
        fun getJSON(file : File) : JSONObject {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
            return try{
                JSONObject(stringBuilder.toString())
            }catch (e : JSONException){
                val json = JSONObject()
                json
            }
        }

        /**
         * Salva l'oggetto JSON nel file
         * @param json: JSONObject - Oggetto JSON da salvare
         * @param file: File - File su cui salvare
         */
        fun saveJson(json: JSONObject, file: File) {
            val userString: String = json.toString()
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(userString)
            bufferedWriter.close()
        }

        /**
         * Ritorna il file in shared storage
         * @return File - File condiviso
         */
        fun getFileShared(): File{
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            path.mkdirs()
            return File(path, ManagerShareClass.NAME_SHARED_FILE)
        }

        /**
         * Ritorna l'oggetto JSON a partire da un InputStream
         * @param inputStreamObject : InputStream - Input da caricare
         * @return JSONObject - Oggetto JSON con i valori dell'inputStream
         */
        fun getJSONFromInputStram(inputStreamObject : InputStream) : JSONObject{
            val streamReader = BufferedReader(InputStreamReader(inputStreamObject, "UTF-8"))
            val responseStrBuilder = StringBuilder()

            var inputStr: String?
            while (streamReader.readLine().also{
                    inputStr = it
                } != null) responseStrBuilder.append(inputStr)

            return JSONObject(responseStrBuilder.toString())
        }

        /**
         * Copia i dati specificato dall'inputStream al file di destinazioni
         */
        fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
            inputStream.use { input ->
                val outputStream = FileOutputStream(outputFile)
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024) // buffer size
                    while (true) {
                        val byteCount = input.read(buffer)
                        if (byteCount < 0) break
                        output.write(buffer, 0, byteCount)
                    }
                    output.flush()
                }
            }
        }
    }


}