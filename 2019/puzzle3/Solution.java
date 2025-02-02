import java.io.*;
import java.util.*;

class Solution {
  static class LineSegment {
    int x1, y1, x2, y2;
    char type;

    LineSegment(int x1, int y1, int x2, int y2) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.type = x1 == x2 ? 'V' : 'H';
    }
  }

  static class Intersection {
    int x, y;
    int stepsW1, stepsW2;

    Intersection(int x, int y, int stepsW1, int stepsW2) {
      this.x = x;
      this.y = y;
      this.stepsW1 = stepsW1;
      this.stepsW2 = stepsW2;
    }
  }

  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("problemIP.txt"))) {
      String[] wire1 = br.readLine().trim().split(",");
      String[] wire2 = br.readLine().trim().split(",");

      List<LineSegment> wireLineSegments1 = parseWire(wire1);
      List<LineSegment> wireLineSegments2 = parseWire(wire2);

      List<int[]> intersections = findIntersections(wireLineSegments1, wireLineSegments2);

      int minDist = Integer.MAX_VALUE;
      int minSteps = Integer.MAX_VALUE;
      for (int[] point : intersections) {
        Intersection intersection = getStepsForIntersection(point, wireLineSegments1, wireLineSegments2);
        int totalSteps = intersection.stepsW1 + intersection.stepsW2;
        int dist = manhattanDistance(point[0], point[1]);
        minDist = dist == 0 ? minDist : Math.min(minDist, dist);
        minSteps = totalSteps == 0 ? minSteps : Math.min(minSteps, totalSteps);
      }

      System.out.println("Part 1: " + minDist);
      System.out.println("Part 2: " + minSteps);
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
  }

  static List<LineSegment> parseWire(String[] wire) {
    ArrayList<LineSegment> segments = new ArrayList<>();
    int x1 = 0, y1 = 0;
    for (String move : wire) {
      char dir = move.charAt(0);
      int dist = Integer.parseInt(move.substring(1));
      int x2 = x1, y2 = y1;
      switch (dir) {
        case 'U':
          y2 += dist;
          break;
        case 'D':
          y2 -= dist;
          break;
        case 'L':
          x2 -= dist;
          break;
        case 'R':
          x2 += dist;
          break;
      }
      segments.add(new LineSegment(x1, y1, x2, y2));
      x1 = x2;
      y1 = y2;
    }
    return segments;
  }

  static List<int[]> findIntersections(List<LineSegment> path1, List<LineSegment> path2) {
    ArrayList<int[]> intersections = new ArrayList<>();

    for (LineSegment segment1 : path1) {
      for (LineSegment segment2 : path2) {
        if (segment1.type != segment2.type) {
          int x = (segment1.type == 'H') ? segment2.x1 : segment1.x1;
          int y = (segment1.type == 'H') ? segment1.y1 : segment2.y1;

          if (segment1.type == 'H' && x >= Math.min(segment1.x1, segment1.x2)
              && x <= Math.max(segment1.x1, segment1.x2)
              && y >= Math.min(segment2.y1, segment2.y2)
              && y <= Math.max(segment2.y1, segment2.y2)) {
            intersections.add(new int[] { x, y });
          } else if (segment1.type == 'V' && x >= Math.min(segment2.x1, segment2.x2)
              && x <= Math.max(segment2.x1, segment2.x2)
              && y >= Math.min(segment1.y1, segment1.y2)
              && y <= Math.max(segment1.y1, segment1.y2)) {
            intersections.add(new int[] { x, y });
          }
        }
      }
    }

    return intersections;
  }

  static int manhattanDistance(int x, int y) {
    return Math.abs(x) + Math.abs(y);
  }

  static int getStepsForWire(int[] point, List<LineSegment> segments) {
    int steps = 0;
    int x1 = 0, y1 = 0;

    for (LineSegment segment : segments) {
      if (segment.type == 'H') {
        if (point[1] == segment.y1 && point[0] >= Math.min(segment.x1, segment.x2)
            && point[0] <= Math.max(segment.x1, segment.x2)) {
          steps += Math.abs(point[0] - x1);
          break;
        }
      } else {
        if (point[0] == segment.x1 && point[1] >= Math.min(segment.y1, segment.y2)
            && point[1] <= Math.max(segment.y1, segment.y2)) {
          steps += Math.abs(point[1] - y1);
          break;
        }
      }
      steps += Math.abs(segment.x2 - segment.x1) + Math.abs(segment.y2 - segment.y1);
      x1 = segment.x2;
      y1 = segment.y2;
    }

    return steps;
  }

  static Intersection getStepsForIntersection(int[] point, List<LineSegment> w1Segments, List<LineSegment> w2Segments) {
    int stepsForW1 = getStepsForWire(point, w1Segments);
    int stepsForW2 = getStepsForWire(point, w2Segments);

    return new Intersection(point[0], point[1], stepsForW1, stepsForW2);
  }

}
