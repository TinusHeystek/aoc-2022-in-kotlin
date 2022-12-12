package extensions.grid

open class PathNode(point: Point) : Node(point) {

    private var gCost: Int = Int.MAX_VALUE// Walking Cost from the Start Node
    private var hCost: Int = 0 // Heuristic Cost to reach End Node
    private var cameFromPathNode: PathNode? = null

    fun getGCost() : Int = gCost
    fun getHCost() : Int = hCost
    fun getFCost() : Int = gCost + hCost

    fun getCameFromPathNode() = cameFromPathNode

    fun setGCost(gCostVal: Int) { gCost = gCostVal }
    fun setHCost(hCostVal: Int) { hCost = hCostVal }
    fun setCameFromPathNode(pathNode: PathNode) { cameFromPathNode = pathNode }

    init {
        initialize()
    }

    fun initialize() {
        gCost = Int.MAX_VALUE
        hCost = 0
        cameFromPathNode = null
    }

    override fun toString(): String = point.toString()
}