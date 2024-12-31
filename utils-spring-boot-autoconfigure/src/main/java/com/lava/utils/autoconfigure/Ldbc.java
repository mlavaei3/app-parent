package com.lava.utils.autoconfigure;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Ldbc {

    private LdbcProperties ldbcProperties;
    private JdbcTemplate jdbcTemplate;

    public Ldbc(LdbcProperties ldbcProperties, JdbcTemplate jdbcTemplate) {
        this.ldbcProperties = ldbcProperties;
        this.jdbcTemplate = jdbcTemplate;
    }

    public String prepareRun(String name, Map<String, Object> data) {
        for (LdbcProperties.QueryDsl qr : ldbcProperties.getQueryDsls()) {
            if (qr.getName().equalsIgnoreCase(name)) {
                ExpressionParser parser = new SpelExpressionParser();
                EvaluationContext context = new StandardEvaluationContext();
                for (var env : data.entrySet()) {
                    context.setVariable(env.getKey(), env.getValue());
                }
                String randomPhrase = parser.parseExpression(
                                qr.getQuery(), new TemplateParserContext())
                        .getValue(context, String.class);

                return randomPhrase;
            }
        }
        return null;
    }

    public String prepareRun(String name, Supplier<String>... fields) {
        for (LdbcProperties.QueryDsl qr : ldbcProperties.getQueryDsls()) {
            if (qr.getName().equalsIgnoreCase(name)) {
                String qry = qr.getQuery();
                qry = qry.replace("#{?}", ":!");
                for (int i = 0; i < fields.length; i++) {
                    String r = fields[i].get();
                    qry = qry.replaceFirst(":!",  r);
                }

                ExpressionParser parser = new SpelExpressionParser();
                String randomPhrase = parser.parseExpression(
                                qry, new TemplateParserContext())
                        .getValue(String.class);
                return randomPhrase;
            }
        }
        return null;
    }
    public <T, R> String prepareRun(String name, T bean, Function<T, R>... fields) {
        for (LdbcProperties.QueryDsl qr : ldbcProperties.getQueryDsls()) {
            if (qr.getName().equalsIgnoreCase(name)) {
                String qry = qr.getQuery();
                qry = qry.replace("#{?}", ":!");
                for (int i = 0; i < fields.length; i++) {
                    R r = fields[i].apply(bean);
                    qry = qry.replaceFirst(":!", "#{" + r.toString() + "}");
                }

                ExpressionParser parser = new SpelExpressionParser();
                String randomPhrase = parser.parseExpression(
                                qry, new TemplateParserContext())
                        .getValue(bean, String.class);
                return randomPhrase;
            }
        }
        return null;
    }

    public List<Map<String, Object>> run(String name, Map<String, Object> data) {
        String qry = prepareRun(name, data);
        if (qry != null) {
            return jdbcTemplate.queryForList(qry);
        }
        return null;
    }

    public <Z> List<Z> run(String name, Class<Z> targetClass, Map<String, Object> data) throws SQLException {
        String qry = prepareRun(name, data);
        if (qry != null) {
            List<Z> zList = selectQuery(targetClass, qry);
            return zList;
        }
        return null;
    }

    public <T, R> List<Map<String, Object>> run(String name, T bean, Function<T, R>... fields) {
        String qry = prepareRun(name, bean, fields);
        if (qry != null) {
            return jdbcTemplate.queryForList(qry);
        }
        return null;
    }
    public <T, R, Z> List<Z> run(String name, Class<Z> targetClass, T bean, Function<T, R>... fields) throws SQLException {
        String qry = prepareRun(name, bean, fields);
        if (qry != null) {
            List<Z> zList = selectQuery(targetClass, qry);
            return zList;
        }
        return null;
    }
    public <T> List<T> selectQuery(Class<T> type, String query) throws SQLException {
        List<T> list = new ArrayList<T>();
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rst = stmt.executeQuery(query)) {
                    while (rst.next()) {
                        T t = type.newInstance();
                        loadResultSetIntoObject(rst, t);// Point 4
                        list.add(t);
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
            }
        }
        return list;
    }
    public static void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            field.setAccessible(true);
            LdbcColumn column = field.getAnnotation(LdbcColumn.class);
            try {
                Object value = rst.getObject(column.columnName());
                Class<?> type = field.getType();
                if (isPrimitive(type)) {//check primitive type(Point 5)
                    Class<?> boxed = boxPrimitiveClass(type);//box if primitive(Point 6)
                    value = boxed.cast(value);
                }
                field.set(object, value);
            } catch (Exception ex) {

            }

        }
    }
    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }
    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }
}