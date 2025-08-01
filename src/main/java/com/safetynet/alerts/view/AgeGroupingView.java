package com.safetynet.alerts.view;

import lombok.Data;

import java.util.List;

@Data
public class AgeGroupingView {
    int adultCount;
    int childCount;
    List<FirstResponderView> personList;

}
