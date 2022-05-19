import java.io.*;
import java.io.IOException;
import java.util.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import org.knowm.xchart.*;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.style.markers.SeriesMarkers;


public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {
         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
     }
     
     
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
     
     /**Implements the normal hash function using multiplication method**/
     public int hash_function (int key) {
         int left_part = (this.A * key) % power2(this.w);
         int right_part = this.w - this.r;
         return ( left_part >> right_part );
     }
     
     /**Implements the linear probing hash function g(k)*/
     public int probe(int key, int i) {
        return ( hash_function(key) + i ) % power2(this.r);
     }
     
     
     
      
     
     /** Search for key k, returns the its value to which the specified key is mapped, 
      * or -1 if this map contains no mapping for the key. **/
     public int searchKey(int key) {
    	 for ( int i = 0; i < m ; i++) {
         	if (Table[probe(key, i)] == key) {
         		return key;
         	}
         	
    	 }	 
    	 return -1;
     }
     
    
     
     /**Inserts key k into hash table. */
        public void insertKey(int key) {
        	boolean inserted = false;
        	
            for ( int i = 0; i < m ; i++) {
            	
            	if (Table[probe(key, i)] == -1 || Table[probe(key, i)] == -2) {
            		Table[probe(key, i)] = key;
            		inserted = true;
            	}
            	
            	if (inserted == true) {
            		break;
            	}
            }
        }
       
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public void insertKeyArray (int[] keyArray){
            for (int key: keyArray) {
                insertKey(key);
            } 
        }
    
        
         /**Remove key k from hash table. Returns the number of collisions encountered*/
        public void removeKey(int key){
        	boolean found = false;
            for (int i = 0; i < m; i++) {
            	if (Table[probe(key, i)] == key) {
            		Table[probe(key, i)] = -2;
            		found = true;
            	}else {
            		continue;
            	}
            	if (found == true) {
            		break;
            	}
            } 
        }
        
        
        // measure the time spent by the algorithm for each number of elements inserted
      
        
        
      
}




