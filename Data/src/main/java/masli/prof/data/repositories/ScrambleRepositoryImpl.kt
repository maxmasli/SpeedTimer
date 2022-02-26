package masli.prof.data.repositories

import masli.prof.domain.enums.EventEnum
import masli.prof.domain.repositories.ScrambleRepository
import java.util.*

class ScrambleRepositoryImpl : ScrambleRepository {

    private val random = Random()
    var currentSeed = random.nextInt().toLong()

    init {
        setSeed(currentSeed)
    }

    override fun getScramble(event: EventEnum): String {
        return when (event) {
            EventEnum.Event3by3 -> {
                getRandom3by3Scramble()
            }
            EventEnum.Event2by2 -> {
                getRandom2by2Scramble()
            }
        }
    }

    override fun setSeed(seed: Long) {
        currentSeed = seed
        random.setSeed(seed)
    }

    override fun getSeed(): Long {
        return currentSeed
    }

    private fun getRandom3by3Scramble(): String {
        val lit = arrayOf(
            arrayOf("F", "F'", "F2"),
            arrayOf("B", "B'", "B2"),
            arrayOf("U", "U'", "U2"),
            arrayOf("D", "D'", "D2"),
            arrayOf("R", "R'", "R2"),
            arrayOf("L", "L'", "L2")
        )

        var res = ""
        val scr: MutableList<String> = ArrayList()
        val banned: MutableList<Int> = ArrayList()
        while (true) {
            val i = random.nextInt(6)// [0, 5]
            val j = random.nextInt(3) // [0, 2]
            if (scr.size > 2 && scr[scr.size - 2] === lit[i][j] || banned.contains(i)) {
                continue
            }
            scr.add(lit[i][j])
            if (i % 2 != 0 && banned.contains(i - 1) || i % 2 == 0 && banned.contains(i + 1)) {
                banned.add(i)
            } else {
                banned.clear()
                banned.add(i)
            }
            if (scr.size >= 20) {
                break
            }
        }

        for (l in scr) {
            res += "$l "
        }
        return res
    }

    private fun getRandom2by2Scramble(): String {
        val scr: MutableList<String> = ArrayList()
        var temp = -1
        var res = ""
        val lit =
            arrayOf(arrayOf("R", "R'", "R2"), arrayOf("F", "F'", "F2"), arrayOf("U", "U'", "U2"))
        while (true) {
            val i = random.nextInt(3)
            val j = random.nextInt(3)
            if (temp == i) {
                continue
            } else {
                temp = i
                scr.add(lit[i][j])
            }
            if (scr.size > 9) {
                break
            }
        }
        for (l in scr) {
            res += "$l "
        }
        return res
    }
}