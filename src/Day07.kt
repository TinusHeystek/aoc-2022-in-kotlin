class Day07 : Day(7) {

    data class FileInfo(val size: Long, val name: String)

    data class Directory(val parent: Directory?, val name: String) {
        val directories = mutableListOf<Directory>()
        private val files = mutableListOf<FileInfo>()

        fun addDirectory(directoryName: String) {
            val directory = Directory(this, directoryName)
            directories.add(directory)
        }

        fun getDirectory(directoryName: String) : Directory {
            return directories.single { it.name == directoryName }
        }

        fun addFile(fileSize: String, fileName: String) {
            val file = FileInfo(fileSize.toLong(), fileName)
            files.add(file)
        }

        fun getDirectorySize() : Long {
            var size: Long = 0
            size += files.sumOf { it.size }
            size += directories.sumOf { it.getDirectorySize() }
            return size
        }
    }

    private fun parseFileSystem(input: String) : Directory {
        val root = Directory(null, "/")
        var currentDirectory = root
        val lines = input.lines()

        for (line in lines) {
            val split = line.split(" ")
            when (split[0]) {
                "\$" -> when (split[1]) {
                    "cd" -> when (split[2]) {
                        "/" -> currentDirectory = root
                        ".." -> { if (currentDirectory.parent != null)
                            currentDirectory = currentDirectory.parent!! }
                        else -> currentDirectory = currentDirectory.getDirectory(split[2])
                        }
                    "ls" -> { /*list*/ }
                    }
                "dir" -> currentDirectory.addDirectory(split[1])
                else -> {
                    currentDirectory.addFile(split[0], split[1])
                }
            }
        }

        return root
    }

    // --- Part 1 ---

    private fun getDirectoryWithSizeLessThan(directory: Directory, limit: Long) : Long {
        var totalSize : Long = 0
        val size = directory.getDirectorySize()
        if (size <= limit)
            totalSize += size
        for (childDir in directory.directories) {
            totalSize += getDirectoryWithSizeLessThan(childDir, limit)
        }
        return totalSize
    }

    override fun part1ToString(input: String): String {
        val root = parseFileSystem(input)
        val totalSize = getDirectoryWithSizeLessThan(root, 100_000)
        return totalSize.toString()
    }

    private fun getDirectoryWithSizeLargerThan(directory: Directory, required: Long) : MutableList<Long> {
        val validSizes = mutableListOf<Long>()
        val size = directory.getDirectorySize()
        if (size >= required)
            validSizes.add(size)
        for (childDir in directory.directories) {
            validSizes.addAll(getDirectoryWithSizeLargerThan(childDir, required))
        }
        return validSizes
    }

    // --- Part 2 ---

    override fun part2ToString(input: String): String {
        val root = parseFileSystem(input)
        val deviceTotalSize = 70_000_000
        val requiredFreeSpace = 30_000_000
        val currentFreeSpace = deviceTotalSize - root.getDirectorySize()
        val spaceToCleanUp = requiredFreeSpace - currentFreeSpace

        val sizes = getDirectoryWithSizeLargerThan(root, spaceToCleanUp)

        return sizes.min().toString()
    }
}

fun main() {
    val day = Day07()
    day.printToStringResults("95437", "24933642")
}
