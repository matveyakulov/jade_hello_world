package org.example.b;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Objects;

public class BMain extends Agent {

  @Override
  public void setup() {
    try {
      System.out.println("Привет! агент " + getAID().getName() + " готов.");
      addBehaviour(new CyclicBehaviour() {
        @Override
        public void action() {
          ACLMessage msg = receive();
          if (Objects.nonNull(msg)) {
            System.out.println(" - " + myAgent.getLocalName() + " received: " + msg.getContent());
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent("Pong");
            send(reply);
          }
          block();
        }
      });
    } catch (Exception e) {
      doDelete();
    }
  }
}
