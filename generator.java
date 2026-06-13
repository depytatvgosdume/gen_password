import java.util.*;

public class generator{
    //public static void mass(char max[]){
        //for (int i = 0; i < max.length; i++){
            //System.out.print(max[i]);
        //}
        //System.out.println("");
    //}//
    public static boolean proverka(char g){
        if (g == ' ' || g == '/' || g == '@' || g == '?'){
            return false;
        }
        return true;
    }
    public static String gen(int len_pass){
        String slovo = "";
        char word;
        int max = 122, min = 48;
        //char mass[] = new char[len_pass];
        for (int i = 0; i < len_pass; i++){
            word = ((char)(min + (int) (((max - min) + 1) * Math.random())));
            if (proverka(word)){
                slovo = slovo + String.valueOf(word);
            }
            else{
                i = i - 1;
            }
        }
        
        
        
        return slovo;
    }
    public static String proverka(String mass){
        boolean hasDigit = false, hasLower = false, hasUpper = false;
        if (mass.length() < 6){
            return "Пароль не защищён";
        }
        if (mass.length() < 10){
            return "Пароль средний";
        }
        for( char c : mass.toCharArray()){
            if (Character.isDigit(c)) hasDigit = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isUpperCase(c)) hasUpper = true;
        }
        return hasDigit && hasLower && hasUpper? "Пароль средний" : "Пароль хорошо защищён";
    }
    
}