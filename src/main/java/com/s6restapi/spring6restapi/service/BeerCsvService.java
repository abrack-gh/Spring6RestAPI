package com.s6restapi.spring6restapi.service;

import com.s6restapi.spring6restapi.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
