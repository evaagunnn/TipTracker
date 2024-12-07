package com.example.tiptracker.utils;

import com.example.tiptracker.models.Tip;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final List<Tip> tips = new ArrayList<>();

    public static List<Tip> getTips() {
        return tips;
    }

    public static void addTip(Tip tip) {
        tips.add(tip);
    }

    public static void removeTip(Tip tip) {
        tips.remove(tip);
    }
}
