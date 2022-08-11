package java.com.johny.solver.core;

import com.johny.solver.core.SolvingState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SolvingStateCloneTest {

    @Test
    void name() {
        SolvingState begin = new SolvingState();
        begin.setPosX(0);

        begin.setBasket(new ArrayList<>());
        String[][] nodeMap = new String[1][1];
        nodeMap[0][0] = "ES";
        begin.setNodeMap(nodeMap);

        begin.getBasket().add("AP");
        //test
        SolvingState begin2 = begin.clone();


        begin.getBasket().add("AP");
        printState("first", begin);
        printState("second", begin2);
        begin.getNodeMap()[0][0] = "ES";
        begin.setPosX(1);
        begin.getBasket().set(0,"BD");
        printState("first after change", begin);
        printState("second after change", begin2);
    }

    private void printState (String prefix, SolvingState toPrint){
        System.out.println(prefix + ", X:      " + toPrint.getPosX());
        System.out.println(prefix + ", nodemap:" + toPrint.getNodeMap()[0][0]);
        System.out.println(prefix + ", basket :" + toPrint.getBasket().get(0));
    }
}
