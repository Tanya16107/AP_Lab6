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

class Coordinate{
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return String.valueOf(x)+" "+String.valueOf(y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}

abstract class Player{
    protected String name;
    protected Coordinate p;

    public Player(String name, Coordinate p){
        this.name = name;
        this.p = p;
    }

    public String getName(){
        return name;
    }

    public Coordinate getCoordinates(){
        return p;
    }

    public boolean sameCoordinate(Coordinate x){
        return (p.getX() == x.getX() && p.getY() == x.getY());
    }

    @Override
    public String toString(){
        return name +" "+p.toString();
    }

}

class Knight extends Player{
    private Stack<Object> magicBox = new Stack<Object>();
    private boolean removed = false;

    public Knight(String name, Coordinate p){
        super(name, p);
    }

    public void add(Object o){
        magicBox.push(o);
    }

    public Object getPopped(){
        if(!magicBox.empty()){
        return magicBox.pop();
        }
        else{
            return null;
        }

    }

    public void remove(){
        removed = true;
    }

    public boolean isRemoved(){
        return removed;
    }

    public void play(PrintWriter w, Knight[] arr, Queen queen) throws StackEmptyException, NonCoordinateException, OverlapException, QueenFoundException{
        Object popped = this.getPopped();
        if(popped==null){
            this.remove();
            throw new StackEmptyException("Stack​ ​Empty​ ​exception");
        }

        if(popped instanceof String || popped instanceof Integer || popped instanceof Float){
            throw new NonCoordinateException("Not a coordinate Exception "+popped.toString());
        }
                
        if(popped instanceof Coordinate){
            Coordinate _popped = (Coordinate)popped ;
            p.setX(_popped.getX());
            p.setY(_popped.getY());

            for(int i=0; i<arr.length; i++){
                if(!arr[i].isRemoved()){
                    if(!name.equals(arr[i].getName())){
                        if(this.sameCoordinate(arr[i].getCoordinates())){

                            arr[i].remove();
                            throw new OverlapException("Knights​ ​Overlap​ ​Exception "+arr[i].getName());
                   
                        }
                    }
                }
            }

            if(this.sameCoordinate(queen.getCoordinates())){
                throw new QueenFoundException("Queen​ ​has​ ​been​ ​Found.​ ​Abort!");
            
            }
        }
                
    }
}






class Queen extends Player{
    boolean found = false;
    public Queen(String name, Coordinate p){
        super(name, p);
    }
    public void milGayi(){
        found = true;
    }

    public boolean found(){
        return found;
    }

}

class NonCoordinateException extends Exception{
    public NonCoordinateException(String message){
        super(message);
    }
}
class StackEmptyException extends Exception{
    public StackEmptyException(String message){
        super(message);
    }
}
class OverlapException extends Exception{
    public OverlapException(String message){
        super(message);
    }
}
class QueenFoundException extends Exception{
    public QueenFoundException(String message){
        super(message);
    }
}

class App{
    public static void main(String[] args) throws IOException, StackEmptyException, NonCoordinateException, OverlapException, QueenFoundException {

        Reader rd = new Reader();
        PrintWriter w = new PrintWriter(new FileWriter("./answer.txt"), true );
        

        System.out.println("Enter number of knights");
        int numKnights = rd.nextInt();
        System.out.println("Enter number of iterations");
        int nIterations = rd.nextInt();
        System.out.println("Enter coordinates of Queen");
        int x = rd.nextInt();
        int y = rd.nextInt();
        Queen queen = new Queen("Queen", new Coordinate(x, y));


        Knight[] knightsArray = new Knight[numKnights];


        for(int k = 1; k<=numKnights; k++){
        FileReader reader = new FileReader("./" + k + ".txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String name = bufferedReader.readLine();
        String line = bufferedReader.readLine();
        String[] arr = line.split(" ");
        Coordinate p = new Coordinate(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
        Knight newKnight = new Knight(name, p);
        int m = Integer.valueOf(bufferedReader.readLine());
        for(int i=0; i<m; i++){
            line = bufferedReader.readLine();
            arr = line.split(" ");
            if(arr[0].equals("Coordinate")){
                p = new Coordinate(Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
                newKnight.add(p);
            }
            else{
                newKnight.add(arr[1]);
            }
        }
        knightsArray[k-1] = newKnight;

        }

        for(int i=0; i<numKnights-1; i++){
            for(int j = 0; j<numKnights-1-i; j++){
                if(knightsArray[j].getName().compareTo(knightsArray[j+1].getName())>0){
                    Knight temp = knightsArray[j];
                    knightsArray[j] = knightsArray[j+1];
                    knightsArray[j+1] = temp;
                }
            }

        }



        int iter = 0;


        while(iter<=nIterations && numKnights>0 && !queen.found()){
            iter++;
            for(int i=0; i<knightsArray.length; i++){

                if(!knightsArray[i].isRemoved()){
                w.println(iter+" "+knightsArray[i].toString());
                boolean success = false;
                try{
                    knightsArray[i].play(w, knightsArray, queen);
                    success = true;
                }
                catch(NonCoordinateException e){
                    w.println(e); 

                }
                catch(StackEmptyException e){
                    w.println(e); 

                }
                catch(OverlapException e){
                    w.println(e);
                }

                catch(QueenFoundException e){
                    queen.milGayi();
                    w.println(e);
                }
                if(success){
                    w.println("No exception "+knightsArray[i].getCoordinates().toString());
        
                }

                }

            
            }
            
        }

        
        
    }
}