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
    val headBoard = Some(Board(1, "title1", "context1", "admin"))
    val lastBoard = Some(Board(4, "title4", "context4", "admin"))

    result map { boards =>
      boards.size should equal(4)
      boards.head should equal(headBoard.value)
      boards.last should equal(lastBoard.value)
    }
  }

  "BoardRepository.delete(index)" should "delete a board that include the parameter index" in {
    val resultBeforeDeletion = repository.findAll()

    resultBeforeDeletion map { boards => boards.size should equal(4) }

    repository.delete(1L)

    val resultAfterDeletion = repository.findAll()

    resultAfterDeletion map { boards => boards.size should equal(3) }
  }

  "BoardRepository.update(board)" should "change a board with the parameter board" in {
    val updateBoard = Board(1, "change title", "change context", "change admin")

    val resultBeforeUpdating = repository.findAll()

    resultBeforeUpdating map { boards => boards.head should not equal updateBoard }

    repository.update(updateBoard)

    val resultAfterUpdating = repository.findAll()

    resultAfterUpdating map { boards => boards.head should equal(updateBoard) }
  }
}
