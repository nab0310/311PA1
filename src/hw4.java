import com.sun.org.apache.xpath.internal.operations.Neg;
import com.sun.xml.internal.bind.api.impl.NameConverter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nick on 10/23/17.
 */
public class hw4 {
    public static class Pair {
        public int max, second;
        public Pair(){
            max = second = 0;
        }
    }

    public static class BinarySearchTree {
        public Node root;
        public BinarySearchTree(){
            this.root = null;
        }

        public boolean find(int id){
            Node current = root;
            while(current!=null){
                if(current.data==id){
                    return true;
                }else if(current.data>id){
                    current = current.left;
                }else{
                    current = current.right;
                }
            }
            return false;
        }
        public boolean delete(int id){
            Node parent = root;
            Node current = root;
            boolean isLeftChild = false;
            while(current.data!=id){
                parent = current;
                if(current.data>id){
                    isLeftChild = true;
                    current = current.left;
                }else{
                    isLeftChild = false;
                    current = current.right;
                }
                if(current ==null){
                    return false;
                }
            }
            //if i am here that means we have found the node
            //Case 1: if node to be deleted has no children
            if(current.left==null && current.right==null){
                if(current==root){
                    root = null;
                }
                if(isLeftChild ==true){
                    parent.left = null;
                }else{
                    parent.right = null;
                }
            }
            //Case 2 : if node to be deleted has only one child
            else if(current.right==null){
                if(current==root){
                    root = current.left;
                }else if(isLeftChild){
                    parent.left = current.left;
                }else{
                    parent.right = current.left;
                }
            }
            else if(current.left==null){
                if(current==root){
                    root = current.right;
                }else if(isLeftChild){
                    parent.left = current.right;
                }else{
                    parent.right = current.right;
                }
            }else if(current.left!=null && current.right!=null){

                //now we have found the minimum element in the right sub tree
                Node successor	 = getSuccessor(current);
                if(current==root){
                    root = successor;
                }else if(isLeftChild){
                    parent.left = successor;
                }else{
                    parent.right = successor;
                }
                successor.left = current.left;
            }
            return true;
        }

