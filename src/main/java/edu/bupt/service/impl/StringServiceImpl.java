package edu.bupt.service.impl;

import edu.bupt.dao.StringMapper;
import edu.bupt.service.StringService;
import edu.bupt.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.function.Function;

public class StringServiceImpl implements StringService {
    /**
     * 执行数据库操作的辅助方法，处理数据库会话的开启和关闭。
     *
     * @param operation 要执行的操作，接受 StringMapper 参数并返回一个结果。
     * @param <T>       操作的返回类型。
     * @return 操作的结果。
     */
    private <T> T performOperation(Function<StringMapper, T> operation) {
        try (SqlSession sqlSession = MybatisUtil.getSession(true)) {
            StringMapper mapper = sqlSession.getMapper(StringMapper.class);
            return operation.apply(mapper);
        }
    }

    /**
     * 获取字符串列表。
     *
     * @return 字符串列表。
     */
    @Override
    public List<String> getStringList() {
        return performOperation(StringMapper::getStringList);
    }

    /**
     * 添加输入字符串。
     *
     * @param string 要添加的输入字符串。
     */
    @Override
    public void addInputString(String string) {
        performOperation(mapper -> {
            mapper.addInputString(string);
            return null; // 方法无返回值
        });
    }

    /**
     * 添加输出字符串。
     *
     * @param string 要添加的输出字符串。
     */
    @Override
    public void addOutputString(String string) {
        performOperation(mapper -> {
            mapper.addOutputString(string);
            return null; // 方法无返回值
        });
    }

    /**
     * 删除输出字符串。
     */
    @Override
    public void deleteOutput() {
        performOperation(mapper -> {
            mapper.deleteOutput();
            return null; // 方法无返回值
        });
    }
}
