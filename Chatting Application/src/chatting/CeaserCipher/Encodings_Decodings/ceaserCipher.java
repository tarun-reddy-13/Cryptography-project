package chatting.CeaserCipher.Encodings_Decodings;

public class ceaserCipher {
    public String ceaserEncoding(String text, int key){

        StringBuilder enc = new StringBuilder("");
        char [] text_chars = text.toCharArray();
        for(char ch:text_chars)
        {
            if(ch >= 'a' && ch <= 'z')
                enc.append(Character.toString((char) (((ch-97 + key)%26)+97)));
            else if(ch >= 'A' && ch <= 'Z')
                enc.append(Character.toString((char) (((ch-65 + key)%26)+65)));
            else if(ch>=' ' && ch<='/'){
                enc.append((char)(((ch-32 +key)%16)+32));
            }
            else if(ch>='0' && ch<='9')
                enc.append((char)(((ch+key)%10)+48));
            else
                enc.append(ch);
        }
        return enc.toString();
    }
    public String ceaserDecoding(String text, int key){

        StringBuilder enc = new StringBuilder("");
        char [] text_chars = text.toCharArray();
        int c;
        for(char ch:text_chars)
        {
            if(ch >= 'a' && ch <= 'z')
            {
                c = (ch-97 - key);
                if(c<0){
                    enc.append((char) ((c + 26) + 97));
                }
                else
                    enc.append((char) (((ch - 97 - key) % 26) + 97));
            }
            else if(ch >= 'A' && ch <= 'Z')
            {
                c = (ch-65 - key);
                if(c<0){
                    enc.append((char) ((c+26)+65));
                }
                else
                    enc.append((char) (((ch - 65 - key) % 26) + 65));
            }
            else if(ch>=' ' && ch<='/')
                enc.append((char)(((ch-32-key)%16)+32));
            else if(ch>='0' && ch<='9')
                enc.append((char)(((ch-key)%10)+48));
            else
                enc.append(ch);
        }
        return enc.toString();
    }

     public static void main(String[] args) {
        ceaserCipher c = new ceaserCipher();
        System.out.println(c.ceaserEncoding("01239",5));
        System.out.println(c.ceaserDecoding("34569",5));
    }     
}
