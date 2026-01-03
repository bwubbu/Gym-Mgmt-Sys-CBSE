package project_oop;
import java.time.LocalDate;
import java.util.*;
class Report{
	private Gym gymDetails;

	Report(){
		gymDetails = new Gym();
	}
	public void setGymDetails(Gym gymDetails){
		this.gymDetails = gymDetails;
	}
	public Gym getGymDetails(){
		return gymDetails;
	}
	public String generateFullReport(){
		LocalDate currentDate = LocalDate.now();
		String lines = "--------------------------------";
		String reportDetails ="\n Date : " + currentDate + "\n";
		reportDetails += generateTrainerReport() + lines + generateMemberReport() + lines
		 + generateMachineReport() + lines + generateBookingReport() + lines + generateReportOfOutStandingBalance() + lines
		 + generateReportOfZeroOutStandingBalance() + lines;
		 return reportDetails;
	}
	public String generateTrainerReport(){
		String reportDetails = "";
		reportDetails += "\n >>> Trainers list\n\n";
		ArrayList<Trainer> trainers = gymDetails.getTrainers();
		for(Trainer t : trainers){
			reportDetails += " " + t.name + " (" + t.regId + ")\n";
		}
		return reportDetails;
	}
	public String generateMemberReport(){
		String reportDetails = "";
		reportDetails += "\n >>> Members list\n\n";
		ArrayList<Member> members = gymDetails.getMembers();
		for(Member m : members){
			reportDetails += " " + m.name + " (" + m.regId + ")\n";
		}
		return reportDetails;
	}
	public String generateMachineReport(){
		String reportDetails = "";
		reportDetails += "\n >>> Machines list\n\n";
		ArrayList<Machine> machines = gymDetails.getMachines();
		for(Machine m : machines){
			reportDetails += " " + m.getName() + " (" + m.getRegId() + ")\n";
		}
		return reportDetails;
	}
	public String generateBookingReport(){
		String reportDetails = "";
		reportDetails += "\n >>> Bookings list\n\n";
		reportDetails += gymDetails.viewAllMachineBookings();
		return reportDetails;
	}
	public String generateReportOfOutStandingBalance(){
		String reportDetails = "";
		reportDetails += "\n >>> Members with OutStanding Balance\n\n";
		ArrayList<Member> members = gymDetails.getMembers();
		for(Member m : members){
			if(m.getPayment().checkStatus().equals("UnPaid")){
				reportDetails += " " + m.name + " (" + m.regId + ") $" + m.getPayment().getOutstandingBalance() + "\n";
			}
		}
		return reportDetails;
	}
	public String generateReportOfZeroOutStandingBalance(){
		String reportDetails = "";
		reportDetails += "\n >>> Members with Zero OutStanding Balance\n\n";
		ArrayList<Member> members = gymDetails.getMembers();
		for(Member m : members){
			if(m.getPayment().checkStatus().equals("Paid")){
				reportDetails += " " + m.name + " (" + m.regId + ") $" + m.getPayment().getOutstandingBalance() + "\n";
			}
		}
		return reportDetails;
	}
	
