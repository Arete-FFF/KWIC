package edu.bupt.service;

import java.util.List;

public interface KwicService {
    public List<String> rotateAndConcatenate(List<String> lineList);

    public List<String> sentenceProcessing(List<String> lineList);

    List<String> kwic(List<String> inputList);
}
