package cs.cooble.nice.chat;

import java.util.ArrayList;

/**
 * Created by Matej on 5.4.2015.
 */
public class SlovoManager {


    public static String getSlovoFrom(String veta,int indexslova){
        if(veta.indexOf(' ')==-1)
            return "";
        else {
            if(!(getPocetSlov(veta)>indexslova))
                throw new IllegalArgumentException("Veta "+veta+" nemá slovo s indexem "+indexslova);
            if(!veta.endsWith(" "))
                veta+=" ";
            if(!veta.startsWith(" "))
                veta=" "+veta;
            int slovoindex=0;
            int startindex=-1;
            int endindex=-1;

            for (int i = 0; i < veta.length(); i++) {
                if(veta.charAt(i)==' '){
                    slovoindex++;
                    if(veta.length()!=i+1)
                        if(veta.charAt(i+1)==' ')
                            i++;
                    if(slovoindex==indexslova+1&&startindex==-1)
                        startindex=i+1;
                    else if(startindex!=-1) {
                        endindex = i;
                        break;
                    }
                }
            }
            return veta.substring(startindex,endindex);
        }
    }
    public static String getSlovoFromCarkovanaVeta(String veta,int indexslova){
        if(veta.indexOf(',')==-1)
            return "";
        else {
            int slovoindex=0;
            int startindex=-1;
            int endindex=-1;

            for (int i = 0; i < veta.length(); i++) {
                if(veta.charAt(i)==','){
                    slovoindex++;
                    if(veta.length()!=i+1)
                        if(veta.charAt(i+1)==',')
                            i++;
                    if(slovoindex==indexslova+1&&startindex==-1)
                        startindex=i+1;
                    else if(startindex!=-1) {
                        endindex = i;
                        break;
                    }
                }
            }
            return veta.substring(startindex,endindex);
        }
    }
    public static String[] divideVeta(String veta, char znakmezi){
        ArrayList<String> substrings=new ArrayList<>();
        boolean readingword=false;
        if(!veta.startsWith(znakmezi+"")){
            veta=znakmezi+veta;
        }
        if(!veta.endsWith(znakmezi+"")){
            veta+=znakmezi;
        }
        int start=0;
        for (int i = 0; i < veta.length(); i++) {
            //Log.println("ctuchar "+veta.charAt(i));
            if(veta.charAt(i)==znakmezi) {
                if (!readingword){
                   // Log.println("start reading");
                    start=i;
                    readingword=true;
                }
                else {
                   // Log.println("stop reading "+veta.substring(start+1,i));
                   // Log.println("Start reading");
                    readingword=true;
                    substrings.add(veta.substring(start+1,i));
                    start=i;

                }
            }
        }
        return substrings.toArray(new String[substrings.size()]);
    }
    public static int getPocetSlov(String veta){
        if(veta.endsWith(" "))
            veta=veta.substring(0,veta.length()-1);
        if(veta.startsWith(" "))
            veta=veta.substring(1);
        if(veta.indexOf(' ')==-1)
            return 1;

        int pocet = 0;
        for (int i = 0; i < veta.length(); i++) {
            if(veta.charAt(i)==' '){
                pocet++;
                if(veta.charAt(i+1)==' ')
                    i++;
            }
        }
        return pocet+1;
    }
    public static String removeKolemMezery(String veta){
        if(veta.endsWith(" "))
            veta=veta.substring(0,veta.length()-1);
        if(veta.startsWith(" "))
            veta=veta.substring(1);
        return veta;
    }
    public static String addKolemMezery(String veta){
        if(!veta.endsWith(" "))
            veta+=" ";
        if(!veta.startsWith(" "))
            veta=" "+veta;
        return veta;
    }
    public static String removeSlovoFromVeta(String veta,int indexslova){
        if(veta.indexOf(' ')==-1)
            return veta;
        else {
            if(!(getPocetSlov(veta)>indexslova))
                return veta;
            veta= addKolemMezery(veta);
            int slovoindex=0;
            int startindex=-1;
            int endindex=-1;

            for (int i = 0; i < veta.length(); i++) {
                if(veta.charAt(i)==' '){
                    slovoindex++;
                    if(slovoindex==indexslova+1&&startindex==-1)
                        startindex=i+1;
                    else if(startindex!=-1) {
                        endindex = i;
                        break;
                    }
                }
            }
            String prefix=veta.substring(0,startindex);
            String suffix = veta.substring(endindex+1);

            return removeKolemMezery(prefix + suffix);
        }
    }

    // Suffixes
    public static String removeSuffix(String filename){
        if(filename.indexOf('.')==-1)
            return filename;
        return filename.substring(0,filename.indexOf('.'));
    }
    public static String getSuffix(String filename){
        if(filename.indexOf('.')==-1)
            return filename;
        return filename.substring(filename.indexOf('.'));
    }
}
