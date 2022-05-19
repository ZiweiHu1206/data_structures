import java.util.*;

public class Discussion_board {
	
	public static ArrayList<String> Discussion_Board(String[] posts){
		
		HashMap<String, Integer> word_frequency = new HashMap<String, Integer>();
		
		HashMap<String, ArrayList<String>> user_word = new HashMap<String, ArrayList<String>>();
		
		TreeMap<Integer, ArrayList<String>> frequency_comparison = new TreeMap<Integer, ArrayList<String>>();
		
		ArrayList<String> word_list_total = new ArrayList<String>();
		
		ArrayList<String> new_word_list = new ArrayList<String>();
		
		ArrayList<String> users_total = new ArrayList<String>();
		
		ArrayList<String> return_array = new ArrayList<String>();
		
		
		/* iterate through the posts to get each message */
		for (String message : posts) {
			String[] message_split = message.split(" ");
			String user = message_split[0];
				
			if (! users_total.contains(user)) {
				users_total.add(user);
			}
			
			if ( ! user_word.containsKey(user)){
				user_word.put(user, new ArrayList<String>());
			}
			
			ArrayList<String> word_list_each_user = user_word.get(user);
			
			
			/* iterate through the message to get the words used by each user */
			for (int i = 1 ; i < message_split.length ; i++) {
				
				String word = message_split[i];
				
				if (! word_list_each_user.contains(word)) {
					word_list_each_user.add(word);
				}
					
				if (! word_list_total.contains(word)) {
					word_list_total.add(word);
				}
					
				/* update the frequency of each word */
				if (! word_frequency.containsKey(word)) {
					word_frequency.put(word, 0);
				}
				
				word_frequency.put(word, word_frequency.get(word) + 1);
				
			}
			
			user_word.put(user, word_list_each_user);
		}
			
			
		
		
		
		/* remove the word that is not used by every user  */
		for (String word : word_list_total) {
			boolean removed = false;
			for (String user : users_total ) {
				ArrayList<String> word_list_user = user_word.get(user);
				
				if( ! word_list_user.contains(word) ) {
					word_frequency.remove(word);
					removed = true;
					break;
				}
			}
			if (removed == false) {
				new_word_list.add(word);
			}
		}
		
		
		
		for (String word : new_word_list) {
			Integer frequency = word_frequency.get(word);
			
			if (! frequency_comparison.containsKey(frequency)) {
				frequency_comparison.put(frequency, new ArrayList<String>());
			}
			
			if(frequency_comparison.containsKey(frequency)) {
				ArrayList<String> word_same_frequency = frequency_comparison.get(frequency);
				word_same_frequency.add(word);
				frequency_comparison.put(frequency, word_same_frequency);
			}
		}
		
		Collections.reverse(return_array);
		
		for(Map.Entry<Integer, ArrayList<String>> entry: frequency_comparison.entrySet() ) {
			ArrayList<String> word_each_frequency = new ArrayList<>(entry.getValue());
			Collections.sort(word_each_frequency);
			Collections.reverse(word_each_frequency);
			for(int i = 0; i < word_each_frequency.size(); i++) {
				return_array.add(word_each_frequency.get(i));
			}
			
		}
		
		Collections.reverse(return_array);
		
		return return_array;
		
	}


	
	


}





