/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 *
 * @author Ishtiaque
 */

/*
The program gets id from user input
and prints names of friends and names of each friend's friend  
I used external json-simple library for java which is in libraries folder
to use jsonparse ,jsonarray and other funcitons


*/
public class Json_test {
    public static void  print_friends(JSONArray ara) //prints  friends of friends
    {
        for(int i=0;i<ara.size();i++)
        {
            long friend_id=(Long)ara.get(i);
            try
            {
            
                String line=connection(friend_id);
                String temp=print_name(line);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public static String connection(long  id)throws Exception //sets up connection with the link and returns data in string format
    {
            URL url=new URL("http://fg-69c8cbcd.herokuapp.com/user/"+id);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode=conn.getResponseCode();
          //  System.out.println(responsecode);
            Scanner sc=new Scanner(url.openStream());
            String inline="";
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            sc.close();
            return inline;
    }
    public static JSONArray get_id_array(String s)throws Exception //used to extract the frinds array from json object data
    {
            JSONParser p=new JSONParser();
            JSONObject obj = (JSONObject)p.parse(s);
            //String to=obj.get("name").toString();
          //  System.out.println(to);
            JSONArray arr=(JSONArray)obj.get("friends");
            //System.out.println(arr.get(1));
            return arr;
        
    }
    public static String print_name(String s)throws Exception //prints the name element  of json object 
    {
            JSONParser p=new JSONParser();
            JSONObject obj = (JSONObject)p.parse(s);
            String to=obj.get("name").toString();
            System.out.println(to);
            return to;
    }
    public static void printall(long id)//initiates the whole operation
    {
        try{
        String line=connection(id);
        System.out.print("User name : ");
        String user=print_name(line);
      //  System.out.println("User name : "+user);
        JSONArray parent=get_id_array(line);
            for(int i=0;i<parent.size();i++)
            {
                long s=(Long)parent.get(i);
               // System.out.println("friend id "+s);
                String level=connection(s);
                System.out.println("Friend's name :");
                String friend_name=print_name(level);
                JSONArray child=get_id_array(level);
                System.out.println("Friends of "+friend_name+" : ");
                print_friends(child);

            }
        
    
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
     }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            System.out.println("Enter id :");
            Scanner sc=new Scanner(System.in);
            long k=sc.nextLong();
            printall(k);
            //String data=connection(k);
            
            //System.out.println(obj.);
            // Iterator<String>keys=obj
            
        }
        catch(Exception e)// TODO code application logic here
        {
            e.printStackTrace();
        }
    }
    
}
