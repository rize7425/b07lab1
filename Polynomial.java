import java.io.*;

public class Polynomial {
	double [] coefficients;
	int [] exponents;
	
	public Polynomial () {
		this.coefficients = new double [] {0};
		this.exponents = new int [] {0};
	}
	
	public Polynomial (double [] coefficients, int [] exponents) {
		this.coefficients = new double [coefficients.length];
		this.exponents = new int [exponents.length];
		
		for (int i = 0; i < coefficients.length; i++) {
			this.coefficients [i] = coefficients [i];
			this.exponents [i] = exponents [i];
		}
	}
	
	public Polynomial (File file) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader (file));
		String poly = r.readLine();
		
		String [] terms = poly.split("(?=[+-])");
		this.exponents = new int [terms.length];
		this.coefficients = new double [terms.length];
		int size = 0;
		
		for (String term: terms) {
			term = term.trim();
			double coefficient = 1;
			int exponent = 0;
			
			if (term.contains("x")){
				String [] cae = term.split("x");
				
				//handle the coefficient
				if (cae[0].trim().equals("-")) {
					coefficient = -1;
				}
				else if (cae[0].trim().equals("+")) {
					coefficient = 1;
				}
				else {
					coefficient = Double.parseDouble(cae[0].trim());
				}
				
				//handle the exponent
				if (cae.length >1 && cae[1].trim().contains("^")) {
					exponent = Integer.parseInt(cae[1].trim().substring(1));
				}
				else {
					exponent = 1;
				}
			}
			else {
				coefficient = Double.parseDouble(term);
				exponent = 0;
			}
			
			// Combine like terms by checking if the exponent already exists
			boolean found = false;
			for (int i = 0; i < size; i++) {
				if (this.exponents[i] == exponent) {
					this.coefficients[i] += coefficient; // Combine coefficients
					found = true;
					break;
					}
			}

			// If the exponent wasn't found, add the new term
			if (!found) {
				this.coefficients[size] = coefficient;
				this.exponents[size] = exponent;
				size++;
			}
		}
		Polynomial removeRedundants = removeRedundantTerms(this);
		this.coefficients = removeRedundants.coefficients;
		this.exponents = removeRedundants.exponents;
		r.close();
	}
	
	public Polynomial add (Polynomial a) {
		int newLength = this.coefficients.length + a.coefficients.length;
		double newCoefficients [] = new double [newLength];
		int newExponents [] = new int [newLength];
		boolean added = false;
		
		// add everything for one polynomial in first
		for (int i = 0; i< this.coefficients.length; i++) {
			newCoefficients[i] = this.coefficients[i];
			newExponents[i] = this.exponents[i];
		}
		
		// now add the second polynomial to the new one
		for (int i = 0; i < a.coefficients.length; i++) {
			int index = 0;
			added = false;
			for (int j = 0; j < newCoefficients.length; j++) {
				index = j;
				if (newExponents[j] == a.exponents[i]) {
					newCoefficients[j] += a.coefficients[i];
					added = true;
					break;
				}
			}
			
			if (!added) {
				newCoefficients[index] = a.coefficients[i];
				newExponents[index] = a.exponents[i];
			}
		}
		
		return removeRedundantTerms(new Polynomial(newCoefficients, newExponents));
	}
	
	// Method to multiply two polynomials
	public Polynomial multiply(Polynomial a) {
		int newLength = this.coefficients.length * a.coefficients.length;
		double[] newCoefficients = new double[newLength];
		int[] newExponents = new int[newLength];
		int index = 0;
		
		for (int i = 0; i < this.coefficients.length; i++) {
			for (int j = 0; j < a.coefficients.length; j++) {
				double newCoefficient = this.coefficients[i] * a.coefficients[j];
				int newExponent = this.exponents[i] + a.exponents[j];
				// Combine terms
				boolean combined = false;
				for (int k = 0; k < index; k++) {
					if (newExponents[k] == newExponent) {
						newCoefficients[k] += newCoefficient;
						combined = true;
						break;
						}
				}
				if (!combined) {
					newCoefficients[index] = newCoefficient;
					newExponents[index] = newExponent;
					index++;
				}
			}
		}
		return removeRedundantTerms(new Polynomial(newCoefficients, newExponents));
	}
	
	public double evaluate (double a) {		
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(a, exponents[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return evaluate(x) == 0.0;
	}
	
	public void saveToFile (String fileName) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		boolean firstTerm = true;
		
		for (int i = 0; i< coefficients.length; i++) {
			double coefficient = coefficients[i];
			int exponent = exponents[i];
			
			if (coefficient == 0) continue;
			
			if (!firstTerm && coefficient > 0) {
				out.write("+");
			}
			
			if (exponent == 0) {
				out.write("" + coefficient);
			}
			else {
				if (coefficient == 1) {
					if (exponent == 1) {
						out.write("x");
					}
					else {
						out.write("x^" + exponent);
					}
				}
				else if (coefficient == -1) {
					if (exponent == 1) {
						out.write("-x");
					}
					else {
						out.write("-x^" + exponent);
					}
				}
				else {
					if (exponent == 1) {
						out.write(coefficient + "x");
					}
					else {
						out.write(coefficient + "x^" + exponent);
					}
				}
			}
			
			firstTerm = false;
		}
		// for if all of them are 0
		if (firstTerm) {
			out.write("0");
		}
		out.close();
	}
	
	public Polynomial removeRedundantTerms(Polynomial p) {
		//count how many non-zero terms
		int nonZeroCount = 0;
		for (int i = 0; i < p.coefficients.length; i++) {
		if (p.coefficients[i] != 0) {
			nonZeroCount++;
		}
	}
		if (nonZeroCount == 0) {
			return new Polynomial(new double[]{0}, new int[]{0});
		}
		double[] newCoefficients = new double[nonZeroCount];
		int[] newExponents = new int[nonZeroCount];
		int index = 0;
		for (int i = 0; i < p.coefficients.length; i++) {
			if (p.coefficients[i] != 0) {
				newCoefficients[index] = p.coefficients[i];
				newExponents[index] = p.exponents[i];
				index++;
			}
		}
		return new Polynomial(newCoefficients, newExponents);
	}
}