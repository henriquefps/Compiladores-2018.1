package BA;

public class Funciona_CodigoSujo {
	static String removerEspacosEmBranco(String a) {
		String res = "";
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != ' ') {
				res += a.charAt(i);
			}
		}
		return res;
	}

	static boolean bemFormada(String a) {
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

	public static int achaArgumento(int i, String a) {
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

	public static int procuraInicioArgs(String a) {
		int inicioArgumentos = 0;
		while (a.charAt(inicioArgumentos) == '[' && a.charAt(inicioArgumentos + 2) == ']') {
			inicioArgumentos += 3;
		}
		return inicioArgumentos;
	}

	@Deprecated
	public static String juntarArgumentos(int alvo, String a) {

		String atual = a;
		String proxima = "";

		int inicioArgumentos = alvo;
		int fimArgumento1 = -1;
		while (true) {
			fimArgumento1 = achaArgumento(inicioArgumentos, atual);
			if (fimArgumento1 > atual.length() - 1) {
				break;
			}
			int fimArgumento2 = achaArgumento(fimArgumento1, atual);
			int j = 0;
			for (; j < inicioArgumentos; j++) {
				proxima += atual.charAt(j);
			}

			proxima += '(';
			if (fimArgumento2 != fimArgumento1) {
				fimArgumento2--;
			}
			for (; j <= fimArgumento2; j++) {
				proxima += atual.charAt(j);
			}
			proxima += ')';

			for (; j < atual.length(); j++) {
				proxima += atual.charAt(j);
			}
			atual = proxima;
			proxima = "";

		}
		return atual;
	}

	public static String removeParentesis(int alvo, String a) {
		if (a.charAt(alvo) == '(') {
			String res = "";
			int fimArgumento = achaArgumento(alvo, a);
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

	public static int encontrarBracket(char bracket, String a) {
		int i;
		for (i = 0; i < a.length(); i++) {
			if (a.charAt(i) == bracket) {
				return i + 2;
			}
		}
		return -1;
	}

	public static int casaParentesis(int alvo, String a) {
		if (a.charAt(alvo) == '(') {
			return achaArgumento(alvo, a);
		}
		return -1;
	}

	public static String aplicarBracket(String a) {
		String atual = a;
		String proxima = "";
		int inicioArgumentos = procuraInicioArgs(a);
		String bracket = atual.charAt(inicioArgumentos - 2) + "";
		while (encontrarBracket(bracket.charAt(0), atual) != -1) {
			int posicao = encontrarBracket(bracket.charAt(0), atual);
			atual = removeParentesis(posicao, atual);
			int arg1 = achaArgumento(posicao, atual);
			System.out.println("1-" + atual + " arg1 = " + arg1);
			int arg2;
			if (arg1 == -1) {
				System.out.println("Erro, Posicao = " + posicao);
				break;
			} else if ((arg1) < atual.length() && atual.charAt(arg1) == ')')
				arg2 = -1;
			else if (arg1 < atual.length())
				arg2 = achaArgumento(arg1, atual);
			else
				arg2 = -1;

			if (arg2 != -1 && arg2 < atual.length() && atual.charAt(arg2) == ')' && atual.charAt(arg2) == '('
					&& atual.charAt(arg2) == ']' && atual.charAt(arg2) == '[') {
				System.out.println("3-criaParentesis");
				int i;
				for (i = 0; i < arg1 - 1; i++) {
					proxima += atual.charAt(i);
				}
				proxima += "((";
				proxima += atual.charAt(i);
				i++;
				proxima += ")";
				proxima += atual.charAt(i);
				proxima += ")";

				i++;
				for (; i < atual.length(); i++) {
					proxima += atual.charAt(i);
				}

			} else if (arg2 != -1) {
				System.out.println("ReduzS");
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
			} else {
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

	public static int contarBrackets(String a) {
		int inicioArgs = procuraInicioArgs(a);
		int count = 0;
		for (int i = 0; i < inicioArgs; i++) {
			if (a.charAt(i) == '[') {
				count++;
			}
		}
		return count;
	}

	public static String juntarArgumentos2(String a) {
		int inicioArgs = procuraInicioArgs(a);
		String aux = a;
		for (int i = inicioArgs; i < aux.length(); i++) {
			int nArgs = contarArgumentos(i, aux);
			if (aux.charAt(i) != '(' && aux.charAt(i) != ')' && nArgs > 2) {
				System.out.println(nArgs);
				String atual = aux;
				String proxima = "";
				for (int j = 0; j < nArgs - 1; j++) {
					int arg1 = achaArgumento(i, atual);
					int arg2 = achaArgumento(arg1, atual);
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
				System.out.println("jr2 " + aux);
			}
		}
		return aux;
	}

	public static String removerParentesisRedundantes(String a) {
		String aux = "", retorno = a;
		for (int i = 0; i < retorno.length() - 2; i++) {
			if (retorno.charAt(i) == '(' && retorno.charAt(i + 2) == ')') {
				int j;
				for (j = 0; j < i; j++) {
					aux += retorno.charAt(j);
				}
				j++;
				aux += retorno.charAt(j);
				j++;
				for (; j < retorno.length(); j++) {
					aux += retorno.charAt(j);
				}
				retorno = aux;
				aux = "";
			}
		}
		return retorno;
	}

	public static int contarArgumentos(int alvo, String a) {
		int arg = alvo;
		int cont = 0;
		while (arg != -1) {
			arg = achaArgumento(arg, a);
			if (arg != -1)
				cont++;
		}
		return cont;
	}

	public static void main(String[] args) {
		String entrada = "[x][y][z]xz(yz)";
		entrada = removerEspacosEmBranco(entrada);
		int iteracoes = contarBrackets(entrada);
		System.out.println("it " + iteracoes);
		for (int j = 0; j < iteracoes; j++) {
			entrada = juntarArgumentos2(entrada);
			System.out.println("Juntar args " + entrada);
			entrada = aplicarBracket(entrada);
		}
		System.out.println(removerParentesisRedundantes(entrada));
	}

}
