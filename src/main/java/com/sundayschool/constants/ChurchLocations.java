package com.sundayschool.constants;

import com.google.common.collect.ImmutableMap;

import static com.sundayschool.constants.ChurchNames.*;

public class ChurchLocations {
    private static ImmutableMap<String, String> churchLocations = ImmutableMap.<String, String>builder()
            .put(SGGL, "32.85215, -96.55432")
            .put(SGIR, "32.802835, -96.924103")
            .put(SMCOI, "32.97649, -96.91263")
            .put(SMFB, "32.93965, -96.88476")
            .put(SPPL, "33.04971, -96.69489")
            .put(STCL, "39.73078, -105.10872")
            .put(STDL, "32.81800, -96.66728")
            .put(STJM, "32.98201, -96.91948")
            .put(STOK, "35.46329, -97.65986")
            .build();

    public static String getLatLongFor(String church) {
        return churchLocations.get(church);
    }
}
