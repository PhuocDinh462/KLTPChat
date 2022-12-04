package AdminForm;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;

public class MergeSort {
	private int typeSort;
	private int secondElementSort;

	MergeSort(int typeSort, int componentSort) {
		this.typeSort = typeSort;

		// No sort
		if (componentSort == 0) {
			this.secondElementSort = -1;
		}
		// Sort name/username
		else if (componentSort == 1) {
			this.secondElementSort = 1;
		}
		// Sort name/date created
		else {
			this.secondElementSort = 8;
		}
	}

	private void merge(Object[][] arr, int l, int m, int r) {
		// Find sizes of two subarrays to be merged
		int n1 = m - l + 1;
		int n2 = r - m;

		/* Create temp arrays */
		Object[][] L = new Object[n1][arr[0].length];
		Object[][] R = new Object[n2][arr[0].length];
		
		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			System.arraycopy(arr[l + i], 0, L[i], 0 , arr[0].length);
		for (int j = 0; j < n2; ++j)
			System.arraycopy(arr[m + 1 + j], 0, R[j], 0 , arr[0].length);

		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays
		int i = 0, j = 0;

		// Initial index of merged subarray array
		int k = l;
		while (i < n1 && j < n2) {
			Integer compValue1 = L[i][2].toString().compareTo(R[j][2].toString());
			if (compValue1 == typeSort) {
//				arr[k] = L[i];
				System.arraycopy(L[i], 0, arr[k], 0 , arr[0].length);
				i++;
			} else if (compValue1 == 0) {
				Integer compValue2 = compareValue(L[i][1].toString(),R[j][1].toString());
				if (compValue2 == typeSort) {
//					arr[k] = L[i];
					System.arraycopy(L[i], 0, arr[k], 0 , arr[0].length);
					i++;
				} else {
//					arr[k] = R[j];
					System.arraycopy(R[j], 0, arr[k], 0 , arr[0].length);
					j++;
				}
			} else {
//				arr[k] = R[j];
				System.arraycopy(R[j], 0, arr[k], 0 , arr[0].length);
				j++;
			}
			k++;
		}

		/* Copy remaining elements of L[] if any */
		while (i < n1) {
//			arr[k] = L[i];
			System.arraycopy(L[i], 0, arr[k], 0 , arr[0].length);
			i++;
			k++;
		}

		/* Copy remaining elements of R[] if any */
		while (j < n2) {
//			arr[k] = R[j];
			System.arraycopy(R[j], 0, arr[k], 0 , arr[0].length);
			j++;
			k++;
		}
	}

	// Main function that sorts arr[l..r] using
	// merge()
	public void sort(Object[][] arr, int l, int r) {
		if (l < r) {
			// Find the middle point
			int m = l + (r - l) / 2;

			// Sort first and second halves
			sort(arr, l, m);
			sort(arr, m + 1, r);

			// Merge the sorted halves
			merge(arr, l, m, r);
		}
	}

	private Integer compareValue(String v1, String v2) {
		if (secondElementSort == 1) {
			return v1.compareTo(v2);
		}

		try {
			Date value1 = (Date) new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(v1);
			Date value2 = (Date) new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH).parse(v2);

			return value1.compareTo(value2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -2;
	}
}
