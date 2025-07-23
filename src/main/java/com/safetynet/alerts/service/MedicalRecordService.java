package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import com.safetynet.alerts.repository.MedicalRecordRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MedicalRecordService {
    MedicalRecordRepository medicalRecordRepository;
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();


    @Autowired //Optional for single constructor
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }
    @PostConstruct
    public void init() {
        medicalRecordList = medicalRecordRepository.processJSONMedicalRecord();
    }
    public List<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordList;
    }


    public List<MedicalRecord> addMedicalRecords(MedicalRecord newRecord) {
        medicalRecordList.addLast(newRecord);
        return  medicalRecordList;
    }

    public boolean updateMedicalRecords(MedicalRecord newRecord,
                                                    String firstName, String lastName)
    {
        boolean record_deleted = false;
        for (MedicalRecord record : medicalRecordList){
            if (record.getFirstName().equalsIgnoreCase(firstName) &&
                    record.getLastName().equalsIgnoreCase(lastName)) {

                record.setBirthdate(newRecord.getBirthdate());
                record.setMedications(newRecord.getMedications());
                record.setAllergies(newRecord.getAllergies());
                record_deleted = true;
                break;
            }
        }
        return record_deleted;
    }

    public boolean deleteMedicalRecords(String firstName, String lastName) {
        Iterator<MedicalRecord> iterator = medicalRecordList.iterator();
        boolean record_deleted = false;
        while (iterator.hasNext()) {
            MedicalRecord record = iterator.next();
            if (record.getFirstName().equalsIgnoreCase(firstName)
                    && record.getLastName().equalsIgnoreCase(lastName)) {
                iterator.remove();
                record_deleted = true;
            }
        }
        return record_deleted;
    }

    public MedicalRecord getMedicalRecordByFullName(String firstName, String lastName) {

        for (MedicalRecord record : medicalRecordList) {
            if (record.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                    record.getLastName().equalsIgnoreCase(lastName.trim())) {
                return record;
            }
        }
        return null;
    }
}
