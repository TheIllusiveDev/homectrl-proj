package com.lukabrdar.homecontrol;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lukabrdar.homecontrol.model.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevicesFragment extends Fragment {
    private TextView selectedDevice;
    private TextView statusValue;
    private TextView roomValue;
    private ImageView statusIcon;
    private View cardViewStatus;
    private Device currentDevice;
    private List<Device> devices;

    public DevicesFragment() {
    }

    public static List<Device> getDevices() {
        return new ArrayList<Device>(Arrays.asList(
                new Device("Light", "Living Room", true, R.drawable.ic_lightbulb),
                new Device("Air Conditioner", "Living Room", false, R.drawable.ic_ac_unit),
                new Device("Smart Lock", "Entrance", true, R.drawable.ic_lock),
                new Device("Alarm", "Whole House", false, R.drawable.ic_alarm),
                new Device("Heating", "Living Room", true, R.drawable.ic_thermostat),
                new Device("Kitchen Light", "Kitchen", false, R.drawable.ic_lightbulb),
                new Device("Garage Door", "Garage", false, R.drawable.ic_garage)
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
        cardViewStatus = view.findViewById(R.id.cardViewStatus);

        ListView listView = view.findViewById(R.id.listViewDevices);
        ArrayAdapter<Device> adapter = new ArrayAdapter<Device>(view.getContext(), R.layout.device_list_item, R.id.textViewDeviceName, devices) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Device d = getItem(position);
                if (d != null) {
                    ((ImageView) v.findViewById(R.id.imageViewDeviceIcon)).setImageResource(d.iconResId);
                }
                return v;
            }
        };
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
            cardViewStatus.setVisibility(View.VISIBLE);

            selectedDevice.setText(getString(R.string.selected_device_label, device.name));
            
            String statusText;
            int color;
            
            if (device.status) {
                color = getResources().getColor(R.color.success_green, null);
                if (device.name.equalsIgnoreCase("Garage Door")) {
                    statusText = getString(R.string.status_open);
                } else if (device.name.equalsIgnoreCase("Smart Lock")) {
                    statusText = getString(R.string.status_unlocked);
                } else {
                    statusText = getString(R.string.status_on);
                }
            } else {
                color = getResources().getColor(R.color.error_red, null);
                if (device.name.equalsIgnoreCase("Garage Door")) {
                    statusText = getString(R.string.status_closed);
                } else if (device.name.equalsIgnoreCase("Smart Lock")) {
                    statusText = getString(R.string.status_locked);
                } else {
                    statusText = getString(R.string.status_off);
                }
            }
            
            statusValue.setText(statusText);
            statusValue.setTextColor(color);

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