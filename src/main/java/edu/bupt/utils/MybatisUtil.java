package edu.bupt.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class MybatisUtil {
    // Mybatis 的 SqlSessionFactory 实例
    private static SqlSessionFactory factory;

    // 静态块，在类加载时初始化 SqlSessionFactory
    static {
        try {
            // 使用 Mybatis 的 SqlSessionFactoryBuilder 从 mybatis-config.xml 文件构建 SqlSessionFactory
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
        } catch (IOException e) {
            // 异常处理，打印异常信息
            e.printStackTrace();
        }
    }

    /**
     * 获取带有指定事务自动提交设置的 SqlSession 实例
     *
     * @param autocommit 是否自动提交事务
     * @return SqlSession 实例
     */
    public static SqlSession getSession(boolean autocommit) {
        // 使用 SqlSessionFactory 创建 SqlSession 实例，并设置是否自动提交事务
        return factory.openSession(autocommit);
    }
}