	// Member Analytics Methods
	public String generateMemberDemographicsReport(){
		LocalDate currentDate = LocalDate.now();
		ArrayList<Member> members = gymDetails.getMembers();
		
		if(members.isEmpty()){
			return "\n Date : " + currentDate + "\n\n >>> Member Demographics Report\n\n" +
				   "Insufficient member data available for analytics. Please ensure member records exist.\n";
		}
		
		String reportDetails = "\n Date : " + currentDate + "\n\n >>> Member Demographics Report\n\n";
		
		// Total member count
		int totalMembers = members.size();
		reportDetails += "Total Members: " + totalMembers + "\n\n";
		
		// Gender distribution
		int maleCount = 0;
		int femaleCount = 0;
		int otherCount = 0;
		int notSpecifiedCount = 0;
		
		for(Member m : members){
			if(m.gender != null){
				String gender = m.gender.toLowerCase();
				if(gender.equals("male") || gender.equals("m")){
					maleCount++;
				} else if(gender.equals("female") || gender.equals("f")){
					femaleCount++;
				} else {
					otherCount++;
				}
			} else {
				notSpecifiedCount++;
			}
		}
		
		reportDetails += "Gender Distribution:\n";
		reportDetails += "  Male: " + maleCount + " (" + String.format("%.1f", (maleCount * 100.0 / totalMembers)) + "%)\n";
		reportDetails += "  Female: " + femaleCount + " (" + String.format("%.1f", (femaleCount * 100.0 / totalMembers)) + "%)\n";
		if(otherCount > 0){
			reportDetails += "  Other: " + otherCount + " (" + String.format("%.1f", (otherCount * 100.0 / totalMembers)) + "%)\n";
		}
		if(notSpecifiedCount > 0){
			reportDetails += "  Not Specified: " + notSpecifiedCount + " (" + String.format("%.1f", (notSpecifiedCount * 100.0 / totalMembers)) + "%)\n";
		}
		reportDetails += "\n";
		
		// Age group distribution
		int ageUnder18 = 0;
		int age18_25 = 0;
		int age26_35 = 0;
		int age36_45 = 0;
		int age46_55 = 0;
		int age56Plus = 0;
		
		for(Member m : members){
			int age = m.age;
			if(age < 18){
				ageUnder18++;
			} else if(age >= 18 && age <= 25){
				age18_25++;
			} else if(age >= 26 && age <= 35){
				age26_35++;
			} else if(age >= 36 && age <= 45){
				age36_45++;
			} else if(age >= 46 && age <= 55){
				age46_55++;
			} else if(age > 55){
				age56Plus++;
			}
		}
		
		reportDetails += "Age Group Distribution:\n";
		if(ageUnder18 > 0){
			reportDetails += "  Under 18 years: " + ageUnder18 + " (" + String.format("%.1f", (ageUnder18 * 100.0 / totalMembers)) + "%)\n";
		}
		reportDetails += "  18-25 years: " + age18_25 + " (" + String.format("%.1f", (age18_25 * 100.0 / totalMembers)) + "%)\n";
		reportDetails += "  26-35 years: " + age26_35 + " (" + String.format("%.1f", (age26_35 * 100.0 / totalMembers)) + "%)\n";
		reportDetails += "  36-45 years: " + age36_45 + " (" + String.format("%.1f", (age36_45 * 100.0 / totalMembers)) + "%)\n";
		reportDetails += "  46-55 years: " + age46_55 + " (" + String.format("%.1f", (age46_55 * 100.0 / totalMembers)) + "%)\n";
		reportDetails += "  56+ years: " + age56Plus + " (" + String.format("%.1f", (age56Plus * 100.0 / totalMembers)) + "%)\n";
		
		return reportDetails;
	}
	
	public String generateGrowthTrendsReport(){
		LocalDate currentDate = LocalDate.now();
		ArrayList<Member> members = gymDetails.getMembers();
		
		if(members.isEmpty()){
			return "\n Date : " + currentDate + "\n\n >>> Growth Trends Report\n\n" +
				   "Insufficient member data available for analytics. Please ensure member records exist.\n";
		}
		
		String reportDetails = "\n Date : " + currentDate + "\n\n >>> Growth Trends Report\n\n";
		
		// Group members by join date (month/year)
		Map<String, Integer> monthlyRegistrations = new HashMap<>();
		Map<Integer, Integer> yearlyRegistrations = new HashMap<>();
		
		for(Member m : members){
			if(m.joinDate != null){
				int month = m.joinDate.getMonth();
				int year = m.joinDate.getYear();
				String monthYear = month + "/" + year;
				
				monthlyRegistrations.put(monthYear, monthlyRegistrations.getOrDefault(monthYear, 0) + 1);
				yearlyRegistrations.put(year, yearlyRegistrations.getOrDefault(year, 0) + 1);
			}
		}
		
		// Yearly growth
		reportDetails += "Yearly Registrations:\n";
		List<Integer> years = new ArrayList<>(yearlyRegistrations.keySet());
		Collections.sort(years);
		for(Integer year : years){
			reportDetails += "  " + year + ": " + yearlyRegistrations.get(year) + " members\n";
		}
		reportDetails += "\n";
		
		// Calculate growth rate if we have multiple years
		if(years.size() >= 2){
			int currentYear = years.get(years.size() - 1);
			int previousYear = years.get(years.size() - 2);
			int currentCount = yearlyRegistrations.get(currentYear);
			int previousCount = yearlyRegistrations.get(previousYear);
			
			if(previousCount > 0){
				double growthRate = ((currentCount - previousCount) * 100.0) / previousCount;
				reportDetails += "Growth Rate (" + previousYear + " to " + currentYear + "): " + 
								String.format("%.1f", growthRate) + "%\n\n";
			}
		}
		
		// Monthly registrations (last 12 months)
		reportDetails += "Monthly Registrations (Recent):\n";
		List<String> monthYears = new ArrayList<>(monthlyRegistrations.keySet());
		Collections.sort(monthYears, (a, b) -> {
			String[] partsA = a.split("/");
			String[] partsB = b.split("/");
			int yearA = Integer.parseInt(partsA[1]);
			int yearB = Integer.parseInt(partsB[1]);
			if(yearA != yearB) return yearB - yearA;
			return Integer.parseInt(partsB[0]) - Integer.parseInt(partsA[0]);
		});
		
		int count = 0;
		for(String monthYear : monthYears){
			if(count >= 12) break;
			reportDetails += "  " + monthYear + ": " + monthlyRegistrations.get(monthYear) + " members\n";
			count++;
		}
		
		return reportDetails;
	}
	
