package com.lukabrdar.homecontrol;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lukabrdar.homecontrol.model.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevicesFragment extends Fragment {
    private TextView selectedDevice;
    private TextView statusValue;
    private TextView roomValue;
    private ImageView statusIcon;
    private Device currentDevice;
    private List<Device> devices;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DevicesFragment() {
    }

    public static DevicesFragment newInstance(String param1, String param2) {
        DevicesFragment fragment = new DevicesFragment();
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

    public static List<Device> getDevices() {
        return new ArrayList<Device>(Arrays.asList(
                new Device("Svjetlo", "dnevni boravak", true, R.drawable.ic_lightbulb),
                new Device("Klima uređaj", "dnevni boravak", false, R.drawable.ic_ac_unit),
                new Device("Pametna brava", "ulaz", true, R.drawable.ic_lock),
                new Device("Alarm", "cijela kuća", false, R.drawable.ic_alarm),
                new Device("Grijanje", "dnevni boravak", true, R.drawable.ic_thermostat),
                new Device("Kuhinjsko svjetlo", "kuhinja", false, R.drawable.ic_lightbulb),
                new Device("Garažna vrata", "garaža", false, R.drawable.ic_garage)
        ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        devices = getDevices();

        selectedDevice = view.findViewById(R.id.textViewSelectedDevice);
        statusValue = view.findViewById(R.id.textViewStatusValue);
        roomValue = view.findViewById(R.id.textViewRoomValue);
        statusIcon = view.findViewById(R.id.imageViewStatusIcon);

        ListView listView = view.findViewById(R.id.listViewDevices);
        ArrayAdapter<Device> adapter = new ArrayAdapter<Device>(view.getContext(), android.R.layout.simple_list_item_1, devices);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device clickedDevice = (Device) parent.getItemAtPosition(position);
                if (clickedDevice == currentDevice) {
                    changeStatus();
                } else {
                    displayInfo(clickedDevice);
                }
            }
        });

        return view;
    }

    public boolean displayInfo(Device device) {
        if (device != null) {
            currentDevice = device;

            selectedDevice.setText(getString(R.string.selected_device_label, device.name));
            statusValue.setText(device.status ? getString(R.string.status_on) : getString(R.string.status_off));
            roomValue.setText(device.location);
            statusIcon.setImageResource(device.iconResId);
            return true;
        }
        return false;
    }

    public boolean changeStatus() {
        if (currentDevice != null) {
            currentDevice.changeState();
            displayInfo(currentDevice);
            return true;
        }
        return false;
    }
}