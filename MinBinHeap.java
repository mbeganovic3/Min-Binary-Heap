package MinBinHeap_A3;

public class MinBinHeap implements Heap_Interface {
  private EntryPair[] array; //load this array
  private int size;
  private static final int arraySize = 10000; //Everything in the array will initially 
                                              //be null. This is ok! Just build out 
                                              //from array[1]

  public MinBinHeap() {
    this.array = new EntryPair[arraySize];
    array[0] = new EntryPair(null, -100000); //0th will be unused for simplicity 
                                             //of child/parent computations...
                                             //the book/animation page both do this.
  }
    
  //Please do not remove or modify this method! Used to test your entire Heap.
  @Override
  public EntryPair[] getHeap() { 
    return this.array;
  }

@Override
public void insert(EntryPair entry) { // page 231 in TB
	if(size == 0) array[1] = entry; // first hole 
	int hole = ++size;
	array[hole] = entry; 
	percolateUp(size); // percolate up from textbook 
}

@Override
public void delMin() { // page 233 in TB
	if(size == 0) return; //delMin is done on an empty heap, treat it as a no-op... i.e., do nothing other than return void.
	swap(size, 1); // swaps the root and the last node even though the last node is rarely ever the min
	array[size] = null; // save out, deletes the min
	size--; // The size of the heap goes down by 1
	percolateDown(1); //Bubble hole down from root, Stop when last element causes heap-order in the hole
}


/*
 * Basically to keep the build method in O(N) time, 
 * you simply have to fill the array and then call bubble down starting at the last node that has children in the heap (size/2). 
 * Bubble down should work where you see if either of the children is less than the parent, if so, swap it. 
 * Then call bubble down again on the node you just swapped to ensure it is in the right place. 
 */
private void percolateDown(int hole) { // page 233 in TB
	int child;
	for(; array[hole * 2] != null; hole = child) { 
		child = hole * 2;
		if(array[child + 1] != null && array[child].getPriority() > array[child + 1].getPriority()) child++;
		if(array[child].getPriority() < array[hole].getPriority()) {
			swap(hole, child); //children is less than the parent, if so, swap it.
			percolateDown(child); //call bubble down again on the node just swapped to ensure it is in the right place. 
		}else break;
	}
}

private void swap(int h1, int h2) {
	EntryPair temp = new EntryPair(null, 0);
	temp = array[h1];
	array[h1] = array[h2];
	array[h2] = temp;
}

@Override
public EntryPair getMin() {
	if(size == 0) return null; // If getMin is done on an empty heap, return null.
	return array[1]; // min should be at top
}

@Override
public int size() {
	if(size < 1) size = 0; //always return a 0 or greater.
	return size;
}

@Override
// page 236 in TB
public void build(EntryPair[] entries) {
	size = entries.length;
	int i = 1;
	for(EntryPair entry : entries)
		array[i++] = entry;
	buildHeap(); // from the textbook
}

// also page 236 in TB
private void buildHeap() {
	for(int i = size / 2; i > 0; i--)
		percolateDown(i);
}

// page 231 in TB
private void percolateUp(int i) {	
	for(; i > 1 && array[i].getPriority() < array[i/2].getPriority(); i /= 2) {
		swap(i, i/2);
		percolateUp(i);
	}
};
}
