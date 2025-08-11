package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FirstResponderService;
import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderAddressView;
import com.safetynet.alerts.view.FirstResponderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;



import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FirstResponderController.class)
public class FirstResponderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirstResponderService firstResponderServiceMock;

    private AgeGroupingView ageGroupingViewMock;
    private List<FirstResponderView> firstResponderViewListMock;
    private List<FirstResponderAddressView> firstResponderAddressViewListMock;

    @BeforeEach
    public void setup(){
        ageGroupingViewMock = new AgeGroupingView();
        // You should set a non-empty person list here for your actual tests
        ageGroupingViewMock.setPersonList(List.of(new FirstResponderView()));

        firstResponderViewListMock = List.of(new FirstResponderView());
        firstResponderAddressViewListMock = List.of(new FirstResponderAddressView());
    }

    @Test
    public void testGetPersonList_Success() throws Exception {
        Mockito.when(firstResponderServiceMock.getPeopleListinAddress(anyString())).thenReturn(ageGroupingViewMock);

        mockMvc.perform(get("/station").param("stationNumber", "1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.personList").isArray());


    }

    @Test
    public void testGetPersonList_NotFound() throws Exception {
        Mockito.when(firstResponderServiceMock.getPeopleListinAddress(anyString()))
                .thenReturn(new AgeGroupingView()); // empty list

        mockMvc.perform(get("/station")
                        .param("stationNumber", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetChildAlertList_Success() throws Exception {
        Mockito.when(firstResponderServiceMock.getChildrenListinAddress(anyString())).thenReturn(firstResponderViewListMock);

        mockMvc.perform(get("/childAlert").param("address", "Delaware Street")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());


    }
    @Test
    public void testGetChildAlertList_Failure() throws Exception {
        Mockito.when(firstResponderServiceMock.getChildrenListinAddress(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/childAlert")
                        .param("address", "Unknown"))
                .andExpect(status().isNotFound());


    }
    @Test
    public void testGetPhoneAlert_Success() throws Exception {
        Mockito.when(firstResponderServiceMock.getPeopleListinAddress(anyString())).thenReturn(ageGroupingViewMock);
        mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetPhoneAlert_Failure() throws Exception {
        AgeGroupingView emptyAgeGrouping = new AgeGroupingView();
        emptyAgeGrouping.setPersonList(Collections.emptyList());

        Mockito.when(firstResponderServiceMock.getPeopleListinAddress(anyString())).thenReturn(emptyAgeGrouping);
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPeopleMedicalHistory_Success() throws Exception {
        Mockito.when(firstResponderServiceMock.getPeopleMedicalHistroy(anyString()))
                .thenReturn(firstResponderAddressViewListMock);

        mockMvc.perform(get("/fire")
                        .param("address", "123 Main St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void testGetFloodApiDetails_Success() throws Exception {
        Mockito.when(firstResponderServiceMock.getFloodApiDetails(anyList()))
                .thenReturn(firstResponderAddressViewListMock);

        mockMvc.perform(get("/flood/stations")
                        .param("stationNumbers", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void testGetPersonInfoByLastName_Success() throws Exception {
        Mockito.when(firstResponderServiceMock.getPersonInfoWithLastName(anyString()))
                .thenReturn(firstResponderAddressViewListMock);

        mockMvc.perform(get("/personInfolastName")
                        .param("lastName", "Chava"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}
