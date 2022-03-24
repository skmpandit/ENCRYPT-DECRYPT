import java.math.BigInteger;
import java.security.SecureRandom;
public class RSA{
private static final BigInteger one=new BigInteger("1");
private static final SecureRandom random=new SecureRandom();
	private BigInteger publicKey;
	private BigInteger privateKey;
	private BigInteger modulus;
	private BigInteger phi;	
	private static final int N=50;
	public RSA(){
	BigInteger p=BigInteger.probablePrime(N/2,random);
	BigInteger q=BigInteger.probablePrime(N/2,random);
		modulus=p.multiply(q);
		BigInteger x=p.subtract(one);
		BigInteger y=q.subtract(one);
		phi=x.multiply(y);
		publicKey=new BigInteger("65537");
		privateKey=publicKey.modInverse(phi);
	}
	public BigInteger encrypt(BigInteger original){
		BigInteger encrypted=original.modPow(publicKey,modulus);
		return encrypted;
	}
	public BigInteger decrypt(BigInteger encrypted){
		BigInteger decrypted=encrypted.modPow(privateKey,modulus);
		return decrypted;
	}
	public BigInteger getPrivateKey(){
		return privateKey;
	}
	public BigInteger getModulus(){
		return modulus;
	}
		
}