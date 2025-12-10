package project_oop;
import java.io.*;
class MemberPlan implements Serializable{
	private int planId;
	private String planName;
	private String planDuration;  // Monthly, Quarterly, Annual
	private double planPrice;
	private String planDescription;
	private boolean accessToAllMachines;
	private boolean personalTrainerIncluded;
	private boolean groupClassesIncluded;
	private boolean lockerAccess;
	private static final long serialVersionUID = 1234567890123456789L;
	
	public MemberPlan(){
		planId = 0;
		planName = null;
		planDuration = null;
		planPrice = 0.0;
		planDescription = null;
		accessToAllMachines = false;
		personalTrainerIncluded = false;
		groupClassesIncluded = false;
		lockerAccess = false;
	}
	
	public MemberPlan(int planId, String planName, String planDuration, double planPrice, String planDescription,
			boolean accessToAllMachines, boolean personalTrainerIncluded, boolean groupClassesIncluded, boolean lockerAccess){
		this.planId = planId;
		this.planName = planName;
		this.planDuration = planDuration;
		this.planPrice = planPrice;
		this.planDescription = planDescription;
		this.accessToAllMachines = accessToAllMachines;
		this.personalTrainerIncluded = personalTrainerIncluded;
		this.groupClassesIncluded = groupClassesIncluded;
		this.lockerAccess = lockerAccess;
	}
	
	public void setPlanId(int planId){
		this.planId = planId;
	}
	public int getPlanId(){
		return planId;
	}
	
	public void setPlanName(String planName){
		this.planName = planName;
	}
	public String getPlanName(){
		return planName;
	}
	
	public void setPlanDuration(String planDuration){
		this.planDuration = planDuration;
	}
	public String getPlanDuration(){
		return planDuration;
	}
	
	public void setPlanPrice(double planPrice){
		this.planPrice = planPrice;
	}
	public double getPlanPrice(){
		return planPrice;
	}
	
	public void setPlanDescription(String planDescription){
		this.planDescription = planDescription;
	}
	public String getPlanDescription(){
		return planDescription;
	}
	
	public void setAccessToAllMachines(boolean accessToAllMachines){
		this.accessToAllMachines = accessToAllMachines;
	}
	public boolean getAccessToAllMachines(){
		return accessToAllMachines;
	}
	
	public void setPersonalTrainerIncluded(boolean personalTrainerIncluded){
		this.personalTrainerIncluded = personalTrainerIncluded;
	}
	public boolean getPersonalTrainerIncluded(){
		return personalTrainerIncluded;
	}
	
	public void setGroupClassesIncluded(boolean groupClassesIncluded){
		this.groupClassesIncluded = groupClassesIncluded;
	}
	public boolean getGroupClassesIncluded(){
		return groupClassesIncluded;
	}
	
	public void setLockerAccess(boolean lockerAccess){
		this.lockerAccess = lockerAccess;
	}
	public boolean getLockerAccess(){
		return lockerAccess;
	}
	
	public boolean validatePlanName(String name){
		if(name == null || name.isEmpty()){
			return false;
		}
		if(name.length() > 50){
			return false;
		}
		return true;
	}
	
	public boolean validatePlanPrice(String price){
		if(!checkNumberIsdouble(price)){
			return false;
		}
		double price1 = Double.parseDouble(price);
		if(price1 < 0.0){
			return false;
		}
		return true;
	}
	
	public boolean checkNumberIsdouble(String number){
		try {
			double number1 = Double.parseDouble(number);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		String features = "";
		if(accessToAllMachines) features += "All Machines Access, ";
		if(personalTrainerIncluded) features += "Personal Trainer, ";
		if(groupClassesIncluded) features += "Group Classes, ";
		if(lockerAccess) features += "Locker Access";
		if(features.endsWith(", ")) features = features.substring(0, features.length() - 2);
		
		return "\n\t\tPlan Details : " +
			"\n\n> Plan ID : " + planId +
			"\n\n> Plan Name : " + planName +
			"\n\n> Duration : " + planDuration +
			"\n\n> Price : $" + String.format("%.2f", planPrice) +
			"\n\n> Description : " + planDescription +
			"\n\n> Features : " + (features.isEmpty() ? "None" : features) +
			"\n---------------------------------------------------------------\n";
	}
}

