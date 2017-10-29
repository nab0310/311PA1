// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class WarWithHash
{
	// member fields and methods
	HashSet<String> _hash;
	int _k;
	
	public WarWithHash(String[] s, int k)
	{
		// implementation
		_hash = new HashSet<String>();
		for(int i=0;i<s.length;i++){
			_hash.add(s[i]);
		}
		_k = k;
	}
	
	public ArrayList<String> compute2k()
	{
		// implementation
		ArrayList<String> result = new ArrayList<String>();
		Iterator<String> itr =_hash.iterator();
		while (itr.hasNext()) {
			String string1 = itr.next();
			if(itr.hasNext()){
				boolean addToResult = false;
				String string2 = itr.next();
				String resultString = string1 + string2;
				for(int p = 1; p< resultString.length() -1 ; p++) {
					String substringToTest = resultString.substring(p, p + _k + 1);
					boolean substringIsInArray = _hash.contains(substringToTest);
					if(!substringIsInArray){
						break;
					}else{
						addToResult = true;
					}
				}
				if(addToResult){
					result.add(resultString);
				}
			}else{
				break;
			}
		}
		return result;
	}

	public static void main(String[] args){
		String[] input = {"AB", "CD", "EF", "BC"};
		WarWithArray wwa = new WarWithArray(input,1);
		ArrayList<String> result = wwa.compute2k();
		for(int i=0; i<result.size();i++){
			System.out.print(result.get(i)+ " ");
		}
	}
}

