import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*; 


public class Task_7 {

    static String  plaintxt = "This is a top secret.";
    static byte[] IVB = hexStringToByteArray("aabbccddeeff00998877665544332211");
       static String ciphtertext = "764aa26b55a4da654df6b19e4bce00f4ed05e09346fb0e762583cb7da2ac93a2";
        byte[] Ci= hexStringToByteArray(ciphtertext);


        public static void main(String[] args) throws Exception {
            try {
               File myObj = new File("words.txt");
               Scanner myReader = new Scanner(myObj);
               while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 data = data.replace("\n", "");
                 if(data.length() < 16){
                   padding(data);
                 }
               }
               myReader.close();
             } catch (FileNotFoundException e) {
               System.out.println("An error occurred.");
               e.printStackTrace();
             } 
       }

        static public byte[] encryption(String plaintext, byte[] ivb, String key) {
            try{
                IvParameterSpec iv = new IvParameterSpec(ivb);
                SecretKeySpec sKeySpec = new SecretKeySpec(key.getBytes(), "AES");
                Cipher cy = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cy.init(Cipher.ENCRYPT_MODE, sKeySpec , iv);
                byte[] CipherText = cy.doFinal(plaintext.getBytes());
                return CipherText;
            }
            catch (Exception ex){
            }
            return null;       
            }


    private static String decryption(byte[] enc, byte[] iVB2, String key2) {
        try{
            IvParameterSpec iv2= new IvParameterSpec(iVB2);
            SecretKeySpec  skey2 = new SecretKeySpec(key2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE,skey2,iv2);
            byte[] oplaintext = cipher.doFinal(enc);
            return new String(oplaintext);
        }
        catch(Exception ex){

        }
        return null;
    }

    public static String bytesToHex(byte[] bytes){
        char[] HEX_array = "0123456789ABCDEF".toCharArray();
        char[] hexchars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexchars[i*2] = HEX_array[v >>> 4];
            hexchars[i*2+1] = HEX_array[v & 0x0F];
        }
        return new String(hexchars);
    } 

    
          

    public static void padding(String a){
        String temp = a;
        if(temp.length() < 16){
            for(int i = temp.length() ; i<16 ; i++)
            temp += "#";
        }
            try{
            String plaintext = "This is a top secret.";
                 byte[] encrypted = encryption(plaintext , IVB , temp);

                 if(encrypted !=null){
                 String encryptedHex = bytesToHex(encrypted);
                 if(encryptedHex.toLowerCase().equals("764aa26b55a4da654df6b19e4bce00f4ed05e09346fb0e762583cb7da2ac93a2")){

                    String plain = decryption(encrypted , IVB , temp);

                    if (plain != null){
                        System.out.println("plainText is : "+plain);
                        System.out.println("key is : " + temp);
                        String keyHex = bytesToHex(temp.getBytes());
                        System.out.println("key in HEX :" + keyHex);
                        System.out.println("cipherText in Hex is : "+encryptedHex);
                        return;
                    }
                    System.out.println("no key");
                    return;
                }
                 }else{

                        }

            }catch (Exception ex){

            } 
    }
    
    public static byte[] hexStringToByteArray(String s) {

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

 
}