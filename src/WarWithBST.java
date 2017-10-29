// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;


public class WarWithBST
{
	// member fields and methods
	BinaryST _bst;
	int _k;
	
	public WarWithBST(String[] s, int k)
	{
		// implementation
		_bst = new BinaryST(s);
		_k = k;
	}
	
	public ArrayList<String> compute2k()
	{
		// implementation
		ArrayList<String> result = new ArrayList<String>();
		String[] inOrder = _bst.inOrder();
		for(int i=0; i< inOrder.length; i++){
			if(i+1 != inOrder.length){
				for(int j=i+1; j< inOrder.length;j++){
					boolean addToResult = false;
					String resultString = inOrder[i] + inOrder[j];
					for(int p = 1; p< resultString.length() -1 ; p++){
						String substringToTest = resultString.substring(p, p+_k+1);
						boolean substringIsInArray = _bst.search(substringToTest);
						if(!substringIsInArray){
							break;
						}else{
							addToResult = true;
						}
					}
					if(addToResult){
						result.add(inOrder[i]+inOrder[j]);
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

