import math
from matplotlib import pyplot as plt

# Sample points
pts = [(1, 7), (2, 1), (3, 4), (6, 5), (7, 2), (8, 6), (9, 3)]

def euclidean_distance(point1, point2):
"""
Calculate Euclidean distance between two points.
"""
return math.sqrt((point1[0] - point2[0]) ** 2 + (point1[1] - point2[1]) ** 2)

def bt4(points):
"""
Compute the bitonic tour distances and path trackers.
Returns distance matrix, tracker, and lines.
"""
n = len(points)
bitonic_distance = [[0 for _ in range(n)] for _ in range(n)]
tracker = [[[] for _ in range(n)] for _ in range(n)]
lines = [[[] for _ in range(n)] for _ in range(n)]

```
inner_loop_count = 0
outer_loop_count = 0

# Initialize distances for the first row
for i in range(1, n):
    inner_loop_count += 1
    bitonic_distance[0][i] = bitonic_distance[0][i - 1] + euclidean_distance(points[i - 1], points[i])
    tracker[0][i] = [0, i - 1]
    lines[0][i] = [i - 1, i]

# Fill the DP tables
for i in range(1, n):
    for j in range(i, n):
        minimum = math.inf
        if i == j or i == j - 1:
            for k in range(i):
                outer_loop_count += 1
                value = bitonic_distance[k][i] + euclidean_distance(points[k], points[j])
                if value < minimum:
                    minimum = value
                    tracker[i][j] = [k, i]
                    lines[i][j] = [k, j]
            bitonic_distance[i][j] = minimum
        else:
            inner_loop_count += 1
            bitonic_distance[i][j] = bitonic_distance[i][j - 1] + euclidean_distance(points[j - 1], points[j])
            tracker[i][j] = [i, j - 1]
            lines[i][j] = [j - 1, j]

print(f"Inner loop iterations: {inner_loop_count}, Outer loop iterations: {outer_loop_count}")
return bitonic_distance, tracker, lines

def add_line_by_pt(pt1, pt2):
"""
Draw a line between two points on the plot.
"""
plt.plot((pt1[0], pt2[0]), (pt1[1], pt2[1]), 'ro-')

def generate_plot(points, tracker, lines):
"""
Reconstruct the bitonic path and visualize it.
"""
n = len(points)
start_end_point_index = lines[n - 1][n - 1]
pt1, pt2 = start_end_point_index
coord = tracker[n - 1][n - 1]
add_line_by_pt(points[pt1], points[pt2])

while n > 1:
    start_end_point_index = lines[coord[0]][coord[1]]
    pt1, pt2 = start_end_point_index
    coord = tracker[coord[0]][coord[1]]
    add_line_by_pt(points[pt1], points[pt2])
    n -= 1

plt.scatter([p[0] for p in points], [p[1] for p in points], c='blue')
plt.title("Bitonic TSP Path")
plt.show()

def main():
distance, tracker, lines = bt4(pts)
generate_plot(pts, tracker, lines)

if **name** == "**main**":
main()