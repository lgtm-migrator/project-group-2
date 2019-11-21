package se.turingturtles.implementations;

import se.turingturtles.ProjectCalculations;
import se.turingturtles.entities.Task;
import se.turingturtles.entities.TeamMember;
import se.turingturtles.entities.Project;

import java.util.List;

public class ProjectCalculationsImp implements ProjectCalculations {



    public double calculateEv(){
        //Cannot calculate yet, because it is dependant on calculateCompletionBudget

        return calculateCompletedWorkPercentage()/calculateCompletionBudget();
    }


    public double calculateSv() {
        //We calculate the Schedule Variance based on calculateBCWP & calculateBCWS

        return calculateBCWP()-calculateBCWS();
    }

    public double calculateCv(){
        //We assume that actual cost of work is entirely based on the total salaries

        return ProjectManagementImp.project.getBudget() - calculateTotalSalaries();
    }



    public double calculateCompletedWorkPercentage(){
        List<Task> tasks = ProjectManagementImp.project.getTasks();
        int completedTasks = 0;

        if (tasks.size() == 0){
            return 0;
        }

        for (int i=0; i<tasks.size(); i++){
            if (tasks.get(i).getCompletion()) {
                completedTasks++;
            }
        }

        return ((double)completedTasks/tasks.size());
    }

    public double calculateCompletionBudget(){
        //We can't assume how to calculate the Completion Budget yet.
        return 0;
    }

    public double calculateBCWP(){
        //Calculate Budget Cost of Work Performed

        return (ProjectManagementImp.project.getBudget()/calculateCompletedWorkPercentage());
    }

    public double calculateBCWS(){
        //Calculate Budgeted Cost of Work Scheduled
        //We are using calculateBCWP to get the remaining amount of the budget

        return ProjectManagementImp.project.getBudget()-calculateBCWP();
    }


    public double calculateTotalSalaries(){
        List<TeamMember> members = ProjectManagementImp.project.getTeamMembers();
        double totalSalary = 0;

        if (members.size() == 0) {
            return 0;
        }

        for (int i=0; i<members.size(); i++){
            totalSalary += members.get(i).getHourlyWage();
        }

        return totalSalary;
    }
    public void increaseBudget(double amount){}
    public void decreaseBudget(double amount){}
}