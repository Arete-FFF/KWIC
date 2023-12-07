package edu.bupt.service.impl;

import edu.bupt.service.FileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileServiceImpl implements FileService {
    /**
     * 从默认文件路径读取数据，默认文件路径为 "src/main/resources/in/input.txt"
     *
     * @return 读取的数据列表
     */
    @Override
    public List<String> fileInput() {
        File file = new File("src/main/resources/in/input.txt");
        System.out.println("输入文件读取完成：" + file.getPath());
        return getDataFromFile(file);
    }

    /**
     * 从指定文件名的路径读取数据
     *
     * @param inputFileName 指定的文件名
     * @return 读取的数据列表
     */
    @Override
    public List<String> fileInput(String inputFileName) {
        File file = new File("src/main/resources/in/" + inputFileName);
        System.out.println("输入文件读取完成：" + file.getPath());
        return getDataFromFile(file);
    }

    /**
     * 从文件中读取数据的通用方法
     *
     * @param file 要读取的文件
     * @return 读取的数据列表
     */
    private List<String> getDataFromFile(File file) {
        List<String> inputResult = new ArrayList<>();
        try (BufferedReader inFile = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = inFile.readLine()) != null) {
                if (!str.trim().isEmpty()) { // 检查是否是空行，如果不是则添加到列表中
                    inputResult.add(str);
                    System.out.println(str);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            // 如果文件未找到，可以选择抛出异常或者做其他处理
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            // 处理文件读取过程中的其他IO异常
        }

        return inputResult;
    }

    /**
     * 将数据写入默认的输出文件，默认输出文件为 "src/main/resources/out/output.txt"
     *
     * @param resultList 要写入文件的数据列表
     */
    @Override
    public void fileOutput(List<String> resultList) {
        File file = new File("src/main/resources/out/output.txt");

        writeDataToFile(resultList, file);
    }

    /**
     * 将数据写入指定文件名的路径
     *
     * @param resultList     要写入文件的数据列表
     * @param outputFileName 指定的输出文件名
     */
    @Override
    public void fileOutput(List<String> resultList, String outputFileName) {
        File file = new File("src/main/resources/out/" + outputFileName);
        // 获取 resources 目录的绝对路径

        writeDataToFile(resultList, file);
    }

    /**
     * 将数据写入文件的通用方法
     *
     * @param resultList 要写入文件的数据列表
     * @param file       要写入的文件
     */
    private void writeDataToFile(List<String> resultList, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : resultList) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            System.out.println("输出文件写入完成：" + file.getPath());
        } catch (IOException e) {
            System.out.println("写入输出文件时出现异常：" + e.getMessage());
        }
    }

}