        public Node getSuccessor(Node deleleNode){
            Node successsor =null;
            Node successsorParent =null;
            Node current = deleleNode.right;
            while(current!=null){
                successsorParent = successsor;
                successsor = current;
                current = current.left;
            }
            //check if successor has the right child, it cannot have left child for sure
            // if it does have the right child, add it to the left of successorParent.
//		successsorParent
            if(successsor!=deleleNode.right){
                successsorParent.left = successsor.right;
                successsor.right = deleleNode.right;
            }
            return successsor;
        }
        public void insert(int id, int position){
            Node newNode = new Node(id, position);
            if(root==null){
                root = newNode;
                return;
            }
            Node current = root;
            Node parent = null;
            while(true){
                parent = current;
                if(id<current.data){
                    current = current.left;
                    if(current==null){
                        parent.left = newNode;
                        return;
                    }
                }else{
                    current = current.right;
                    if(current==null){
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    static class Node{
        int data;
        Node left;
        Node right;
        int position;
        public Node(int data, int position){
            this.data = data;
            this.position = position;
            left = null;
            right = null;
        }
    }

    public static Pair getLargestAndSecond(int arr[], int low, int high){
        Pair result = new Pair();
        Pair lowerHalf = new Pair();
        Pair upperHalf = new Pair();
        int mid;

        if(low == high) {
            result.max = arr[low];
            return result;
        }

        if(high == low +1){
            if(arr[low] > arr[high]) {
                result.max = arr[low];
                result.second = arr[high];
            }else{
                result.max = arr[high];
                result.second = arr[low];
            }
        }
        mid = (low + high) /2;
        lowerHalf = getLargestAndSecond(arr, low, mid);
        upperHalf = getLargestAndSecond(arr, mid+1, high);

        int max, second;

        max = Math.max(lowerHalf.max, upperHalf.max);
        if(max == lowerHalf.max){
            second = Math.max(upperHalf.max, lowerHalf.second);
        }else{
            second = Math.max(lowerHalf.max, upperHalf.second);
        }

        result.max = max;
        result.second = second;

        return result;
    }

    public static ArrayList<Integer> betterSortKSortedArray(int arr[], int k){
        ArrayList<Integer> result = new ArrayList<>();
        BinarySearchTree bst = new BinarySearchTree();
        for(int i=1; i<=k;i++){
            bst.insert(arr[arr.length - i], arr.length-i);
        }

        while(bst.root!=null){
            if(bst.root.right != null){
                result.add(bst.root.right.data);
                if(bst.root.right.position - k >= 0){
                    bst.insert(arr[bst.root.right.position - k], bst.root.right.position - k);
                }
                bst.delete(bst.root.right.data);
            }else{
                result.add(bst.root.data);
                if(bst.root.position - k >= 0){
                    bst.insert(arr[bst.root.position - k], bst.root.position - k);
                }
                bst.delete(bst.root.data);
            }
        }
        return result;
    }

    public static ArrayList<Integer> sortKSortedArray(int arr[], int k){
        ArrayList<ArrayList<Integer>> listOfArrays = new ArrayList<>();
        for(int i=0; i< k; i++){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int j=i;j<arr.length;j=j+k){
                temp.add(arr[j]);
            }
            listOfArrays.add(temp);
        }
        if(listOfArrays.size() == 1){
            return listOfArrays.get(1);
        }else if(listOfArrays.size() == 2){
            return merge(listOfArrays.get(0), listOfArrays.get(1));
        }else{
            ArrayList<Integer> result = merge(listOfArrays.get(0), listOfArrays.get(1));
            for(int i=2;i<listOfArrays.size();i++){
                //Merge arrays start from the end of arrays
                result = merge(result, listOfArrays.get(i));
            }
            return result;
        }
    }

    public static ArrayList<Integer> merge(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
        ArrayList<Integer> result = new ArrayList<>();
        int p1 ,p2;
        p1 = arr1.size()-1;
        p2 = arr2.size()-1;
        while(p1 >= 0 && p2 >= 0){
            if(arr1.get(p1) > arr2.get(p2)){
                result.add(arr1.get(p1));
                p1--;
            }else{
                result.add(arr2.get(p2));
                p2--;
            }
        }
        while(p2 >= 0){
                result.add(arr2.get(p2));
                p2--;
        }while(p1 >= 0){
                result.add(arr1.get(p1));
                p1--;
        }
        return result;
    }

    public static int Sumij(int[] a){
        int bestSoFar = Integer.MIN_VALUE, bestNow = 0;

        for (int i = 0; i < a.length; i++)
        {
            bestNow = bestNow + a[i];
            if (bestSoFar < bestNow) {
                bestSoFar = bestNow;
            }
            if (bestNow < 0) {
                bestNow = 0;
            }
        }
        return bestSoFar;
    }

    public static void main(String[] args){
        int[] arr = {2,2,3,1,5,6};
        Pair hi = new Pair();
        hi = getLargestAndSecond(arr, 0, arr.length-1);

        System.out.println("High: "+hi.max+". Second: "+hi.second);

        int[] k2arr = {2,6,3,12,56,14};
        //What does having a k-sorted array do for us.
        //well, we know that a[1], a[1+k], a[1+k+k], a[1+k+k+k] is sorted for us....
        //We have a case of multiple sorted arrays, all we have to do is merge them.
        ArrayList<Integer> result = sortKSortedArray(k2arr,2);
        for(int i=0; i<result.size();i++){
            System.out.print(result.get(i) + " ");
        }

        System.out.println();

        result = betterSortKSortedArray(k2arr, 2);

        for(int i=0; i<result.size();i++){
            System.out.print(result.get(i) + " ");
        }

        System.out.println();

        int[] array = {-1, -2, -3, -13, -14};
        int resultOfSumIJ = Sumij(array);
        System.out.println(resultOfSumIJ);
    }
}
