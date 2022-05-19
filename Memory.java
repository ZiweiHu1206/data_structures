
import java.util.LinkedList;

public class Memory {
	//This class defines a memory array and a linked list of objects.
	//This class has various methods for manipulating string in the memory array.
	
	//fields
	public LinkedList<StringInterval> intervalList;
	public char[] memoryArray;
	public static int idCount;
	
	//constructor
	public Memory(int length) {
		this.intervalList = new LinkedList<StringInterval>();
		this.memoryArray = new char[length];
		idCount = 0;
		
	}
	
	
	
	//helper method to get the string in the array by each StringInterval
	public String getStringByInterval(StringInterval interval) {
		int startIndex = interval.start;
		int lengthString = interval.length;
		int endIndex = startIndex + lengthString;
		String stringReturn = "";
		
		//iterate through the array at right position to get string
		for( int i = startIndex; i < endIndex; i++) {
			stringReturn += memoryArray[i];
		}
		
		return stringReturn;
		
	}
	
	
	
	//method to get string from the array by passing an id value
	public String get(int id) {
		if (id > idCount) {
			return null;
		} else {
			//iterate through intervalList to check the id 
			for (StringInterval interval : intervalList) {
				
				if (interval.id == id ) {
					return getStringByInterval(interval);
				}
			}
			//if no such id has been found, return null
			return null;
		}
	}
	
	
	
	//method to get id of the string passing through 
	public int get(String s) {
		for (StringInterval interval : intervalList) {
			if (interval.length == s.length()) {
				String tmpString = getStringByInterval(interval);
				
				if (s.equals(tmpString)){
					return interval.id;
				}
			}
		}
		return -1;
	}
	
	
	
	//method to remove the StringInterval with the id passing through
	public String remove(int id) {
		if(get(id) == null) {
			return null;
		}else {
			String s = get(id);
			
			int index = 0;
			for (StringInterval interval : intervalList) {
				if (interval.id == id) {
					break;
				}
				index ++;
			}
			intervalList.remove(index);
			return s;
		}
	}
	
	
	
	//method to remove the StringInterval with the string passing through
	public int remove(String s) {
		if(get(s) != -1) {
			int id = get(s);
			
			int index = 0;
			for (StringInterval interval : intervalList) {
				if (interval.id == id) {
					break;
				}
				index ++;
			}
			intervalList.remove(index);
			return id;
		}
		return -1;
	}
	
	
	
	//method to put string into an empty space
	public int put(String s) {
		if (memoryArray.length >= s.length()) {
			
			//create a copy of memoryArray to check the gap
			char[] realStringArray = new char[memoryArray.length];
			for (StringInterval interval : intervalList) {
				int startIndex = interval.start;
				int lengthString = interval.length;
				int endIndex = startIndex + lengthString;
				
				for( int k = startIndex; k < endIndex; k++) {
					realStringArray[k] = 'a'; //make the slot not to be null
				}
			}
			
			for (int index = 0; index <= memoryArray.length - s.length(); index ++) {
				int countEmpty = 0;
				
				for (int tmpIndex = index; tmpIndex < index + s.length(); tmpIndex ++) {
					if (realStringArray[tmpIndex] == '\u0000') {
						countEmpty += 1;
					}
					
				if (countEmpty == s.length()) {
					for (int i = 0; i < s.length(); i++) {
						memoryArray[i + index] = s.charAt(i);
					}
					int newId = idCount;
					StringInterval newInterval = new StringInterval(newId,index,s.length());
					intervalList.add(newInterval);
					idCount ++;
					return newId;
				}
				}
			}
			
			defragment();
			
			char[] realStringArray2 = new char[memoryArray.length];
			for (StringInterval interval : intervalList) {
				int startIndex = interval.start;
				int lengthString = interval.length;
				int endIndex = startIndex + lengthString;
				
				for( int k = startIndex; k < endIndex; k++) {
					realStringArray2[k] = 'a'; //make the slot not to be null
				}
			}
			
			for (int index = 0; index <= memoryArray.length - s.length(); index ++) {
				int countEmpty = 0;
				
				for (int tmpIndex = index; tmpIndex < index + s.length(); tmpIndex ++) {
					if (realStringArray2[tmpIndex] == '\u0000') {
						countEmpty += 1;
					}
					
				if (countEmpty == s.length()) {
					for (int i = 0; i < s.length(); i++) {
						memoryArray[i + index] = s.charAt(i);
					}
					int newId = idCount;
					StringInterval newInterval = new StringInterval(newId,index,s.length());
					intervalList.add(newInterval);
					idCount ++;
					return newId;
				}
				}
			}
			
		}
		return -1;
	}
	
	
	
	//remove all the gap between characters
	public void defragment() {
		//sort the intervalList with increasing order of start
		for (int i = 0; i < intervalList.size() - 1; i++) {
			boolean sorted = true;
			
			for (int k = 0; k < intervalList.size() - 1 - i; k++) {
				if (intervalList.get(k).start > intervalList.get(k+1).start) {
					StringInterval tmp1 = intervalList.get(k);
					StringInterval tmp2 = intervalList.get(k+1);
					intervalList.set(k, tmp2);
					intervalList.set(k+1, tmp1);
					sorted = false;
				}
			}
			if (sorted) {
				break;
			}
		}
		
		char[] arrayWithoutGap = new char[memoryArray.length];
		
		int startingIndex = 0;
		for (StringInterval interval : intervalList) {
			for (int j = 0; j < getStringByInterval(interval).length(); j++) {
				arrayWithoutGap[j + startingIndex] = getStringByInterval(interval).charAt(j);
			}
			interval.start = startingIndex;
			startingIndex += interval.length;
		}
		
		memoryArray = arrayWithoutGap;

		
	}


	
    
	public class StringInterval{
		
		//fields that describe a location in memory where the string is stored
		public int id;          
		public int start;       
		public int length;
		
		
		//constructor
		public StringInterval(int id, int start, int length){
			this.id = id;
			this.start = start;
			this.length = length;
			
		}
	}

}
