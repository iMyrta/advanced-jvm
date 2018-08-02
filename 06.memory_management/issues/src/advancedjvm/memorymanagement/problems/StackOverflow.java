package advancedjvm.memorymanagement.problems;

public class StackOverflow {

	public static void main(String[] args) {
		a();
		int n = 1000;
		System.out.println("The " + n + " fibonacci number is: " + fibonacci(n));
	}

	public static int fibonacci(int n) {
		if (n == 1 || n == 2) {
			return 1;
		}
		else {
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}
	
	public static void a() {
		b();
	}
	
	public static void b() {
		a();
	}
	
}
