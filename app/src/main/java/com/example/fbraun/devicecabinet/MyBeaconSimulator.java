package com.example.fbraun.devicecabinet;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.simulator.BeaconSimulator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by fbraun on 16.03.15.
 */
public class MyBeaconSimulator implements BeaconSimulator {

   @Override
    public ArrayList<Beacon> getBeacons(){
       Beacon beacon = new Beacon.Builder().setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
               .setId2("1")
               .setId3("2")
               .setManufacturer(0x0118)
               .setTxPower(-59)
               .setDataFields(Arrays.asList(new Long[]{0l}))
               .build();

       ArrayList<Beacon> beacons = new ArrayList<>();
       //beacons.add(beacon);

       return beacons;
   }
}
