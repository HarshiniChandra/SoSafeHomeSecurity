public class RotateArray {

/*
* input: an array, k--> number of steps, that array has to be rotated.
*
*/
	public static void arrayRotate(int[] array, int k) {
		if(array == null || k < 0) {
			return;
		}

		for (int steps = 1; steps <= k; steps++) {
			int len = array.length; // store the array length.
			int temp = array[len - 1]; //Store the last value in a temporary variable.
			for (int i = (len - 2); i >= 0; i--) {
				// i starts from the end of the array, i.e, second from last.
				
				array[i + 1] = array[i];
			}
			array[0] = temp;
		}

	}
	public static void main(String[] args) {

		int[] array = {10, 202, 30, 11, 56, 65, 70};
		//call the function with the array
		arrayRotate(array, 4);
		System.out.println("The rotated array is");
		for (int i = 0; i < array.length; i++) {
			System.out.print(" " + array[i]);

		}

		
	}

}