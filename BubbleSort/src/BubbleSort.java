
public class BubbleSort {

	public static int[] bubbleSort(int[] numbers) {

		int temp;

		for (int i = 0; i < numbers.length - 1; i++) {
			for (int j = 0; j < numbers.length - 1; j++) {

				System.out.println("Durchgang Aussenschleiffe" + i + " innere schleife" + j);
				if (numbers[j] > numbers[j + 1]) {
					temp = numbers[j];
					numbers[j] = numbers[j + 1];
					numbers[j + 1] = temp;

				}

			}

		}

		return numbers;

	}

	public static void printArray(int numbers[]) {
		int n = numbers.length;
		for (int i = 0; i < n; i++) {

			System.out.println(numbers[i] + " ");

		}

	}

}
