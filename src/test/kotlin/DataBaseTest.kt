import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DataBaseTest {

    @Test
    fun `should create and retrieve player`() {
        TestDataBase.withTestDataBase {
            val repository = GameRepository()
            val player = repository.createPlayer("Алексей")

            val found = repository.getPlayerByName("Алексей")
            assertNotNull(found)
            assertEquals("Алексей", found?.getName())
            assertEquals(1000, found?.getElo())
        }
    }

    @Test
    fun `should not create duplicate player`() {
        TestDataBase.withTestDataBase {
            val repository = GameRepository()
            repository.createPlayer("Алексей")

            assertThrows(IllegalArgumentException::class.java) {
                repository.createPlayer("Алексей")
            }
        }
    }

    @Test
    fun `should update player elo`() {
        TestDataBase.withTestDataBase {
            val repository = GameRepository()
            repository.createPlayer("Алексей")
            repository.updatePlayerElo("Алексей", 1200)

            val player = repository.getPlayerByName("Алексей")
            assertEquals(1200, player?.getElo())
        }
    }
}
