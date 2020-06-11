package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.ToolModal;
import com.hr.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    /**
     * 检查该手机号是否存在
     *
     * @param phone 手机号
     * @return
     */
    @Select("SELECT COUNT(userid) FROM hnuser WHERE state = 1 AND phone = #{phone}")
    int isPhone(String phone);

    @Select("SELECT userid FROM hnuser WHERE phone = #{phone}")
    String PhoneGetUserid(String phone);

    /**
     * 登陆方法
     *
     * @param map phone：用户名   password：密码
     * @return 登陆人实体类
     */
    @Select("SELECT * FROM View_User WHERE 1 = 1 AND password = #{password} AND phone = #{phone}")
    User Login(Map<String, Object> map);

    @Update("UPDATE hnuser SET password = #{password} WHERE phone = #{phone}")
    int UpdatePas(Map<String,Object> map);
    /**
     * 密码错误时，更改该用户登陆时间和登陆次数
     *
     * @param phone
     * @return
     */
    @Update("UPDATE hnuser SET logintime = GETDATE() , num = num+1 WHERE phone = #{phone}")
    int UpdateLoginTime(String phone);

    /**
     * 修改用户为不可登陆
     *
     * @param phone
     * @return
     */
    @Update("UPDATE hnuser SET state = 2 WHERE phone = #{phone}")
    int UpdateState(String phone);

    /**
     * 查看该手机号登陆错误次数
     *
     * @param Phone
     * @return
     */
    @Select("SELECT CONVERT(VARCHAR(100), logintime, 20) AS code,num AS data,state AS mes FROM hnuser WHERE phone = #{phone}")
    UtilModel Num(String Phone);

    /**
     * 登陆成功时，修改上次登陆时间以及错误登陆次数
     *
     * @param phone
     * @return
     */
    @Update("UPDATE hnuser SET logintime = GETDATE() , num = 0 WHERE phone = #{phone}")
    int ReLoginTimeNum(String phone);

    /**
     * 获取所有得用户信息
     *
     * @param map
     * @return
     */
    @Select("EXECUTE UserList #{page},#{limit},#{data}")
    List<User> list(Map<String, Object> map);

    @Select("SELECT * FROM View_User")
    List<User> SumList();

    /**
     * 统计所有用户数量
     *
     * @param map
     * @return
     */
    @Select("SELECT COUNT(id) FROM View_User WHERE 1 = 1 ${data}")
    int GetSize(Map<String, Object> map);

    /**
     * 根据用户ID查询所属部门
     *
     * @param userid 用户ID
     * @return
     */
    @Select("SELECT deptid FROM hnuser WHERE userId = #{userid}")
    String GetDeptId(String userid);

    /**
     * 所有用户信息
     *
     * @return
     */
    @Select("SELECT userid AS id,deptName+'-'+username AS name FROM View_user")
    List<ToolModal> VisList();

    /**
     * 添加一条用户信息
     *
     * @param user 用户实体类
     * @return
     */
    @Insert("INSERT INTO hnuser(userid, username, password, sex, phone, day, deptid, idcard, state, role_id,salary, NativePlace ,Education) VALUES (#{userid}, #{username}, 'VFZSRmVFMVVSWGc9', #{sex}, #{phone}, #{day}, #{deptid}, #{idcard}, #{state}, #{roleId},#{salary} ,#{nativePlace}, #{education})")
    int Add(User user);

    /**
     * @param userid
     * @return
     */
    @Select("SELECT * FROM View_User WHERE userid = #{userid}")
    User GetOneUser(String userid);

    /**
     * 添加修改时判断手机号是否重复！
     *
     * @param user 用户对象
     * @return
     */
    @Select("SELECT COUNT(id) FROM hnuser WHERE userid != #{userid} AND phone = #{phone}")
    int GetPhoneNum(User user);

    /**
     * 修改用户信息数据
     *
     * @param user
     * @return
     */
    @Update("UPDATE hnuser SET role_id = #{roleId},salaryCardNo = #{salaryCardNo} ,threadLength = #{threadLength},entryTime =#{entryTime},contractExpirationDate=#{contractExpirationDate},height = #{height},weight = #{weight},email= #{email},urgent_Name = #{urgentName},urgent_Relationship = #{urgentRelationship},urgent_Phone = #{urgentPhone},birthplace = #{birthplace},address=#{address},userType=#{userType},userState =#{userState},marriage =#{marriage}, username = #{username},sex = #{sex} ,phone = #{phone},day = #{day},deptid = #{deptid},idcard = #{idcard} ,state = #{state}, salary = #{salary} , NativePlace = #{nativePlace},Education = #{education} ,size = #{size},Children = #{children} ,SupportTheOld = #{supportTheOld} ,Adult = #{adult} ,Serious = #{serious} ,Renting = #{renting}, Draw = #{draw},post =#{post},str1= #{str1},str2=#{str2},str3 = CONVERT(VARCHAR(20),GETDATE(),20) WHERE userid = #{userid}")
    int UpdateUser(User user);

    /**
     * 用来查询所有当月所参与的日历信息
     *
     * @return
     */
    @Select("SELECT CONVERT(varchar, start,23 ) AS name FROM calendarList WHERE DATEPART(mm, start) = DATEPART(mm, GETDATE()) and DATEPART(yy, start) = DATEPART(yy, GETDATE()) AND userid = #{orderid} ORDER BY start")
    List<String> DayMonth(String orderid);

    /**
     * 查询某人在下个月所参与的日历信息
     *
     * @param orderid
     * @return
     */
    @Select("SELECT CONVERT(varchar, start,23 ) AS name FROM calendarList WHERE DATEPART(mm, start) = DATEPART(mm, GETDATE()) + 1 AND DATEPART(yy, start) = DATEPART(yy, GETDATE()) AND userid = #{orderid} ORDER BY start")
    List<String> LastMonth(String orderid);

    /**
     * 获取所有需要排班的人员列表
     *
     * @return
     */
    @Select("SELECT userid AS code,username + '-' + deptid AS data FROM View_user WHERE state = 1")
    List<UtilModel> SelectUserList();

    /**
     * 根据用户ID获取用户名和所在部门编码
     *
     * @param userid 用户ID
     * @return
     */
    @Select("SELECT username + '-'+ CASE deptid WHEN '' THEN '' ELSE (SELECT name FROM hn_dept WHERE id = deptid) END AS ss FROM hnuser WHERE userid = #}{SELECT username + '-'+ CASE deptid WHEN '' THEN '' ELSE (SELECT name FROM hn_dept WHERE id = deptid) END AS ss FROM hnuser WHERE userid = '1'SELECT username + '-'+ CASE deptid WHEN '' THEN '' ELSE (SELECT name FROM hn_dept WHERE id = deptid) END AS ss FROM hnuser WHERE userid = '1'SELECT username + '-'+ CASE deptid WHEN '' THEN '' ELSE (SELECT name FROM hn_dept WHERE id = deptid) END AS ss FROM hnuser WHERE userid = '1'SELECT username + '-'+ CASE deptid WHEN '' THEN '' ELSE (SELECT name FROM hn_dept WHERE id = deptid) END AS ss FROM hnuser WHERE userid = #{userid}")
    String GetUserMes(String userid);

    /**
     * 通过userid获取同一部门人员信息
     *
     * @param map 前端请求参数
     *            userid 替班人
     *            time 所需要替班时间
     * @return
     */
    @Select("SELECT userid AS code,day AS mes FROM View_CalendarList WHERE endTime >= #{time} AND day NOT IN (SELECT userid FROM hnuser WHERE userid != #{userid} AND deptid = (SELECT deptid FROM hnuser WHERE userid = #{userid}))")
    List<UtilModel> DeptUserList(Map<String, Object> map);
}