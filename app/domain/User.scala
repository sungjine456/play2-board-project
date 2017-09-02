package domain

class User(val id: String, val password: String, val name: String) {
  override def toString: String = "id : " + id + ", name : " + name
}