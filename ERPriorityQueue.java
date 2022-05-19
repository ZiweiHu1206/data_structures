import java.util.ArrayList;
import java.util.HashMap;

public class ERPriorityQueue{

	public ArrayList<Patient>  patients;
	public HashMap<String,Integer>  nameToIndex;

	public ERPriorityQueue(){

		//  use a dummy node so that indexing starts at 1, not 0

		patients = new ArrayList<Patient>();
		patients.add( new Patient("dummy", 0.0) );

		nameToIndex  = new HashMap<String,Integer>();
	}

	private int parent(int i){
		return i/2;
	}

	private int leftChild(int i){
	    return 2*i;
	}

	private int rightChild(int i){
	    return 2*i+1;
	}

    
	
	public void swap(int i, int j) {
		if ((i < this.patients.size()) && (j < this.patients.size())) {
			Patient patient1 = this.patients.get(i);
			Patient patient2 = this.patients.get(j);
			String patient1name = patient1.name;
			String patient2name = patient2.name;
			this.patients.set(j, patient1);
			this.patients.set(i, patient2);
			this.nameToIndex.replace(patient1name, j);
			this.nameToIndex.replace(patient2name, i)
			
			;
		}
	}
	
	
	public boolean isEmpty() {
		if (this.patients.size() <= 1) {
			return true;
		}
		return false;
	}
	
	
	public boolean isLeaf(int i) {
		if (leftChild(i) >= this.patients.size()) {
			return true;
		}
		return false;
	}

	
	public void upHeap(int i){
		while( (i > 1) && (this.patients.get(i).priority < this.patients.get(parent(i)).priority)) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

	
	public void downHeap(int i){
		while (leftChild(i) < this.patients.size()) {
        	int child = leftChild(i);
        	if (leftChild(i) < this.patients.size() - 1) {
        		if (this.patients.get(rightChild(i)).priority < this.patients.get(leftChild(i)).priority) {
        			child = child + 1;
        		}
        	}
        	if (this.patients.get(child).priority < this.patients.get(i).priority) {
        		swap(i, child);
        		i = child;
        	}else {
        		return;
        	}
        }

	}

	
	public boolean contains(String name){
        if (this.nameToIndex.get(name) != null) {
        	return true;
        }
        return false;
	}

	
	public double getPriority(String name){
        if (contains(name) == true) {
        	int index = this.nameToIndex.get(name);
        	double priority = this.patients.get(index).priority;
        	return priority;
        }
        return -1;
	}

	
	public double getMinPriority(){
        if (isEmpty() != true) {
        	double minPriority = this.patients.get(1).priority;
        	return minPriority;
        }
        return -1;
	}

	
	public String removeMin(){
        if(isEmpty() == false) {
        	String minName = peekMin();
        	int lastIndex = this.patients.size() - 1;
        	swap(1, lastIndex);
        	String nameLastIndex = this.patients.get(lastIndex).name;
        	this.patients.remove(lastIndex);
        	this.nameToIndex.remove(nameLastIndex);
        	
        	downHeap(1);
        	
        	return minName;
        }
        return null;
	}

	
	public String peekMin(){
        if (isEmpty() != true) {
        	String minName = this.patients.get(1).name;
        	return minName;
        }
        return null;
	}

	
	/*
	 * There are two add methods.  The first assumes a specific priority.
	 * The second gives a default priority of Double.POSITIVE_INFINITY
	 *
	 * If the name is already there, then return false.
	 */

	public boolean  add(String name, double priority){
        if(contains(name) == false) {
        	Patient newPatient = new Patient(name, priority);
        	this.patients.add(newPatient);
        	upHeap(this.patients.size() - 1);
        	int index = this.patients.indexOf(newPatient);
        	this.nameToIndex.put(name, index);
        
        	return true;
        }
        return false;
	}

	
	public boolean  add(String name){
        if(contains(name) == false) {
        	Patient newPatient = new Patient(name, Double.POSITIVE_INFINITY);
        	this.patients.add(newPatient);
        	int index = this.patients.size() - 1;
        	this.nameToIndex.put(name, index);
        	
        	return true;
        }
		return false;
	}

	
	public boolean remove(String name){
        if(contains(name) == true) {
        	
        	int index = this.nameToIndex.get(name);
        	int lastIndex = this.patients.size() - 1;
        	
        	swap(index, lastIndex);
        	
        	this.patients.remove(lastIndex);
        	this.nameToIndex.remove(name);
        	
        	if(index == lastIndex) {
        		return true;
        	}
        	
        	downHeap(index);
        	
            
        	return true;
        }
        return false;
	}

	
	
	/*
	 *   If new priority is different from the current priority then change the priority
	 *   (and possibly modify the heap).
	 *   If the name is not there, return false
	 */

	public boolean changePriority(String name, double priority){
		if (contains(name) == true) {
			
			int index = this.nameToIndex.get(name);
			Patient patient = this.patients.get(index);
			patient.setPriority(priority);
			this.patients.set(index, patient);
			
			if (index > 1) {
				if(this.patients.get(index).priority < this.patients.get(parent(index)).priority) {
					upHeap(index);
			    }
        	}
			
			int newIndex = this.nameToIndex.get(name);
        	downHeap(newIndex);
        	
			return true;
		}
       
        return false;
	}

	
	public ArrayList<Patient> removeUrgentPatients(double threshold){
		ArrayList<Patient> patientsRemoved = new ArrayList<Patient>();
		
        for(int i = 1; i < this.patients.size(); i++) {
        	
        	if(this.patients.get(i).priority <= threshold) {
        		patientsRemoved.add(this.patients.get(i));
        	}
        }
        
        for(Patient aPatient : patientsRemoved) {
        	String name = aPatient.getName();
        	this.remove(name);	
        	
        }
        
        return patientsRemoved;
	}

	
	public ArrayList<Patient> removeNonUrgentPatients(double threshold){
		ArrayList<Patient> patientsRemoved = new ArrayList<Patient>();
		
		for(int i = 1; i < this.patients.size(); i++) {
			if(this.patients.get(i).priority >= threshold) {
        		patientsRemoved.add(this.patients.get(i));
        	}
        }
	
        for(Patient aPatient : patientsRemoved) {
        	String name = aPatient.getName();
        	this.remove(name);	
        	
        }
        
        return patientsRemoved;
	}



	static class Patient{
		private String name;
		private double priority;

		Patient(String name,  double priority){
			this.name = name;
			this.priority = priority;
		}

		Patient(Patient otherPatient){
			this.name = otherPatient.name;
			this.priority = otherPatient.priority;
		}

		double getPriority() {
			return this.priority;
		}

		void setPriority(double priority) {
			this.priority = priority;
		}

		String getName() {
			return this.name;
		}

		@Override
		public String toString(){
			return this.name + " - " + this.priority;
		}

		public boolean equals(Object obj){
			if (!(obj instanceof  ERPriorityQueue.Patient)) return false;
			Patient otherPatient = (Patient) obj;
			return this.name.equals(otherPatient.name) && this.priority == otherPatient.priority;
		}

	}
}
