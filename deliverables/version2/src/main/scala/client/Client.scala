package client

import akka.actor.{ ActorSystem, Props }
import client.com.NetClientFactory
import client.com.NetClientFactory.NetClient
import client.io.input.InputEngine
import client.supervisor.Supervisor

abstract class Client (val net : NetClient) {

  type I <: InputEngine

  val system = ActorSystem("client")

  val supervisor = system.actorOf(Props(new Supervisor (net)), "supervisor")

  val inputEngine : I

  def run : Unit
}

import client.io.input.terminal.TerminalInputEngine

object Client extends Client(NetClientFactory ()) {

  type I = TerminalInputEngine

  val inputEngine = new TerminalInputEngine(supervisor ! _)

  def run = inputEngine run
}
