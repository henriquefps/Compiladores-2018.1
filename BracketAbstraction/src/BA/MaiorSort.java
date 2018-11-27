package BA;

public class MaiorSort {
	public static void imprimirVetor(int[] vetor){
		for (int i = 0; i < vetor.length; i++) {
			System.out.print(vetor[i] + " ");
		}
		System.out.println();
	}
	public static void maiorNaFrente(int[] v, int n){
		for (int i = 0; i <= n; i++) {
			if(v[0] < v[i]){
				int aux = v[i];
				v[i] = v[0];
				v[0] = aux;
			}
		}
	}
	
	public static void maiorSort(int[] v, int tam){
		
		for (int t = tam; t >= 0; t--) {
			maiorNaFrente(v, t);
			int aux = v[0];
			v[0] = v[t];
			v[t] = aux;
		}
	}
	public static void main(String[] args) {
		int[] vetor = {10, 20, 5, 3, 7, 64, 22, 11, 12};
		imprimirVetor(vetor);
		maiorSort(vetor, vetor.length-1);
		imprimirVetor(vetor);
	}
}
