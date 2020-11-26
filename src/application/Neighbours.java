package application;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Neighbours {
//	Point p = new Point();
	ArrayList<Point2D> visited = new ArrayList<>();

//	int x, y;
	int width, height;
	Point2D p;

	public Neighbours(Point2D p, int width, int height, ArrayList<Point2D> visited) {
//		this.x = x;
//		this.y = y;
		this.width = width;
		this.height= height;
		this.p = p;
		this.visited = visited;
	}

	public ArrayList<Point2D> getNeighbours() {
		ArrayList<Point2D> neighbours = new ArrayList<>();
		getOneNeighbours(new Point2D(p.getX() - 2, p.getY()), neighbours);
		getOneNeighbours(new Point2D(p.getX() + 2, p.getY()), neighbours);
		getOneNeighbours(new Point2D(p.getX(), p.getY() - 2), neighbours);
		getOneNeighbours(new Point2D(p.getX(), p.getY() + 2), neighbours);
//		System.out.println(neighbours);
		return neighbours;
	}

	private ArrayList<Point2D> getOneNeighbours(Point2D newPoint, ArrayList<Point2D> neighbours) {
//		System.out.println("p="+p);
//		System.out.println("h="+y);
//		System.out.println(newPoint);
//		System.out.println(isVisited(newPoint));
//		System.out.println(isNotBorder(newPoint));
		if (isVisited(newPoint) && isNotBorder(newPoint))
			neighbours.add(newPoint);
//		System.out.println(neighbours);
		return neighbours;
	}

	private boolean isVisited(Point2D p) {
		return visited.contains(p) ? false : true;
	}

	private boolean isNotBorder(Point2D p) {
		boolean bool = false;
		if(p.getX() > 0 && p.getX() < width && p.getY() > 0 && p.getY() < height)
			bool = true;
		
//		return (p.getX() > 0 || p.getX() < width || p.getY() > 0 || p.getY() < height) ? true : false;
		return bool;
	}

//public int[][] getNeighbours(int x, int y, int width, int height) {
//	int size=4;
//	if (x-1==0 || x+1==width) {size--;}	//0  //2
//	if(y-1==0 || y+1==height) {size--;}		//1  //3	
//	
//	int[][] neighbours = new int[size][2];
//	if(size!=4) {
//			
//		 if(x-1==0 && y-1==0) {
//	    		neighbours[0][0]=x+2;	neighbours[0][1]=y;		//2
//	    		neighbours[1][0]=x;		neighbours[1][1]=y+2;	//3
//		 }else if(x+1==width && y+1==height) {
//			 		neighbours[0][0]=x-2;	neighbours[0][1]=y;		//0
//			 		neighbours[1][0]=x;		neighbours[1][1]=y+2;	//3
//		 }else if(x+1==width && y-1==0) {
//	 			neighbours[0][0]=x-2;	neighbours[0][1]=y;		//0
//	 			neighbours[1][0]=x;		neighbours[1][1]=y-2;	//1
//		 }else if(y+1==height && x-1==0) {
//	 			neighbours[0][0]=x+2;	neighbours[0][1]=y;		//2
//	 			neighbours[1][0]=x;		neighbours[1][1]=y+2;	//3
//		 }
//		 
//		 if(x<0 && y+1!=height && y-1!=0) {
//		 		neighbours[0][0]=x;		neighbours[0][1]=y-2;	//1
//	    		neighbours[1][0]=x+2;	neighbours[1][1]=y;		//2
//	    		neighbours[2][0]=x;		neighbours[2][1]=y+2;	//3
//		 }
//		 if(y<0 && x+1!=width && x-1!=0) {
//		 		neighbours[0][0]=x-2;	neighbours[0][1]=y;		//0
//		 		neighbours[1][0]=x;		neighbours[1][1]=y-2;	//1
//		 		neighbours[2][0]=x+2;	neighbours[2][1]=y;		//2
//		 }
//		 
//		 if(x>0 && y+1!=height && y-1!=0) {
//			 	neighbours[0][0]=x-2;	neighbours[0][1]=y;		//0
//	    		neighbours[1][0]=x;		neighbours[1][1]=y-2;	//1
//	    		neighbours[2][0]=x;		neighbours[2][1]=y+2;	//3
//		 }
//		 if(y>0 && x+1!=width && x-1!=0) {
//		 			neighbours[0][0]=x-2;	neighbours[0][1]=y;		//0
//		 			neighbours[1][0]=x+2;	neighbours[1][1]=y;		//2
//		 			neighbours[2][0]=x;		neighbours[2][1]=y+2;	//3
//		 }
//		 
//		
//		
//	}else {
//		neighbours[0][0]=x-2;	neighbours[0][1]=y;		//0
//		neighbours[1][0]=x;		neighbours[1][1]=y-2;	//1
//		neighbours[2][0]=x+2;	neighbours[2][1]=y;		//2
//		neighbours[3][0]=x;		neighbours[3][1]=y+2;	//3
//		
//	}
//	
//	return neighbours;
//}
}
