package labs.pdm.mushrooms.data

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

data class MushroomDetails(var name: String?, var commonName: String?
                    , var description: String?, var edibility: String?, var family: String?, var location: String?
) {
    constructor(json: JSONObject?) : this(
        json?.getString("name"),
        ( if(json?.has("common_name")!!) json.getString("common_name") else ""),
        json.getString("description"),
        json.getString("edibility"),
        json.getString("family"),
        json.getString("location"),
        )

    init { }
}

data class Mushroom(var name: String?, var commonName: String?) {
    constructor(json: JSONObject?) : this(
        json?.getString("name"),
        ( if(json?.has("common_name")!!) json.getString("common_name") else "")
    )

    init { }
}

object Content {

    val repository: MutableList<Mushroom> = mutableListOf()
    var selectedMush: MushroomDetails? = null

    val client = OkHttpClient()

    init {
        try {
            val url = "http://10.0.2.2:5000/shrooms/get"
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) { println(e) }
                override fun onResponse(call: Call, response: Response) {
                    val dataArr = JSONArray(response.body()?.string())
                    println(dataArr)
                    for (data in 0 until dataArr.length()) {
                        repository += Mushroom(dataArr.getJSONObject(data))
                    }
                }
            })
        } catch(e: Exception) {
            e.printStackTrace();
        }
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
            .append("${repository.get(position).name}.")
            .append("\n${repository.get(position).commonName}.")
        return builder.toString()
    }

    fun fetch(mush: String) {
        try {
            val url = "http://10.0.2.2:5000/shrooms/get/$mush"
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) { println(e) }
                override fun onResponse(call: Call, response: Response) {
                    val data = JSONObject(response.body()!!.string())
                    selectedMush = MushroomDetails(data)
                }
            })
        } catch(e: Exception) {
            e.printStackTrace();
        }
    }

}