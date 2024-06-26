/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package testsuite;

import java.util.*;

import com.google.common.collect.Collections2;

public class Customer {
    private Set<String> preferences;  // set of preferences
    public List<String> advertisements;   // list of received advertisements
    private int maxAdvs;     // maximum number of ads a customer can have

    public Customer(Set<String> preferences, List<String> advertisements, int maxAdvs) {
        this.preferences = preferences;
        this.advertisements = advertisements;
        this.maxAdvs = maxAdvs;
    }
  
     // Getters and setters for all fields
    public Set<String> getPreferences() {
        return new HashSet<>(this.preferences);  // Return a copy to prevent direct modification
    }

    public List<String> getAdvertisements() {
        return new ArrayList<>(this.advertisements);  // Return a copy to prevent direct modification
    }

    public int getMaxAdvs() {
        return this.maxAdvs;
    }

    public void setPreferences(Set<String> preferences) {
        this.preferences = new HashSet<>(preferences);
    }

    public void setAdvertisements(List<String> advertisements) {
        this.advertisements = new ArrayList<>(advertisements);
    }

    public void setMaxAdvs(int maxAdvs) {
        this.maxAdvs = maxAdvs;
    }

    public boolean addAdvertisement(String adv) {
        if (this.advertisements.size() < this.maxAdvs) {
            this.advertisements.add(adv);
            return true;
        } else {
            return false;
        }
    }

    public void readAdvertisements(int n) {
        int size = this.advertisements.size();
        int count = Math.min(n, size);  // Number of advertisements to print/remove
    
        // Print the last 'count' advertisements in reverse order
        for (int i = size - 1; i >= size - count; i--) {
            System.out.println(this.advertisements.get(i));
        }
    
        // Remove the printed advertisements from the list
        this.advertisements = new ArrayList<>(this.advertisements.subList(0, size - count));
    }
    
}

