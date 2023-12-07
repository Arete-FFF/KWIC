package edu.bupt.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StringService {
    // 获取字符串列表
    List<String> getStringList();

    // 添加输入字符串
    void addInputString(String string);

    // 添加输出字符串
    void addOutputString(String string);

    // 删除所有输出内容
    void deleteOutput();
}
