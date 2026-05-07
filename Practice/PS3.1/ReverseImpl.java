import ReverseModule.*;

public class ReverseImpl extends ReversePOA{
    public String reverse_string(String str){
        return new StringBuilder(str).reverse().toString();
    }
}