	public String generateBodyStatisticsReport(){
		LocalDate currentDate = LocalDate.now();
		ArrayList<Member> members = gymDetails.getMembers();
		
		if(members.isEmpty()){
			return "\n Date : " + currentDate + "\n\n >>> Body Statistics Report\n\n" +
				   "Insufficient member data available for analytics. Please ensure member records exist.\n";
		}
		
		String reportDetails = "\n Date : " + currentDate + "\n\n >>> Body Statistics Report\n\n";
		
		double totalHeight = 0;
		double totalWeight = 0;
		double totalBMI = 0;
		int validRecords = 0;
		
		// BMI categories
		int underweight = 0;  // BMI < 18.5
		int normal = 0;        // 18.5 <= BMI < 25
		int overweight = 0;    // 25 <= BMI < 30
		int obese = 0;         // BMI >= 30
		
		for(Member m : members){
			if(m.getHeight() > 0 && m.getWeight() > 0){
				totalHeight += m.getHeight();
				totalWeight += m.getWeight();
				double bmi = m.getBmi();
				totalBMI += bmi;
				validRecords++;
				
				if(bmi < 18.5){
					underweight++;
				} else if(bmi < 25){
					normal++;
				} else if(bmi < 30){
					overweight++;
				} else {
					obese++;
				}
			}
		}
		
		if(validRecords == 0){
			return reportDetails + "No valid body statistics data available.\n";
		}
		
		double avgHeight = totalHeight / validRecords;
		double avgWeight = totalWeight / validRecords;
		double avgBMI = totalBMI / validRecords;
		
		reportDetails += "Average Statistics:\n";
		reportDetails += "  Average Height: " + String.format("%.2f", avgHeight) + " feet\n";
		reportDetails += "  Average Weight: " + String.format("%.2f", avgWeight) + " kg\n";
		reportDetails += "  Average BMI: " + String.format("%.2f", avgBMI) + "\n\n";
		
		reportDetails += "BMI Category Distribution:\n";
		reportDetails += "  Underweight (BMI < 18.5): " + underweight + " (" + 
						String.format("%.1f", (underweight * 100.0 / validRecords)) + "%)\n";
		reportDetails += "  Normal (18.5 <= BMI < 25): " + normal + " (" + 
						String.format("%.1f", (normal * 100.0 / validRecords)) + "%)\n";
		reportDetails += "  Overweight (25 <= BMI < 30): " + overweight + " (" + 
						String.format("%.1f", (overweight * 100.0 / validRecords)) + "%)\n";
		reportDetails += "  Obese (BMI >= 30): " + obese + " (" + 
						String.format("%.1f", (obese * 100.0 / validRecords)) + "%)\n";
		
		return reportDetails;
	}
	
	public String generateFitnessGoalDistributionReport(){
		LocalDate currentDate = LocalDate.now();
		ArrayList<Member> members = gymDetails.getMembers();
		
		if(members.isEmpty()){
			return "\n Date : " + currentDate + "\n\n >>> Fitness Goal Distribution Report\n\n" +
				   "Insufficient member data available for analytics. Please ensure member records exist.\n";
		}
		
		String reportDetails = "\n Date : " + currentDate + "\n\n >>> Fitness Goal Distribution Report\n\n";
		
		Map<String, Integer> goalCount = new HashMap<>();
		int totalWithGoals = 0;
		
		for(Member m : members){
			if(m.getFitnessGoal() != null && !m.getFitnessGoal().trim().isEmpty()){
				String goal = m.getFitnessGoal().trim();
				goalCount.put(goal, goalCount.getOrDefault(goal, 0) + 1);
				totalWithGoals++;
			}
		}
		
		int totalMembers = members.size();
		reportDetails += "Total Members: " + totalMembers + "\n";
		reportDetails += "Members with Fitness Goals: " + totalWithGoals + "\n\n";
		
		if(goalCount.isEmpty()){
			return reportDetails + "No fitness goals data available.\n";
		}
		
		reportDetails += "Fitness Goal Distribution:\n";
		List<Map.Entry<String, Integer>> sortedGoals = new ArrayList<>(goalCount.entrySet());
		sortedGoals.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort by count descending
		
		for(Map.Entry<String, Integer> entry : sortedGoals){
			String goal = entry.getKey();
			int count = entry.getValue();
			double percentage = (count * 100.0) / totalWithGoals;
			reportDetails += "  " + goal + ": " + count + " members (" + 
							String.format("%.1f", percentage) + "%)\n";
		}
		
		return reportDetails;
	}
	
