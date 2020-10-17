package masli.prof.speedtimer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import masli.prof.cubinghelper.CubingHelper;
import masli.prof.cubinghelper.Result;

public class ResultsFragment extends Fragment implements MainActivity.UpdateListener {

    private RecyclerView recycler_results;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results, container, false);
        recycler_results = (RecyclerView) v.findViewById(R.id.recycler_results);
        recycler_results.setHasFixedSize(true);
        recycler_results.setLayoutManager(new LinearLayoutManager(getContext()));

        updateAdapter();

        return v;
    }


    //адаптер
    private class AdapterResults extends RecyclerView.Adapter<HolderResults> {

        private List<Result> list;

        public AdapterResults(List<Result> l) {
            this.list = l;
        }

        @NonNull
        @Override
        public HolderResults onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_result, parent, false);

            HolderResults holder = new HolderResults(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull HolderResults holder, int position) {
            holder.bind(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //холдер
    private class HolderResults extends RecyclerView.ViewHolder {

        private TextView res;
        private TextView scr;

        public HolderResults(@NonNull View itemView) {
            super(itemView);
            res = (TextView) itemView.findViewById(R.id.result);
            scr = (TextView) itemView.findViewById(R.id.scr);
        }

        public void bind(Result r) {
            res.setText(String.valueOf(r.getTime()));
            scr.setText(r.getScramble());
        }
    }

    @Override
    public void updateAdapter() { // тут обновляется адаптер
        if (recycler_results == null) { // проверка на нулл
            return;
        }

        switch (CubingHelper.currentEvent) {
            case CubingHelper.event_333:
                recycler_results.setAdapter(new AdapterResults(CubingHelper.get().getResults333()));
                break;
            case CubingHelper.event_222:
                recycler_results.setAdapter(new AdapterResults(CubingHelper.get().getResults222()));
                break;
        }
    }

}

