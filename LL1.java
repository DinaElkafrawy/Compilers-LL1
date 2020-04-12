import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class LL1 {

	/*
	 * Please update the file/class name, and the following comment
	 */

	// T14_37_2387_Dina_Elkafrawy
	
	static class FirstFollow {
		
			ArrayList<String> allRules = new ArrayList<String>();
			ArrayList<Character> variables = new ArrayList<Character>();
			ArrayList<String> first = new ArrayList<String>();
			ArrayList<String> follow = new ArrayList<String>();
			ArrayList<Character> alreadyVisited = new ArrayList<Character>();
			
			public FirstFollow(String input) {
				split(input);
			}
			
			private void split(String input) {
				String[] firstSplit = input.split(";");
				
				for(int i = 0; i < firstSplit.length; i++) {
					allRules.add(firstSplit[i]);
				}
				
				for(int j = 0; j < firstSplit.length; j++) {
					String[] secondSplit = firstSplit[j].split(",");
					variables.add(secondSplit[0].charAt(0));
				}
			}
			
			// Sorts the output into an alphabetical order with $ at the end.
			private String alphabeticalOrder(String input) {
				String output = "";
				boolean found$ = false;
				char charArray[] = input.toCharArray();
			    Arrays.sort(charArray);
			      
				for(int i = 0; i < charArray.length; i++) {
					if(charArray[i] == '$')
						found$ = true;
					else
						output += charArray[i];
				}
				
				if(found$)
					output += '$';
				
				return output;
			}
			
			// Checks whether the input variable contains an epsilon in its rule.
			private boolean hasEpsilon(Character currentVariable) {
				String[] currentRule = null;
				
				for(int i = 0; i < allRules.size(); i++) {
					currentRule = allRules.get(i).split(",");
					
					if(currentRule[0].charAt(0) == currentVariable) 
						break;
				}
				
				for(int i = 1; i < currentRule.length; i++) {
					if(currentRule[i].charAt(0) == 'e')
						return true;
				}
				return false;
			}
			
			// Checks if the input contains an epsilon.
			private boolean outputWithEpsilon(String currentOutput) {
				for(int i = 0; i < currentOutput.length(); i++) {
					if(currentOutput.charAt(i) == 'e')
						return true;
				}
				
				return false;
			}
			
			// If a variable was already visited this returns true.
			private boolean wasVisited(Character variable) {
				if(alreadyVisited.contains(variable))
					return true;
				else {
					alreadyVisited.add(variable);
					return false;
					}
			}
			
			// Makes sure the output of the First doesn't contain duplicates
			private String duplicate(String currentOutput, String toBeInserted) {
				
				for(int i = 0; i < currentOutput.length(); i++) {
					for(int j = 0; j < toBeInserted.length(); j++) {
						if(currentOutput.charAt(i) == toBeInserted.charAt(j)) {
							if(toBeInserted == "e")
								toBeInserted = "";
							else
								toBeInserted = toBeInserted.replace(Character.toString(toBeInserted.charAt(j)), "");
						}
					}
				}
				
				return toBeInserted;
			}
			
			private String removeEpsilon(String currentOutput) {
				//all epsilons removed
						for(int k = 0; k < currentOutput.length(); k++) {
							if(currentOutput == "e")
								currentOutput = "";
							
							if(currentOutput.charAt(k) == 'e') {
								currentOutput = currentOutput.replace(Character.toString(currentOutput.charAt(k)), "");
							}
						}
				return currentOutput;
			}
			
			// Makes sure that the output of the Follow contains no duplicates or epsilons.
			private String duplicateFollow(String currentOutput, String toBeInserted) {		
				//all epsilons removed
				for(int k = 0; k < toBeInserted.length(); k++) {
					if(toBeInserted == "e")
						toBeInserted = "";
					
					if(toBeInserted.charAt(k) == 'e') {
						toBeInserted = toBeInserted.replace(Character.toString(toBeInserted.charAt(k)), "");
					}
				}
				
				for(int i = 0; i < currentOutput.length(); i++) {
					for(int j = 0; j < toBeInserted.length(); j++) {
						
						if(currentOutput.charAt(i) == toBeInserted.charAt(j)) {
							if(toBeInserted == "e")
								toBeInserted = "";
							else
								toBeInserted = toBeInserted.replace(Character.toString(toBeInserted.charAt(j)), "");
						}
					}
				}

				return toBeInserted;
			}
			
			// Fetches the First of the desired variable.
			private String findExistingFirst(Character variable) {
				String output = "";
				
				for(int i = 0; i < first.size(); i++) {
					String[] currentRule = first.get(i).split(",");

					if(currentRule[0].charAt(0) == variable && i == first.size() - 1) {
						output = first.get(i).substring(2);

						if(output.charAt(output.length() - 1) == ';')
							output = output.replace(Character.toString(output.charAt(output.length() - 1)), "");

					}
					else if( currentRule[0].charAt(0) == variable && i == first.get(i).length() - 1)
						output = first.get(i).substring(2);

					else if(currentRule[0].charAt(0) == variable) {
							output = first.get(i).substring(2,  first.get(i).length() - 1 );
					
					}
					if(output != "") {
						if(output.charAt(output.length() -1) == ';')
							output = output.replace(Character.toString(output.charAt(output.length() - 1)), "");
					}
					
				}
				return output;
			}
			
			// Fetches the Follow of the desired variable.
			private String findExistingFollow(Character variable) {
				String output = "";
				
				for(int i = 0; i < follow.size(); i++) {
					String[] currentRule = follow.get(i).split(",");
					
					if(currentRule[0].charAt(0) == variable) {
						output = follow.get(i).substring(2,  follow.get(i).length() - 1 );
					}
				}
				
				return output;
			}
			
			// Handles all the cases to get the First.
			private String fetchFirst(Character currentVariable, String currentOutput) {
				String[] currentRule = null;
				String temp = "";
				Character checkVar = '%';
				
				if(wasVisited(currentVariable))
					return "";
				
				if(!variables.contains(currentVariable))
					return Character.toString(currentVariable);
				
				for(int i = 0; i < allRules.size(); i++) {
					currentRule = allRules.get(i).split(",");
					
					if(currentRule[0].charAt(0) == currentVariable) 
						break;
				}
				
				for(int j = 1; j < currentRule.length; j++) {
					// Fetch all terminals.
					if(!variables.contains(currentRule[j].charAt(0)) && currentRule[j].charAt(0) != 'e') {
						currentOutput += currentRule[j].charAt(0);
					}
						
					// Non-terminals
					else {
						if(currentRule[0].charAt(0) != currentRule[j].charAt(0)) {
							if(!hasEpsilon(currentRule[j].charAt(0))) {
								temp = findExistingFirst(currentRule[j].charAt(0));

								if(temp == "")
									temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
								
								currentOutput += duplicate(currentOutput, temp);
								checkVar = currentRule[j].charAt(0);
								
								if(outputWithEpsilon(temp)){
									int size = currentRule[j].length();
									while(size > 1) {
										currentRule[j] = currentRule[j].substring(1);
										
										temp = findExistingFirst(currentRule[j].charAt(0));
										
										if(temp == "")
											temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
										
										currentOutput += duplicate(currentOutput, temp);
										checkVar = currentRule[j].charAt(0);
										
										size--;
										
										if(!outputWithEpsilon(temp))
											size = 1;
									}
									
									if(hasEpsilon(currentRule[j].charAt(0)) && currentRule[j].charAt(0) != 'e'){
										
										temp = findExistingFirst(currentRule[j].charAt(0));
										
										if(temp == "")
											temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
										
										currentOutput += duplicate(currentOutput, temp);
										checkVar = currentRule[j].charAt(0);
										}
								}
								
								currentOutput += duplicate(currentOutput, temp);
								checkVar = currentRule[j].charAt(0);

							}
							else {						
								int size = currentRule[j].length();
								while(size > 1) {
									temp = findExistingFirst(currentRule[j].charAt(0));
									
									if(temp == "")
										temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
									
									currentOutput += duplicate(currentOutput, temp);
									checkVar = currentRule[j].charAt(0);
									
									if(size > 1 && !outputWithEpsilon(temp)) {
										currentRule[j] = currentRule[j].substring(1);
										
										temp = findExistingFirst(currentRule[j].charAt(0));
										
										if(temp == "")
											temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
										
										currentOutput += duplicate(currentOutput, temp);
										checkVar = currentRule[j].charAt(0);
									}
									size = currentRule[j].length();
									if(!hasEpsilon(currentRule[j].charAt(0)))
										break;
									else if(size > 1)
										currentRule[j] = currentRule[j].substring(1);
									
									size = currentRule[j].length();
									}
								
								if(hasEpsilon(currentRule[j].charAt(0)) && currentRule[j].charAt(0) != 'e'){
									temp = findExistingFirst(currentRule[j].charAt(0));
									
									if(temp == "")
										temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
									
									currentOutput += duplicate(currentOutput, temp);
									checkVar = currentRule[j].charAt(0);
									}
								
								currentOutput += duplicate(currentOutput, "e");

								}
							}
						
						//Start variable is the same as left hand side
						else {
							if(hasEpsilon(currentRule[0].charAt(0))) {
								int size = currentRule[j].length();
								while(size > 1) {
									currentRule[j] = currentRule[j].substring(1);
									
									temp = findExistingFirst(currentRule[j].charAt(0));
									
									if(temp == "")
										temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
									
									currentOutput += duplicate(currentOutput, temp);
									checkVar = currentRule[j].charAt(0);
									
									size--;
									
									if(!hasEpsilon(currentRule[j].charAt(0)))
										size = 1;
								}
								
								if(hasEpsilon(currentRule[j].charAt(0)) && currentRule[j].charAt(0) != 'e'){
									
									temp = findExistingFirst(currentRule[j].charAt(0));
									
									if(temp == "")
										temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
									
									currentOutput += duplicate(currentOutput, temp);
									checkVar = currentRule[j].charAt(0);
									}
							}
						}
					}
				}
				
				if(!variables.contains(checkVar) && checkVar != 'e' && checkVar != '%') {
					return removeEpsilon(currentOutput);

				}

				if((!hasEpsilon(checkVar) && variables.contains(checkVar)) && checkVar != 'e' && checkVar != '%') {
					temp = fetchFirst(checkVar, currentOutput);
								
					if(!outputWithEpsilon(temp) && checkVar != currentVariable) {
							return removeEpsilon(currentOutput);
					}
				}
				
				return currentOutput;
			}
			
			// Handles all the cases for the Follow.
			private String fetchFollow(Character currentVariable, String currentOutput) {
				String[] currentRule = null;
				String temp = "";
				
				if(wasVisited(currentVariable))
					return "";
				
				for(int i = 0; i < allRules.size(); i++) {
					currentRule = allRules.get(i).split(",");

					for(int j = 1; j < currentRule.length; j++) {

						for(int k = 0; k < currentRule[j].length(); k++) {
							//Found variable
							if(currentRule[j].charAt(k) == currentVariable) {

								//Not a singleton 
								if(currentRule[j].length() > 1) {
									if(k+1 < currentRule[j].length()){
										//Terminal
										if(!variables.contains(currentRule[j].charAt(k+1))) 
											currentOutput += duplicateFollow(currentOutput,Character.toString(currentRule[j].charAt(k+1)));
										//Variable
										else {
											temp = findExistingFirst(currentRule[j].charAt(k+1));
											currentOutput += duplicateFollow(currentOutput, temp);
											int index = k + 1;
											while(outputWithEpsilon(temp)) {

												if( index + 1< currentRule[j].length()) {
													index++;
													//Terminal
													if(!variables.contains(currentRule[j].charAt(index))) {
														currentOutput += duplicateFollow(currentOutput,Character.toString(currentRule[j].charAt(index)));
														temp = "";
													}
													else {
														temp = findExistingFirst(currentRule[j].charAt(index));
														currentOutput += duplicateFollow(currentOutput, temp);
													}
												}
												else {
													//At the end of the String
													if(currentRule[0].charAt(0) != currentVariable) {
														temp = findExistingFollow(currentRule[0].charAt(0));
														
														if(temp == "")
															temp = fetchFollow(currentRule[0].charAt(0), currentOutput);
														
														currentOutput += duplicateFollow(currentOutput, temp);
													}
													else
														temp = "";
												}
											}
										}
									}
									else {
										//Variable found at the end of the string
										if(currentRule[0].charAt(0) != currentVariable) {
											//A single variable
											temp = findExistingFollow(currentRule[0].charAt(0));
											
											if(temp == "") {
												temp = fetchFollow(currentRule[0].charAt(0), currentOutput);
											}
											
											currentOutput += duplicateFollow(currentOutput, temp);
										}
									}
								}
								else {
									if(currentRule[0].charAt(0) != currentVariable) {

										//A single variable
										temp = findExistingFollow(currentRule[0].charAt(0));
										
										if(temp == "")
											temp = fetchFollow(currentRule[0].charAt(0), currentOutput);
										
										currentOutput += duplicateFollow(currentOutput, temp);
									}
								}
							}
						}
					}
				}
				
				if(currentVariable == 'S')
					currentOutput += "$";

				return currentOutput;
			}

			// Fetches the First for each variable.
			public String First() {
				String output = "";
				String currentOutput = "";
				String[] currentRule = null;
				String outerOutput = "";
				
				for(int i = 0; i < variables.size(); i++) {
					
					alreadyVisited.clear();
					currentOutput += fetchFirst(variables.get(i), currentOutput);
					
					if(outputWithEpsilon(currentOutput) && !hasEpsilon(variables.get(i))) {
						
						for(int j = 0; j < allRules.size(); j++) {
							currentRule = allRules.get(i).split(",");
							
							if(currentRule[0].charAt(0) == variables.get(i)) 
								break;
						}
						
						for(int j = 1; j < currentRule.length; j++) {
							if(variables.get(i) == currentRule[j].charAt(0)) {
								currentRule[j] = currentRule[j].substring(1);
								
								String temp = findExistingFirst(currentRule[j].charAt(0));

								if(temp == "")
									temp = fetchFirst(currentRule[j].charAt(0), currentOutput);
								
								currentOutput += duplicate(currentOutput, temp);			
							}
						}
					}
					
					currentOutput = alphabeticalOrder(currentOutput);
					if( i == variables.size() - 1)
						outerOutput += variables.get(i) +","+ currentOutput;
					else
						outerOutput += variables.get(i) +","+ currentOutput + ";";
					

					output += outerOutput;
					first.add(outerOutput);
					
					currentOutput = "";
					outerOutput = "";
				}
				
				return output;
			} 
			
			//Fetches the Follow for each variable.
			public String Follow() {
				String output = "";
				
				String currentOutput = "";
				String outerOutput = "";
				
				for(int i = 0; i < variables.size(); i++) {
					alreadyVisited.clear();
					currentOutput += fetchFollow(variables.get(i), currentOutput);

					currentOutput = alphabeticalOrder(currentOutput);
					
					if( i == variables.size() - 1)
						outerOutput += variables.get(i) +","+ currentOutput;
					else
						outerOutput += variables.get(i) +","+ currentOutput + ";";
					
					output += outerOutput;
					follow.add(outerOutput);
					
					currentOutput = "";
					outerOutput = "";
				}
				
				return output;
			}
	}
	
	static class CFG {
		String grammar;
		FirstFollow cfg;
		String firstEncoding;
		String followEncoding;
		String table;
		ArrayList<Character> rows = new ArrayList<Character>();
		ArrayList<Character> columns = new ArrayList<Character>();
		ArrayList<String> allRules = new ArrayList<String>();
		
		/**
		 * Creates an instance of the CFG class. This should parse a string
		 * representation of the grammar and set your internal CFG attributes
		 * 
		 * @param grammar A string representation of a CFG
		 */
		public CFG(String grammar) {
			this.grammar = grammar;
			cfg = new FirstFollow(grammar);
			firstEncoding = cfg.First(); 
			followEncoding = cfg.Follow();
			rowsColumns();
		}
		
		
		public void rowsColumns() {
			String[] firstSplit = grammar.split(";");
			
			for(int i = 0; i < firstSplit.length; i++) {
				String[] secondSplit = firstSplit[i].split(",");
				
				allRules.add(firstSplit[i]);
				rows.add(secondSplit[0].charAt(0));
			}
			
			for(int z = 0; z < firstSplit.length; z++) {
				String[] secondSplit = firstSplit[z].split(",");

				for(int j = 1; j < secondSplit.length; j++) {
					for(int k = 0; k < secondSplit[j].length(); k++) {
						if(!rows.contains(secondSplit[j].charAt(k)) && secondSplit[j].charAt(k) != 'e' && !columns.contains(secondSplit[j].charAt(k)))
							columns.add(secondSplit[j].charAt(k));
					}
				}
			}
			
			Collections.sort(rows); 
			Collections.sort(columns); 
			
			columns.add('$');
			
//			for(int x = 0; x < rows.size(); x++) 
//				System.out.println("varaible " + rows.get(x));
//			
//			for(int y = 0; y < columns.size(); y++) 
//				System.out.println("terms " + columns.get(y));	
		}
		
		// Checks if the input contains an epsilon.
		private boolean outputWithEpsilon(String currentOutput) {
			for(int i = 0; i < currentOutput.length(); i++) {
				if(currentOutput.charAt(i) == 'e')
					return true;
			}
			
			return false;
		}
		
		public String fetchFirst(Character variable) {
			String currentRule= "";
			
			for(int i = 0; i < firstEncoding.length(); i++) {
				String[] split = firstEncoding.split(";");
				
				for(int j = 0; j < split.length; j++) {
					String[] secondSplit = split[j].split(",");
					
					if(secondSplit[0].charAt(0) == variable) {
						currentRule = secondSplit[1];
					}
				}
			}
			return currentRule;
		}
		
		public String fetchFollow(Character variable) {
			String follow  = "";
			
			for(int i = 0; i < followEncoding.length(); i++) {
				String[] split = followEncoding.split(";");
				
				for(int j = 0; j < split.length; j++) {
					String[] secondSplit = split[j].split(",");
					
					if(secondSplit[0].charAt(0) == variable) {
						follow = secondSplit[1];
					}
				}
			}
			return follow;
		}

		public String computeFirst(Character variable, Character foundFirst) {
			String currentRule = "";
			
			for(int i = 0; i < allRules.size(); i++) {
				String[] rules = allRules.get(i).split(",");
				
				if(rules[0].charAt(0) == variable) {
					for(int j = 1; j < rules.length; j++) {
						if(foundFirst == rules[j].charAt(0))
							return rules[j];
						else if(rows.contains(rules[j].charAt(0))){
							String temp = fetchFirst(rules[j].charAt(0));
							
							if(temp.indexOf(foundFirst) != -1)
								return rules[j];
						}
					}
				}
			}
			return currentRule;
		}
		
		/**
		 * Generates the parsing table for this context free grammar. This should set
		 * your internal parsing table attributes
		 * 
		 * @return A string representation of the parsing table
		 */
		public String table() {
			String output = "";
			Boolean firstRule = false;
			
			for(int i = 0; i < rows.size(); i++) {
				String first = fetchFirst(rows.get(i));

				for(int j = 0; j < columns.size(); j++) {
					for(int k = 0; k < first.length(); k++){
						if(first.charAt(k) == columns.get(j)) {
							String rule = computeFirst(rows.get(i), columns.get(j));
							
							if(!firstRule) {
								firstRule = true;
								output += rows.get(i) + "," + columns.get(j) + "," + rule;
							}
							else
								output += "," + rule;
							
						}
					}
					
					if(outputWithEpsilon(first)) {
						String follow = fetchFollow(rows.get(i));

						for(int l = 0; l < follow.length(); l++) {
							if(follow.charAt(l) == columns.get(j)) {								
								if(!firstRule) {
									firstRule = true;
									output += rows.get(i) + "," + columns.get(j) + "," + 'e';
								}
								else
									output += "," + 'e';
							}
						}
					}
					
					if(output.length() > 1 && output.charAt(output.length()-1) != ';')
						output += ";";
					firstRule = false;
				}
				if(output.length() > 1 && output.charAt(output.length()-1) != ';')
					output += ";";
				firstRule = false;
			}
			
			output = output.substring(0, output.length() - 1);
			table = output;
			
			return output;
		}

		public String fetchFromTable(Character row, Character column) {
			String output = "";
			
			String[] firstSplit = table.split(";");
			
			for( int i = 0; i < firstSplit.length; i++) {
				String[] secondSplit = firstSplit[i].split(",");
				
				if(secondSplit[0].charAt(0) == row && secondSplit[1].charAt(0) == column)
					return secondSplit[2];
			}
			
			return output;
		}
		
		public String inStack(Stack<Character> stack) {
			String output = "";
			
			while(stack.size() > 1) {
				output += stack.pop();
			}
			
			for(int i = output.length() - 1; i >= 0; i--) {
				stack.push(output.charAt(i));
			}

			return output;
		}
		/**
		 * Parses the input string using the parsing table
		 * 
		 * @param s The string to parse using the parsing table
		 * @return A string representation of a left most derivation
		 */
		public String parse(String input) {
			String output= "";
			String matched = "";
			Boolean errorFound = false;
			Stack<Character> stack = new Stack<Character>();
			stack.push('$');
			stack.push('S');
			
			output += 'S';
			
			while(input.length() != 0) {
				Character top = stack.pop();
				if(input.charAt(0) == top) {
					matched += input.charAt(0);
					input = input.substring(1);
				}
				else {
					Character row = top;
					Character column = input.charAt(0);
					
					String fromTable = fetchFromTable(row, column);

					if(fromTable != "") {
						if(fromTable.charAt(0) != 'e') {
						for(int x = fromTable.length() - 1; x >= 0; x--) {
							stack.push(fromTable.charAt(x));
							}
						}
						output +=  "," + matched + inStack(stack);
					}
					else {
						output += "," + "ERROR"; 
						errorFound = true;
						break;
					}
				}
			}
			
			if(!errorFound) {
				while(stack.size() >= 1) {
					Character top = stack.pop();
					String fromTable = fetchFromTable(top, '$');
					
					if(top != '$' && (fromTable == "" || fromTable.charAt(0) != 'e')) {
						output += "," + "ERROR";
						break;
					}
					else if(top != '$') {
						output += "," + matched + inStack(stack);
					}
				}
			}
			
			return output;
		}
	}

	public static void main(String[] args) {

		/*
		 * Please make sure that this EXACT code works. This means that the method
		 * and class names are case sensitive
		 */

		String grammar = "S,iST,e;T,cS,a";
		//String grammar = "S,aB;B,SC,e;C,+B,*B";
		//String grammar = "S,(L),a;L,(L)B,aB;B,-SB,e";
		//String grammar = "S,aA;A,SB,e;B,pA,mA";
		
		String input1 = "iiac"; 
		String input2 = "iia";
		
		CFG g = new CFG(grammar);
		
		System.out.println(g.table());
		System.out.println(g.parse(input1));
		System.out.println(g.parse(input2));
	}

}
