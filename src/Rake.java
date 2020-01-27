import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Rake
{

    public static void main(String[] args)throws IOException
    {
        Scanner scanner = new Scanner(new File("rake.dat"));
        int j = 1;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.equals("-1")){
                break;
            }
            //System.out.println("input: " + line );

            Scanner lineReader = new Scanner(line);

            LinkedList<Node> stack = new LinkedList<>();
            Node root = new Node(lineReader.nextInt());
            Node currentNode = root;
            stack.addLast(currentNode);
            while(lineReader.hasNextInt()){

                int currentInt = lineReader.nextInt();
                currentNode = stack.removeLast();
                //System.out.println(currentInt);
                if(currentNode.leftHasBeenInitialized){
                    if(currentInt == -1){
                        currentNode.right = null;
                        currentNode.rightHasBeenInitialized = true;
                    }
                    else {
                        currentNode.right = new Node(currentInt);
                        currentNode.rightHasBeenInitialized = true;
                        stack.addLast(currentNode.right);
                    }
                }
                else {
                    if (currentInt == -1) {
                        currentNode.left = null;
                        currentNode.leftHasBeenInitialized = true;
                        stack.addLast(currentNode);
                    } else {
                        currentNode.left = new Node(currentInt);
                        currentNode.leftHasBeenInitialized = true;
                        stack.addLast(currentNode);
                        stack.addLast(currentNode.left);
                    }
                }
            }

            LinkedList<Node> nodeStack = new LinkedList<>();
            LinkedList<Integer> valueStack = new LinkedList<>();
            HashMap<Integer, Integer> piles = new HashMap<>();
            int lowestValue = 0;
            int highestValue = 0;
            nodeStack.addLast(root);
            valueStack.addLast(0);
            while(!nodeStack.isEmpty()){
                Node curNode = nodeStack.removeLast();
                int curVal = valueStack.removeLast();
                if(curNode == null){
                    continue;
                }

                if(curVal < lowestValue){
                    lowestValue = curVal;
                }
                if(curVal > highestValue){
                    highestValue = curVal;
                }
                if (piles.containsKey(curVal)){
                    piles.put(curVal, (piles.get(curVal) + curNode.value));
                }
                else {
                    piles.put(curVal, curNode.value);
                }
                //System.out.println(curVal + ", " + curNode.value);

                valueStack.addLast(curVal-1);
                nodeStack.addLast(curNode.left);
                valueStack.addLast(curVal+1);
                nodeStack.addLast(curNode.right);

            }
            System.out.println("Case: "+ j);
            for (int i = lowestValue; i <= highestValue; i++) {
                System.out.print(piles.get(i) + " ");
            }
            System.out.println();
            System.out.println();
            j++;


        }
    }


    private static class Node
    {
        private Integer value;
        public Node left;
        public boolean leftHasBeenInitialized;
        public Node right;
        public boolean rightHasBeenInitialized;

        public Node(Integer value){
            this.value = value;
            left = null;
            right = null;
        }

        public boolean hasLeft(){
            return left != null;
        }
        public boolean hasRight(){
            return right != null;
        }

        @Override
        public String toString() {
            if (this == null) {
                return "";
            }
            String s = "";
            ArrayList<ArrayList<Node>> lists = new ArrayList<>();
            lists.add(new ArrayList<Node>());
            lists.get(0).add(this);
            int index = 0;
            boolean breakout = false;
            while (!breakout) {

                ArrayList<Node> current = lists.get(index);
                ArrayList<Node> nodeList = new ArrayList<>();
                breakout = true;
                for (int i = 0; i < current.size(); i++){
                    Node currentNode = current.get(i);
                    if(currentNode == null){
                        s += "n ";
                        continue;
                    }

                    if(currentNode.left != null || currentNode.right != null){
                        breakout = false;
                    }

                    nodeList.add(currentNode.left);
                    nodeList.add(currentNode.right);
                    s += currentNode.value + " ";
                }
                index ++;
                s += "\n";
                lists.add(nodeList);
            }
            return s;
        }


    }
}
