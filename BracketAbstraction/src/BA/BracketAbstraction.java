package BA;

import java.util.ArrayList;

public class BracketAbstraction {

	/*
	 * Remove blank spaces from the string
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
	 */
	static boolean wellFormed(String a) {
		int bemForm1 = 0;
		int bemForm2 = 0;
		int inicioArgs = findArgumentsStartPosition(a);
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
			if (i > inicioArgs && a.charAt(i) == '[') {
				return false;
			}
		}

		if (findArgumentsStartPosition(a) == -1) {
			return false;
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
	public static int findArgumentsStartPosition(String a) {
		try {
			int inicioArgumentos = 0;
			while (a.charAt(inicioArgumentos) == '[' && a.charAt(inicioArgumentos + 2) == ']') {
				inicioArgumentos += 3;
			}
			return inicioArgumentos;
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
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
	 * Searches if a bracket that is being applied still on the string, if not,
	 * returns -1 and the "applyBracket()" loop stops
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
		int inicioArgumentos = findArgumentsStartPosition(a);
		char bracket = atual.charAt(inicioArgumentos - 2);
		while (findBracket(bracket, atual) != -1) {
			int posicao = findBracket(bracket, atual);
			atual = removeParenthesis(posicao, atual);
			int arg1 = findArgument(posicao, atual), arg2;

			if ((arg1) < atual.length() && atual.charAt(arg1) == ')')
				arg2 = -1;// Checking if there is only one argument
			else if (arg1 < atual.length())
				arg2 = findArgument(arg1, atual);// If there is more than one, then find the end of the second argument
			else
				arg2 = -1;// Catches the case where the end of the first argument is the last position at
							// string, avoiding OutOfBOunds

			if (arg2 != -1)
				atual = applyS(atual, posicao, arg1, arg2, bracket);// If there is more than one argument, then [x]ab
																	// == S([x]a)([x]b)
			else { // If there is only one argument, then for [x]a == Ka if a != x, else [x]a == I
				if (arg1 - 1 == posicao) { // Avoiding to apply K or I, mainly K, in a example like ([x](aaa)), the
											// mistake would result in (Kaaa)
					if (bracket == atual.charAt(arg1 - 1))
						atual = applyI(atual, posicao, arg1);
					else
						atual = applyK(atual, posicao, arg1);
				}
			}
		}

		return atual;
	}

	/*
	 * ([x]ab) == (S([x]a)([x]b))
	 */
	public static String applyS(String atual, int posicao, int arg1, int arg2, char bracket) {
		String proxima = "";
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

		return proxima;
	}

	/*
	 * ([x]a) == (Ka), if x != a
	 */
	public static String applyK(String atual, int posicao, int arg1) {
		String proxima = "";
		int i;
		for (i = 0; i < arg1 - 1 - 3; i++) {
			proxima += atual.charAt(i);
		}
		proxima += "K";
		i += 3;
		for (; i < atual.length(); i++) {
			proxima += atual.charAt(i);
		}
		return proxima;
	}

	/*
	 * ([x]a) == (I), if x == a
	 */
	public static String applyI(String atual, int posicao, int arg1) {
		String proxima = "";
		int i;
		for (i = 0; i < arg1 - 1 - 3; i++) {
			proxima += atual.charAt(i);
		}
		proxima += "I";
		i += 4;
		for (; i < atual.length(); i++) {
			proxima += atual.charAt(i);
		}
		return proxima;
	}

	/*
	 * Tells how many iterations are necessary to abstract all brackets
	 */
	public static int countBrackets(String a) {
		int inicioArgs = findArgumentsStartPosition(a);
		int count = 0;
		for (int i = 0; i < inicioArgs; i++) {
			if (a.charAt(i) == '[') {
				count++;
			}
		}
		return count;
	}

	/*
	 * Search the string associating all arguments, granting that at any moment,
	 * there will only occur [x]ab or [x]a
	 */
	public static String leftAssociation(String a) {
		int inicioArgs = findArgumentsStartPosition(a); // Association only starts after the brackets
		String aux = a;
		for (int i = inicioArgs; i < aux.length(); i++) { // Search the whole string for a position with more than 2
															// arguments
			int nArgs = countArguments(i, aux);
			if (i == inicioArgs || (aux.charAt(i) != '(' && aux.charAt(i) != ')') && nArgs > 2) { // If there is such
																									// case, then left
																									// associate them
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
	 * Remove redundant parenthesis, to be called after all brackets have been
	 * abstracted
	 */
	public static String removeRedundantParenthesis(String a) {
		String aux = "", retorno = a;
		for (int i = 0; i + 2 < retorno.length(); i++) {
			if (retorno.charAt(i) == '(' && retorno.charAt(i + 2) == ')') {
				int j;
				for (j = 0; j < i; j++) {
					aux += retorno.charAt(j);
				}
				j++;
				aux += retorno.charAt(j);
				j += 2;
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
	 * KSI Machine and is operators
	 */
	public static String evaluateExpression(String entrada, String args) {
		String atual = runAbstraction(entrada);
		String argumentos = removeRedundantParenthesis(removeBlankSpaces(args));
		if (atual != null && countArguments(0, argumentos) == countBrackets(entrada)) {
			atual = removeRedundantParenthesis(removeBlankSpaces(atual += args));
			System.out.println("Evaluate expression");
			System.out.println(atual);
			System.out.println();
			int inicioExpr = 0;
			while (inicioExpr < atual.length()) {
				switch (atual.charAt(inicioExpr)) {
				case '(':
					atual = removeParenthesis(inicioExpr, atual);
					break;
				case 'S':
					atual = evaluateS(inicioExpr, atual);
					break;
				case 'K':
					atual = evaluateK(inicioExpr, atual);
					break;
				case 'I':
					atual = evaluateI(inicioExpr, atual);
					break;
				default:
					inicioExpr++;
					break;
				}
				 System.out.println(atual); 
			}
		} else if (countArguments(0, argumentos) < countBrackets(entrada)) {
			System.err.println("Less arguments than expected");
		} else if ((countArguments(0, argumentos) > countBrackets(entrada))) {
			System.err.println("More arguments than expected");
		}
		return atual;
	}

	/*
	 * For Sabc does ac(bc)
	 */
	public static String evaluateS(int inicioExpr, String atual) {
		String proxima = "";
		int inicioArg1 = inicioExpr + 1;
		int fimArg1 = findArgument(inicioArg1, atual);
		int fimArg2 = findArgument(fimArg1, atual);
		int fimArg3 = findArgument(fimArg2, atual);
		for (int i = 0; i < inicioExpr; i++) {
			proxima += atual.charAt(i);
		}

		for (int i = inicioArg1; i < fimArg1; i++) {
			proxima += atual.charAt(i);
		}
		for (int i = fimArg2; i < fimArg3; i++) {
			proxima += atual.charAt(i);
		}
		proxima += '(';
		for (int i = fimArg1; i < fimArg2; i++) {
			proxima += atual.charAt(i);
		}
		for (int i = fimArg2; i < fimArg3; i++) {
			proxima += atual.charAt(i);
		}
		proxima += ')';

		for (int i = fimArg3; i < atual.length(); i++) {
			proxima += atual.charAt(i);
		}

		return proxima;
	}

	/*
	 * For Kab does a
	 */
	public static String evaluateK(int inicioExpr, String atual) {
		String proxima = "";
		int inicioArg1 = inicioExpr + 1;
		int fimArg1 = findArgument(inicioArg1, atual);
		int fimArg2 = findArgument(fimArg1, atual);
		for (int i = 0; i < inicioExpr; i++) {
			proxima += atual.charAt(i);
		}

		for (int i = inicioArg1; i < fimArg1; i++) {
			proxima += atual.charAt(i);
		}

		for (int i = fimArg2; i < atual.length(); i++) {
			proxima += atual.charAt(i);
		}

		return proxima;
	}

	/*
	 * For Ia does a
	 */
	public static String evaluateI(int inicioExpr, String atual) {
		String proxima = "";
		int inicioArg1 = inicioExpr + 1;
		for (int i = 0; i < inicioExpr; i++) {
			proxima += atual.charAt(i);
		}

		for (int i = inicioArg1; i < atual.length(); i++) {
			proxima += atual.charAt(i);
		}

		return proxima;
	}

	/*
	 * Full script
	 */
	public static String runAbstraction(String entrada) {
		entrada = removeRedundantParenthesis(removeBlankSpaces(entrada));
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
			entrada = removeRedundantParenthesis(entrada);
			System.out.println(entrada);
			System.out.println();
			return entrada;
		} else {
			System.err.println("Bad String");
			return null;
		}

	}

	public static void evaluateNumericalPreFixExpression(String a) {
		ArrayList<Character> stack = new ArrayList<Character>();
		for (int i = 0; i < a.length(); i++) {
			stack.add(a.charAt(i));
		}
		int i = stack.size()-1;
		while (stack.size() > 1 && i >= 0) {
			switch (stack.get(i)) {
			case '+':
				Integer aux = Integer.parseInt(stack.get(i+1)+"") + Integer.parseInt(stack.get(i+2)+"");
				stack.remove(i+2);
				stack.remove(i+1);
				stack.remove(i);
				stack.add(i, aux.toString().charAt(0));
				break;
			default:
				i--;
				break;
			}
			for (int j = 0; j < stack.size(); j++) {
				System.out.print(stack.get(j));
			}
			System.out.println();
		}
		
	}
	public static void main(String[] args) {
//		runAbstraction("[a][b][c]+bc");
		evaluateNumericalPreFixExpression(evaluateExpression("[a][b]+ab", "32"));
	}
}
