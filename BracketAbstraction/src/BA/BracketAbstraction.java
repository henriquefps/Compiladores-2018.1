package BA;

public class BracketAbstraction {
	
	/*
	 *Remove blank spaces from the string
	 */
	static String removeBlankSpaces(String a) {
		String res = "";
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != ' ') {
				res += a.charAt(i);
			}
		}
		return res;
	}

	/*
	 * Says if a string is well formed for the algorithm
	 * TODO Check if all brackets come before all arguments
	 */
	static boolean wellFormed(String a) {
		int bemForm1 = 0;
		int bemForm2 = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == '[') {
				bemForm1++;
			} else if (a.charAt(i) == ']') {
				bemForm1--;
			} else if (a.charAt(i) == '(') {
				bemForm2++;
			} else if (a.charAt(i) == ')') {
				bemForm2--;
			}
		}

		if (bemForm1 == 0 && bemForm2 == 0)
			return true;
		else
			return false;
	}

	/*
	 * Returns the position after the end of a argument
	 */
	public static int findArgument(int i, String a) {
		int aux = Integer.valueOf(i);
		if (aux < a.length() && a.charAt(aux) == '(') {
			aux++;
			int count = 1;
			while (count > 0) {
				if (a.charAt(aux) == '(')
					count++;
				else if (a.charAt(aux) == ')')
					count--;
				aux++;
			}
			return aux;
		} else if (aux < a.length() && a.charAt(aux) == ')')
			return -1;
		else if (aux < a.length())
			return aux + 1;
		else
			return -1;
	}

	/*
	 * Searches for the beginning of the arguments/end of the brackets
	 */
	public static int searchArgumentsStartPosition(String a) {
		int inicioArgumentos = 0;
		while (a.charAt(inicioArgumentos) == '[' && a.charAt(inicioArgumentos + 2) == ']') {
			inicioArgumentos += 3;
		}
		return inicioArgumentos;
	}

	/*
	 * Remove a parenthesis and its correspondent from the string, and returns it
	 */
	public static String removeParenthesis(int alvo, String a) {
		if (a.charAt(alvo) == '(') {
			String res = "";
			int fimArgumento = findArgument(alvo, a);
			int i;
			for (i = 0; i < alvo; i++) {
				res += a.charAt(i);
			}
			i++;
			for (; i < fimArgumento - 1; i++) {
				res += a.charAt(i);
			}
			i++;
			for (; i < a.length(); i++) {
				res += a.charAt(i);
			}
			return res;
		} else
			return a;

	}

	/*
	 * Searches if a bracket that is being applied still on the string, if not, returns -1 and the "applyBracket()" loop stops 
	 */
	public static int findBracket(char bracket, String a) {
		int i;
		for (i = 0; i < a.length(); i++) {
			if (a.charAt(i) == bracket) {
				return i + 2;
			}
		}
		return -1;
	}

	/*
	 * Apply the last bracket before the start of the arguments
	 */
	public static String applytBracket(String a) {
		String atual = a;
		String proxima = "";
		int inicioArgumentos = searchArgumentsStartPosition(a);
		String bracket = atual.charAt(inicioArgumentos - 2) + "";
		while (findBracket(bracket.charAt(0), atual) != -1) {
			int posicao = findBracket(bracket.charAt(0), atual);
			atual = removeParenthesis(posicao, atual);
			int arg1 = findArgument(posicao, atual);
			int arg2;
			if ((arg1) < atual.length() && atual.charAt(arg1) == ')') //Checking if there is only one argument
				arg2 = -1;
			else if (arg1 < atual.length()) //If there is more than one, then find the end of the second argument
				arg2 = findArgument(arg1, atual);
			else // Catches the case where the end of the first argument is the last position at string, avoiding OutOfBOunds
				arg2 = -1;

			if (arg2 != -1) { //If there is more than one argument, then [x]ab == S([x]a)([x]b) 
				if (arg2 == arg1) {
					arg2++;
				}
				int x = 0;
				for (; x < posicao - 3; x++) {
					proxima += atual.charAt(x);
				}
				x += 3;
				proxima += "S([" + bracket + "]";
				for (; x < arg1; x++) {
					proxima += atual.charAt(x);
				}
				proxima += ")([" + bracket + "]";
				for (; x < arg2; x++) {
					proxima += atual.charAt(x);
				}
				proxima += ")";
				for (; x < atual.length(); x++) {
					proxima += atual.charAt(x);
				}
			} else { //If there is only one argument, then for [x]a == Ka if a != x, else [x]a == I 
				if (arg1 - 1 == posicao) {
					if (bracket.charAt(0) == atual.charAt(arg1 - 1)) {
						int i;
						for (i = 0; i < arg1 - 1 - 3; i++) {
							proxima += atual.charAt(i);
						}
						proxima += "I";
						i += 4;
						for (; i < atual.length(); i++) {
							proxima += atual.charAt(i);
						}
					} else {
						int i;
						for (i = 0; i < arg1 - 1 - 3; i++) {
							proxima += atual.charAt(i);
						}
						proxima += "K";
						i += 3;
						for (; i < atual.length(); i++) {
							proxima += atual.charAt(i);
						}
					}
				} else
					proxima = atual;
			}
			atual = proxima;
			proxima = "";
		}
		return atual;
	}

	/*
	 * Tells how many iterations are necessary to abstract all brackets
	 */
	public static int countBrackets(String a) {
		int inicioArgs = searchArgumentsStartPosition(a);
		int count = 0;
		for (int i = 0; i < inicioArgs; i++) {
			if (a.charAt(i) == '[') {
				count++;
			}
		}
		return count;
	}
	
	/*
	 * Search the string associating all arguments, granting that at any moment, there will only
	 * occur [x]ab or [x]a
	 */
	public static String leftAssociation(String a) {
		int inicioArgs = searchArgumentsStartPosition(a); //Association only starts after the brackets
		String aux = a;
		for (int i = inicioArgs; i < aux.length(); i++) { // Search the whole string for a position with more than 2 arguments
			int nArgs = countArguments(i, aux);
			if (aux.charAt(i) != '(' && aux.charAt(i) != ')' && nArgs > 2) { // If there is such case, then left associate them
				String atual = aux;
				String proxima = "";
				for (int j = 0; j < nArgs - 1; j++) {
					int arg1 = findArgument(i, atual);
					int arg2 = findArgument(arg1, atual);
					int k;
					for (k = 0; k < i; k++) {
						proxima += atual.charAt(k);
					}
					proxima += "(";
					for (; k < arg2; k++) {
						proxima += atual.charAt(k);
					}
					proxima += ")";

					for (; k < atual.length(); k++) {
						proxima += atual.charAt(k);
					}
					atual = proxima;
					proxima = "";
				}
				aux = atual;
			}
		}
		return aux;
	}
	
	/*
	 * Remove redundant parenthesis, to be called after all brackets have been abstracted
	 */
	public static String removeRedundantParenthesis(String a) {
		String aux = "", retorno = a;
		for (int i = 0; i+2 < retorno.length(); i++) {
			if (retorno.charAt(i) == '(' && retorno.charAt(i + 2) == ')') {
				int j;
				for (j = 0; j < i; j++) {
					aux += retorno.charAt(j);
				}
				j++;
				aux += retorno.charAt(j);
				j+=2;
				for (; j < retorno.length(); j++) {
					aux += retorno.charAt(j);
				}
				retorno = aux;
				aux = "";
			}
		}
		return retorno;
	}
	
	/*
	 * Counts how many arguments are relative to a certain position in the string 
	 */
	public static int countArguments(int alvo, String a) {
		int arg = alvo;
		int cont = 0;
		while (arg != -1) {
			arg = findArgument(arg, a);
			if (arg != -1)
				cont++;
		}
		return cont;
	}
	
	/*
	 * Full script
	 */
	public static void runAbstraction(String entrada) {
		entrada = removeBlankSpaces(entrada);
		System.out.println("String");
		System.out.println(entrada);
		System.out.println();
		if (wellFormed(entrada)) {
			int iteracoes = countBrackets(entrada);
			for (int j = 0; j < iteracoes; j++) {
				entrada = leftAssociation(entrada);
				entrada = applytBracket(entrada);
				System.out.println("After Bracket Abstracted");
				System.out.println(entrada);
				System.out.println();
			}
			System.out.println("Without redundant parenthesis");
			System.out.println(removeRedundantParenthesis(entrada));
		} else System.out.println("Bad String");
	}

	public static void main(String[] args) {
		runAbstraction("[x][y][z][w]xw((zw)y)");
	}
}