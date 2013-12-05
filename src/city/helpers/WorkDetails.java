package city.helpers;

import city.interfaces.RoleInterface;


public class WorkDetails {
	public RoleInterface workRole;
	public String workLocation;
	public int workStartHour;
	public int workEndHour;
	public WorkDetails(RoleInterface job, String location) {
		this.workRole = job;
		this.workLocation = location;
		if (job.getClass().getName().contains("employ")) {
			this.workStartHour = 10;
			this.workEndHour = 10;
		}
		else if (job.getClass().getName().contains("Stack")) {
			this.workStartHour = 8;
			this.workEndHour = 20;
		}
		else if (job.getClass().getName().contains("Sheh")) {
			this.workStartHour = 9;
			this.workEndHour = 21;
		}
		else if (job.getClass().getName().contains("Bank")) {
			this.workStartHour = 9;
			this.workEndHour = 18;
		}
		else if (job.getClass().getName().contains("Market")) {
			this.workStartHour = 7;
			this.workEndHour = 24;
		}
		else if (job.getClass().getName().contains("Lord")) {
			this.workStartHour = 12;
			this.workEndHour = 18;
		}
	}
};