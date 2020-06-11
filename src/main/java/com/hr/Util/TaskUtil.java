package com.hr.Util;

import com.hr.model.CalendarList;
import com.hr.model.Task;
import com.hr.model.TaskList;

public class TaskUtil {
    /**
     * 审批过程详细表
     *
     * @param taskList 审批过程实体类
     * @param code     关联code
     * @param time     审批时间
     * @param state    审批状态  2：同意   3：拒绝
     * @param Opinion  审批意见
     * @param OrderNum 审批次序
     * @return
     */
    public static TaskList taskListUtil(TaskList taskList, String code, String time, long state, String Opinion, long OrderNum) {
        taskList.setCode(code);
        taskList.setTime(time);
        taskList.setState(state+"");
        taskList.setOpinion(Opinion);
        taskList.setOrderNum(OrderNum);
        return taskList;
    }

    /**
     * 排班记录表
     *
     * @param calendarList 排班实体类
     * @param userid       用户ID
     * @param start        开始时间
     * @param end          结束时间
     * @return
     */
    public static CalendarList calendarListUtil(CalendarList calendarList, String userid, String start, String end) {
        calendarList.setTitle(userid);
        calendarList.setUserId(userid);
        calendarList.setStart(start);
        calendarList.setEndTime(end);
        calendarList.setState("2");
        calendarList.setMarke("");
        return calendarList;
    }
}