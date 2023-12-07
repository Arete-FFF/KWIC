package edu.bupt.service.impl;

import edu.bupt.service.KwicService;

import java.util.*;

public class KwicServiceImpl implements KwicService {

    /**
     * 旋转并连接每行中的单词
     *
     * @param lineList 包含多行文本的列表
     * @return 旋转并连接后的结果列表
     */
    @Override
    public List<String> rotateAndConcatenate(List<String> lineList) {
        List<String> resultList = new ArrayList<>();

        for (String line : lineList) {
            String[] words = line.split(" ");

            List<String> tempList = new ArrayList<>(Arrays.asList(words));


            for (int j = 0; j < words.length; j++) {

                // 使用StringBuilder构建字符串，避免频繁的字符串拼接
                StringBuilder rotatedLine = new StringBuilder();
                for (String word : tempList) {
                    rotatedLine.append(word).append(" ");
                }
                resultList.add(rotatedLine.toString().trim());

                Collections.rotate(tempList, 1);
            }
        }

        return resultList;
    }



    @Override
    public List<String> sentenceProcessing(List<String> lineList) {
        //使用Set去重
        Set<String> uniqueStrings = new HashSet<>(lineList);
        //将Set转换为List（去重后的列表）
        List<String> uniqueList = new ArrayList<>(uniqueStrings);
        //利用PriorityQueue排序
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(uniqueList);
        List<String> uniqueLineList = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            uniqueLineList.add(priorityQueue.poll());
        }
        System.out.println("kwic处理结果：");
        for (String sortedSentence : uniqueLineList) {
            System.out.println(sortedSentence);
        }
        return uniqueLineList;
    }

    @Override
    public List<String> kwic(List<String> inputList) {
        System.out.println("对输入数据进行kwic处理");
        return sentenceProcessing(rotateAndConcatenate(inputList));
    }
}
