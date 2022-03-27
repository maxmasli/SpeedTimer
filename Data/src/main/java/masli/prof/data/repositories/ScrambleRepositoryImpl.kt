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
            EventEnum.EventPyra -> {
                getRandomPyraScramble()
            }
            EventEnum.EventSkewb -> {
                getSkewbRandomScramble()
            }
            EventEnum.EventClock -> {
                getClockRandomScramble()
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

    private fun getRandomPyraScramble(): String {
        var res = ""
        val cornersCount = random.nextInt(5)
        var litCount = 0
        val buffer = mutableListOf<Int>()
        val lit = arrayOf(
            arrayOf("L", "L'"),
            arrayOf("R", "R'"),
            arrayOf("U", "U'"),
            arrayOf("B", "B'")
        )
        while (true) {
            val i = random.nextInt(4)
            val j = random.nextInt(2)
            val generateLit = lit[i][j]
            if (buffer.contains(i)) continue
            else {
                res += "$generateLit "
                buffer.clear()
                buffer.add(i)
                litCount++
                if (litCount >= 8) break
            }
        }

        val cornersLit = mutableListOf(
            arrayOf("l", "l'"),
            arrayOf("r", "r'"),
            arrayOf("b", "b'"),
            arrayOf("u", "u'")
        )

        for (count in 0 until cornersCount) {
            println(cornersLit.size)
            val i = random.nextInt(cornersLit.size)
            val j = random.nextInt(2)
            val generateLit = cornersLit[i][j]
            res += "$generateLit "
            litCount++
            cornersLit.removeAt(i)
        }

        return res
    }

    private fun getSkewbRandomScramble(): String {
        val letterSet = listOf("R ", "R' ", "U ", "U' ", "B ", "B' ", "L ", "L' ")
        var newScramble = ""
        var iterator = 0
        var randomNumber = 0
        var tempRandom = 0
        while (iterator < 11) {
            if (randomNumber % 2 == 0) {
                if (tempRandom == randomNumber || tempRandom == randomNumber + 1) {
                    tempRandom = random.nextInt(8) // [0, 7]
                    continue
                }
            }
            if (randomNumber % 2 == 1) {
                if (tempRandom == randomNumber || tempRandom == randomNumber - 1) {
                    tempRandom = random.nextInt(8)
                    continue
                }
            }
            randomNumber = tempRandom
            newScramble += letterSet[randomNumber]
            iterator++
        }
        return newScramble
    }

    private fun getClockRandomScramble(): String {
        val scramble = arrayListOf("UR", "num", "sign", "DR", "num", "sign", "DL", "num", "sign", "UL", "num", "sign",  "U",
            "num", "sign", "R", "num", "sign", "D", "num", "sign", "L", "num", "sign", "ALL", "num", "sign", "y2 ",
            "U", "num", "sign", "R", "num", "sign", "D", "num", "sign", "L", "num", "sign", "ALL", "num", "sign", "", "", "", "", "")
        val letterNum = listOf("0", "1", "2", "3", "4", "5", "6")
        val letterSign = listOf("+", "-")
        val lastFour = listOf("UR ", "DR ", "DL ", "UL ")
        for (i in 0..26 step 3) {
            scramble[i + 1] = letterNum[random.nextInt(7)]
            scramble[i + 2] = letterSign[random.nextInt(2)]
        }
        for (i in 28..40 step 3) {
            scramble[i + 1] = letterNum[random.nextInt(7)]
            scramble[i + 2] = letterSign[random.nextInt(2)]
        }
        for (i in 44..47) {
            if (random.nextInt(2) == 1) {
                scramble[i] = lastFour[i - 44]
            }
        }
        var scrambleString = ""
        for (j in scramble) {
            scrambleString += if (j in letterSign) "$j "
            else j
        }
        return scrambleString
    }
}
