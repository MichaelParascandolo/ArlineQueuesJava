import java.util.Scanner;

public class AirlineCheckinSim {
    // Data Fields
/** Queue of frequent flyers. */
private PassengerQueue frequentFlyerQueue = new PassengerQueue("Frequent Flyer"); 
/** Queue of regular passengers. */
private PassengerQueue regularPassengerQueue = new PassengerQueue("Regular Passenger"); 
/** Maximum number of frequent flyers to be served
before a regular passenger gets served. */
private int frequentFlyerMax;
/** Maximum time to service a passenger. */
private int maxProcessingTime;
/** Total simulated time. */
private int totalTime;
/** If set true, print additional output. */
private boolean showAll;
/** Simulated clock. */
private int clock = 0;
/** Time that the agent will be done with the current passenger.*/ 
private int timeDone;
/** Number of frequent flyers served since the
last regular passenger was served. */ 
private int frequentFlyersSinceRP;


private void runSimulation() {
        for (clock = 0; clock < totalTime; clock++) {
            frequentFlyerQueue.checkNewArrival(clock, showAll);
            regularPassengerQueue.checkNewArrival(clock, showAll);
            if (clock >= timeDone) {
                startServe();
        }
    } 
}
private void startServe() {
    if (!frequentFlyerQueue.isEmpty() && ((frequentFlyersSinceRP <= frequentFlyerMax) || regularPassengerQueue.isEmpty())) {
        // Serve the next frequent flyer. 
        frequentFlyersSinceRP++;
        timeDone = frequentFlyerQueue.update(clock, showAll);
    } 
    else if (!regularPassengerQueue.isEmpty()) {
    // Serve the next regular passenger. 
        frequentFlyersSinceRP = 0;
        timeDone = regularPassengerQueue.update(clock, showAll);
    } 
    else if (showAll) {
        System.out.println("Time is " + clock + " server is idle");
    }
} 
//
private void enterData() {
    try (Scanner myObj = new Scanner(System.in)) {
        System.out.println("Expected number of frequent flyer arrivals per hour: ");
        frequentFlyerQueue.arrivalRate = myObj.nextDouble()/60;
//15
        System.out.println("Expected number of regular passenger arrivals per hour: ");
        regularPassengerQueue.arrivalRate = myObj.nextDouble()/60;
//30
        System.out.println("The maximum number of frequent flyers served between regular passengers: ");
        frequentFlyerMax = myObj.nextInt();
//5
        System.out.println("Enter Max Processing Time In Minutes: ");
        maxProcessingTime = myObj.nextInt();
        Passenger.setMaxProcessingTime(maxProcessingTime);
//4
        System.out.println("Total Simulation Time In Minutes: ");
        totalTime = myObj.nextInt();
//10
        System.out.println("Display Minute By Minute Trace (Y/N): \n");
        char input = myObj.next().charAt(0);
//y
        if(input == 'y') {
            //true
            showAll = true;
        }
        else if(input == 'n') {
            //false
            showAll = false;
        }
        else {
            System.out.println("Invalid Input . . .");
        }
    }

}
//
/** Method to show the statistics. */ 
private void showStats() {
    System.out.println("\nThe number of regular passengers served was " + regularPassengerQueue.getNumServed());
double averageWaitingTime = (double) regularPassengerQueue.getTotalWait() / (double) regularPassengerQueue.getNumServed();
    System.out.println(" with an average waiting time of " + averageWaitingTime);
    System.out.println("The number of frequent flyers served was " + frequentFlyerQueue.getNumServed());
averageWaitingTime = (double) frequentFlyerQueue.getTotalWait() / (double) frequentFlyerQueue.getNumServed();
    System.out.println(" with an average waiting time of " + averageWaitingTime);
    System.out.println("Passengers in frequent flyer queue: " + frequentFlyerQueue.size());
    System.out.println("Passengers in regular passenger queue: " + regularPassengerQueue.size());
}
    public static void main(String args[]) {
        AirlineCheckinSim sim = new AirlineCheckinSim();
        sim.enterData();
        sim.runSimulation();
        sim.showStats();
        System.exit(0);
    }
}
