package com.hr.service.impl;

import com.hr.Overall.KaoQinData;
import com.hr.Overall.UtilModel;
import com.hr.dao.StaffDao;
import com.hr.model.CalendarList;
import com.hr.model.Overtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FiveUtil {

    @Autowired
    private StaffDao staffDao;

    /**
     * 计算吃饭需要扣除的钱
     *
     * @param data 实际上班时间
     * @param sum  吃饭需要扣除的钱
     * @param list 吃饭的打卡时间
     * @param map  加班记录
     * @return
     * @throws ParseException
     */
    public double getDemos(List<CalendarList> data, double sum, List<String> list, Map<String, Object> map,String type) throws ParseException {
        if (list == null) {
            return 0;
        }
        if (data.size() <= 0) {
            return 0;
        }
        //吃饭打卡时间
        Long cfdk = null;
        //实际上班时间
        Long sjpbsb = null;
        //时间下班时间
        Long sjpbxb = null;
        //加班开始时间
        Long start = null;
        //加班结束时间
        Long end = null;
        //需要扣除的吃饭钱“
        double work = 0;
        // 吃饭需要扣除的钱
        double num = 0;
        List<UtilModel> overTime = staffDao.GetTimeS(map);
        //吃饭集合
        if (type.equals("DL") || type.equals("IDL")){
            return 0;
        }
        for (int n = 0; n < list.size(); n++) {
            cfdk = Long.valueOf(list.get(n).replace(":", "").replace("-", "").replace(" ", ""));
            //排班集合
            System.out.println("N的值：" + n);
            for (int i = 0; i < data.size(); i++) {
                System.out.println("I的值：" + i);
                sjpbsb = Long.valueOf(data.get(i).getStart().replace(":", "").replace("-", "").replace(" ", ""));
                sjpbxb = Long.valueOf(data.get(i).getEndTime().replace(":", "").replace("-", "").replace(" ", ""));
                if (cfdk >= sjpbsb && cfdk <= sjpbxb) {
                    //正常上班时间不扣除钱
                    break;
                } else {
                    //查询是否在加班如加班则还是不扣钱
                    work = aaa(overTime, start, end, cfdk, work, sum);
                    break;
                }
            }
//            //查询是否在加班如加班则还是不扣钱
//            work = aaa(overTime, start, end, cfdk, work, sum);
//            break;
        }

        return work;
    }

    public double aaa(List<UtilModel> overTime, Long start, Long end, Long cfdk, double work, double sum) {
        for (UtilModel utilModel : overTime) {
            start = Long.valueOf(utilModel.getCode().replace(":", "").replace("-", "").replace(" ", ""));
            end = Long.valueOf(utilModel.getData().replace(":", "").replace("-", "").replace(" ", ""));
            if (cfdk >= start && cfdk <= end) {
                break;
            }
        }
        //需要扣除的吃饭钱

        return work + sum;
    }

    /**
     * @param fives
     * @param rowss
     * @param userid
     * @return
     * @throws ParseException
     */
    public static Map<String, Double> getDemo(List<CalendarList> fives, double rowss, String userid) throws ParseException {
        UtilModel data = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 迟到/早退(5分钟之内)	三次以上从当月薪资扣除50元 五次以上旷工一日
        double five = 0;
        // 迟到/早退(超过5分钟不超过30分钟)	每次从当月薪资扣减50元
        double thirty = 0;
        double thirtys = 0;
        // 迟到/早退(30分钟不超过60分钟)	每次从当月薪资 扣除70元
        double sixty = 0;
        double sixtys = 0;
        // 迟到/早退(超出1小时)	按旷工半日处理
        double oneHour = 0;
        // 迟到/早退(超出4小时)	按旷工一日处理
        double fourHour = 0;
        //五分钟
        Long mm5 = 300000L;
        //三十分钟
        long mm30 = 1800000L;
        //六十分钟
        long mm60 = 3200000L;
        //240分钟
        long mm240 = 12800000L;

        Date sb = null;
        Date xb = null;
        Date zc = null;
        Date zt = null;

        //排班记录
        for (int i = 0; i < fives.size(); i++) {
            CalendarList calendar = fives.get(i);
            if (calendar != null) {
                String q = fives.get(i).getStart();
                sb = sdf.parse(q);
                // 应下班时间
                String xiaBan = fives.get(i).getEndTime();
                xb = sdf.parse(xiaBan);
                try {
                    data = KaoQinData.GetMaxMin(userid, q.substring(0, 10));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (data.getCode().equals("1")) {
                    System.err.println("此人于：" + q.substring(0, 10) + " 无上班记录！");
                } else {
                    //打卡时间！
                    String r = data.getCode();
                    //打卡状态
                    String mes = data.getData();
                    zc = sdf.parse(r);
                    zt = sdf.parse(mes);
                    if (sb != null && zc != null && xb != null && zt != null) {
                        if (sb.getTime() < zc.getTime() + mm240 || xb.getTime() < zc.getTime() + mm240) {
                            fourHour += 1;
                        }

                        if (sb.getTime() < zc.getTime() + mm60 && sb.getTime() > zc.getTime() + mm240 || xb.getTime() < zc.getTime() + mm60 && xb.getTime() > zt.getTime() + mm240) {
                            oneHour += 1;
                        }

                        if (sb.getTime() < zc.getTime() + mm30 && sb.getTime() > zc.getTime() + mm60) {
                            sixty += 1;
                        } else if (xb.getTime() < zc.getTime() + mm30 && xb.getTime() > zt.getTime() + mm60) {
                            sixtys += 1;
                        }

                        if (sb.getTime() < zc.getTime() + mm5 && sb.getTime() > zc.getTime() + mm30) {
                            thirty += 1;
                        }
                        if (xb.getTime() < zt.getTime() + mm5 && xb.getTime() > zt.getTime() + mm30) {
                            thirtys += 1;
                        }

                        if (sb.getTime() < zc.getTime() && sb.getTime() > zc.getTime() + mm5) {
                            five += 1;
                            if (five % 3 == 0) {
                                thirty += 1;
                            } else if (five % 5 == 0) {
                                fourHour += 1;
                            }
                        } else if (xb.getTime() < zt.getTime() && xb.getTime() > zt.getTime() + mm5) {
                            five += 1;
                            if (five % 3 == 0) {
                                thirtys += 1;
                            } else if (five % 5 == 0) {
                                fourHour += 1;
                            }
                        }
                    }
                }

            } else {
                break;
            }
        }

        Map<String, Double> map = new HashMap<>();
        // 每月迟到需要扣除的金额
        double late = thirty * 50 + sixty * 70;
        // 每月早退需要扣除的金额
        double early = thirtys * 50 + sixtys * 70;
        //旷工需要扣除的钱
        double absenteeism = oneHour * 3 * 4 * rowss + fourHour * 3 * 8 * rowss;
        map.put("late", late);
        map.put("early", early);
        map.put("absenteeism", absenteeism);
        return map;
    }
}		