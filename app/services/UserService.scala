package services

import dao.UserRepository
import domain.User

class UserService {
  def findUser: User = {
    UserRepository.findAll
    new User("1","1","1")
  }
}
