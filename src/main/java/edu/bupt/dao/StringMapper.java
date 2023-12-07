package edu.bupt.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StringMapper {
    // 从输入中选择字符串列表
    @Select("select string from input")
    List<String> getStringList();

    // 插入一条输入数据
    @Insert("insert into input(string) values(#{string})")
    void addInputString(String string);

    // 插入一条输出数据
    @Insert("insert into output(string) values(#{string})")
    void addOutputString(String string);

    // 删除所有输出内容
    @Delete("delete from output")
    void deleteOutput();
}
