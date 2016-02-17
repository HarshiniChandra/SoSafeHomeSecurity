import java.util.Arrays;
/*
Two strings are called anagrams if they contain same set of characters but in different order. 
For example,“Dormitory – Dirty Room”, “keep – peek”,  “School Master – The Classroom” are some anagrams.
*/
public class Anagram {

	public static boolean anagram(String s1, String s2) {
		/*if the lengths of two string doesnot match or
		 *the any one of the string lenght is 0 then return false
		 */
		if(s1.length() == 0 || s2.length() == 0 || s1.length() != s2.length()) {
			return false;
		}

		
		//convert the given Strings to char array, 
		char[] s1a = s1.toCharArray();
		char[] s2a = s2.toCharArray();

		// Sort one char array
		Arrays.sort(s1a);

		int j = 0;
		for(int i = 0; i < s1a.length; i++) {
			if(s1a[i] == s2a[j]) {
				j++;
				return true;
			}

		}
		return false;


	}

}