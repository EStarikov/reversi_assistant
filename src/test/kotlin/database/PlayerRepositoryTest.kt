package database// test/PlayerRepositoryTest.kt
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import player.Player


class PlayerRepositoryTest {

    private lateinit var playerRepo: GenericRepository<Player>

    @BeforeEach
    fun setup() {
        TestDatabaseFactory.init()
        playerRepo = GenericRepository(table = Players,
            toEntity = { row -> row.toPlayerEntity() },
            toInsertMap = { player -> player.toPlayersInsertMap() }
        )
    }

    @AfterEach
    fun cleanup() {
        TestDatabaseFactory.clearAndRecreate()
    }

    @Test
    fun `saveItem should save and retrieve player`() {
        val player = Player(name = "Alice", elo = 1500.0)

        playerRepo.saveItem(player)
        val retrieved = playerRepo.getAll()

        assertEquals(1, retrieved.size)
        assertEquals("Alice", retrieved[0].getName())
        assertEquals(1500.0, retrieved[0].getElo())
    }

    @Test
    fun `getById should return correct player`() {

        val player = Player(name = "Bob", elo = 1200.0)
        playerRepo.saveItem(player)

        val retrieved = playerRepo.getById(1)

        assertNotNull(retrieved)
        assertEquals("Bob", retrieved?.getName())
    }

    @Test
    fun `many players`() {
        playerRepo.saveItem(Player(name = "Alice", elo = 1500.0))
        playerRepo.saveItem(Player(name = "Bob", elo = 1200.0))
        playerRepo.saveItem(Player(name = "Sam", elo = 1300.0))

        val retrieved = playerRepo.getAll()
        assertEquals(3, retrieved.size)
        assertEquals("Alice", retrieved[0].getName())
        assertEquals(1500.0, retrieved[0].getElo())
        assertEquals("Bob", retrieved[1].getName())
        assertEquals(1200.0, retrieved[1].getElo())
        assertEquals("Sam", retrieved[2].getName())
        assertEquals(1300.0, retrieved[2].getElo())

        assertEquals("Bob", playerRepo.getById(2)?.getName())
    }

    @Test
    fun `update elo`() {
        playerRepo.saveItem(Player(name = "Alice", elo = 1500.0))
        playerRepo.updateItem(1, 1200.0)

        val retrieved = playerRepo.getAll()

        assertEquals(1, retrieved.size)
        assertEquals("Alice", retrieved[0].getName())
        assertEquals(1200.0, retrieved[0].getElo())
    }
}