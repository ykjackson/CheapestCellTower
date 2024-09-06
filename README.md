Here we model a city map containing cell towers using Point Quadtrees. Each cell tower has a location and a service cost, and the task is to efficiently find the cheapest cell tower within a specified distance from a query point on a 2D plane.
Quadtree data structure used to represent points in a 2D space by recursively dividing the plane into quadrants. Each node stores a point and has four children, representing NE, NW, SE, and SW quadrants.

Main Features are as follows:-

Insertion: Efficiently inserts a CellTower into the quadtree based on its coordinates.

Cheapest Tower Search: Given a query point and radius, finds the cell tower with the lowest cost within that distance.

Existence Check: Checks if a cell tower exists at a specific coordinate.

CellTower Class:

int x: X-coordinate of the cell tower.

int y: Y-coordinate of the cell tower.

int cost: Service cost of the cell tower.

double distance(int b, int c): Computes the Euclidean distance from the tower to point (b, c).

PointQuadtree Class:

insert(CellTower a): Inserts a cell tower into the quadtree.

cellTowerAt(int x, int y): Checks if a tower exists at (x, y).

chooseCellTower(int x, int y, int r): Returns the cheapest tower within distance r from (x, y).

Time Complexity: O(n)

