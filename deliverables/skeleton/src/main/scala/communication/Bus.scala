trait Bus{  
  def disconnect
  def send (message :Msg)
  def recv :Msg
}
