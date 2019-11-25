package se.turingturtles.entities;

import se.turingturtles.implementations.ProjectManagementImp;

import java.util.ArrayList;

public class TeamMember {

    private String name;
    private int id;
    private double hourlyWage;
    private ArrayList<Task> tasks;

    public TeamMember(String name, int id, double hourlyWage){
        this.name = name;
        this.id = id;
        this.hourlyWage = hourlyWage;
        this.tasks = new ArrayList<Task>();
    }

    public TeamMember(){}

    //--------------------Getters & Setters--------------------
    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public int getId(){
        return this.id;
    }
    public double getHourlyWage(){
        return this.hourlyWage;
    }
    public void setHourlyWage(double newHourlyWage){
        this.hourlyWage = newHourlyWage;
    }
    public ArrayList<Task> getTasks(){
            return tasks;
    }
    //--------------------Methods--------------------
    public void addTask(Task task){
        tasks.add(task);
    }
    public void removeTask(Task task){
        tasks.remove(task);
    }
    public int totalTasks(){
        return tasks.size();
    }
    public int getWeeksSpent(){
        int currentWeek = ProjectManagementImp.getProject().assignStartWeek();
        int weeksSpent = 0;
        for(Task obj : tasks){
            weeksSpent += (currentWeek - obj.getStartWeek());
        }
        return weeksSpent;
    }

    @Override
    public String toString() {
        return this.getName() + "(ID: " + this.getId() + ")";
    }

}
