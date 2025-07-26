package com.safetynet.alerts.view;

import lombok.Data;

import java.util.List;

@Data
public class AgeGroupingView {
    List<FirstResponderView> personList;
    int adultCount;
    int childCount;
}
