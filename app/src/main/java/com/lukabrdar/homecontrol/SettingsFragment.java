package com.lukabrdar.homecontrol;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.lukabrdar.homecontrol.model.Device;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private Spinner spinner;
    private RadioGroup rgModes;
    private CheckBox cbTheme;
    private Button btnSave;
    private MaterialCardView card;
    private TextView tvRoom, tvMode, tvTheme;
    private ImageView ivSuccess;
    private RadioButton rb;
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        spinner = view.findViewById(R.id.spinner_rooms);
        rgModes = view.findViewById(R.id.rg_modes);
        cbTheme = view.findViewById(R.id.cb_theme);
        btnSave = view.findViewById(R.id.btn_save_settings);
        card = view.findViewById(R.id.card_summary);
        tvRoom = view.findViewById(R.id.tv_summary_room);
        tvMode = view.findViewById(R.id.tv_summary_mode);
        tvTheme = view.findViewById(R.id.tv_summary_theme);
        ivSuccess = view.findViewById(R.id.iv_success);

        onCreateSpinner();

        int currentMode = androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode();
        cbTheme.setChecked(currentMode == androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);

        btnSave.setOnClickListener(v -> saveSettings());

        return view;
    }

    private void onCreateSpinner() {
        List<Device> devices = DevicesFragment.getDevices();

        Set<String> individualRooms = new HashSet<String>();
        for (Device device : devices) {
            individualRooms.add(device.location);
        }
        List<String> rooms = new ArrayList<>(individualRooms);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void saveSettings() {
        String selectedRoom = spinner.getSelectedItem().toString();
        rb = view.findViewById(rgModes.getCheckedRadioButtonId());
        String selected = rb.getText().toString();

        boolean isDarkMode = cbTheme.isChecked();
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES 
                           : androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
        );

        tvRoom.setText("Room: " + selectedRoom);
        tvMode.setText("Mode: " + selected);
        tvTheme.setText("Dark Theme: " + (isDarkMode ? "Enabled" : "Disabled"));

        card.setVisibility(View.VISIBLE);
        ivSuccess.setVisibility(View.VISIBLE);
    }
}