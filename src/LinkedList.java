public class LinkedList <T extends Comparable<T>> implements List<T>{

    private boolean isSorted;
    private Node head;
    private int sizeOfList;

    public LinkedList(){
        isSorted = true;
        head = new Node<>(-1, null);  // a headed list which means it has the first value in the list to not be part of the list
        sizeOfList = 0;
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
    public boolean add(T element){
        if(element == null){
            return false;
        }
        if(head.getNext() == null) {        // add the first element
            head.setNext(new Node<T>(element));
            isSorted = isSorted();
            sizeOfList++;
            return true;
        }
        Node<T> trailer = head;
        Node<T> ptr = head.getNext();

        while (ptr != null) { // starts after head and exits loop when found null then adds the element to the end of the list
            trailer = ptr;
            ptr = ptr.getNext();
        }

        trailer.setNext(new Node<T>(element, null));
        isSorted = isSorted();
        sizeOfList++;
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
    public boolean add(int index, T element){
        Node<T> ptr = head.getNext().getNext();
        Node<T> trailer = head.getNext();
        int count = 1;

        if(element == null || index < 0 || index > sizeOfList-1){  // size-1 bc list starts at 0 index
            return false;
        }
        if(index == 0){     // if index is 0 then u can say head.setnext
            Node<T> temp = head.getNext();
            head.setNext(new Node<T>(element,temp));
        }
        else {
            while (count < index) {     // loop until count == index then u add to that index
                trailer = ptr;
                ptr = ptr.getNext();
                count++;
            }
            trailer.setNext(new Node<T>(element, ptr));
        }
        isSorted = isSorted();
        sizeOfList++;
        return true;
    }

    /**
     * Remove all elements from the list and updates isSorted accordingly.
     */
    public void clear(){
        head.setNext(null);
        isSorted = true;
        sizeOfList = 0;
    }

    /**
     * Return the element at given index. If index is
     * out-of-bounds, it will return null.
     * @param index index to obtain from the list.
     * @return the element at the given index.
     */
    public T get(int index){
        Node<T> ptr = head.getNext();
        int count = 0;

        if(index > sizeOfList-1 || index < 0)
            return null;

        while(count != index){              // count each time you move through the list and exit loop once u found the "index"
            count++;
            ptr = ptr.getNext();
        }
        T data = (T) ptr.getData();         // return the data at that index
        return data;
    }

    /**
     * Return the first index of element in the list. If element
     * is null or not found in the list, return -1. If isSorted is
     * true, uses the ordering of the list to increase the efficiency
     * of the search.
     *
     *
     * @param element element to be found in the list.
     * @return first index of the element in the list.
     */

    public int indexOf(T element){
        Node<T> ptr = head.getNext();
        int count = 0;

        if(element == null)
            return -1;

        while(ptr != null){                 // loop through and compare each data in the list with the element we want while also
                                            // incrementing count and once we find the element you return count
            if(ptr.getData() == element)
                return count;
            ptr = ptr.getNext();
            count++;
        }
        return -1;          // if the element was not found return -1
    }

    /**
     * Return true if the list is empty and false otherwise.
     *
     * @return if the list is empty.
     */
    public boolean isEmpty(){
        if(head.getNext() == null)
            return true;
        else
            return false;
    }

    /**
     * size() return the number of elements in the list. Be careful
     * not to confuse this for the length of a list like for an ArrayList.
     * For example, if 4 elements are added to a list, size will return
     * 4, while the last index in the list will be 3. Another example
     * is that an ArrayList like [5, 2, 3, null, null] would have a size
     * of 3 for an ArrayList.
     * ArrayList and LinkedList hint: create a class variable in both ArrayList
     * and LinkedList to keep track of the sizes of the respective lists.
     *
     * @return size of the list.
     */

    public int size(){
        return sizeOfList;
    }

    /**
     * Sort the elements of the list in ascending order using insertion sort.
     * If isSorted is true, do NOT re-sort.
     * Hint: Since T extends Comparable, you will find it useful
     * to use the public int compareTo(T other) method.
     * Updates isSorted accordingly.
     */
    public void sort() {

        // 1. take one element from original list and put it in the new headed list
        // 2. before you add find the correct position to put it in the new list

        Node<T> ptr = head.getNext();
        Node<T> newHead = new Node<T>(null);
        Node<T> ptr2;   // starts at first element
        T temp;
        Node<T> trailer2; // starts at empty head

        if(isSorted == true)
            return;

        while (ptr != null) {
            temp = remove(0);    // removes first element from list
            sizeOfList++;// every time you remove() make sure you increase size bc remove decreases size of list
            ptr2 = newHead.getNext();   // starts at first element in new list
            trailer2 = newHead; // starts at empty head of new list

            if (newHead.getNext() == null) {      // add the first element to new list
                newHead.setNext(new Node<T>(temp, null));
            } else {
                while (ptr2 != null) {    // checks the rest of the newlist
                    if (ptr2.getData().compareTo(temp) >= 0) {             // if temp is smaller than ptr2 then add it before ptr
                        trailer2.setNext(new Node<T>(temp, ptr2));
                        break;
                    }
                    trailer2 = ptr2;
                    ptr2 = ptr2.getNext();
                }

                if (ptr2 == null) {             // if temp is the biggest number than add it to the end of the list
                    trailer2.setNext(new Node<T>(temp, null));
                }
            }
            ptr = ptr.getNext();        // traverses through old list
        }
        head = newHead;     // set old head to new head
        head.setNext(newHead.getNext());
        isSorted = true;
    }

    /**
     * Removes all elements of the list that are not equal to 'element'. If element is null, don't do anything.
     * When this function returns, the only elements that should be left in this list
     * are equal to 'element'. This method should not change the ordering of the list.
     * If the list is sorted, use this fact to increase the efficiency of this method.
     * This method should be done IN PLACE. Do not use any extra data structures to
     * solve this problem. (You are NOT allowed to create a new array for this function).
     * Updates isSorted accordingly.
     * @param element type of element to be kept in the list.
     */

    public void equalTo(T element) {
        Node<T> ptr = head.getNext();
        Node<T> trailer = head;

        if(element == null)
            return;

        while(ptr != null){
            if(ptr.getData().compareTo(element) == 0){      // you only move trailer when you find the element you are looking for
                trailer.setNext(ptr);                   // if the ptr == to element then u make trailer, which is before ptr, to set AFTER PTR
                trailer = ptr;
                sizeOfList++;
            }
            ptr = ptr.getNext();
            sizeOfList--;
        }

        isSorted = isSorted();
    }

    public void reverse(){
        Node trailer = head.getNext();;
        Node ptr = head.getNext().getNext();;

        if (head.getNext() == null || head.getNext().getNext() == null)  // nothing to reverse since only one node
            return;

        while (ptr != null) {
            trailer.setNext(ptr.getNext());   // u never move trailer u just move everything around it and this helps bring the first element to the end
            ptr.setNext(head.getNext());
            head.setNext(ptr);
            ptr = trailer.getNext();
        }
        isSorted = isSorted();
    }

    public String toString() {
        String listReturn = "";
        Node ptr = head.getNext();
        while(ptr != null){
            listReturn += ptr.getData();
            listReturn += "\n";
            ptr = ptr.getNext();
        }

        return listReturn;
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

        LinkedList<T> other = (LinkedList<T>) otherList;
        sort();
        other.sort();

        if(other.head.getNext() == null || head.getNext() == null)
            return;

        Node<T> newListHead = new Node<T>(null);
        Node<T> newList = newListHead.getNext();
        Node<T> ptr = head.getNext();
        Node<T> ptr2 = other.head.getNext();
        boolean check = false;

        while(check == false){
            if(ptr.getData().compareTo(ptr2.getData()) <= 0){        //if == or if ptr is smaller then ptr2 then you add it to the newList
                if(newListHead.getNext() == null){          // adds the very first element to the new list

                    newListHead.setNext(ptr);       // new merged list will have a headed list so do head.setNext
                    newList = ptr;
                }
                else {          // else adds the element after first one
                    newList.setNext(ptr);
                    newList = ptr;
                }
                ptr = ptr.getNext();
            }
            else{
                if(newListHead.getNext() == null){  //  adds the very first element to the new list
                    newListHead.setNext(ptr2);
                    newList = ptr2;
                }
                else {           // else adds the element after first element
                    newList.setNext(ptr2);
                    newList = ptr2;
                }
                ptr2 = ptr2.getNext();
            }

            if(ptr == null || ptr2 == null){            // once one of the lists we are adding is done then you exit the loop
                check = true;
            }
        }

        // now check which one the of lists did not finish adding to the new list and add the rest of the elements to the new list

        if(ptr != null){
            while(ptr != null) {
                newList.setNext(ptr);
                newList = ptr;
                ptr = ptr.getNext();
            }
        }
        if (ptr2 != null){
            while(ptr2 != null) {
                newList.setNext(ptr2);
                newList = ptr2;
                ptr2 = ptr2.getNext();
            }
        }

        sizeOfList += other.sizeOfList;
        head = newListHead;             // set the old head to the head of the new merged list
        isSorted = true;            // now the list is sorted so make it true
    }

    public boolean isSorted() {
        if(head.getNext() == null || head.getNext().getNext() == null) {        // if there is only one element or no elements then the list is sorted
            isSorted = true;
            return isSorted;
        }
        Node<T> ptr = head.getNext().getNext();
        Node<T> trailer = head.getNext();
        isSorted = true;

        while(ptr != null){
            if(trailer.getData().compareTo(ptr.getData()) < 0){   // if trailer is bigger than ptr then uk that the list IS NOT
                                                                    // SORTED since for example 9 is bigger then 3 and 9 is trailer
                isSorted = false;
                break;
            }
            trailer = ptr;
            ptr = ptr.getNext();
        }
        return isSorted;
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
        Node<T> ptr = head.getNext();
        Node<T> trailer = head;
        Node<T> data = null;
        int count = 0;

        if(index < 0 || index >= sizeOfList)
            return null;

        if(index == 0){
            data = head.getNext();              // if idx is 0 then u can say head.setNext to after the first element
            head.setNext(head.getNext().getNext());
        }

        else{
            while(ptr != null){
                if(count == index){     // count to find where the index is and once u find it then set trailer's next to be AFTER THE PTR
                    data = ptr;
                    trailer.setNext(ptr.getNext());
                }
                trailer = ptr;
                ptr = ptr.getNext();
                count++;
            }
        }
        sizeOfList--;           // decrease the size of the list when u remove an element
        isSorted = isSorted();
        return data.getData();
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
    public boolean rotate(int n){
        int counter = 1;
        Node<T> trailer = head;
        Node<T> ptr = head.getNext();

        Node<T> end = null;
        Node<T> ptr2 = head.getNext();

        if(n <= 0 || sizeOfList <= 1)
            return false;

        if(n % sizeOfList == 0)    //checks to see if u would rotate the list to its original order
            return true;

        while(counter < sizeOfList-n){     // finds where to start to rotate
            counter++;
            trailer = trailer.getNext();
            ptr = ptr.getNext();
        }

        while(ptr2 != null){        // finds where the end of the list is and use it to set its next to old head
            end = ptr2;
            ptr2 = ptr2.getNext();
        }

        Node temp = head.getNext();
        head.setNext(ptr.getNext());  // sets new head

        end.setNext(temp); // set end to the original head

        ptr.setNext(null); // this is making the new end of the list

        isSorted = isSorted();
        return true;
    }

}


