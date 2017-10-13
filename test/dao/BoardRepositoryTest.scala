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

  "BoardRepository.findAll()" should "find all boards" in {
    val result = repository.findAll()
    val board = Some(Board(1, "title", "context", "admin"))

    result map { boards =>
      boards.size should equal(1)
      boards.head should equal(board.value)
    }
  }

  "BoardRepository.delete(index)" should "delete a board that include the parameter index" in {
    repository.delete(1L)

    val result = repository.findAll()

    result map { boards =>
      boards.size should equal(0)
    }
  }

  "BoardRepository.update(board)" should "change a board with the parameter board" in {
    val board = Board(1, "change title", "change context", "change admin")

    repository.update(board)

    val result = repository.findAll()

    result map { boards =>
      boards.size should equal(1)
      boards.last should equal(board)
    }
  }
}
