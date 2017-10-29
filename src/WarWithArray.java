// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.lang.reflect.Array;
import java.util.ArrayList;


public class WarWithArray
{
	// member fields and methods
	private String[] _s;
	private int _k;
	
	public WarWithArray(String[] s, int k)
	{
		// implementation
		_s = mergeSort(s);
		_k = k;
	}

	public static String[] mergeSort(String[] list) {
		String [] sorted = new String[list.length];
		if (list.length == 1) {
			sorted = list;
		} else {
			int mid = list.length/2;
			String[] left = null;
			String[] right = null;
			if ((list.length % 2) == 0) {
				left = new String[list.length/2];
				right = new String[list.length/2];
			} else {
				left = new String[list.length/2];
				right = new String[(list.length/2)+1];
			}
			int x=0;
			int y=0;
			for ( ; x < mid; x++) {
				left[x] = list[x];
			}
			for ( ; x < list.length; x++) {
				right[y++] = list[x];
			}
			left = mergeSort(left);
			right = mergeSort(right);
			sorted = mergeArray(left,right);
		}

		return sorted;
	}

	private static String[] mergeArray(String[] left, String[] right) {
		String[] merged = new String[left.length+right.length];
		int lIndex = 0;
		int rIndex = 0;
		int mIndex = 0;
		int comp = 0;
		while (lIndex < left.length || rIndex < right.length) {
			if (lIndex == left.length) {
				merged[mIndex++] = right[rIndex++];
			} else if (rIndex == right.length) {
				merged[mIndex++] = left[lIndex++];
			} else {
				comp = left[lIndex].compareTo(right[rIndex]);
				if (comp > 0) {
					merged[mIndex++] = right[rIndex++];
				} else if (comp < 0) {
					merged[mIndex++] = left[lIndex++];
				} else {
					merged[mIndex++] = left[lIndex++];
				}
			}
		}
		return merged;
	}

	public ArrayList<String> compute2k()
	{
		// implementation
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0; i< _s.length; i++){
			if(i+1 != _s.length){
				for(int j=i+1; j< _s.length;j++){
					boolean addToResult = false;
					String resultString = _s[i] + _s[j];
					for(int p = 1; p< resultString.length() -1 ; p++){
						String substringToTest = resultString.substring(p, p+_k+1);
						boolean substringIsInArray = false;
						for(int l =0; l<_s.length;l++){
							if(substringToTest.compareTo(_s[l]) == 0){
								substringIsInArray = true;
								break;
							}
						}
						if(!substringIsInArray){
							break;
						}else{
							addToResult = true;
						}
					}
					if(addToResult){
						result.add(_s[i]+_s[j]);
					}
				}
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

