public class Algorithm {

    public static boolean DFSColorFC(DigraphAM am){
        int[] lista = new int[am.size+1];
        return DFSColorFCAux(am, am.getFirst(), lista, 1);
    }
    private static boolean DFSColorFCAux(DigraphAM am, int nodo, int[]lista, int color){
        boolean col = true;
        if(lista[nodo] == 0){
            lista[nodo]=color;
            for(Integer s: am.getSuccessors(nodo)){
                if(color==1){
                    col = DFSColorFCAux(am, s, lista, 2);
                }else{
                    col = DFSColorFCAux(am, s, lista, 1); 
                }
                if(!col) return false;
            }
        }else{
         if(color == lista[nodo]){
                return true;
         }else{
                return false;
            }
        }
        return col;
    }
}
