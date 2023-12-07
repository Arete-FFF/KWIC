package edu.bupt.service;

import java.util.List;

public interface FileService {

    public List<String> fileInput();
    public List<String> fileInput(String inputFileName);


    public void fileOutput(List<String> resultList);
    public void fileOutput(List<String> resultList, String outputFileName);
}
