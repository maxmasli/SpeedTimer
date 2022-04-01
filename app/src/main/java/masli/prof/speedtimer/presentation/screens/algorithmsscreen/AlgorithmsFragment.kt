package masli.prof.speedtimer.presentation.screens.algorithmsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import masli.prof.domain.enums.AlgorithmsEnum
import masli.prof.domain.models.AlgorithmsModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentAlgorithmsBinding

class AlgorithmsFragment : Fragment() {

    private var binding: FragmentAlgorithmsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAlgorithmsBinding.inflate(layoutInflater)

        val algorithmsList = mutableListOf<AlgorithmsModel>()

        val algorithmsStrings = // get resources
            requireContext().resources.getStringArray(R.array.algorithms).toList()
        val algorithmsSrc = listOf(R.drawable.oll1)
        val algorithmsTypes = listOf(AlgorithmsEnum.OLL)

        for (i in algorithmsStrings.indices) {
            algorithmsList.add(
                AlgorithmsModel(
                    algName = algorithmsStrings[i],
                    src = algorithmsSrc[i],
                    algorithmType = algorithmsTypes[i]
                )
            )
        }

        binding?.algorithmsRecyclerView?.layoutManager = GridLayoutManager(context, 2)
        binding?.algorithmsRecyclerView?.adapter = AlgorithmsAdapter(algorithmsList)

        return binding?.root
    }

    class AlgorithmsAdapter(private val list: List<AlgorithmsModel>) :
        RecyclerView.Adapter<AlgorithmsViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlgorithmsViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_algorithms, parent, false)
            return AlgorithmsViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: AlgorithmsViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int = list.size
    }

    class AlgorithmsViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(algorithmModel: AlgorithmsModel) {
            itemView.findViewById<AppCompatImageView>(R.id.item_algorithms_image_view)
                .setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, algorithmModel.src))
            itemView.findViewById<AppCompatTextView>(R.id.item_algorithms_text_view).text =
                algorithmModel.algName

            itemView.setOnClickListener {
                when(algorithmModel.algorithmType) {
                    AlgorithmsEnum.OLL -> {
                        //navigate to algorithm details screen
                    }

                    //TODO
                }
            }
        }
    }
}