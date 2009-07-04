package rsmith;

public class VoronoiToken implements Comparable<VoronoiToken> {

    private int exponent;
    private int numerator;
   
    public VoronoiToken() {}
    
    public VoronoiToken mid(VoronoiToken token) {
        VoronoiToken result = new VoronoiToken();
        int e1 = this.exponent;
        int e2 = token.exponent;
        int max = Math.max(e1,e2);
        int min = Math.min(e1,e2);
        int nmax = (max == e1 ? this.numerator : token.numerator);
        int nmin = (max == e1 ? token.numerator : this.numerator);
        int n=0,e=0;
        if(max == min) {
            n = nmax + nmin;
            int z = Integer.numberOfTrailingZeros(n);
            n = (n >> z);
            e = max - z + 1;
        } else {
            e = max + 1;
            n = nmax + (nmin << (max-min));       
        }
        result.exponent=e;
        result.numerator=n;
        return result;
    }
    
    @Override
    public int compareTo(VoronoiToken token) {
        int e1 = this.exponent;
        int e2 = token.exponent;
        int max = Math.max(e1,e2);
        int min = Math.min(e1,e2);
        int nmax = (max == e1 ? this.numerator : token.numerator);
        int nmin = (max == e1 ? token.numerator : this.numerator);
        int diff = nmax - (nmin << (max-min));  
        int result = ( max == e1 ?
                    (diff > 0 ? 1  : (diff < 0 ? -1 : 0)):
                    (diff > 0 ? -1 : (diff < 0 ? 1 : 0)));
        return result;
    }
  
    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }
    
}
