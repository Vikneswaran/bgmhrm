package com.beans.leaveapp.batch.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.beans.leaveapp.employee.model.Employee;
import com.beans.leaveapp.employee.service.EmployeeService;
import com.beans.leaveapp.jbpm6.util.ApplicationContextProvider;
import com.beans.leaveapp.monthlyreport.model.AnnualLeaveReport;
import com.beans.leaveapp.monthlyreport.service.AnnualLeaveReportService;

public class UpdateAnnualLeaveReport implements Serializable {
 
private static final long serialVersionUID = 1L;
	
	ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
	EmployeeService employeeService = (EmployeeService) applicationContext.getBean("employeeService");
	AnnualLeaveReportService annualLeaveReportService = (AnnualLeaveReportService) applicationContext.getBean("annualLeaveReportService");   

public void UpdatingAnnualLeaveReport(){
	
	
	int currentDateMonth;
	int currentDateYear;
	int joinDateMonth;
	int joinDateYear;		
	int sortingMonthId;	
	int beforeYear;
	int decemberMonthid;
	
	Date currentDate = new Date();		
	Calendar cal = Calendar.getInstance();
	cal.setTime(currentDate);
	currentDateMonth = cal.get(Calendar.MONTH);
	currentDateYear = cal.get(Calendar.YEAR);
	
	sortingMonthId = currentDateMonth+1;
	beforeYear = currentDateYear-1;
	decemberMonthid = 12;
	
	List<Employee> employeeList = employeeService.findByEmployeeTypePermAndCont();
	for(Employee employee:employeeList){
		
		Date joinDate;
		joinDate = employee.getJoinDate();
		if(joinDate != null){
		cal.setTime(joinDate);
		joinDateMonth = cal.get(Calendar.MONTH);
		joinDateYear = cal.get(Calendar.YEAR);		
	
		int yearBalanceRemaining;
		yearBalanceRemaining = currentDateYear - joinDateYear;
		
		double noOfLeavesCredited;		
		
		if((currentDateMonth == joinDateMonth)  && (currentDateYear != joinDateYear))
		{
			
			if(sortingMonthId == 1){
				AnnualLeaveReport annualLeaveReportDec = annualLeaveReportService.findAnnualLeaveReport(employee.getId(), decemberMonthid, beforeYear);
				double leavesBroughtForward;
				leavesBroughtForward = annualLeaveReportDec.getCurrentLeaveBalance();
				List<AnnualLeaveReport> annualLeaveReportList = annualLeaveReportService.findAnnualLeaveReportByEmployeeId(employee.getId(), sortingMonthId, currentDateYear);
				ArrayList<AnnualLeaveReport> annualLeaveToBeUpdatedList = new ArrayList<AnnualLeaveReport>();				
					AnnualLeaveReport annualLeaveReportCurrentMonth =	annualLeaveReportList.get(0);				
					double currentBalance = annualLeaveReportCurrentMonth.getCurrentLeaveBalance();
					double currentBalanceWithAddedLeave = currentBalance+yearBalanceRemaining+1.0;
					annualLeaveReportCurrentMonth.setCurrentLeaveBalance(currentBalanceWithAddedLeave);
					annualLeaveReportCurrentMonth.setBalanceBroughtForward(leavesBroughtForward);
				if(yearBalanceRemaining <= 4){
					noOfLeavesCredited = yearBalanceRemaining+1.0;
					annualLeaveReportCurrentMonth.setLeavesCredited(noOfLeavesCredited);
				}
				else
				{
					noOfLeavesCredited = 4.0+1.0;
					annualLeaveReportCurrentMonth.setLeavesCredited(noOfLeavesCredited);
				}
					annualLeaveToBeUpdatedList.add(annualLeaveReportCurrentMonth);
					AnnualLeaveReport annualLeaveReportTotal =	annualLeaveReportList.get(1);
					annualLeaveReportTotal.setLeavesCredited(noOfLeavesCredited);
					annualLeaveReportTotal.setBalanceBroughtForward(leavesBroughtForward);
					annualLeaveToBeUpdatedList.add(annualLeaveReportTotal);
					for(AnnualLeaveReport annualLeaveReport : annualLeaveToBeUpdatedList){
						annualLeaveReportService.update(annualLeaveReport);
					}				
			}
			else 
			{
				List<AnnualLeaveReport> annualLeaveReportList = annualLeaveReportService.findAnnualLeaveReportByEmployeeId(employee.getId(), sortingMonthId, currentDateYear);
				ArrayList<AnnualLeaveReport> annualLeaveToBeUpdatedList = new ArrayList<AnnualLeaveReport>();
				
				AnnualLeaveReport annualLeaveReportCurrentMonth =	annualLeaveReportList.get(0);
				
				double currentBalance = annualLeaveReportCurrentMonth.getCurrentLeaveBalance();
				double currentBalanceWithAddedLeave = currentBalance+yearBalanceRemaining+1.0;
				annualLeaveReportCurrentMonth.setCurrentLeaveBalance(currentBalanceWithAddedLeave);
				if(yearBalanceRemaining <= 4){
					noOfLeavesCredited = yearBalanceRemaining+1.0;
					annualLeaveReportCurrentMonth.setLeavesCredited(noOfLeavesCredited);
				}
				else
				{
					noOfLeavesCredited = 4.0+1.0;
					annualLeaveReportCurrentMonth.setLeavesCredited(noOfLeavesCredited);
				}
					annualLeaveToBeUpdatedList.add(annualLeaveReportCurrentMonth);
					AnnualLeaveReport annualLeaveReportTotal =	annualLeaveReportList.get(1);
					annualLeaveReportTotal.setLeavesCredited(noOfLeavesCredited);
					annualLeaveToBeUpdatedList.add(annualLeaveReportTotal);
					for(AnnualLeaveReport annualLeaveReport : annualLeaveToBeUpdatedList){
						annualLeaveReportService.update(annualLeaveReport);
					}
				}
		}		
		else
		{
			
			
			if(sortingMonthId == 1){
				AnnualLeaveReport annualLeaveReportDec = annualLeaveReportService.findAnnualLeaveReport(employee.getId(), decemberMonthid, beforeYear);
				double leavesBroughtForward;
				leavesBroughtForward = annualLeaveReportDec.getCurrentLeaveBalance();
				List<AnnualLeaveReport> annualLeaveReportList = annualLeaveReportService.findAnnualLeaveReportByEmployeeId(employee.getId(), sortingMonthId, currentDateYear);
				ArrayList<AnnualLeaveReport> annualLeaveToBeUpdatedList = new ArrayList<AnnualLeaveReport>();
				
				AnnualLeaveReport annualLeaveReportCurrentMonth =	annualLeaveReportList.get(0);
				double currentBalance = annualLeaveReportCurrentMonth.getCurrentLeaveBalance();
				double currentBalanceWithAddedLeave = currentBalance+1.0;
				annualLeaveReportCurrentMonth.setCurrentLeaveBalance(currentBalanceWithAddedLeave);			
				noOfLeavesCredited = 1.0;
				annualLeaveReportCurrentMonth.setLeavesCredited(noOfLeavesCredited);
				annualLeaveReportCurrentMonth.setBalanceBroughtForward(leavesBroughtForward);
				annualLeaveToBeUpdatedList.add(annualLeaveReportCurrentMonth);
				AnnualLeaveReport annualLeaveReportTotal =	annualLeaveReportList.get(1);
				annualLeaveReportTotal.setLeavesCredited(noOfLeavesCredited);
				annualLeaveReportTotal.setBalanceBroughtForward(leavesBroughtForward);
				annualLeaveToBeUpdatedList.add(annualLeaveReportTotal);
				for(AnnualLeaveReport annualLeaveReport : annualLeaveToBeUpdatedList){
					annualLeaveReportService.update(annualLeaveReport);
				}
			}
			else
			{
			List<AnnualLeaveReport> annualLeaveReportList = annualLeaveReportService.findAnnualLeaveReportByEmployeeId(employee.getId(), sortingMonthId, currentDateYear);
			ArrayList<AnnualLeaveReport> annualLeaveToBeUpdatedList = new ArrayList<AnnualLeaveReport>();
			
			AnnualLeaveReport annualLeaveReportCurrentMonth =	annualLeaveReportList.get(0);
			double currentBalance = annualLeaveReportCurrentMonth.getCurrentLeaveBalance();
			double currentBalanceWithAddedLeave = currentBalance+1.0;
			annualLeaveReportCurrentMonth.setCurrentLeaveBalance(currentBalanceWithAddedLeave);			
			noOfLeavesCredited = 1.0;
			annualLeaveReportCurrentMonth.setLeavesCredited(noOfLeavesCredited);
			annualLeaveToBeUpdatedList.add(annualLeaveReportCurrentMonth);
			AnnualLeaveReport annualLeaveReportTotal =	annualLeaveReportList.get(1);
			annualLeaveReportTotal.setLeavesCredited(noOfLeavesCredited);
			annualLeaveToBeUpdatedList.add(annualLeaveReportTotal);
			for(AnnualLeaveReport annualLeaveReport : annualLeaveToBeUpdatedList){
				annualLeaveReportService.update(annualLeaveReport);
			}
		}		
		}
		}	
	}
	}

public EmployeeService getEmployeeService() {
	return employeeService;
}

public void setEmployeeService(EmployeeService employeeService) {
	this.employeeService = employeeService;
}

public AnnualLeaveReportService getAnnualLeaveReportService() {
	return annualLeaveReportService;
}

public void setAnnualLeaveReportService(
		AnnualLeaveReportService annualLeaveReportService) {
	this.annualLeaveReportService = annualLeaveReportService;
}




}
