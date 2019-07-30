package Task4;

import Task1.common.Semaphore;

/**
 * Class BlockStack Implements character block stack and operations upon it.
 *
 * $Revision: 1.4 $ $Last Revision Date: 2019/07/02 $
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca; Inspired by an earlier
 *         code by Prof. D. Probst
 */
class BlockStack {

	public int stackAccessCounter = 0;
	
	private static Semaphore mutex = new Semaphore(1);
	/**
	 * # of letters in the English alphabet + 2
	 */
	public static final int MAX_SIZE = 28;

	/**
	 * Default stack size
	 */
	public static final int DEFAULT_SIZE = 6;

	/**
	 * Current size of the stack
	 */
	private int iSize = DEFAULT_SIZE;

	/**
	 * Current top of the stack
	 */
	private int iTop = 3;

	/**
	 * stack[0:5] with four defined values
	 */
	private char acStack[] = new char[] { 'a', 'b', 'c', 'd', '*', '*' };

	/**
	 * Default constructor
	 */
	public BlockStack() {
	}

	/**
	 * Supplied size
	 */
	public BlockStack(final int piSize) {

		if (piSize != DEFAULT_SIZE) {
			this.acStack = new char[piSize];

			// Fill in with letters of the alphabet and keep
			// 2 free blocks
			for (int i = 0; i < piSize - 2; i++)
				this.acStack[i] = (char) ('a' + i);

			this.acStack[piSize - 2] = this.acStack[piSize - 1] = '*';

			this.iTop = piSize - 3;
			this.iSize = piSize;
		}
	}

	/**
	 * Picks a value from the top without modifying the stack
	 * 
	 * @return top element of the stack, char
	 */
	public char pick() {
		stackAccessCounter++;
		return this.acStack[this.iTop];
	}

	/**
	 * Returns arbitrary value from the stack array
	 * 
	 * @return the element, char
	 */
	public char getAt(final int piPosition) {
		stackAccessCounter++;
		return this.acStack[piPosition];
	}

	/**
	 * Standard push operation
	 */
	public void push(char pcBlock) {
		stackAccessCounter++;
		mutex.P();
		try {
			
			if (this.isEmpty())
				pcBlock = 'a';
			System.out.println("Successful push");

			if (this.iTop == iSize - 1)
				throw new FullStackException(
						"Stack is full, can't push element");
			else
				this.acStack[++this.iTop] = pcBlock;
			System.out.println("Successful push");
			mutex.V();
		} catch (FullStackException e) {
			System.err.print("Stack is full, can't push element.");
		}

	}

	/**
	 * Standard pop operation
	 * 
	 * @return ex-top element of the stack, char
	 */
	public char pop() {
		stackAccessCounter++;

		char cBlock = ' ';
		mutex.P();
		try {
			cBlock = this.acStack[this.iTop];
			if (this.isEmpty())
				throw new EmptyStackException(
						"Stack is empty, can't pop element.");
			else {
				this.acStack[this.iTop--] = '$';
				System.out.println("Successful push");
			}
			mutex.V();
		} catch (EmptyStackException e) {
			System.err.print("Stack is empty, can't pop element.");
		}

		return cBlock;
	}

	public int getITop() {
		return iTop;

	}

	public void setITop(int x) {
		this.iTop = x;
	}

	public int getISize() {
		return iSize;

	}

	public void setISize(int iSize) {
		this.iSize = iSize;
	}
	
	public char[] getacStack(){
		return acStack;
	}

	public void setacStack(char[] arr){
		this.acStack = arr;
	}

	public int getAccessCounter() {
		return stackAccessCounter;

	}

	public boolean isEmpty() {
		return (this.iTop == -1);
	}
}

// EOF
