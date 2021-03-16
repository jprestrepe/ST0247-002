/**
 *  @author Juan Pablo Restrepo, Juan José Sánchez
 */
public class Taller08{

  //Esta es la implementación del QuickSort
    public int partition(int arreglo[], int low, int high) {
        int pivot = arreglo[high];
        int i = (low-1); 
         for (int j=low; j<high; j++)
        {
            if (arreglo[j] <= pivot)
            {
                i++;
                int temp = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temp;
            }
        }
        int temp = arreglo[i+1];
        arreglo[i+1] = arreglo[high];
        arreglo[high] = temp;
        return i+1;
    }

    public void sort(int [] arreglo){
        sort(arreglo,0,arreglo.length-1);
    }

    public void sort(int arr[], int low, int high) {
        if (low < high){
            int pi = partition(arr, low, high); 
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }
 
  //Esta es la implementación del Merge
  private static int[] aM;

    public static void merge(int arr[], int l, int m, int r) 
    { 
        int n1 = m - l + 1; 
        int n2 = r - m; 
        int L[] = new int[n1]; 
        int R[] = new int[n2]; 

        for (int i = 0; i < n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j = 0; j < n2; ++j) 
            R[j] = arr[m + 1 + j]; 
        int i = 0, j = 0; 
        int k = l; 
        while (i < n1 && j < n2) { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } 
            else { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
        while (i < n1) { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
        while (j < n2) { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
  
    public static void sort(int arr[], int l, int r) 
    { 
        if (l < r) { 
            int m = (l + r) / 2; 
            sort(arr, l, m); 
            sort(arr, m + 1, r); 
            merge(arr, l, m, r); 
        } 
    }
