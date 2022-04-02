package masli.prof.speedtimer.presentation.screens.algorithmsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import masli.prof.domain.enums.AlgorithmsEnum
import masli.prof.domain.models.AlgorithmsModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentAlgorithmsBinding
import masli.prof.speedtimer.presentation.bundlekeys.ALGORITHM_KEY
import masli.prof.speedtimer.presentation.algorithmsOLL
import masli.prof.speedtimer.themes.AppTheme

private const val ITEM_WIDTH = 400

class AlgorithmsFragment : Fragment() {

    private var binding: FragmentAlgorithmsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAlgorithmsBinding.inflate(layoutInflater)
        setTheme()

        val algorithmEnum = arguments?.getSerializable(ALGORITHM_KEY) as AlgorithmsEnum

        val algorithms = mutableListOf<AlgorithmsModel>()

        when (algorithmEnum) {
            AlgorithmsEnum.OLL -> {
                val algorithmsString =
                    requireContext().resources.getStringArray(R.array.oll_algs).toList()
                for (i in algorithmsString.indices) {
                    algorithms.add(
                        AlgorithmsModel(
                            algorithm = algorithmsString[i],
                            src = algorithmsOLL[i],
                            algorithmType = AlgorithmsEnum.OLL
                        )
                    )
                }
            }

            //for different types
        }

        binding?.algorithmsRecyclerView?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                val recyclerWidth = binding?.algorithmsRecyclerView?.width
                val spanCount = recyclerWidth!! / ITEM_WIDTH
                binding?.algorithmsRecyclerView?.layoutManager = GridLayoutManager(context, spanCount)
                binding?.algorithmsRecyclerView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })

        binding?.algorithmsRecyclerView?.adapter = AlgorithmsAdapter(algorithms)

        return binding?.root
    }

    private fun setTheme() {
        binding?.backgroundConstraintLayout?.background = ContextCompat.getDrawable(requireContext(), AppTheme.theme.background)
    }

    inner class AlgorithmsAdapter(private val list: List<AlgorithmsModel>) :
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

    inner class AlgorithmsViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(algorithmModel: AlgorithmsModel) {
            itemView.findViewById<MaterialCardView>(R.id.item_card_view).background = ContextCompat.getDrawable(requireContext(), AppTheme.theme.itemBackground)

            val textView = itemView.findViewById<AppCompatTextView>(R.id.item_algorithms_text_view)
            textView.text = algorithmModel.algorithm
            textView.setTextColor(requireContext().getColor(AppTheme.theme.textColorOnMainColor))

            itemView.findViewById<AppCompatImageView>(R.id.item_algorithms_image_view)
                .setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, algorithmModel.src)
                )
        }
    }
}