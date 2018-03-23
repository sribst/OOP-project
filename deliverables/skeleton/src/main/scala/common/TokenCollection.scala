implicit def listToTokenCollection(l:List[Token]) = new
  TokenCollection(l)
class TokenCollection(tokenList: List[Token])
