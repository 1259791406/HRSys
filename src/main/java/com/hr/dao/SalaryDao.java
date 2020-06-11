package com.hr.dao;

import com.hr.model.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SalaryDao {


    @Update("UPDATE salary SET basic = #{basic},hour = #{hour},night = #{night},month_performance = #{monthPerformance},quarter_performance = #{quarterPerformance},year_end_bonus = #{yearEndBonus},overtime = #{overtime},allowance = #{allowance},particularly_aawards = #{particularlyAawards}," +
            "Overtime_pay = #{overtime_Pay},festival = #{festival},travel = #{travel},provide_for_the_aged = #{provideForTheAged},medical_treatment = #{medicalTreatment},housing = #{housing},in_dry_dock = #{inDryDock},occupational_injury = #{occupationalInjury},Pension_Company = #{pensionCompany}," +
            "Medcal_Company = #{medcalCompany},Unemployment_Company = #{unemploymentCompany},Birth_Company = #{birthCompany},Industriak_Company = #{industriakCompany},Housing_Company = #{housingCompany},Children = #{children},SupportTheOld = #{supportTheOld},Adult = #{adult},Draw = #{draw},Renting = #{draw},Serious = #{serious},salary = #{salary},items = #{items},LEAVE = #{leave},late = #{late},early = #{early},absenteeism = #{absenteeism} " +
            "WHERE state != 2 and id = #{id}")
    Integer updateSalary(Salary salary);
}