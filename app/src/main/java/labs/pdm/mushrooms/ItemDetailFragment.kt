package labs.pdm.mushrooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import labs.pdm.mushrooms.data.Content
import labs.pdm.mushrooms.data.MushroomDetails

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private var item: MushroomDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = Content.selectedMush
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                it.getString(ARG_ITEM_ID)?.let { it1 ->
                    activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
                                item?.name
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item?.let {
            rootView.findViewById<TextView>(R.id.item_detail).text =
                ("""
                            Common Name: """ + it.commonName.toString() + """
                            Family: """ + it.family + """
                            Edibility: """ + it.edibility + """
                            
                            """ + it.description + """
                            
                            Where it is found: """ + it.location + """
                            """).trimIndent()
        }

        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}