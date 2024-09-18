public class Polynomial {
	double [] coefficients;
	
	public Polynomial () {
		this.coefficients = new double [] {0};
	}
	
	public Polynomial (double [] coefficients) {
		this.coefficients = new double [coefficients.length];
		
		for (int i = 0; i < coefficients.length; i++) {
			this.coefficients [i] = coefficients [i];
		}
	}
	
	public Polynomial add (Polynomial a) {
		int degreeNew = Math.max (this.coefficients.length, a.coefficients.length);
		double newCoefficients [] = new double [degreeNew];
		
		for (int i = 0; i < degreeNew; i++) {
			if (i < this.coefficients.length && i < a.coefficients.length) {
				newCoefficients[i] = this.coefficients[i] + a.coefficients[i];
			}
			else if (i >= this.coefficients.length) {
				newCoefficients[i] = a.coefficients[i];
			}
			else {
				newCoefficients[i] = this.coefficients[i];
			}
		}
		return new Polynomial(newCoefficients);
	}
	
	public double evaluate (double a) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(a, i);
        }
		return result;
	}
	
	public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }
}