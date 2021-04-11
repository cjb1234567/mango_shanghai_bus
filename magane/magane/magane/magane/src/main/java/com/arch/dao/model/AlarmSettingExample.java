package com.arch.dao.model;

import java.util.ArrayList;
import java.util.List;

public class AlarmSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AlarmSettingExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoIsNull() {
            addCriterion("hardware_sno is null");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoIsNotNull() {
            addCriterion("hardware_sno is not null");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoEqualTo(String value) {
            addCriterion("hardware_sno =", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoNotEqualTo(String value) {
            addCriterion("hardware_sno <>", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoGreaterThan(String value) {
            addCriterion("hardware_sno >", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoGreaterThanOrEqualTo(String value) {
            addCriterion("hardware_sno >=", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoLessThan(String value) {
            addCriterion("hardware_sno <", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoLessThanOrEqualTo(String value) {
            addCriterion("hardware_sno <=", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoLike(String value) {
            addCriterion("hardware_sno like", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoNotLike(String value) {
            addCriterion("hardware_sno not like", value, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoIn(List<String> values) {
            addCriterion("hardware_sno in", values, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoNotIn(List<String> values) {
            addCriterion("hardware_sno not in", values, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoBetween(String value1, String value2) {
            addCriterion("hardware_sno between", value1, value2, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andHardwareSnoNotBetween(String value1, String value2) {
            addCriterion("hardware_sno not between", value1, value2, "hardwareSno");
            return (Criteria) this;
        }

        public Criteria andBusNoIsNull() {
            addCriterion("bus_no is null");
            return (Criteria) this;
        }

        public Criteria andBusNoIsNotNull() {
            addCriterion("bus_no is not null");
            return (Criteria) this;
        }

        public Criteria andBusNoEqualTo(String value) {
            addCriterion("bus_no =", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoNotEqualTo(String value) {
            addCriterion("bus_no <>", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoGreaterThan(String value) {
            addCriterion("bus_no >", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoGreaterThanOrEqualTo(String value) {
            addCriterion("bus_no >=", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoLessThan(String value) {
            addCriterion("bus_no <", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoLessThanOrEqualTo(String value) {
            addCriterion("bus_no <=", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoLike(String value) {
            addCriterion("bus_no like", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoNotLike(String value) {
            addCriterion("bus_no not like", value, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoIn(List<String> values) {
            addCriterion("bus_no in", values, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoNotIn(List<String> values) {
            addCriterion("bus_no not in", values, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoBetween(String value1, String value2) {
            addCriterion("bus_no between", value1, value2, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusNoNotBetween(String value1, String value2) {
            addCriterion("bus_no not between", value1, value2, "busNo");
            return (Criteria) this;
        }

        public Criteria andBusSidIsNull() {
            addCriterion("bus_sid is null");
            return (Criteria) this;
        }

        public Criteria andBusSidIsNotNull() {
            addCriterion("bus_sid is not null");
            return (Criteria) this;
        }

        public Criteria andBusSidEqualTo(String value) {
            addCriterion("bus_sid =", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidNotEqualTo(String value) {
            addCriterion("bus_sid <>", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidGreaterThan(String value) {
            addCriterion("bus_sid >", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidGreaterThanOrEqualTo(String value) {
            addCriterion("bus_sid >=", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidLessThan(String value) {
            addCriterion("bus_sid <", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidLessThanOrEqualTo(String value) {
            addCriterion("bus_sid <=", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidLike(String value) {
            addCriterion("bus_sid like", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidNotLike(String value) {
            addCriterion("bus_sid not like", value, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidIn(List<String> values) {
            addCriterion("bus_sid in", values, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidNotIn(List<String> values) {
            addCriterion("bus_sid not in", values, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidBetween(String value1, String value2) {
            addCriterion("bus_sid between", value1, value2, "busSid");
            return (Criteria) this;
        }

        public Criteria andBusSidNotBetween(String value1, String value2) {
            addCriterion("bus_sid not between", value1, value2, "busSid");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNull() {
            addCriterion("direction is null");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNotNull() {
            addCriterion("direction is not null");
            return (Criteria) this;
        }

        public Criteria andDirectionEqualTo(Integer value) {
            addCriterion("direction =", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotEqualTo(Integer value) {
            addCriterion("direction <>", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThan(Integer value) {
            addCriterion("direction >", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThanOrEqualTo(Integer value) {
            addCriterion("direction >=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThan(Integer value) {
            addCriterion("direction <", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThanOrEqualTo(Integer value) {
            addCriterion("direction <=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionIn(List<Integer> values) {
            addCriterion("direction in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotIn(List<Integer> values) {
            addCriterion("direction not in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionBetween(Integer value1, Integer value2) {
            addCriterion("direction between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotBetween(Integer value1, Integer value2) {
            addCriterion("direction not between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andDeptStopIsNull() {
            addCriterion("dept_stop is null");
            return (Criteria) this;
        }

        public Criteria andDeptStopIsNotNull() {
            addCriterion("dept_stop is not null");
            return (Criteria) this;
        }

        public Criteria andDeptStopEqualTo(String value) {
            addCriterion("dept_stop =", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopNotEqualTo(String value) {
            addCriterion("dept_stop <>", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopGreaterThan(String value) {
            addCriterion("dept_stop >", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopGreaterThanOrEqualTo(String value) {
            addCriterion("dept_stop >=", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopLessThan(String value) {
            addCriterion("dept_stop <", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopLessThanOrEqualTo(String value) {
            addCriterion("dept_stop <=", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopLike(String value) {
            addCriterion("dept_stop like", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopNotLike(String value) {
            addCriterion("dept_stop not like", value, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopIn(List<String> values) {
            addCriterion("dept_stop in", values, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopNotIn(List<String> values) {
            addCriterion("dept_stop not in", values, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopBetween(String value1, String value2) {
            addCriterion("dept_stop between", value1, value2, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopNotBetween(String value1, String value2) {
            addCriterion("dept_stop not between", value1, value2, "deptStop");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameIsNull() {
            addCriterion("dept_stop_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameIsNotNull() {
            addCriterion("dept_stop_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameEqualTo(String value) {
            addCriterion("dept_stop_name =", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameNotEqualTo(String value) {
            addCriterion("dept_stop_name <>", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameGreaterThan(String value) {
            addCriterion("dept_stop_name >", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_stop_name >=", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameLessThan(String value) {
            addCriterion("dept_stop_name <", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameLessThanOrEqualTo(String value) {
            addCriterion("dept_stop_name <=", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameLike(String value) {
            addCriterion("dept_stop_name like", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameNotLike(String value) {
            addCriterion("dept_stop_name not like", value, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameIn(List<String> values) {
            addCriterion("dept_stop_name in", values, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameNotIn(List<String> values) {
            addCriterion("dept_stop_name not in", values, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameBetween(String value1, String value2) {
            addCriterion("dept_stop_name between", value1, value2, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptStopNameNotBetween(String value1, String value2) {
            addCriterion("dept_stop_name not between", value1, value2, "deptStopName");
            return (Criteria) this;
        }

        public Criteria andDeptTimeIsNull() {
            addCriterion("dept_time is null");
            return (Criteria) this;
        }

        public Criteria andDeptTimeIsNotNull() {
            addCriterion("dept_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeptTimeEqualTo(Integer value) {
            addCriterion("dept_time =", value, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeNotEqualTo(Integer value) {
            addCriterion("dept_time <>", value, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeGreaterThan(Integer value) {
            addCriterion("dept_time >", value, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("dept_time >=", value, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeLessThan(Integer value) {
            addCriterion("dept_time <", value, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeLessThanOrEqualTo(Integer value) {
            addCriterion("dept_time <=", value, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeIn(List<Integer> values) {
            addCriterion("dept_time in", values, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeNotIn(List<Integer> values) {
            addCriterion("dept_time not in", values, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeBetween(Integer value1, Integer value2) {
            addCriterion("dept_time between", value1, value2, "deptTime");
            return (Criteria) this;
        }

        public Criteria andDeptTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("dept_time not between", value1, value2, "deptTime");
            return (Criteria) this;
        }

        public Criteria andStartAlarmIsNull() {
            addCriterion("start_alarm is null");
            return (Criteria) this;
        }

        public Criteria andStartAlarmIsNotNull() {
            addCriterion("start_alarm is not null");
            return (Criteria) this;
        }

        public Criteria andStartAlarmEqualTo(Integer value) {
            addCriterion("start_alarm =", value, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmNotEqualTo(Integer value) {
            addCriterion("start_alarm <>", value, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmGreaterThan(Integer value) {
            addCriterion("start_alarm >", value, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_alarm >=", value, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmLessThan(Integer value) {
            addCriterion("start_alarm <", value, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmLessThanOrEqualTo(Integer value) {
            addCriterion("start_alarm <=", value, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmIn(List<Integer> values) {
            addCriterion("start_alarm in", values, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmNotIn(List<Integer> values) {
            addCriterion("start_alarm not in", values, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmBetween(Integer value1, Integer value2) {
            addCriterion("start_alarm between", value1, value2, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andStartAlarmNotBetween(Integer value1, Integer value2) {
            addCriterion("start_alarm not between", value1, value2, "startAlarm");
            return (Criteria) this;
        }

        public Criteria andDaySwitchIsNull() {
            addCriterion("day_switch is null");
            return (Criteria) this;
        }

        public Criteria andDaySwitchIsNotNull() {
            addCriterion("day_switch is not null");
            return (Criteria) this;
        }

        public Criteria andDaySwitchEqualTo(String value) {
            addCriterion("day_switch =", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchNotEqualTo(String value) {
            addCriterion("day_switch <>", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchGreaterThan(String value) {
            addCriterion("day_switch >", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchGreaterThanOrEqualTo(String value) {
            addCriterion("day_switch >=", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchLessThan(String value) {
            addCriterion("day_switch <", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchLessThanOrEqualTo(String value) {
            addCriterion("day_switch <=", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchLike(String value) {
            addCriterion("day_switch like", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchNotLike(String value) {
            addCriterion("day_switch not like", value, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchIn(List<String> values) {
            addCriterion("day_switch in", values, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchNotIn(List<String> values) {
            addCriterion("day_switch not in", values, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchBetween(String value1, String value2) {
            addCriterion("day_switch between", value1, value2, "daySwitch");
            return (Criteria) this;
        }

        public Criteria andDaySwitchNotBetween(String value1, String value2) {
            addCriterion("day_switch not between", value1, value2, "daySwitch");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}