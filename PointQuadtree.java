public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    public boolean insert(CellTower a) {
        if(cellTowerAt(a.x, a.y)){
            return false;
        }
        this.root=helpinsert(a, this.root);
        return true;
    }

    private PointQuadtreeNode helpinsert(CellTower a, PointQuadtreeNode root1){
        if(root1==null){
            root1=new PointQuadtreeNode(a);
            return root1;
        }
        else{
            if(root1.celltower.x>=a.x && root1.celltower.y>a.y){
                root1.quadrants[2]=helpinsert(a, root1.quadrants[2]);
            }
            else if(root1.celltower.x>a.x && root1.celltower.y<=a.y){
                root1.quadrants[0]=helpinsert(a, root1.quadrants[0]);
            }
            else if(root1.celltower.x<a.x && root1.celltower.y>=a.y){
                root1.quadrants[3]=helpinsert(a, root1.quadrants[3]);
            }
            else if(root1.celltower.x<=a.x && root1.celltower.y<a.y){
                root1.quadrants[1]=helpinsert(a, root1.quadrants[1]);
            }
            return root1;
        }
    }

    public boolean cellTowerAt(int x, int y) {
        return helpcelltowerAt(x, y, this.root);
    }

    private boolean helpcelltowerAt(int x, int y, PointQuadtreeNode root1){
        if(root1==null){
            return false;
        }
        else if(root1.celltower.x==x && root1.celltower.y==y){
            return true;
        }
        else{
            if(root1.celltower.x>=x && root1.celltower.y>y){
                root1=root1.quadrants[2];
            }
            else if(root1.celltower.x>x && root1.celltower.y<=y){
                root1=root1.quadrants[0];
            }
            else if(root1.celltower.x<x && root1.celltower.y>=y){
                root1=root1.quadrants[3];
            }
            else if(root1.celltower.x<=x && root1.celltower.y<y){
                root1=root1.quadrants[1];
            }
            return helpcelltowerAt(x,y, root1);
        }
    }

    private int cost(CellTower a){
        if(a==null){
            return Integer.MAX_VALUE;
        }
        return a.cost;
    }

    public CellTower chooseCellTower(int x, int y, int r) {
        if(this.root==null){
            return null;
        }
        return helpchooseCell(x, y, r, this.root);
    }

    private CellTower helpchooseCell(int x, int y, int r, PointQuadtreeNode root1){
        if(root1==null){
            return null;
        }
        CellTower zerochild=null,secondchild=null,thirdchild=null,firstchild=null;
        double dis=root1.celltower.distance(x, y);
        if(dis<r){
            if(root1.quadrants[0]!=null || root1.quadrants[1]!=null || root1.quadrants[2]!=null || root1.quadrants[3]!=null){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
                firstchild=helpchooseCell(x,y,r,root1.quadrants[1]);
                secondchild=helpchooseCell(x,y,r,root1.quadrants[2]);
                thirdchild=helpchooseCell(x,y,r,root1.quadrants[3]);
                CellTower ans=null;
                if(cost(zerochild)>cost(firstchild)){
                    ans=firstchild;
                }
                else{
                    ans=zerochild;
                }
                if(cost(ans)>cost(secondchild)){
                    ans=secondchild;
                }
                if(cost(ans)>cost(thirdchild)){
                    ans=thirdchild;
                }
                if(cost(ans)>cost(root1.celltower)){
                    ans=root1.celltower;
                }
                return ans;
            }
            else{
                if(dis<=r){
                    return root1.celltower;
                }
                return null;
            }
        }
        else{
            if(root1.celltower.x<x+r && root1.celltower.x<x-r && root1.celltower.y>=y+r && root1.celltower.y>y-r){
                thirdchild=helpchooseCell(x,y,r,root1.quadrants[3]);
            }
            else if(root1.celltower.x<x+r && root1.celltower.x<=x-r && root1.celltower.y<y+r && root1.celltower.y<y-r){
                firstchild=helpchooseCell(x,y,r,root1.quadrants[1]);
            }
            else if(root1.celltower.x>x+r && root1.celltower.x>x-r && root1.celltower.y<y+r && root1.celltower.y<=y-r){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
            }
            else if(root1.celltower.x>=x+r && root1.celltower.x>x-r && root1.celltower.y>y+r && root1.celltower.y>y-r){
                secondchild=helpchooseCell(x,y,r,root1.quadrants[2]);
            }
            else if(root1.celltower.x<x+r && root1.celltower.x<=x-r && root1.celltower.y<y+r && root1.celltower.y>=y-r){
                thirdchild=helpchooseCell(x,y,r,root1.quadrants[3]);
                firstchild=helpchooseCell(x,y,r,root1.quadrants[1]);
            }
            else if(root1.celltower.x<=x+r && root1.celltower.x>x-r && root1.celltower.y<y+r && root1.celltower.y<=y-r){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
                firstchild=helpchooseCell(x, y, r, root1.quadrants[1]);
            }
            else if(root1.celltower.x>=x+r && root1.celltower.x>x-r && root1.celltower.y<=y+r && root1.celltower.y>y-r){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
                secondchild=helpchooseCell(x, y, r, root1.quadrants[2]);
            }
            else if(root1.celltower.x<x+r && root1.celltower.x>=x-r && root1.celltower.y>=y+r && root1.celltower.y>y-r){
                secondchild=helpchooseCell(x,y,r,root1.quadrants[2]);
                thirdchild=helpchooseCell(x, y, r, root1.quadrants[3]);
            }
            else if(root1.celltower.x<x && root1.celltower.y<y){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
                firstchild=helpchooseCell(x, y, r, root1.quadrants[1]);
                thirdchild=helpchooseCell(x, y, r, root1.quadrants[3]);
            }
            else if(root1.celltower.x<x && root1.celltower.y>y){
                secondchild=helpchooseCell(x,y,r,root1.quadrants[2]);
                firstchild=helpchooseCell(x, y, r, root1.quadrants[1]);
                thirdchild=helpchooseCell(x, y, r, root1.quadrants[3]);
            }
            else if(root1.celltower.x>x && root1.celltower.y<y){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
                firstchild=helpchooseCell(x, y, r, root1.quadrants[1]);
                secondchild=helpchooseCell(x, y, r, root1.quadrants[2]);
            }
            else if(root1.celltower.x>x && root1.celltower.y>y){
                zerochild=helpchooseCell(x,y,r,root1.quadrants[0]);
                secondchild=helpchooseCell(x, y, r, root1.quadrants[2]);
                thirdchild=helpchooseCell(x, y, r, root1.quadrants[3]);
            }
            CellTower ans=null;
            if(cost(zerochild)>cost(firstchild)){
                ans=firstchild;
            }
            else{
                ans=zerochild;
            }
            if(cost(ans)>cost(secondchild)){
                ans=secondchild;
            }
            if(cost(ans)>cost(thirdchild)){
                ans=thirdchild;
            }
            if(cost(ans)>cost(root1.celltower) && dis==r){
                ans=root1.celltower;
            }
            return ans;
        }
   }
}
