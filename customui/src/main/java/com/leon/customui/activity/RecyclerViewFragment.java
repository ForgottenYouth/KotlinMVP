/**
 * FileName: RecyclerViewFragment
 * Author: shiwenliang
 * Date: 2021/7/12 16:19
 * Description:
 */
package com.leon.customui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.customui.R;
import com.leon.customui.fixeddatadisablescrollrecycleview.FixedDataDisableAdaper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private RecyclerView recyclerView;

    private int i = 0;

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int tempI = i; i < tempI + 50; i++) {
            data.add("ChildView item " + i);
        }
        return data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycleview_layout, container, false);

        recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final FixedDataDisableAdaper adapter = new FixedDataDisableAdaper((ArrayList<String>) getData());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
