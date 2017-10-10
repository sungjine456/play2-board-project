package dao

import play.api.db.evolutions.Evolutions

import domain.Board
import org.scalatest.Matchers._
import org.scalatest.{ AsyncFlatSpec, BeforeAndAfter }

class BoardRepositoryTest extends AsyncFlatSpec with BeforeAndAfter with TestDatabaseSupport {

  val repository = new BoardRepository(dbConfig)

  before {
    Evolutions.applyEvolutions(database)
  }

  after {
    Evolutions.cleanupEvolutions(database)
  }

  "test the findAll method in BoardRepository" should "size of boards is one" in {
    val result = repository.findAll()
    val board = Some(Board(1, "title", "context", "admin"))

    result map { boards =>
      boards.size should equal(1)
      boards.head should equal(board.value)
    }
  }

  "test the delete method in BoardRepository" should "size of boards is zero" in {
    repository.delete(1L)

    val result = repository.findAll()

    result map { boards =>
      boards.size should equal(0)
    }
  }

  "test the update method in BoardRepository" should "the board has been changed" in {
    val board = Board(1, "change title", "change context", "change admin")

    repository.update(board)

    val result = repository.findAll()

    result map { boards =>
      boards.size should equal(1)
      boards.last should equal(board)
    }
  }
}
