import java.io.File;
import java.io.IOException;
public class Driver {

	public static void main(String[] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,0,0,5};
		int [] c11 = {0, 1, 2, 3};
		Polynomial p1 = new Polynomial(c1, c11);
		double [] c2 = {0,-2,0,0,-9};
		int [] c22 = {0, 1, 2, 3, 4};
		Polynomial p2 = new Polynomial(c2, c22);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		System.out.println ("\nTesting constructor from file and saveToFile");
		File a = new File ("C:\\Users\\ryanz\\b07lab1\\input.txt");
		Polynomial p3 = new Polynomial (a);
		for (int i = 0; i<p3.coefficients.length; i++) {
			System.out.println("Coefficient: " + p3.coefficients[i] + "  Exponent:" + p3.exponents[i]);
		}
		p3.saveToFile("C:\\Users\\ryanz\\b07lab1\\output.txt");
		
		
		System.out.println ("\nTesting Multiply");
		double[] coeffs1 = {1, 1};
		int[] exps1 = {1, 0};

		double[] coeffs2 = {1, 1};
		int[] exps2 = {1, 0};

		Polynomial p4 = new Polynomial(coeffs1, exps1);
		Polynomial p5 = new Polynomial(coeffs2, exps2);

		Polynomial result = p4.multiply(p5);
		for (int i = 0; i<result.coefficients.length; i++) {
			System.out.println("Coefficient: " + result.coefficients[i] + "  Exponent:" + result.exponents[i]);
		}
	}

}