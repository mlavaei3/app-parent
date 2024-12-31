package com.lava.utils.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "com.lava.ldbc")
public class LdbcProperties {

    private List<Map<String, Object>> props;
    private List<QueryDsl> queryDsls;

    public List<Map<String, Object>> getProps() {
        return props;
    }

    public void setProps(List<Map<String, Object>> props) {
        this.props = props;
    }

    public List<QueryDsl> getQueryDsls() {
        return queryDsls;
    }

    public void setQueryDsls(List<QueryDsl> queryDsls) {
        this.queryDsls = queryDsls;
    }

    public static class QueryDsl {

        private String name;
        private String query;
        private List<String> selection;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public List<String> getSelection() {
            return selection;
        }

        public void setSelection(List<String> selection) {
            this.selection = selection;
        }
    }


}