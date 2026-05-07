import java.rmi.Remote;

public interface VowelServerInterface extends Remote{
    public int count_vowel(String str);
}
