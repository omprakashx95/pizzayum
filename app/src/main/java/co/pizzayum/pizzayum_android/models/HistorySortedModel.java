package co.pizzayum.pizzayum_android.models;

import java.util.List;

public class HistorySortedModel {
    private List<OrderDetailItem> history_slider_data;


    public HistorySortedModel(List<OrderDetailItem> history_slider_data) {
        this.history_slider_data = history_slider_data;
    }

    public List<OrderDetailItem> getHistory_slider_data() {
        return history_slider_data;
    }

    public void setHistory_slider_data(List<OrderDetailItem> history_slider_data) {
        this.history_slider_data = history_slider_data;
    }
}
