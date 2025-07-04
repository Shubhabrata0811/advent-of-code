import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RelationCounter {

  public static void main(String[] args) {
    String fileName = "input.txt";
    RelationCounter counter = new RelationCounter();
    try {
      Map<String, String> childToParent = counter.parseInputFile(fileName);
      Set<String> allNodes = counter.getAllNodes(childToParent);
      int totalRelations = counter.calculateTotalRelations(allNodes, childToParent);
      System.out.println("Total direct + indirect relations: " + totalRelations);
    } catch (IOException e) {
      System.err.println("Error reading the input file: " + e.getMessage());
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

  public Set<String> getAllNodes(Map<String, String> childToParent) {
    Set<String> nodes = new HashSet<>();
    nodes.addAll(childToParent.keySet());
    nodes.addAll(childToParent.values());
    return nodes;
  }

  public int calculateTotalRelations(Set<String> nodes, Map<String, String> childToParent) {
    int total = 0;

    for (String node : nodes) {
      int direct = childToParent.containsKey(node) ? 1 : 0;
      int indirect = 0;

      String current = childToParent.get(node);

      if (current != null) {
        current = childToParent.get(current);
      }

      while (current != null) {
        indirect++;
        current = childToParent.get(current);
      }

      total += direct + indirect;
    }

    return total;
  }
}
