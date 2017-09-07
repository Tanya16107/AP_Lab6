//Tanya Gupta 
//2016107
import java.util.*;
import java.io.*;


/*Reader class*/
class Reader
{
    BufferedReader br;
    StringTokenizer tk;
    Reader() throws IOException
    {
        br=new BufferedReader(new InputStreamReader(System.in));
        tk=new StringTokenizer("");
    }
    public int nextInt() throws IOException
    {
        while(!tk.hasMoreTokens())
        {
            tk=new StringTokenizer(br.readLine().replaceAll("\r",""));
        }
        return Integer.parseInt(tk.nextToken());
    }
    public Float nextFloat() throws IOException
    {
        while(!tk.hasMoreTokens())
        {
            tk=new StringTokenizer(br.readLine().replaceAll("\r",""));
        }
        return Float.parseFloat(tk.nextToken());
    }
    public String next() throws IOException
    {
        return br.readLine();
    }
}

class App{
    public static void main(String[] args) throws IOException {
        
    }
}