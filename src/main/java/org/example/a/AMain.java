package org.example.a;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

public class AMain extends Agent {

  @Override
  public void setup() {
    try {
      System.out.println("Привет! агент " + getAID().getName() + " готов.");
      addBehaviour(new CyclicBehaviour(this) { // Поведение агента исполняемое в цикле
        @Override
        public void action() {
          ACLMessage msg = receive();
          if (msg != null) {
            System.out.println(" – " + myAgent.getLocalName() + " received: " + msg.getContent());
          } //Вывод на экран локального имени агента и полученного сообщения     block();//Блокируем поведение, пока в очереди сообщений агента не появится хотя бы одно сообщение
        }
      });
      AMSAgentDescription[] agents = null;
      try {
        SearchConstraints c = new SearchConstraints();
        c.setMaxResults(-1L);
        agents = AMSService.search(this, new AMSAgentDescription(), c);
      } catch (Exception e) {
        System.out.println("Problem searching AMS: " + e);
        e.printStackTrace();
      }
      for (int i = 0; i < 4; i++) {
        AID agentID = agents[i].getName();
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(agentID); // id агента которому отправляем сообщение
        msg.setLanguage("English"); //Язык
        msg.setContent("Ping"); //Содержимое сообщения
        send(msg); //отправляем сообщение
      }
    } catch (Exception e) {
      doDelete();
      e.printStackTrace();
    }
  }
}
