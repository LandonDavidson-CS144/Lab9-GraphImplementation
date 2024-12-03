import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        int[] vertices = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i: vertices) {
            graph.addVertex(i);
        }
        for (int i = 0; i < vertices.length * 2; i++) {
            int end = (i + i / 10 + 1) % vertices.length;
            graph.addEdge(graph.nodes.get(i % vertices.length), graph.nodes.get(end));
        }
        graph.displayTraversals();

        System.out.println("Node 7 removed");
        graph.removeVertex(7);

        graph.displayTraversals();
    }
}



class Graph {
    static class Node {
        int value;
        ArrayList<Node> neighbors = new ArrayList<>();
        public Node(int value) {
            this.value = value;
        }
        public void addEdge(Node target) {
            if (neighbors.contains(target)) return;
            neighbors.add(target);
            target.neighbors.add(this);
        }
        public void removeEdge(Node target) {
            neighbors.remove(target);
            target.neighbors.remove(this);
        }
        public boolean isAdjacent(Node target) {
            return neighbors.contains(target);
        }
    }
    ArrayList<Node> nodes = new ArrayList<>();
    public void addVertex(int value) {
        nodes.add(new Node(value));
    }
    public void removeVertex(int value) {
        for (Node n: nodes) {
            if (n.value == value) {
                ArrayList<Node> neighborsCopy = new ArrayList<>(n.neighbors);
                for (Node neighbor: neighborsCopy) {
                    n.removeEdge(neighbor);
                }
                nodes.remove(n);
                return;
            }
        }
    }
    public void addEdge(Node start, Node end) {
        start.addEdge(end);
    }
    public void removeEdge(Node start, Node end) {
        start.removeEdge(end);
    }
    public boolean isAdjacent(Node start, Node end) {
        return start.isAdjacent(end);
    }
    public void breadthFirst(Node start) {
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(start);
        ArrayList<Node> previousNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node curNode = queue.pop();
            if (previousNodes.contains(curNode)) continue;
            queue.addAll(curNode.neighbors);
            previousNodes.add(curNode);
            System.out.println(curNode.value);
        }
    }
    public void depthFirst(Node start) {
        ArrayDeque<Node> stack = new ArrayDeque<>();
        stack.add(start);
        ArrayList<Node> previousNodes = new ArrayList<>();
        while (!stack.isEmpty()) {
            Node curNode = stack.pollLast();
            if (previousNodes.contains(curNode)) continue;
            stack.addAll(curNode.neighbors);
            previousNodes.add(curNode);
            System.out.println(curNode.value);
        }
    }
    public void displayTraversals() {
        System.out.println("Breadth First Traversal");
        breadthFirst(nodes.getFirst());

        System.out.println("Depth First Traversal");
        depthFirst(nodes.getFirst());
    }
}
