package masli.prof.speedtimer.presentation.screens.algorithmslistscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import masli.prof.domain.enums.AlgorithmsEnum
import masli.prof.domain.models.AlgorithmsModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentAlgorithmsListBinding
import masli.prof.speedtimer.presentation.bundlekeys.ALGORITHM_KEY
import masli.prof.speedtimer.themes.AppTheme

private const val ITEM_WIDTH = 400

class AlgorithmsListFragment : Fragment(){

    private var binding: FragmentAlgorithmsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAlgorithmsListBinding.inflate(layoutInflater)
        setTheme()

        val algorithmsList = mutableListOf<AlgorithmsModel>()

        val algorithmsStrings = // get resources
            requireContext().resources.getStringArray(R.array.algorithms).toList()
        val algorithmsSrc = listOf(R.drawable.oll1, R.drawable.pll1)
        val algorithmsTypes = listOf(AlgorithmsEnum.OLL, AlgorithmsEnum.PLL)

        for (i in algorithmsStrings.indices) {
            algorithmsList.add(
                AlgorithmsModel(
                    algorithm = algorithmsStrings[i],
                    src = algorithmsSrc[i],
                    algorithmType = algorithmsTypes[i]
                )
            )
        }
        binding?.algorithmsListRecyclerView?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                val recyclerWidth = binding?.algorithmsListRecyclerView?.width
                val spanCount = recyclerWidth!! / ITEM_WIDTH
                binding?.algorithmsListRecyclerView?.layoutManager = GridLayoutManager(context, spanCount)
                binding?.algorithmsListRecyclerView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            }
        })

        binding?.algorithmsListRecyclerView?.adapter = AlgorithmsListAdapter(algorithmsList)

        return binding?.root
    }

    private fun setTheme() {
        binding?.backgroundConstraintLayout?.background = ContextCompat.getDrawable(requireContext(), AppTheme.theme.background)
    }

    inner class AlgorithmsListAdapter(private val list: List<AlgorithmsModel>) :
        RecyclerView.Adapter<AlgorithmsListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlgorithmsListViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_algorithms, parent, false)
            return AlgorithmsListViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: AlgorithmsListViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int = list.size
    }

    inner class AlgorithmsListViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(algorithmModel: AlgorithmsModel) {
            itemView.findViewById<MaterialCardView>(R.id.item_card_view).background = ContextCompat.getDrawable(requireContext(), AppTheme.theme.itemBackground)

            itemView.findViewById<AppCompatImageView>(R.id.item_algorithms_image_view)
                .setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, algorithmModel.src))

            val textView = itemView.findViewById<AppCompatTextView>(R.id.item_algorithms_text_view)
            textView.text = algorithmModel.algorithm
            textView.setTextColor(requireContext().getColor(AppTheme.theme.textColorOnMainColor))

            itemView.setOnClickListener {
                when(algorithmModel.algorithmType) {
                    AlgorithmsEnum.OLL -> {
                        val bundle = Bundle()
                        bundle.putSerializable(ALGORITHM_KEY, AlgorithmsEnum.OLL)
                        this@AlgorithmsListFragment.findNavController().navigate(R.id.action_algorithmsListFragment_to_algorithmsFragment, bundle)
                    }

                    AlgorithmsEnum.PLL -> {
                        val bundle = Bundle()
                        bundle.putSerializable(ALGORITHM_KEY, AlgorithmsEnum.PLL)
                        this@AlgorithmsListFragment.findNavController().navigate(R.id.action_algorithmsListFragment_to_algorithmsFragment, bundle)
                    }
                }
            }
        }
    }


}