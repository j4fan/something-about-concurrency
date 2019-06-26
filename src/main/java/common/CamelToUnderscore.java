package common;

public class CamelToUnderscore {

    private String CamelToUnderScore(String camelString){
        StringBuffer sb = new StringBuffer();
        String lowerString = getLowerCaseString(camelString);
        sb.append(lowerString.substring(0,1));
        for(int i=1;i<camelString.length();i++){
            String formal = camelString.substring(i,i+1);
            String letter = lowerString.substring(i,i+1);
            if(!formal.equals(letter)){
                sb.append("_").append(letter);
            }else{
                sb.append(letter);
            }
        }
        return sb.toString();
    }

    private String getLowerCaseString(String str){
        return str.toLowerCase();
    }

    public static void main(String[] args) {
        String camelString = "createTime";
        System.out.println(new CamelToUnderscore().CamelToUnderScore(camelString));
    }
}
