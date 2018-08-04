package com.epam.advancedjvm.memory.offheap;

import java.nio.ByteBuffer;

public class AllocateOffHeap {
	
	private static final int SIZE = 1300000;
	
	public static void main(String[] args) throws InterruptedException {
		int[] array = new int[SIZE];
		ByteBuffer buffer = ByteBuffer.allocateDirect(SIZE);
		
		for (int i = 0; i < SIZE; i++) {
			buffer.putInt(i);
			array[i] = i;
			System.out.println("arr[" + i + "] = " + array[i] + ", buffer[" + i + "] = " + buffer.getInt(i * 4));
			Thread.sleep(50);
		}
	}

}
