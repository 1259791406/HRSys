package com.hr.model;

public class ReturnParam {
	//扣税
	private double  TaxDeduction;
	//五险一金
	private double risks;
	//实际工资
	private double  salary;
	//专项款
	private double  special;
	public double getTaxDeduction() {
		return TaxDeduction;
	}
	public void setTaxDeduction(double taxDeduction) {
		TaxDeduction = taxDeduction;
	}
	public double getRisks() {
		return risks;
	}
	public void setRisks(double risks) {
		this.risks = risks;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getSpecial() {
		return special;
	}
	public void setSpecial(double special) {
		this.special = special;
	}
	
	
}
