package com.jiuzhe.hotel.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SqlService {

    public class InnerData {
        private String sql;
        private String action;
        private List columns;
        private List values;
        private List conditions;
        private String table;
        private String singleCondition;
        private int gotWhere;
        private JdbcTemplate jdbcTemplate;
        private int gotLeftJoin;
        private List lTable;
        private List lConditions;
        boolean isBatch;
        String end;
        boolean endWhere;
        private List unionAll;


        public InnerData(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            this.action = "";
            this.sql = "";
            this.table = "";
            this.columns = new ArrayList<String>();
            this.values = new ArrayList<String>();
            this.conditions = new ArrayList<String>();
            this.gotWhere = 0;
            this.gotLeftJoin = 0;
            this.lTable = new ArrayList<String>();
            this.lConditions = new ArrayList<String>();
            this.isBatch = false;
            this.end = "";
            this.endWhere = false;
            this.unionAll = new ArrayList<String>();
        }

        public SqlService.InnerData reset() {
            this.action = "";
            this.sql = "";
            this.columns.clear();
            this.values.clear();
            this.conditions.clear();
            this.singleCondition = "";
            this.gotWhere = 0;
            this.table = "";
            this.gotLeftJoin = 0;
            this.lTable.clear();
            this.lConditions.clear();
            this.isBatch = false;
            this.end = "";
            this.endWhere = false;
            this.unionAll.clear();
            return this;
        }

        private List<Map<String, String>> convert(List<Map<String, Object>> rawList) {
            List<Map<String, String>> rs = new ArrayList<Map<String, String>>();
            int rawSize = rawList.size();
            if (rawSize == 0)
                return rs;

            Map temp = null;
            for (int i = 0; i < rawSize; i++) {
                Map<String, String> rsMap = new HashMap<String, String>();
                temp = rawList.get(i);
                temp.forEach((k, v) -> {
                    if (v != null) {
                        rsMap.put(k.toString(), v.toString());
                    }
                });
                rs.add(rsMap);
            }

            return rs;
        }

        public SqlService.InnerData unionSelect() {
            this.action = "unionSelect";
            return this;
        }


        public SqlService.InnerData select() {
            this.action = "select";
            return this;
        }

        public SqlService.InnerData update() {
            this.action = "update";
            return this;
        }

        public SqlService.InnerData delete() {
            this.action = "delete";
            return this;
        }

        public SqlService.InnerData insert() {
            this.action = "insert";
            return this;
        }

        public SqlService.InnerData batchInsert() {
            this.action = "bInsert";
            this.isBatch = true;
            return this;
        }


        public SqlService.InnerData batchUpdate() {
            this.action = "bUpdate";
            this.isBatch = true;
            return this;
        }

        public SqlService.InnerData lJoin(String table) {
            this.gotLeftJoin = 1;
            this.lTable.add(table);
            return this;
        }

        public SqlService.InnerData on(String condition) {
            lConditions.add(condition);
            return this;
        }

        public SqlService.InnerData column(String... c) {
            for (int i = 0; i < c.length; i++)
                columns.add(c[i]);
            return this;
        }

        public SqlService.InnerData unionAll(String... c) {
            for (int i = 0; i < c.length; i++)
                unionAll.add(c[i]);
            return this;
        }

        public SqlService.InnerData value(String... v) {
            for (int i = 0; i < v.length; i++)
                values.add("'" + v[i] + "'");
            return this;
        }

        public SqlService.InnerData valueI(String v) {
            values.add(v);
            return this;
        }

        public SqlService.InnerData end(String condition) {
            end += " " + condition;
            return this;
        }

        public SqlService.InnerData endI(String condition, String value) {
            String whereAnd = " where ";
            if (!endWhere) {
                endWhere = true;
            } else {
                whereAnd = " and ";
            }
            end += whereAnd + condition + " = " + value;
            return this;
        }

        public SqlService.InnerData end(String condition, String value) {
            String whereAnd = " where ";
            if (!endWhere) {
                endWhere = true;
            } else {
                whereAnd = " and ";
            }
            end += whereAnd + condition + " = " + "'" + value + "'";
            return this;
        }

        public SqlService.InnerData conditionI(String condition, String value) {
            conditions.add(condition + value);
            gotWhere = 1;
            return this;
        }

        public SqlService.InnerData condition(String condition, String value) {
            conditions.add(condition + "'" + value + "'");
            gotWhere = 1;
            return this;
        }

        public SqlService.InnerData table(String t) {
            this.table = t;
            return this;
        }

        private String assembleCondition() {
            int csize = conditions.size();
            String sql = " ";
            if (csize != 0) {
                if (gotWhere == 1)
                    sql = " where ";
                for (int i = 0; i < csize; i++) {
                    sql += conditions.get(i);
                    if (i == conditions.size() - 1)
                        break;
                    sql += " and ";
                }

            }
            if (gotLeftJoin == 1) {
                for (int i = 0; i < lTable.size(); i++) {
                    sql += " left join " + lTable.get(i) + " on " + lConditions.get(i);
                }
            }
//            if (!on.equals(""))
//                sql += " on " + on;

            if (!end.equals(""))
                sql += " " + end;

            return sql;
        }

        private String assembleSelect() {
            String sql = "select ";

            for (int i = 0; i < columns.size(); i++) {
                sql += columns.get(i);
                if (i == columns.size() - 1) {
                    sql += " from ";
                    break;
                }
                sql += " ,";
            }
            sql += table + assembleCondition();
            return sql;
        }

        private String assembleInsert() {
            int csize = columns.size();
            int vsize = values.size();
            String sql = "insert into " + table + "(";
            for (int i = 0; i < csize; i++) {
                sql += columns.get(i);
                if (i == columns.size() - 1) {
                    sql += ")";
                    break;
                }
                sql += ",";
            }

            int j = vsize / csize;
            sql += " values ";
            for (int i = 0; i < j; i++) {
                sql += "(";
                for (int z = 0; z < csize; z++) {
                    sql += (values.get(i * csize + z));

                    if (z == csize - 1) {
                        sql += ")";
                        break;
                    }
                    sql += ",";
                }

                if (i == j - 1) {
                    sql += ";";
                    break;
                }
                sql += ",";
            }
            if (!end.equals("")) {
                sql += end;
            }

            return sql;
        }

        private String assembleunionSelect() {
            int size = unionAll.size();
            String sql = "SELECT * FROM ( ";
            if (size > 0) {
                sql += StringUtils.join(unionAll, " UNION ALL ");
            }
            sql += " )a ";
            if (!end.equals("")) {
                sql += end;
            }
            return sql;
        }


        private String assembleBatchInsert() {
            int csize = columns.size();

            String sql = "insert into " + table + "(";
            for (int i = 0; i < csize; i++) {
                sql += columns.get(i);
                if (i == csize - 1) {
                    sql += ")";
                    break;
                }
                sql += ",";
            }
            sql += " values (";

            for (int i = 0; i < csize; i++) {
                if (i == csize - 1) {
                    sql += "? )";
                    break;
                }
                sql += "?,";
            }

            return sql;
        }


        private String assembleUpdate() {
            String sql = "update " + table + " set ";
            for (int i = 0; i < columns.size(); i++) {
                sql += columns.get(i) + " = " + values.get(i);
                if (i == columns.size() - 1) {
                    break;
                }
                sql += ",";
            }

            sql += assembleCondition();
            return sql;
        }

        private String assembleBatchUpdate() {
            String sql = "update " + table + " set ";
            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).toString().contains("+")) {
                    String str = columns.get(i).toString().replace("+", "");
                    sql += str + "= " + str + "+ " + "?";
                } else {
                    sql += columns.get(i) + " = ? ";
                }
                if (i == columns.size() - 1) {
                    break;
                }
                sql += ",";
            }
            sql += assembleCondition();
            return sql;
        }

        private String assembleDelete() {
            String sql = "delete from " + table + assembleCondition();
            return sql;
        }

        public List<Map<String, String>> unionQuery() {
            String sql = assembleunionSelect();
            return convert(jdbcTemplate.queryForList(sql));
        }

        public List<Map<String, String>> query() {
            String sql = assembleSelect();
            return convert(jdbcTemplate.queryForList(sql));
        }

        public Map queryCount() {
            String sql = assembleSelect();
            String countSql = "select count(1) total from (" + sql + ")countTable ";
            return jdbcTemplate.queryForMap(countSql);
        }

        public Map queryMap() {
            String sql = assembleSelect();
            return jdbcTemplate.queryForMap(sql);
        }

        public List<Map<String, String>> queryUnion() {
            String sql = assembleunionSelect();
            return convert(jdbcTemplate.queryForList(sql));
        }

        public Map queryUnionCount() {
            String sql = assembleunionSelect();
            String countSql = "select count(1) total from (" + sql + ")countTable ";
            return jdbcTemplate.queryForMap(countSql);
        }

        public String sql() {
            String sql = "";
            switch (action) {
                case "bInsert":
                    sql = assembleBatchInsert();
                    break;
                case "unionSelect":
                    sql = assembleunionSelect();
                    break;
                case "select":
                    sql = assembleSelect();
                    break;
                case "insert":
                    sql = assembleInsert();
                    break;
                case "bUpdate":
                    sql = assembleBatchUpdate();
                    break;
                case "update":
                    sql = assembleUpdate();
                    break;
                case "delete":
                    sql = assembleDelete();
                    break;
            }
            return sql;
        }

        public int modify() {
            String sql = "";

             switch (action) {
                case "bInsert":
                    sql = assembleBatchInsert();
                    break;
                case "insert":
                    sql = assembleInsert();
                    break;
                case "bUpdate":
                    sql = assembleBatchInsert();
                    break;
                case "update":
                    sql = assembleUpdate();
                    break;
                case "delete":
                    sql = assembleDelete();
                    break;
            }

            this.sql = sql;
            if (sql.equals(""))
                return -1;

            return jdbcTemplate.update(sql);
        }

        public int[] modify(List<Object[]> batchArgs) {
            String sql = "";

            switch (action) {
                case "bInsert":
                    sql = assembleBatchInsert();
                    break;
                case "bUpdate":
                    sql = assembleBatchUpdate();
                    break;
            }
            this.sql = sql;
            if (sql.equals(""))
                return null;
            return jdbcTemplate.batchUpdate(sql, batchArgs);
        }

    }

    private SqlService.InnerData innerData;

    @Autowired
    public SqlService(JdbcTemplate jdbcTemplate) {
        this.innerData = new SqlService.InnerData(jdbcTemplate);
    }

    public SqlService.InnerData init() {
        this.innerData.reset();
        return this.innerData;
    }


}
