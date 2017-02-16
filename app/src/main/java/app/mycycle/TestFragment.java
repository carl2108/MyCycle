package app.mycycle;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by carloconnor on 16/02/17.
 */

public class TestFragment extends Fragment {

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);

        textView = (TextView) view.findViewById(R.id.textView);

        return view;
    }

    public void incrementTextview() {
        int i = Integer.parseInt(textView.getText().toString());
        i++;
        textView.setText(i);
    }

}
