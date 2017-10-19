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

  "BoardRepository.findByIndex(index)" should "find a board that include the parameter index" in {
    val result = repository.findByIndex(2L)
    val board = Some(Board(2, "title2", "context2", "admin"))

    result map { b => b should equal(board) }
  }

  it should "return a none if board is not found" in {
    val result = repository.findByIndex(5L)

    result map { b => b should equal(None) }
  }

  "BoardRepository.delete(index)" should "delete a board that include the parameter index" in {
    val resultBeforeDeletionAll = repository.findAll()
    val resultBeforeDeletionFoundOne = repository.findByIndex(1L)

    resultBeforeDeletionAll map { boards => boards.size should equal(4) }
    resultBeforeDeletionFoundOne map { board => board should not equal None }

    repository.delete(1L)

    val resultAfterDeletionAll = repository.findAll()
    val resultAfterDeletionFoundOne = repository.findByIndex(1L)

    resultAfterDeletionAll map { boards => boards.size should equal(3) }
    resultAfterDeletionFoundOne map { board => board should equal(None) }
  }

  "BoardRepository.update(board)" should "change a board with the parameter board" in {
    val updateBoard = Some(Board(1L, "change title", "change context", "change admin"))

    val resultBeforeUpdating = repository.findByIndex(1L)

    resultBeforeUpdating map { board => board should not equal updateBoard }

    repository.update(updateBoard.value)

    val resultAfterUpdating = repository.findByIndex(1L)

    resultAfterUpdating map { board => board should equal(updateBoard) }
  }

  "BoardRepository.insert(board)" should "add the parameter board with increasing index" in {
    val resultBeforeInsert = repository.findAll()

    resultBeforeInsert map { boards => boards.size should equal(4) }

    val board = Board(0, "testT", "testC", "testW")
    repository.insert(board)

    val allResultsAfterInsert = repository.findAll()
    val resultAfterInsert = repository.findByIndex(5L)

    allResultsAfterInsert map { boards => boards.size should equal(5) }
    resultAfterInsert map { b =>
      b.get.title should equal(board.title)
      b.get.context should equal(board.context)
      b.get.writer should equal(board.writer)
    }
  }
}