	// Equipment Usage Analytics Method
	public String generateEquipmentUsageAnalyticsReport(){
		LocalDate currentDate = LocalDate.now();
		ArrayList<Machine> machines = gymDetails.getMachines();
		
		if(machines.isEmpty()){
			return "\n Date : " + currentDate + "\n\n >>> Equipment Usage Analytics Report\n\n" +
				   "No machine data available. Please ensure machine records exist.\n";
		}
		
		String reportDetails = "\n Date : " + currentDate + "\n\n >>> Equipment Usage Analytics Report\n\n";
		
		int totalMachines = machines.size();
		int totalCapacity = totalMachines * 8; // Each machine has capacity of 8 bookings
		int totalBookings = 0;
		int machinesWithBookings = 0;
		int machinesFullyBooked = 0;
		int machinesUnused = 0;
		
		// Machine popularity tracking
		Map<String, Integer> machineBookingCount = new HashMap<>();
		Map<String, Double> machineUtilization = new HashMap<>();
		
		for(Machine m : machines){
			Member[] bookings = m.getBookings();
			int machineBookings = 0;
			
			for(int i = 0; i < bookings.length; i++){
				if(bookings[i] != null){
					machineBookings++;
					totalBookings++;
				}
			}
			
			machineBookingCount.put(m.getName() + " (" + m.getRegId() + ")", machineBookings);
			double utilization = (machineBookings * 100.0) / 8.0; // 8 is the capacity per machine
			machineUtilization.put(m.getName() + " (" + m.getRegId() + ")", utilization);
			
			if(machineBookings > 0){
				machinesWithBookings++;
			}
			if(machineBookings == 8){
				machinesFullyBooked++;
			}
			if(machineBookings == 0){
				machinesUnused++;
			}
		}
		
		// Check if no bookings exist
		if(totalBookings == 0){
			reportDetails += "No booking data available. All machines are currently unused.\n\n";
			reportDetails += "Overall Statistics:\n";
			reportDetails += "  Total Machines: " + totalMachines + "\n";
			reportDetails += "  Total Capacity: " + totalCapacity + " bookings\n";
			reportDetails += "  Total Bookings: 0\n";
			reportDetails += "  Average Utilization Rate: 0.0%\n\n";
			reportDetails += "Capacity Distribution:\n";
			reportDetails += "  Machines with Bookings: 0\n";
			reportDetails += "  Machines Fully Booked: 0\n";
			reportDetails += "  Machines Unused: " + machinesUnused + "\n\n";
			reportDetails += "Machine Utilization Rates:\n";
			for(Machine m : machines){
				reportDetails += "  " + m.getName() + " (" + m.getRegId() + "): 0.0% (0/8 bookings)\n";
			}
			return reportDetails;
		}
		
		// Overall statistics
		double averageUtilization = (totalBookings * 100.0) / totalCapacity;
		reportDetails += "Overall Statistics:\n";
		reportDetails += "  Total Machines: " + totalMachines + "\n";
		reportDetails += "  Total Capacity: " + totalCapacity + " bookings\n";
		reportDetails += "  Total Bookings: " + totalBookings + "\n";
		reportDetails += "  Average Utilization Rate: " + String.format("%.1f", averageUtilization) + "%\n\n";
		
		// Capacity distribution
		reportDetails += "Capacity Distribution:\n";
		reportDetails += "  Machines with Bookings: " + machinesWithBookings + " (" + 
						String.format("%.1f", (machinesWithBookings * 100.0 / totalMachines)) + "%)\n";
		reportDetails += "  Machines Fully Booked: " + machinesFullyBooked + " (" + 
						String.format("%.1f", (machinesFullyBooked * 100.0 / totalMachines)) + "%)\n";
		reportDetails += "  Machines Unused: " + machinesUnused + " (" + 
						String.format("%.1f", (machinesUnused * 100.0 / totalMachines)) + "%)\n\n";
		
		// Machine popularity (sorted by booking count)
		reportDetails += "Machine Popularity (by booking count):\n";
		List<Map.Entry<String, Integer>> sortedMachines = new ArrayList<>(machineBookingCount.entrySet());
		sortedMachines.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort by count descending
		
		for(Map.Entry<String, Integer> entry : sortedMachines){
			String machineName = entry.getKey();
			int bookings = entry.getValue();
			double utilization = machineUtilization.get(machineName);
			reportDetails += "  " + machineName + ": " + bookings + " bookings (" + 
							String.format("%.1f", utilization) + "% utilization)\n";
		}
		
		return reportDetails;
	}
}