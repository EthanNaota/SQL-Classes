/*
 * This program is used to create a direct connection to the 68.178.217.12
 * database and provides two different methods on what to do with the data 
 * once the direct connection has been esablished.  the first method creates 
 * a csv file and writes data from the query to it.  the second method looks 
 * in the query for a question I created and then outputs the data neatly for 
 * the user to see.  I used the megamergesortdemo as a means to sort the data.  
 * I have never used a database program before and didn't find out until after 
 * I was finished with the project that i could add a condition that would sort 
 * the data for me instead of doing it all in java but this program still works 
 * perfectly. - Ethan Infelice 2017
 */
package sql.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class SQLClasses {

    public static void main(String[] args) throws Exception {

        // Connect to a database by  establishing a Connection object
        Connection conn = DriverManager.getConnection("jdbc:mysql://68.178.217.12/CWHDemo", "CWHDemo", "Access2017!");
        System.out.println("Database connection established.\n");
        System.out.println("**********************************************");
        
        // Create a statement Object for this  database connection
        Statement st = conn.createStatement();

        // call a method to query the database and write the results to
        // a csv file
        System.out.println("QUERY: SELECT crn, subject, course, section, days, time FROM fall2014 WHERE subject = \"CSCI\"; \n");
        writeToCSV(st);
        
        System.out.println("**********************************************\n");
        
        // call a method to query the database
        queryQuestion(st);

        // Close the connection
        conn.close();
    } // end main()
//*****************************************************************************************
    
    /* This method searches the query for all intermediate level CIS courses
     * host in the main campus.  It then outputs the results with all information
     * for the classes found.
     */
    public static void queryQuestion(Statement s) throws SQLException, ClassNotFoundException {

        String queryString;     // a String to hold an SQL query 
        ResultSet rs;           // the result set from an SQL query as a table
        
        System.out.println("QUERY: Find above intermediate level CIS courses on Main Campus. \n");
        
        // Create an SQL query as as String for this statement
        // this query returns all rows and columns from the database where the
        // subject = CIS and campus  = MAI and course # is > 199
        queryString = "SELECT * FROM fall2014 WHERE subject = \"CIS\" AND campus = \"MAI\" AND course >= 200; ";
 
        // Send a statement executing the query and saving the result set 
        rs = s.executeQuery(queryString);

        // print headings for the output
        System.out.println(queryString);
        System.out.printf("%-7s%-10s%-10s%-10s%-10s%-20s%-7s%-7s%-10s%-10s%-10s%n", "CRN", "subject", "course", 
                "section", "credits", "time", "days", "term", "campus", "room", "enrollment");
        System.out.println("**************************************************************************************************************");

        // start pointer at first row of results
        rs.beforeFirst();
        
        // Iterate through the result set and print all attributes
        while (rs.next())
            System.out.printf("%-7s%-10s%-10s%-10s%-10s%-20s%-7s%-7s%-10s%-10s%-10s%n", rs.getString(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                    rs.getString(10), rs.getString(11));
        
        System.out.println("**************************************************************************************************************");

    } // end queryQuestion()
//*****************************************************************************************
    
    /* this method creates a CSCI Course csv file and searches for results to match
     * the SQL query. then it sorts the found results in order of CRN # and writes the
     * results to the created csv file.
     */
    public static void writeToCSV(Statement s) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException{
        
        String queryString;     // a String to hold an SQL query 
        ResultSet rs;           // the result set from an SQL query as a table
        int rsCounter = 0;      // counts how many rows were found in results
        int i = 0;              // while rs.next loop 
        String fileName = "CSCI Course.csv";    // name of file to be created
        
        // Create an SQL query as as String for this statement
        // this query returns all rows and columns from the database results
        queryString = "SELECT * FROM fall2014 WHERE subject = \"CSCI\"; ";
        
        // Send a statement executing the query and saving the result set
        rs = s.executeQuery(queryString);
        
        // start pointer at first row of results
        rs.beforeFirst();

        // go through the results rows to see how many results were returned
        while (rs.next())
            rsCounter++;
        
        // create two class arrays to sort the data
        ClassesData[] classes = new ClassesData[rsCounter];
        ClassesData[] temp = new ClassesData[classes.length];

        // start pointer at first row of results
        rs.beforeFirst();
        
        // initiate the classData array and store the info for each row
        while (rs.next()){
            
            // initate the object
            classes[i] = new ClassesData();
            
            // store data 
            classes[i].setCRN(Integer.parseInt(rs.getString(1)) );
            classes[i].setSubject(rs.getString(2));
            classes[i].setCourse(rs.getString(3));
            classes[i].setSection(rs.getString(4));
            classes[i].setCredits(rs.getString(5));
            classes[i].setTime(rs.getString(6));
            classes[i].setDays(rs.getString(7));
            classes[i].setTerm(rs.getString(8));
            classes[i].setCampus(rs.getString(9));
            classes[i].setRoom(rs.getString(10));
            classes[i].setEnrollment(rs.getString(11));
            
            // move to next spot in array
            i++;
            
        } //end while
        
        // use mergeSort method to sort the classes array by CRN #
        mergeSort(classes, temp, 0, (classes.length - 1));

        // create a file to store the retrived data as a csv file
        createFile(fileName);

        // create an instance of a printwriter
        PrintWriter pw = new PrintWriter(new FileWriter(fileName), true);
        
        // create the header for the csv file and write it to the file
        csvFile("CRN", "subject", "course", "section", "days", "time", fileName, pw);
        
        // write all of the sorted results data to csv file
        for(int j = 0; j < classes.length; j++)
            csvFile(Integer.toString(classes[j].getCRN()), classes[j].getSubject(), classes[j].getCourse(),
                    classes[j].getSection(), classes[j].getDays(), classes[j].getTime(), fileName, pw);
        
        // close printwriter
        pw.close();
        
        System.out.println(fileName + " was succesfully updated! \n");
    } // end writeToCSV() 
//*****************************************************************************************
    
    /* this method creates a file with the filename that is given 
     */
    public static void createFile(String fileName){
        
        System.out.println("Attempting to Create " + fileName);
        
        // try to create the file and if it already exists tell the user the
        // file is being overwritten
    	try {
            
            //create file
	    File file = new File(fileName);
            
            //tell user if file was created or overwitten
	    if (file.createNewFile()){
                System.out.println(fileName + " was created! \n");
	    }else{
                System.out.println(fileName + " already exists. Overwriting Existing File. \n");
	    } // end if/else
            
        } catch (IOException e) {
	    e.printStackTrace();
	} // end try/catch
    } // end createFile()
//*****************************************************************************************
    
    /* this method takes in the info needed to be written to the file and also 
     * the printwriter and appended the file so each time it runs it creates a
     * new line with all of the information properly formatted
     */
    public static void csvFile(String crn, String subject, String course,
            String section, String days, String time, String fileName, PrintWriter pw) throws FileNotFoundException, IOException{

        //appends csv file with info from database
        pw.append(crn + ",");
        pw.append(subject + ",");
        pw.append(course + ",");
        pw.append(section + ",");
        pw.append(days + ",");
        pw.append(time + "," + '\n');   
    } // end csvFile()
//*****************************************************************************************
    
    /* Prints some info from the ClassData array was only used for testing purposes
     */
    public static void printArray(ClassesData[] a){
        
        // loop prints data from array for length of array
        for(int i = 0; i < a.length; i++){
            
            System.out.println(a[i].getCRN() );
            System.out.println(a[i].getCourse() );
            System.out.println(a[i].getDays());
            System.out.println("");
 
        } // end for loop
    } // end printArray()
//*****************************************************************************************    
    
    /* This method merges the two halves of the set being sorted back together.
     * the low half goes from a[low] to a[mid]
     * the high half goes from a[mid+1] to a[high]
     * (High and low only refer to index numbers, not the values in the array.)
     * 
     * The work of sorting occurs as the two halves are merged back into one 
     * sorted set.
     * 
     * This version of mergesort copies the set to be sorted into the same 
     * locations in a temporary array, then sorts them as it puts them back.
     * Some versions of mergesort sort into the temporary array then copy it back.
     */
    public static void merge(ClassesData[] a, ClassesData[] temp, int low, int mid, int high) {
        //  low is the low index of the part of the array to be sorted
        //  high is the high index of the part of the array to be sorted
        //  mid is the middle of the array – last item in low half
        
        // copy the two sets from a[] to the same locations in the temporary array
        for (int i = low; i <= high; i++) {
            temp[i] = a[i];
        }

        //set up necessary pointers 
        int lowP = low;         // pointer to current item in low half
        int highP = mid + 1;    // pointer to current item in high half
        int aP = low;           // pointer to where each item will be put back in a[]

        // while the pointers have not yet reached the end of either half)
        while ((lowP <= mid) && (highP <= high)) {
            
            // if current item in low half <= current item in high half 
            if (temp[lowP].getCRN() <= temp[highP].getCRN()) {
                // move item at lowP back to array a and increment low pointer
                a[aP] = temp[lowP];
                lowP++;
            } else {
                // move item at highP back to array a and increment high pointer
                a[aP] = temp[highP];
                highP++;
            } // end if..else
            
            // increment pointer for location in original array
            aP++;
        } // end while

        /* When the while loop is done, either the low half or the high half is done 
         * We now simply move back everything in the half not yet done.
         * Remember, each half is already in order itself.
         */
        // if lowP has reached end of low half, then low half is done, move rest of high half
        if (lowP > mid) 
            for (int i = highP; i <= high; i++) {
                a[aP] = temp[i];
                aP++;
            } // end for
        else // high half is done, move rest of low half
        
            for (int i = lowP; i <= mid; i++) {
                a[aP] = temp[i];
                aP++;
            }// end for
        
        return;
    } // end merge()
// *************************************************************
    
    public static void mergeSort(ClassesData[] a, ClassesData[] temp, int low, int high) {
        //  low is the low index of the part of the array to be sorted
        //  high is the high index of the part of the array to be sorted
        
        int mid;  // the middle of the array – last item in low half
        
        // if high > low then there is more than one item in the list to be sorted
        if (high > low) {

            // split into two halves and mergeSort each part

            // find middle (last element in low half)   
            mid = (low+high)/2;
            mergeSort(a, temp, low, mid );
            mergeSort(a, temp, mid+1, high);
            
            // merge the two halves back together, sorting while merging
            merge(a, temp, low, mid, high);
        } // end if 

        return;
    }// end mergerSort()    
//*****************************************************************************************
    
} // end SQLClasses class
