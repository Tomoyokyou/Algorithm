/**
 ** Java program to implement Hopcroft-Karp Alg
 **/

 import java.util.*;

 /** Class Hopcroft-Karp **/
 public class HopcroftKarpAlg
 {
   private final int NIL = 0;
   private final int INF = Integer.MAX_VALUE;
   private ArrayList<Integer>[] adj;
   private int[] Pair;
   private int[] Dist;
   private int cx, cy;

   public boolean BFS(){
      Queue<Integer> q = new LinkedList<Integer>();
      for(int v = 1; v<=cx; v++){
        if(Pair[v] == NIL){
          Dist[v] = 0;
          q.add(v);
        }
        else{
          Dist[v] = INF;
        }
      }
      Dist[NIL] = INF;
      while(!q.isEmpty()){
        int v = q.poll();
        if(Dist[v]<Dist[NIL]){
          for(int u: adj[v]){
            if(Dist[Pair[u]]==INF){
              Dist[Pair[u]] = Dist[v] + 1;
              q.add(Pair[u]);
            }
          }
        }
      }

      return Dist[NIL]!=INF;
   }

   public boolean DFS(int v){
     if(v!=NIL){
       for(int u: adj[v]){
         if(Dist[Pair[u]] == Dist[v] + 1){
           if(DFS(Pair[u])){
             Pair[u] = v;
             Pair[v] = u;
             return true;
           }
         }
       }
       Dist[v] = INF;
       return false;
     }
     return true;
   }

   public int HopcroftKarp(){
     Pair = new int[cx + cy + 1];
     Dist = new int[cx + cy + 1];
     int matching = 0;

     while (BFS()){
       for(int v = 1; v<=cx; v++){
         if(Pair[v] == NIL){
           if(DFS(v)){
             matching++;
           }
         }
       }
     }


     return matching;
   }

   public void makeGraph(int[] x, int[] y, int E){
      adj = new ArrayList[cx + cy + 1];
      for(int i=0; i<adj.length; i++){
        adj[i] = new ArrayList<Integer>();
      }
      for(int i=0;i<E;i++){
        addEdge(x[i]+1, y[i]+1);
      }
   }
   public void addEdge(int u, int v){
     adj[u].add(cx + v);
     adj[cx + v].add(u);
   }
    public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      System.out.println("Hopcroft Algorithm Test\n");
      HopcroftKarpAlg hck = new HopcroftKarpAlg();

      System.out.println("Enter number of edges\n");
      int E = sc.nextInt();
      int[] x = new int[E];
      int[] y = new int[E];
      hck.cx = 0;
      hck.cy = 0;

      System.out.println("Enter "+ E +" x, y coordinates ");
      for(int i=0;i<E;i++){
        x[i] = sc.nextInt();
        y[i] = sc.nextInt();
        hck.cx = Math.max(hck.cx, x[i]);
        hck.cy = Math.max(hck.cy, y[i]);
      }
      hck.cx +=1;
      hck.cy +=1;

      hck.makeGraph(x, y, E);

      System.out.println("\nMatches : "+ hck.HopcroftKarp());

    }
 }
