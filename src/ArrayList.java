public class ArrayList <T extends Comparable<T>> implements List<T>{

    private T[] a;
    private boolean isSorted;
    private int sizeOfList;

    public ArrayList(){
        a = (T[]) new Comparable [2];
        isSorted = true;
    }
    /**
     * Add an element to end of the list. If element is null,
     * it will NOT add it and return false.  Otherwise, it
     * will add it and return true. Updates isSorted to false if
     * the element added breaks sorted order.
     *
     * @param element element to be added to the list.
     * @return if the addition was successful.
     */
    public boolean add(T element) {
        if(element == null)
            return false;

        if(sizeOfList == a.length) // that means the list is full and u have to resize list
            a = resizeList();

        a[sizeOfList++] = element;
        isSorted = isSorted();
        return true;
    }

    /**
     * Add an element at specific index. This method should
     * also shift the element currently at that position (if
     * any) and any subsequent elements to the right (adds
     * one to their indices). If element is null, or if index
     * index is out-of-bounds (index < 0 or index >= size_of_list),
     * it will NOT add it and return false. Otherwise it will
     * add it and return true. See size() for the definition
     * of size of list. Also updates isSorted variable to false if the
     * element added breaks the current sorted order.
     *
     * @param index index at which to add the element.
     * @param element element to be added to the list.
     * @return if the addition was successful.
     */
    public boolean add(int index, T element) {
        int count = sizeOfList-1;

        if(element == null || index >= sizeOfList || index < 0)
            return false;

        if(sizeOfList == a.length)
            a = resizeList();

        // loop and move everything to the right
        while(count >= index){
            a[count+1] = a[count];
            count--;
        }

        // then add the element in
        a[index] = element;

        isSorted = isSorted();
        sizeOfList++;
        return true;
    }

    public T[] resizeList(){
        T[] newList = (T[]) new Comparable[sizeOfList*2+1];
        System.arraycopy(a,0,newList,0,sizeOfList);
        return newList;
    }

    /**
     * Remove whatever is at index 'index' in the list and return
     * it. If index is out-of-bounds, return null. For the ArrayList,
     * elements to the right of index should be shifted over to maintain
     * contiguous storage. Must check to see if the list is sorted after removal
     * of the element at the given index and updates isSorted accordingly.
     *
     * @param index position to remove from the list.
     * @return the removed element.
     */
    public T remove(int index) {
        T[] newList = (T[]) new Comparable[a.length];

        if(index > sizeOfList || index < 0) {
            return null;
        }

        T data = a[index];

        if(index == 0){
            System.arraycopy(a, 1, newList, 0, sizeOfList);// if idx 0 then u have to start copying from the 2nd element of this array
        }
        else {
            System.arraycopy(a, 0, newList, 0, index);    // copies everythings until index
            System.arraycopy(a, index + 1, newList, index, sizeOfList - index); // copies everything after the index
        }
        sizeOfList--;
        a = newList;
        isSorted = isSorted();
        return data;
    }

    public void clear() {
        sizeOfList = 0;
        isSorted = true;
        a = (T[]) new Comparable[2];
    }

    /**
     * Return the element at given index. If index is
     * out-of-bounds, it will return null.
     *
     * @param index index to obtain from the list.
     * @return the element at the given index.
     */
    public T get(int index) {
        if(index >= sizeOfList || index<0)
            return null;
        return a[index];
    }

    /**
     * Return the first index of element in the list. If element
     * is null or not found in the list, return -1. If isSorted is
     * true, uses the ordering of the list to increase the efficiency
     * of the search.

     * @param element element to be found in the list.
     * @return first index of the element in the list.
     */
    public int indexOf(T element) {
        if(element == null)
            return -1;
        for(int i = 0; i<sizeOfList; i++){          // loop until u find the element
            if(a[i] == element)
                return i;
        }
        return -1;  // retrun -1 when we didnt find it
    }

    public boolean isEmpty() {
        if(sizeOfList == 0)
            return true;
        return false;
    }

    public int size() {
        return sizeOfList;
    }

    public void sort() {
        T s;
        int k;
        if(isSorted == true)
            return;
        for (int i = 1; i < sizeOfList; i++) {
            s = a[i];
            k = i;
            for (int j = i-1; j >= 0; j--) {
                if(s.compareTo(a[j]) <= 0) {
                    T temp = a[j];
                    a[j] = a[j+1];
                    a[j + 1] = temp;
                    k = j;
                }
            }
            a[k] = s;
        }
    }



    /**
     * Removes all elements of the list that are not equal to 'element'. If element is null, don't do anything.
     * When this function returns, the only elements that should be left in this list
     * are equal to 'element'. This method should not change the ordering of the list.
     * If the list is sorted, use this fact to increase the efficiency of this method.
     * This method should be done IN PLACE. Do not use any extra data structures to
     * solve this problem. (You are NOT allowed to create a new array for this function).
     * Updates isSorted accordingly.
     *
     * @param element type of element to be kept in the list.
     */
    public void equalTo(T element) {
        int i = 0;
        if(element == null){
            return;
        }
        else {
            while(i<sizeOfList) {
                if (a[i] != element) {      //remove one element at a time if it doesnt == to element
                    remove(i);
                }
                else
                    i++;
            }
        }
        isSorted = isSorted();
    }


    public void reverse() {
        int count = sizeOfList-1;
        for(int i = 0; i<sizeOfList/2; i++){              // reverse half of the list bc u cant reverse the middle element
            T temp = a[i];
            a[i] = a[count-i];
            a[count-i] = temp;
        }
        isSorted = isSorted();
    }

    /**
     * Merges two sorted lists together into this list. If other is null, do not attempt to merge.
     * Sort MUST be called first on both this list and other list. The resulting list should be sorted.
     * Updates isSorted to true. You will have to cast otherList from a List<T> type to a LinkedList<T>
     * or ArrayList<T> type.
     *
     * After error checking, the first two lines of your code should be:
     * LinkedList<T> other = (LinkedList<T>) otherList; or ArrayList<T> other = (Arraylist<T>) otherList;
     * sort();
     * other.sort();
     *
     * Other than these two lines, you may not sort, or call the sort method, anywhere else in this function.
     * Ignoring this rule will result in an invalid solution.
     *
     * IMPORTANT NOTE: Ignore the time complexity of the sort function calls when determining the time
     * complexity of this method. (i.e. Just consider the merging portion of this function).
     *
     * Second Note for ArrayList: You will be required to create an array of the perfect size to
     * fill all elements from both lists into the new one. Then you will update the current list to
     * this new one.
     *
     * @param otherList list to be merged into this one.
     */
    public void merge(List<T> otherList) {
        int count = 0;
        int counter1 = 0;
        int counter2 = 0;
        boolean check = false;

        ArrayList<T> other = (ArrayList<T>) otherList;
        other.sort();
        sort();
        T[] mergedList = (T[]) new Comparable[sizeOfList + other.sizeOfList];       // sizes new list

        if(other.a == null || a == null)
            return;

        while(check == false){

            if(a[counter1].compareTo(other.a[counter2]) < 0){  // this checks if index of list a is smaller then other
                mergedList[count++] = a[counter1++];
            }

            else {  // opposite of if statement
                mergedList[count++] = other.a[counter2++];

            }

            if(counter1 == sizeOfList || counter2 == other.sizeOfList)  // if one of the lists have no elements left you exit loop
                check = true;
        }

        if(counter1 < sizeOfList){                  // add the rest of the elements to new array
            for(int k = counter1; k < sizeOfList; k++){
                mergedList[count] = a[k];
                count++;
            }
        }

        if(counter2 < other.sizeOfList){
            for(int l = counter2; l< other.sizeOfList; l++){
                mergedList[count] = other.a[l];
                count++;
            }
        }

        a = mergedList;         // set old array to new array
        sizeOfList = sizeOfList+other.sizeOfList;           // make sure to add in all new elements added
        isSorted = isSorted();
    }


    /**
     * Rotate this list to the right by n positions. This rotation must be done IN PLACE. Any use of
     * intermediate data structures will yield your solution invalid. If
     * n is less than or equal to 0 OR the list length is less than or equal to 1, return false without rotating.
     * Returns true otherwise after completing the rotation. Updates isSorted accordingly.
     * ArrayList hint: try to think about how the number of rotations could be simplified down based
     * on the size of the array.
     * LinkedList hint: try to think about the linked list in a circular way when rotating.
     *
     * @param n number of rotations.
     * @return if the rotation was successful.
     */
    public boolean rotate(int n) {
        T temp, trailer;
        if(n<= 0 || sizeOfList <= 1)
            return false;

       for(int i = 0; i<n; i++){
           trailer = a[sizeOfList-1];

           for(int j = 0; j<sizeOfList; j++){

               temp = a[j];
               a[j] = trailer;
               trailer = temp;
           }
       }
       return true;
    }

    public boolean isSorted() {
        isSorted = true;
        for (int i = 0; i < sizeOfList-1; i++) {
            if (a[i].compareTo(a[i+1]) > 0)
                isSorted = false;
        }
        return isSorted;
    }
    public String toString(){
        String returnString = " ";
        for(int i = 0; i<sizeOfList; i++){
            returnString += a[i];
            returnString += ", ";
        }
        return returnString;
    }
}
