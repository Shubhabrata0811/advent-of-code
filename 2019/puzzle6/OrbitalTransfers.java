import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OrbitalTransfers {

  public static void main(String[] args) {
    String fileName = "input.txt";
    String node1 = "YOU";
    String node2 = "SAN";

    OrbitalTransfers ot = new OrbitalTransfers();
    try {
      Map<String, String> childToParent = ot.parseInputFile(fileName);
      Map<String, List<String>> graph = ot.buildBidirectionalGraph(childToParent);

      String start = childToParent.get(node1);
      String end = childToParent.get(node2);

      int transfers = ot.findMinimumTransfers(graph, start, end);
      System.out.println("Minimum transfers from parent of " + node1 + " to parent of " + node2 + ": " + transfers);

    } catch (IOException e) {
      System.err.println("Error reading input file: " + e.getMessage());
    }
  }

  public Map<String, String> parseInputFile(String fileName) throws IOException {
    Map<String, String> childToParent = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty()) {
          String[] parts = line.split("\\)");
          if (parts.length == 2) {
            String parent = parts[0];
            String child = parts[1];
            childToParent.put(child, parent);
          }
        }
      }
    }
    return childToParent;
  }

  public Map<String, List<String>> buildBidirectionalGraph(Map<String, String> childToParent) {
    Map<String, List<String>> graph = new HashMap<>();

    for (Map.Entry<String, String> entry : childToParent.entrySet()) {
      String child = entry.getKey();
      String parent = entry.getValue();

     
      graph.computeIfAbsent(child, k -> new ArrayList<>()).add(parent);
     
      graph.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
    }

    return graph;
  }

  public int findMinimumTransfers(Map<String, List<String>> graph, String start, String end) {
    Set<String> visited = new HashSet<>();
    Queue<NodeLevel> queue = new LinkedList<>();
    queue.add(new NodeLevel(start, 0));

    while (!queue.isEmpty()) {
      NodeLevel current = queue.poll();
      if (current.node.equals(end)) {
        return current.level;
      }

      visited.add(current.node);

      for (String neighbor : graph.getOrDefault(current.node, new ArrayList<>())) {
        if (!visited.contains(neighbor)) {
          queue.add(new NodeLevel(neighbor, current.level + 1));
        }
      }
    }

    return -1;
  }

  static class NodeLevel {
    String node;
    int level;

    NodeLevel(String node, int level) {
      this.node = node;
      this.level = level;
    }
  }
}
