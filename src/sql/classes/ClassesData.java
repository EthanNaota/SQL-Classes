/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.classes;

/**
 *
 * @author Naota
 */
public class ClassesData {
    
    private int crn;    // CRN Primary Key
    private String subject; // CIS, CSCI, or OA
    private String course; // Course Number
    private String section; // Section Number
    private String credits;    // Number of Credits
    private String time;    // The Time the Course Meets
    private String days;    // The Days the Course Meets: M T W R F S
    private String term;    // 15A, 7A, 7B or 7N
    private String campus;  // MAIN, NERC, NWRC, WEST
    private String room;    // Room Number
    private String enrollment;  // Randomly Generated
    
    public ClassesData(){
        crn = 0;
        subject = "";
        course = "";
        section = "";
        credits = "";
        time = "";
        days = "";
        term = "";
        campus = "";
        room = "";
        enrollment = "";
    }
    
    public ClassesData(int a, String b, String c, String d, String e, String f, 
            String g, String h, String i, String j, String k){

        this.crn = a;
        this.subject = b;
        this.course = c;
        this.section = d;
        this.credits = e;
        this.time = f;
        this.days = g;
        this.term = h;
        this.campus = i;
        this.room = j;
        this.enrollment = k;
    }
    
    public void setCRN(int a){
        this.crn = a;
    }
    
    public void setSubject(String a){
        this.subject = a;
    }
    
    public void setCourse(String a){
        this.course = a;
    }    
    
    public void setSection(String a){
        this.section = a;
    }    
    
    public void setCredits(String a){
        this.credits = a;
    }    
    
    public void setTime(String a){
        this.time = a;
    }    
    
    public void setDays(String a){
        this.days = a;
    }   
    
    public void setTerm(String a){
        this.term = a;
    }   
    
    public void setCampus(String a){
        this.campus = a;
    }   

    public void setEnrollment(String a){
        this.enrollment = a;
    }
    
    public void setRoom(String a){
        this.room = a;
    }
    
    public int getCRN(){
        return this.crn;
    }
    
    public String getSubject(){
        return this.subject;
    }
    
    public String getCourse(){
        return this.course;
    } 
    
    public String getSection(){
        return this.section;
    }
    
    public String getCredits(){
        return this.credits;
    }
    
    public String getTime(){
        return this.time;
    }
    
    public String getDays(){
        return this.days;
    }
    
    public String getTerm(){
        return this.term;
    }
    
    public String getCampus(){
        return this.campus;
    }

    public String getRoom(){
        return this.room;
    }
    
    public String getEnrollment(){
        return this.enrollment;
    }
}
