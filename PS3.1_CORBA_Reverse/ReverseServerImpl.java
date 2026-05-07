import ReverseModule.*;


class ReverseServerImpl extends ReversePOA{
    public String reverse_string(String str){
        return new StringBuilder(str).reverse().toString();
    }
}