package masli.prof.speedtimer.presentation.screens.resultsscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.R
import masli.prof.speedtimer.databinding.FragmentResultsBinding
import masli.prof.speedtimer.presentation.bundlekeys.EVENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.FRAGMENT_KEY
import masli.prof.speedtimer.presentation.bundlekeys.RESULT_KEY
import masli.prof.speedtimer.presentation.listeners.DialogDetailsResultListener
import masli.prof.speedtimer.presentation.screens.dialogs.DialogDetailsResult
import masli.prof.speedtimer.utils.mapToTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

private const val DIALOG_DETAILS_RESULT_TAG = "dialog_details_result"
private const val ITEM_WIDTH = 300

class ResultsFragment : Fragment(), DialogDetailsResultListener, ViewTreeObserver.OnGlobalLayoutListener, Serializable {

    private val viewModel: ResultsViewModel by viewModel<ResultsViewModel>()
    private var binding: FragmentResultsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultsBinding.inflate(layoutInflater)
        //binding?.resultsRecyclerView?.layoutManager = GridLayoutManager(context, 3)
        binding?.resultsRecyclerView?.viewTreeObserver?.addOnGlobalLayoutListener(this)

        val event = arguments?.getSerializable(EVENT_KEY) as EventEnum?
        if (event != null) viewModel.setEvent(event)
        else throw Exception("event is null")

        viewModel.getAllResults() // we need to call this to see all results correct

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bindings
        binding?.backImageButton?.setOnClickListener {
            activity?.onBackPressed()
        }

        //observers
        viewModel.allResultsByEventLiveData.observe(viewLifecycleOwner) { resultList ->
            binding?.resultsRecyclerView?.adapter = ResultsRecyclerAdapter(resultList)
        }

        viewModel.currentEventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                EventEnum.Event2by2 -> binding?.eventTextView?.text = context?.getString(R.string._2by2)
                EventEnum.Event3by3 -> binding?.eventTextView?.text = context?.getString(R.string._3by3)
                EventEnum.EventPyra -> binding?.eventTextView?.text = context?.getString(R.string.pyra)
            }
        }

        viewModel.avgResultLiveData.observe(viewLifecycleOwner) {resultAvg ->
            val dnfText = context?.getString(R.string.dnf)
            val avg5text = resultAvg.avg5?.let { mapToTime(it) }
            val avg12text = resultAvg.avg12?.let { mapToTime(it) }
            val avg50text = resultAvg.avg50?.let { mapToTime(it) }
            val avg100text = resultAvg.avg100?.let { mapToTime(it) }
            binding?.resultAvg5TextView?.text = avg5text ?: dnfText
            binding?.resultAvg12TextView?.text = avg12text ?: dnfText
            binding?.resultAvg50TextView?.text = avg50text ?: dnfText
            binding?.resultAvg100TextView?.text = avg100text ?: dnfText
        }

    }

    override fun updateResult(result: ResultModel) {
        viewModel.updateResult(result)
    }

    override fun deleteResult(result: ResultModel) {
        viewModel.deleteResult(result)
    }

    override fun onGlobalLayout() {
        val recyclerWidth = binding?.resultsRecyclerView?.width
        val spanCount = recyclerWidth!! / ITEM_WIDTH
        binding?.resultsRecyclerView?.layoutManager = GridLayoutManager(context, spanCount)
        binding?.resultsRecyclerView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }

    inner class ResultsRecyclerAdapter(private val resultsList: List<ResultModel>) : RecyclerView.Adapter<ResultViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
            return ResultViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.bind(resultsList[position])
        }

        override fun getItemCount(): Int = resultsList.size
    }

    inner class ResultViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTextView = itemView.findViewById<AppCompatTextView>(R.id.item_time_text_view)
        private val timeHasDescription = itemView.findViewById<AppCompatImageView>(R.id.item_has_description_image_view)
        fun bind(result: ResultModel) {
            if (result.description.isEmpty()) {timeHasDescription.visibility = View.GONE}
            else {timeHasDescription.visibility = View.VISIBLE}

            val timeText = when {
                result.isDNF -> itemView.context.getString(R.string.dnf)
                result.isPlus -> itemView.context.getString(R.string.plus_2_template, mapToTime(result.time + 2000))
                else -> mapToTime(result.time)
            }
            timeTextView.text = timeText
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable(RESULT_KEY, result)
                bundle.putSerializable(FRAGMENT_KEY, this@ResultsFragment)
                val dialog = DialogDetailsResult.newInstance(bundle)
                dialog.show(childFragmentManager, DIALOG_DETAILS_RESULT_TAG)
            }
        }
    }




}